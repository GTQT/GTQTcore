package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.ColourMultiplier;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockPart;
import keqing.gtqtcore.api.capability.IKQCC;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Objects;

public class MetaTileEntityKQCCPartHatch extends MetaTileEntityMultiblockPart implements
        IMultiblockAbilityPart<IKQCC>, IKQCC {

    String type;
    int level;

    public MetaTileEntityKQCCPartHatch(ResourceLocation metaTileEntityId, String type, int level) {
        super(metaTileEntityId, 2);
        this.type = type;
        this.level = level;
    }


    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 180, 240)
                .widget(new DynamicLabelWidget(28, 12, () -> "计算部件仓"))
                .image(4, 28, 172, 128, GuiTextures.DISPLAY)
                .widget((new AdvancedTextWidget(8, 32, this::addDisplayText, 16777215)).setMaxWidthLimit(180))
                .bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 8, 160);
        return builder.build(this.getHolder(), entityPlayer);
    }
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("类型： " + type));
        textList.add(new TextComponentString("等级: " + level));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        ICubeRenderer baseTexture = GTQTTextures.KQCC_COMMON;
        pipeline = ArrayUtils.add(pipeline, new ColourMultiplier(GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering())));
        baseTexture.render(renderState, translation, pipeline);

        if (this.shouldRenderOverlay()) {
            if (Objects.equals(type, "ram")) {
                if (level == 1)
                    GTQTTextures.KQCC_RAM_1.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 2)
                    GTQTTextures.KQCC_RAM_2.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 3)
                    GTQTTextures.KQCC_RAM_3.renderSided(getFrontFacing(), renderState, translation, pipeline);
            }
            if (Objects.equals(type, "cpu")) {
                if (level == 1)
                    GTQTTextures.KQCC_CPU_1.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 2)
                    GTQTTextures.KQCC_CPU_2.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 3)
                    GTQTTextures.KQCC_CPU_3.renderSided(getFrontFacing(), renderState, translation, pipeline);
            }
            if (Objects.equals(type, "gpu")) {
                if (level == 1)
                    GTQTTextures.KQCC_GPU_1.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 2)
                    GTQTTextures.KQCC_GPU_2.renderSided(getFrontFacing(), renderState, translation, pipeline);
                if (level == 3)
                    GTQTTextures.KQCC_GPU_3.renderSided(getFrontFacing(), renderState, translation, pipeline);
            }

        }
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityKQCCPartHatch(metaTileEntityId, type, level);
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this);
    }
    @Override
    public MultiblockAbility<IKQCC> getAbility() {
        return GTQTMultiblockAbility.KQCC_MULTIBLOCK_ABILITY;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getLevel() {
        return this.level;
    }
}
