package keqing.gtqtcore.api.metaileentity;

import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.widgets.ProgressWidget;

public class SteamProgressIndicators {

    public static final SteamProgressIndicator EXTRACTION_STEAM = new SteamProgressIndicator(GuiTextures.PROGRESS_BAR_COMPRESS_STEAM, ProgressWidget.MoveType.VERTICAL_DOWNWARDS, 20, 20);
    public static final SteamProgressIndicator COMPRESS = new SteamProgressIndicator(GuiTextures.PROGRESS_BAR_COMPRESS_STEAM, ProgressWidget.MoveType.HORIZONTAL, 20, 15);
}