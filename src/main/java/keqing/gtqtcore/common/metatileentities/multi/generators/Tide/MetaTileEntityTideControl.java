package keqing.gtqtcore.common.metatileentities.multi.generators.Tide;

import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.api.metaileentity.MetaTileEntityBaseWithControl;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class MetaTileEntityTideControl extends MetaTileEntityBaseWithControl {
    private IEnergyContainer energyContainer;
    int [][] io=new int[16][5];
    //分别为 启动？ 坐标（三位） 等级
    int circuit;

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("circuit", circuit);
        for(int i=0;i<16;i++) data.setIntArray("io"+i,io[i]);

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        circuit = data.getInteger("circuit");
        for(int i=0;i<16;i++) io[i]=data.getIntArray("io"+i);
    }

    public MetaTileEntityTideControl(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("EEE", "YYY","YHY")
                .aisle("EEE", "YYY","YHY")
                .aisle("ESE", "YYY","YHY")
                .where('S', selfPredicate())
                .where('E',states(getCasingState())
                        .or(abilities(MultiblockAbility.OUTPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('Y', states(getCasingState()))
                .where('H', frames(Materials.Steel))
                .build();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("在以控制器为中心半径为16的范围内放置 潮汐浮漂单元，即可自动检测链接！"));
        tooltip.add(I18n.format("控制器可最多容纳16个潮汐浮标单元"));
    }

    @Override
    public boolean usesMui2() {
        return false;
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("序列向后"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThreshold)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("序列向前"));
        group.addWidget(new ClickButtonWidget(0, 9, 18, 9, "", this::refresh)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("检测"));
        return group;
    }
    private void refresh(Widget.ClickData clickData) {
        checkUnit();
    }
    private void incrementThreshold(Widget.ClickData clickData) {
        this.circuit = MathHelper.clamp(circuit + 1, 0, 16);
    }

    private void decrementThreshold(Widget.ClickData clickData) {
        this.circuit = MathHelper.clamp(circuit - 1, 0, 16);
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .setWorkingStatus(true, isActive() && isWorkingEnabled()) // transform into two-state system for display
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GREEN, "电量：%s", realEu));
                        tl.add(new TextComponentTranslation("========================"));
                        for (int i = -3; i <= 3; i++) {

                            if (i == 0) {
                                {
                                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序号：%s|x：%s y：%s z：%s/E：%s", circuit + 1,this.io[circuit][1], this.io[circuit][2],this.io[circuit][3],this.io[circuit][4]));
                                }
                            } else if (circuit + i >= 0 && circuit + i < 15) {
                                {
                                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s|x：%s y：%s z：%s/E：%s", circuit+ i + 1,  this.io[circuit+ i][1], this.io[circuit+ i][2],this.io[circuit+ i][3],this.io[circuit+ i][4]));
                                }
                            }
                        }
                    }
                });
    }
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        this.energyContainer = new EnergyContainerList(getAbilities(MultiblockAbility.OUTPUT_ENERGY));
    }
    int realEu;
    int updateTime;
    @Override
    protected void updateFormedValid() {
        updateTime++;
        if(updateTime==600) {
            updateTime=0;
            checkUnit();
        }
        int coutEu=0;
        for(int p=0;p<16;p++)
        {
            if(io[p][0]!=0)
            {
                TileEntity te = this.getWorld().getTileEntity(new BlockPos(io[p][1],io[p][2],io[p][3]));
                if (te instanceof IGregTechTileEntity igtte) {
                    MetaTileEntity mte = igtte.getMetaTileEntity();
                    if (mte instanceof MetaTileEntityTideUnit) {
                        io[p][4]=((MetaTileEntityTideUnit) mte).gen;
                        coutEu+=io[p][4];
                    }
                    else {
                        io[p][0]=0;
                        io[p][1]=0;
                        io[p][2]=0;
                        io[p][3]=0;
                        io[p][4]=0;

                    }
                }
                else
                {
                    io[p][0]=0;
                    io[p][1]=0;
                    io[p][2]=0;
                    io[p][3]=0;
                    io[p][4]=0;
                }
            }
        }
        if(coutEu!=realEu)realEu=coutEu;

        this.energyContainer.addEnergy(realEu);
    }
    /*
    @Override
    public void checkStructurePattern() {
        if(checkControl()) super.checkStructurePattern();
    }
    public boolean checkControl()
    {
        final int xDir = this.getFrontFacing().getOpposite().getXOffset();
        final int zDir = this.getFrontFacing().getOpposite().getZOffset();

        for (int i = -16 + 1; i <= 16 - 1; ++i) {
            for (int j = -16 + 1; j <= 16 - 1; ++j) {
                for (int h = -4; h < 5; h++) {
                    BlockPos CheckPos = this.getPos().add(xDir + i, h, zDir + j);
                    TileEntity te = this.getWorld().getTileEntity(CheckPos);
                    if (te instanceof IGregTechTileEntity igtte) {
                        MetaTileEntity mte = igtte.getMetaTileEntity();
                        if (mte instanceof MetaTileEntityTideControl)
                            return false;
                    }
                }
            }
        }
        return true;
    }

     */
    public void checkUnit()
    {
        final int xDir = this.getFrontFacing().getOpposite().getXOffset();
        final int zDir = this.getFrontFacing().getOpposite().getZOffset();

        for (int i = -16 + 1; i <= 16 - 1; ++i) {
            for (int j = -16 + 1; j <= 16 - 1; ++j) {
                for (int h = -4; h < 5; h++) {
                    BlockPos CheckPos =this.getPos().add(xDir + i, h, zDir + j);
                    TileEntity te = this.getWorld().getTileEntity(CheckPos);
                    if (te instanceof IGregTechTileEntity igtte) {
                        MetaTileEntity mte = igtte.getMetaTileEntity();
                        if (mte instanceof MetaTileEntityTideUnit) {
                            if(((MetaTileEntityTideUnit) mte).isStructureFormed()) {
                                for(int p=0;p<16;p++)
                                {
                                    if(io[p][0]==0&& !((MetaTileEntityTideUnit) mte).con)
                                    {
                                        ((MetaTileEntityTideUnit) mte).setCon(true);
                                        io[p][0]=1;
                                        io[p][1]=CheckPos.getX();
                                        io[p][2]=CheckPos.getY();
                                        io[p][3]=CheckPos.getZ();
                                        io[p][4]= (int) ((MetaTileEntityTideUnit) mte).gen;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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
        return new MetaTileEntityTideControl(metaTileEntityId);
    }
    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
