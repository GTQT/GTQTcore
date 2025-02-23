package keqing.gtqtcore.common.metatileentities.multi.generators.Wind;
import keqing.gtqtcore.api.utils.GTQTDateHelper;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.behaviors.AbstractMaterialPartBehavior;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.client.objmodels.ObjModels;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.behaviors.WindRotorBehavior;
import keqing.gtqtcore.api.metaileentity.MetaTileEntityBaseWithControl;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VN;

public class MetaTileEntityWindGenerator extends MetaTileEntityBaseWithControl  implements IFastRenderMetaTileEntity {
    private final ItemStackHandler containerInventory;
    long height;
    long baseOutput;
    long TotalTick;
    long workTime;
    // 改进天气判断逻辑
    int weather;
    public MetaTileEntityWindGenerator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.containerInventory = new GTItemStackHandler(this, 1);
    }
    @Override
    public void onRemoval() {
        super.onRemoval();
        for (int i = 0; i < containerInventory.getSlots(); i++) {
            var pos = getPos();
            if(!containerInventory.getStackInSlot(i).isEmpty())
            {
                getWorld().spawnEntity(new EntityItem(getWorld(),pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,containerInventory.getStackInSlot(i)));
                containerInventory.extractItem(i,1,false);
            }

        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        return super.writeToNBT(data);
    }
    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 209)
                .bindPlayerInventory(entityPlayer.inventory, 126)
                .widget(new SlotWidget(this.containerInventory,0, 88-9,30,true,true,true)
                        .setBackgroundTexture(GuiTextures.SLOT)
                        .setChangeListener(this::markDirty)
                        .setTooltipText("请放入风电扇叶"))
                .widget(new ImageWidget(88-9, 48, 18, 6, GuiTextures.BUTTON_POWER_DETAIL))
                .widget((new AdvancedTextWidget(7, 68, this::addDisplayText, 2302755)).setMaxWidthLimit(181));;
        return builder.build(this.getHolder(),entityPlayer);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("输出功率: " + getOutputEu(baseOutput, weather) + " " + VN[GTUtility.getTierByVoltage(getOutputEu(baseOutput, weather))]));
        textList.add(new TextComponentString("折损速率: " + getDamage()));
        textList.add(new TextComponentString("已经工作: " + GTQTDateHelper.getTimeFromTicks(workTime)));
        textList.add(new TextComponentString("距离损坏: " + GTQTDateHelper.getTimeFromTicks(TotalTick-workTime)));
    }
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
    }
    @Override
    protected void updateFormedValid() {
        if (this.frontFacing == EnumFacing.UP||this.frontFacing == EnumFacing.DOWN) return;


        // 确保槽位存在且不为空
        ItemStack stack = containerInventory.getStackInSlot(0);
        if (stack.isEmpty()) {
            baseOutput = 0;
            workTime=0;
            TotalTick=0;
            return;
        }


        // 确保行为对象不为 null
        WindRotorBehavior behavior = getWindRotorBehavior();
        if (behavior == null) {
            baseOutput = 0;
            workTime=0;
            TotalTick=0;
            return;
        }

        if (isItemValid(stack)) {
            behavior.applyDamage(containerInventory.getStackInSlot(0), getDamage());

            workTime=AbstractMaterialPartBehavior.getPartDamage(containerInventory.getStackInSlot(0))/getDamage();
            TotalTick=behavior.getPartMaxDurability(containerInventory.getStackInSlot(0))/getDamage();

            baseOutput = V[behavior.getTier()-1] * (10 - behavior.getTier()) / 10;
            // 改进天气判断逻辑
            weather = getWeatherFactor();

            this.energyContainer.addEnergy(getOutputEu(baseOutput, weather));
        }
        else {
            baseOutput = 0;
            workTime=0;
            TotalTick=0;
        }
    }

    private int getWeatherFactor() {
        // 考虑更多天气情况
        if (getWorld().isRaining()) {
            return 2;
        } else if (getWorld().isThundering()) {
            return 3; // 雷暴天气
        } else {
            return 1; // 正常天气
        }
    }

    private long getOutputEu(long baseOutput, int weather) {
        return baseOutput * height * weather;
    }

    public int getDamage()
    {
        return getWeatherFactor();
    }
    @Nullable
    private WindRotorBehavior getWindRotorBehavior() {
        ItemStack stack = containerInventory.getStackInSlot(0);
        if (stack.isEmpty()) return null;

        return WindRotorBehavior.getInstanceFor(stack);
    }

    public boolean isItemValid(@Nonnull ItemStack stack) {
        return WindRotorBehavior.getInstanceFor(stack) != null;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        height=getWorld().getHeight()/100;
    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("EEE", "YYY","HEH","HEH","HEH","HEH","HEH","HEH","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","HEH","EEE","EEE")
                .aisle("EEE", "YYY","EEE","EEE","EEE","EEE","EEE","EEE"," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E "," E ","EEE","EEE","EEE")
                .aisle("ESE", "YYY","HEH","HEH","HEH","HEH","HEH","HEH","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","H H","HEH","EEE","EEE")
                .where('S', selfPredicate())
                .where('E',states(getCasingState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('Y', states(getCasingState()))
                .where('H', frames(Materials.Steel))
                .where(' ', any())
                .build();
    }
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_ROCKET_ENGINE_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityWindGenerator(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        IFastRenderMetaTileEntity.super.renderMetaTileEntity(x, y, z, partialTicks);

        if(isStructureFormed()&& GTQTCoreConfig.OBJRenderSwitch.EnableObj&& GTQTCoreConfig.OBJRenderSwitch.EnableObjSWind)
        {
            final int xDir = this.getFrontFacing().getOpposite().getXOffset();
            final int zDir = this.getFrontFacing().getOpposite().getZOffset();
            //机器开启才会进行渲染
            //这是一些opengl的操作,GlStateManager是mc自身封装的一部分方法  前四条详情请看 https://turou.fun/minecraft/legacy-render-tutor/
            //opengl方法一般需要成对出现，实际上他是一个状态机，改装状态后要还原  一般情况按照我这些去写就OK
            GlStateManager.pushAttrib(); //保存变换前的位置和角度
            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.disableCull();
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(ObjModels.WindRotor_pic); //自带的材质绑定 需要传递一个ResourceLocation
            GlStateManager.translate(x, y, z);//translate是移动方法 这个移动到xyz是默认的 不要动

            GlStateManager.translate(xDir * 3 + 0.5, 31, zDir * 3 + 0.5);

            float angle = (System.currentTimeMillis() % 3600) / 10.0f; //我写的随时间变化旋转的角度

            if (this.frontFacing == EnumFacing.WEST) {
                GlStateManager.rotate(-90, 0F, 1F, 0F);
            } else if (this.frontFacing == EnumFacing.EAST) {
                GlStateManager.rotate(90, 0F, 1F, 0F);
            } else if (this.frontFacing == EnumFacing.NORTH) {
                GlStateManager.rotate(180, 0F, 1F, 0F);
            }

            GlStateManager.rotate(angle, 0F, 0F, 1F);//我让dna围绕z轴旋转，角度是实时变化的

            GlStateManager.scale(3, 3, 3);
            // ObjModels.Tree_Model.renderAllWithMtl(); //这个是模型加载器的渲染方法  这是带MTL的加载方式
            ObjModels.WindRotor.renderAll(); //这个是模型加载器的渲染方法  这是不带MTL的加载方式
            GlStateManager.popMatrix();//读取变换前的位置和角度(恢复原状) 下面都是还原状态机的语句
            GlStateManager.enableLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableCull();
        }

    }
    //渲染模型的位置
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        //这个影响模型的可视范围，正常方块都是 1 1 1，长宽高各为1，当这个方块离线玩家视线后，obj模型渲染会停止，所以可以适当放大这个大小能让模型有更多角度的可视
        return new AxisAlignedBB(this.getPos().add(-50,-50,-50),this.getPos().add(50,50,50));
    }
    public boolean isGlobalRenderer() {
        return true;
    }
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,true);
    }
}
