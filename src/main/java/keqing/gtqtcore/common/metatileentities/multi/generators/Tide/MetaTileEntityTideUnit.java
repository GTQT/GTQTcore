package keqing.gtqtcore.common.metatileentities.multi.generators.Tide;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.pattern.*;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.BlockInfo;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityTideUnit extends MetaTileEntityBaseWithControl {
    private int coilHeight;
    protected int heatingCoilLevel;
    boolean isWorking;
    int water;
    int gen;
    boolean con;
    public void setCon(boolean c)
    {
        con=c;
    }
    @Override
    protected void addWarningText(List<ITextComponent> textList) {
        super.addWarningText(textList);
        if (isStructureFormed()) {
            if (!con) {
                textList.add(new TextComponentTranslation("未连接！"));
            }
        }
    }
    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 18, 18, "", this::refresh)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("检测"));
        return group;
    }
    public void refresh(Widget.ClickData clickData) {
        water=checkWater();
    }
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("coilHeight", coilHeight);
        data.setInteger("heatingCoilLevel", heatingCoilLevel);
        data.setInteger("water", water);
        data.setInteger("gen",gen);
        data.setBoolean("isWorking",isWorking);
        data.setBoolean("con",con);
        return super.writeToNBT(data);
    }


    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        coilHeight = data.getInteger("coilHeight");
        heatingCoilLevel = data.getInteger("heatingCoilLevel");
        water = data.getInteger("water");
        gen = data.getInteger("gen");
        isWorking =data.getBoolean("isWorking");
        con =data.getBoolean("con");
    }
    public MetaTileEntityTideUnit(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("以单元长度为半径，深度；检测范围内的水量叠算线圈等级后除以四为最终发电"));
        tooltip.add(I18n.format("只能在河流，海洋群系工作！控制器高度必须位于55-65之间,单元半径5格内不得有其他浮标单元"));
        tooltip.add(I18n.format("每一个 潮汐浮标单元 只能链接到一个潮汐浮标控制器"));
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("线圈高度：%s 线圈等级：%s", coilHeight, heatingCoilLevel));
        textList.add(new TextComponentTranslation("范围水量：%s 期望发电：%s", water, gen));
        textList.add(new TextComponentTranslation("控制链接：%s", con));
    }
    @Override
    public boolean isActive() {
        return isWorking;
    }
    int updateTime;
    @Override
    protected void updateFormedValid() {
        if(isInValidLocation())
        {
            updateTime++;
            if(updateTime==1200) {
                updateTime=0;
                water=checkWater();
            }
            this.isWorking=true;
            gen=water*heatingCoilLevel/4;
        }
        else
        {
            gen=0;
        }
    }
    public boolean isInValidLocation() {
        Biome biome = getWorld().getBiome(getPos());
        Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);

        if (getPos().getY() < 55 || getPos().getY() > 65) {
            return false;
        }

        return biomeTypes.contains(BiomeDictionary.Type.WATER);
    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("YSY", "HYH","YHY")
                .aisle("YYY", "HYH","YYY")
                .aisle("YHY", "HCH","YHY").setRepeatable(3, 7)
                .aisle("H H", "H H","H H")

                .where('S', selfPredicate())
                .where('Y', states(getCasingState()))
                .where('H', frames(Materials.Steel))
                .where('C', coilPredicate())
                .build();
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
        coilHeight = context.getInt("Count");
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
        }
        water=checkWater();
    }
    public int checkWater()
    {
        int waterpos=0;

        final int xDir = this.getFrontFacing().getOpposite().getXOffset();
        final int zDir = this.getFrontFacing().getOpposite().getZOffset();

        for (int i = -coilHeight + 1; i <= coilHeight - 1; ++i) {
            for (int j = -coilHeight + 1; j <= coilHeight - 1; ++j) {
                for (int h = -coilHeight; h < 0; h++) {
                    BlockPos waterCheckPos =this.getPos().add(xDir + i, h, zDir + j);
                    if(this.getWorld().getBlockState(waterCheckPos)== Blocks.WATER.getDefaultState())waterpos++;
                }
            }
        }
        return waterpos;
    }
    private TraceabilityPredicate coilPredicate() {
        return new TraceabilityPredicate((blockWorldState) -> {
            IBlockState blockState = blockWorldState.getBlockState();
            if (blockState.getBlock() instanceof BlockWireCoil) {
                BlockWireCoil blockWireCoil = (BlockWireCoil) blockState.getBlock();
                BlockWireCoil.CoilType coilType = blockWireCoil.getState(blockState);
                Object currentCoilType = blockWorldState.getMatchContext().getOrPut("CoilType", coilType);
                if (!currentCoilType.toString().equals(coilType.getName())) {
                    blockWorldState.setError(new PatternStringError("gregtech.multiblock.pattern.error.coils"));
                    return false;
                } else {
                    blockWorldState.getMatchContext().increment("Count", 1);
                    blockWorldState.getMatchContext().getOrPut("VABlock", new LinkedList<>()).add(blockWorldState.getPos());
                    return true;
                }
            } else {
                return false;
            }
        }, () -> Arrays.stream(BlockWireCoil.CoilType.values()).map((type) -> new BlockInfo(MetaBlocks.WIRE_COIL.getState(type), null)).toArray(BlockInfo[]::new)).addTooltips("gregtech.multiblock.pattern.error.coils");
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
        return GTQTTextures.CVD_UNIT_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityTideUnit(metaTileEntityId);
    }


    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
