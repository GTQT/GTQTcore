package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.pressureSystem;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GCYSValues;
import keqing.gtqtcore.api.capability.IPressureContainer;
import keqing.gtqtcore.api.capability.impl.PressureContainer;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.api.utils.NumberFormattingUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static gregtech.api.util.RelativeDirection.*;

public class MetaTileEntityPressureTank extends MultiblockWithDisplayBase {
    @Override
    public boolean usesMui2() {
        return false;
    }
    private final IPressureContainer pressureContainer;

    int tier;

    public MetaTileEntityPressureTank(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId);
        this.tier = tier;
        pressureContainer = new PressureContainer(this, GCYSValues.decreaseDetailP[this.tier], GCYSValues.increaseDetailP[this.tier], 20 * tier);
    }

    @Override
    protected void updateFormedValid() {
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0) {
            if (isStructureFormed()) {
                for (IPressureContainer container : getPressureContainers()) {
                    IPressureContainer.mergeContainers(false, container, pressureContainer);
                }
            }
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240);
        builder.dynamicLabel(8, 12, () -> "大型耐压储罐 等级" + tier, 0xFFFFFF);
        builder.image(4, 28, 172, 128, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(8, 32, this::addDisplayText, 16777215)).setMaxWidthLimit(180).setClickHandler(this::handleDisplayClick));
        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("容积：%s", 20 * tier));
        textList.add(new TextComponentTranslation("behavior.tricorder.current_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getPressure())).setStyle(new Style().setColor(TextFormatting.AQUA))));
        textList.add(new TextComponentTranslation("behavior.tricorder.min_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure())).setStyle(new Style().setColor(TextFormatting.GREEN))));
        textList.add(new TextComponentTranslation("behavior.tricorder.max_pressure", new TextComponentString(NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxPressure())).setStyle(new Style().setColor(TextFormatting.GREEN))));
    }

    public List<IPressureContainer> getPressureContainers() {
        return getAbilities(GTQTMultiblockAbility.PRESSURE_CONTAINER);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("XSX", "XXX", "XXX")
                .aisle("XXX", "XFX", "XXX")
                .aisle("XXX", "XFX", "XXX")
                .aisle("XXX", "XFX", "XXX")
                .aisle("XXX", "XXX", "XXX")
                .where('S', selfPredicate())
                .where('X', states(getCasing())
                        .or(abilities(GTQTMultiblockAbility.PRESSURE_CONTAINER).setMinGlobalLimited(1).setMaxGlobalLimited(4)))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where(' ', any())
                .build();
    }

    private IBlockState getCasing() {
        switch (this.tier) {
            case (1) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
            }

            case (2) -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STAINLESS_CLEAN);

            }
            default -> {
                return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.TUNGSTENSTEEL_ROBUST);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.tier) {
            case (1) -> {
                return Textures.SOLID_STEEL_CASING;
            }

            case (2) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }

            default -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
        }
    }


    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPressureTank(this.metaTileEntityId, tier);
    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("用于高低压气体缓存，最多接受4个仓的气体交互"));
        tooltip.add(I18n.format("多方块容积：%s", 20 * tier));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.minimum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMinPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMinPressure())]));
        tooltip.add(I18n.format("gtqtcore.universal.tooltip.pressure.maximum", NumberFormattingUtil.formatDoubleToCompactString(pressureContainer.getMaxPressure()), GCYSValues.PNF[GTQTUtil.getTierByPressure(pressureContainer.getMaxPressure())]));
    }


    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true,
                isStructureFormed());
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.RESEARCH_STATION_OVERLAY;
    }

    public IPressureContainer getPressureContainer() {
        return pressureContainer;
    }
}
