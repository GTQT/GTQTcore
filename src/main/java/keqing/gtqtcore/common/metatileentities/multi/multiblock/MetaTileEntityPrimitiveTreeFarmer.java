package keqing.gtqtcore.common.metatileentities.multi.multiblock;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.wood.BlockGregPlanks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.GTQTCoreConfig;
import keqing.gtqtcore.client.objmodels.ObjModels;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityPrimitiveTreeFarmer extends MultiblockWithDisplayBase  implements IFastRenderMetaTileEntity {

    private IItemHandlerModifiable logOut;
    private IItemHandlerModifiable saplingOut;
    private IItemHandlerModifiable itemIn;

    public MetaTileEntityPrimitiveTreeFarmer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        logOut = new NotifiableItemStackHandler(this, 1,this,true);
        saplingOut = new NotifiableItemStackHandler(this,1, this, true);
        itemIn = new NotifiableItemStackHandler(this,1, this, true);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityPrimitiveTreeFarmer(metaTileEntityId);
    }

    int time;
    boolean work;
    int saplingsIn;
    int numSaplingsInOutput;

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("time", time);
        data.setInteger("saplingsIn", saplingsIn);
        data.setInteger("numSaplingsInOutput", numSaplingsInOutput);
        data.setBoolean("work",work);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        time = data.getInteger("time");
        saplingsIn = data.getInteger("saplingsIn");
        numSaplingsInOutput = data.getInteger("numSaplingsInOutput");
        work= data.getBoolean("work");
    }



    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("生成耗时：%s / %s", time,saplingsIn*200));
        textList.add(new TextComponentTranslation("输出原木：%s / 树苗：%s", saplingsIn,saplingsIn * 2));
    }


    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote && isStructureFormed())
        {

            if (!work&&itemIn.getStackInSlot(0).isItemEqual(new ItemStack(Blocks.SAPLING)) && logOut.getStackInSlot(0).isEmpty() && (saplingOut.getStackInSlot(0).isEmpty() || saplingOut.getStackInSlot(0).isItemEqual(new ItemStack(Blocks.SAPLING)))) {
                saplingsIn = itemIn.getStackInSlot(0).getCount();
                numSaplingsInOutput = saplingOut.getStackInSlot(0).getCount();
                itemIn.setStackInSlot(0, new ItemStack(Blocks.AIR));

                work = true;
            }
            if(work)
            {
                time++;
                if(time==saplingsIn*200) {
                    logOut.setStackInSlot(0, new ItemStack(Blocks.LOG, saplingsIn));
                    if (numSaplingsInOutput + (saplingsIn * 2) <= 64) {
                        saplingOut.insertItem(0, new ItemStack(Blocks.SAPLING, saplingsIn * 2), false);
                    } else {
                        saplingOut.setStackInSlot(0, new ItemStack(Blocks.SAPLING, 64));
                    }

                    work=false;
                    time=0;
                    saplingsIn=0;
                    numSaplingsInOutput=0;
                }
            }

        }
    }


    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    private void initializeAbilities() {
        this.logOut = getAbilities(MultiblockAbility.EXPORT_ITEMS).get(0);
        this.saplingOut = getAbilities(MultiblockAbility.EXPORT_ITEMS).get(1);
        this.itemIn = getAbilities(MultiblockAbility.IMPORT_ITEMS).get(0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.BACK, RelativeDirection.UP)

                .aisle( "BBBBBBBBBBBB",
                        "BDDDDDDBBWBB",
                        "BDDDDDDBBWBB",
                        "BDDDDDDBBWBB",
                        "BBBSBBBBBBBB")

                .aisle( "            ",
                        "        IFO ",
                        "        I O ",
                        "        IFO ",
                        "            ")

                .aisle( "            ",
                        "        IFO ",
                        "        I O ",
                        "        IFO ",
                        "            ")

                .aisle( "       WWWWW",
                        "       WWWWW",
                        "       WWWWW",
                        "       WWWWW",
                        "       WWWWW")

                .where('S', selfPredicate())
                .where('B', states(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.BRICK)))
                .where('D', states(Blocks.DIRT.getDefaultState()))
                .where('W', states(MetaBlocks.PLANKS.getState(BlockGregPlanks.BlockType.TREATED_PLANK)))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.TreatedWood).getBlock(Materials.TreatedWood)))
                .where('O', states(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.BRICK))
                        .or(metaTileEntities(MetaTileEntities.ITEM_EXPORT_BUS[0]).setMaxGlobalLimited(2).setMinGlobalLimited(2)))
                .where('I', states(GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.BRICK))
                        .or(metaTileEntities(MetaTileEntities.ITEM_IMPORT_BUS[0]).setMaxGlobalLimited(1).setMinGlobalLimited(1)))
                .where(' ', any())
                .build();
    }
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.BRICK;
    }
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.BLOWER_OVERLAY;
    }    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        IFastRenderMetaTileEntity.super.renderMetaTileEntity(x, y, z, partialTicks);

        if(isStructureFormed()&& GTQTCoreConfig.OBJRenderSwitch.EnableObj && GTQTCoreConfig.OBJRenderSwitch.EnableObjPrimitiveTreeFarmer)
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
            FMLClientHandler.instance().getClient().getTextureManager().bindTexture(ObjModels.Tree_pic); //自带的材质绑定 需要传递一个ResourceLocation
            GlStateManager.translate(x, y, z);//translate是移动方法 这个移动到xyz是默认的 不要动
            GlStateManager.translate(xDir * 2 + 0.5, 3, zDir * 2 + 0.5);//translate是移动方法 这个移动到xyz是默认的 不要动


            float angle = (System.currentTimeMillis() % 3600) / 10.0f; //我写的随时间变化旋转的角度
            //GlStateManager.rotate(90, 0F, 1F, 0F);//rotate是旋转模型的方法  DNA的初始位置不太对 我旋转了一下   四个参数为：旋转角度，xyz轴，可以控制模型围绕哪个轴旋转
            GlStateManager.rotate(angle, 0F, 1F, 0F);//我让dna围绕z轴旋转，角度是实时变化的


            GlStateManager.scale(6, 6, 6);
            // ObjModels.Tree_Model.renderAllWithMtl(); //这个是模型加载器的渲染方法  这是带MTL的加载方式
            ObjModels.Tree_Model1.renderAll(); //这个是模型加载器的渲染方法  这是不带MTL的加载方式
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
        return new AxisAlignedBB(getPos(),getPos().add(5,5,5));
    }


    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true, true);
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("在输入总线内放入树苗（仅限橡木）"));
        tooltip.add(I18n.format("生成完毕后输出两倍树苗与原木"));
    }

}