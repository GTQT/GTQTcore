package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc;

import gregtech.api.GTValues;
import gregtech.api.capability.IHPCACoolantProvider;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.SimpleOverlayRenderer;

import gregtech.common.metatileentities.multi.multiblockpart.hpca.MetaTileEntityHPCAComponent;
import net.minecraft.util.ResourceLocation;

public class MetaTileEntityHPCAAdvancedCooler extends MetaTileEntityHPCAComponent implements IHPCACoolantProvider {


    private final boolean supers;
    private final boolean ultimate;

    public MetaTileEntityHPCAAdvancedCooler(ResourceLocation metaTileEntityId, boolean supers, boolean ultimate) {
        super(metaTileEntityId);
        this.supers = supers;
        this.ultimate = ultimate;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityHPCAAdvancedCooler(metaTileEntityId, supers, ultimate);
    }

    @Override
    public boolean isAdvanced() {
        if (isDamaged())
            return supers;
        return ultimate;
    }

    @Override//正面覆盖 T/F TODO
    public SimpleOverlayRenderer getFrontOverlay() {
        if (isDamaged())
            return supers ? Textures.HPCA_ACTIVE_COOLER_OVERLAY : Textures.HPCA_HEAT_SINK_OVERLAY;
        return ultimate ? Textures.HPCA_ACTIVE_COOLER_OVERLAY : Textures.HPCA_HEAT_SINK_OVERLAY;
    }

    @Override//组件图标(UI) T/F TODO
    public TextureArea getComponentIcon() {
        if (isDamaged())
            return supers ? GuiTextures.HPCA_ICON_ACTIVE_COOLER_COMPONENT : GuiTextures.HPCA_ICON_HEAT_SINK_COMPONENT;
        return ultimate ? GuiTextures.HPCA_ICON_ACTIVE_COOLER_COMPONENT : GuiTextures.HPCA_ICON_HEAT_SINK_COMPONENT;
    }

    @Override//运转中正面覆盖 T/F TODO
    public SimpleOverlayRenderer getFrontActiveOverlay() {
        if (isDamaged())
            return supers ? Textures.HPCA_ACTIVE_COOLER_ACTIVE_OVERLAY : getFrontOverlay();
        return ultimate ? Textures.HPCA_ACTIVE_COOLER_ACTIVE_OVERLAY : getFrontOverlay();
    }

    @Override//维持(EU/t) T/F
    public int getUpkeepEUt() {
        if (isDamaged())
            return GTValues.VA[supers ? 8 : 6];
        return GTValues.VA[ultimate ? 9 : 6];
    }

    @Override//冷却量 T/F
    public int getCoolingAmount() {
        if (isDamaged())
            return supers ? 32 : 8;
        return ultimate ? 128 : 8;
    }

    @Override
    public boolean isActiveCooler() {
        return true;
    }

    @Override//最大冷却液 T/F
    public int getMaxCoolantPerTick() {
        if (isDamaged())
            return supers ? 1024 : 256;
        return ultimate ? 4096 : 256;
    }
    @Override
    public boolean canBeDamaged() {
        return false;
    }
}