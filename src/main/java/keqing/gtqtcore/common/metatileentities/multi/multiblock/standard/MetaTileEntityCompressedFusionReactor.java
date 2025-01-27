package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import com.google.common.util.concurrent.AtomicDouble;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.gui.widgets.ImageCycleButtonWidget;
import gregtech.api.gui.widgets.ImageWidget;
import gregtech.api.gui.widgets.IndicatorImageWidget;
import gregtech.api.gui.widgets.ProgressWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityLaserHatch;
import keqing.gtqtcore.api.gui.GTQTGuiTextures;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

import static gregtech.api.GTValues.*;

public class MetaTileEntityCompressedFusionReactor extends RecipeMapMultiblockController {
    //  Block State for CFR, for Mark 4 and Mark 5,
    //  we do not need Cryostat, Divertor and Vacuum in CFR anymore.
    //  Parameter {@code CoilState} means Fusion Coils.
    public final IBlockState casingState;
    public final IBlockState coilState;
    public final IBlockState frameState;
    //  Tier of CFR (Compressed Fusion Reactor), used to add more CFRs by one class,
    //  we use {@link #LuV} to {@link #UEV} for Mark 1 to Mark 5.
    private final int tier;
    //  Used for Special Progress Bar in Modular UI.
    //  TODO Add new Modular UI form of CFR (different with Fusion Reactor)?
    private final FusionProgressSupplier progressBarSupplier;
    //  Internal Energy Container, just like common Fusion Reactors.
    private EnergyContainerList inputEnergyContainers;

    //  Heat, like common Fusion Reactors.
    //  TODO Delete Heat system of CFR?
    private long heat = 0;

    public MetaTileEntityCompressedFusionReactor(ResourceLocation metaTileEntityId,
                                                 int tier,
                                                 IBlockState casingState,
                                                 IBlockState coilState,
                                                 IBlockState frameState) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
        this.recipeMapWorkable = new CompressedFusionReactorRecipeLogic(this);
        this.tier = tier;
        this.casingState = casingState;
        this.coilState = coilState;
        this.frameState = frameState;
        this.energyContainer = new EnergyContainerHandler(this, Integer.MAX_VALUE, 0, 0, 0, 0) {
            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
        this.progressBarSupplier = new FusionProgressSupplier();
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCompressedFusionReactor(metaTileEntityId, tier, casingState, coilState, frameState);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCIBICF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                .aisle("                                               ", "           CCCCC               CCCCC           ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "           CCCCC               CCCCC           ", "                                               ")
                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                .aisle("                                               ", "       CCC                           CCC       ", "      ECHCC                         CCHCE      ", "      CHHHC                         CHHHC      ", "      ECHCC                         CCHCE      ", "       CCC                           CCC       ", "                                               ")
                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                .aisle("  CBC                                     CBC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "B#HHH#B                                 B#HHH#B", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CBC                                     CBC  ")
                .aisle("  CIC                                     CIC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "I#HHH#I                                 I#HHH#I", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CIC                                     CIC  ")
                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCE                           ECHCC     ", "     CHHHC                           CHHHC     ", "     CCHCE                           ECHCC     ", "      CCC                             CCC      ", "                                               ")
                .aisle("                                               ", "       CCC                           CCC       ", "      ECHCC                         CCHCE      ", "      CHHHC                         CHHHC      ", "      ECHCC                         CCHCE      ", "       CCC                           CCC       ", "                                               ")
                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCE                       ECHCC       ", "       CHHHC                       CHHHC       ", "       CCHCE                       ECHCC       ", "        CCC                         CCC        ", "                                               ")
                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                .aisle("                                               ", "           CCCCC               CCCCC           ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "          CHHHHHCCC FCIBICF CCCHHHHHC          ", "          ECHHCCCCC FCCCCCF CCCCCHHCE          ", "           CCCCC               CCCCC           ", "                                               ")
                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                .aisle("                    FCIBICF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCIBICF                    ")
                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCISICF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                .where('S', this.selfPredicate())
                .where('B', states(getGlassState()))
                .where('C', states(getCasingState()))
                .where('I', states(getCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS)
                                .setMinGlobalLimited(2)
                                .setPreviewCount(16))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS)
                                .setMinGlobalLimited(2)
                                .setPreviewCount(16)))
                .where('F', states(getFrameState()))
                .where('H', states(getCoilState()))
                .where('E', states(getCasingState())
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.INPUT_ENERGY)
                                .stream()
                                .filter(mte -> {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                                    return container != null && container.getInputVoltage() == V[tier];
                                })
                                .toArray(MetaTileEntity[]::new))
                                .setMaxGlobalLimited(32)
                                .setPreviewCount(32)
                        )
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.INPUT_LASER)
                                .stream()
                                .filter(mte -> {
                                    if(mte instanceof MetaTileEntityLaserHatch laserHatch ) {

                                        // 使用反射获取 tier 字段的值
                                        Field tierField = null;
                                        try {
                                            tierField = MetaTileEntityLaserHatch.class.getDeclaredField("tier");
                                        } catch (NoSuchFieldException ignored) {}
                                        tierField.setAccessible(true); // 设置字段可访问
                                        try {
                                            int level = (int) tierField.get(laserHatch);
                                            return level==tier;
                                        } catch (IllegalAccessException ignored) {}
                                    }
                                    return false;
                                })
                                .toArray(MetaTileEntity[]::new))
                                .setMaxGlobalLimited(32)
                                .setPreviewCount(0)
                        )
                )
                .where('#', air())
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        return casingState;
    }

    private IBlockState getCoilState() {
        return coilState;
    }

    private IBlockState getFrameState() {
        return frameState;
    }

    private IBlockState getGlassState() {
        if (tier == GTValues.UHV)
            return GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_IV);
        if (tier == UEV)
            return GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_V);
        if (tier == UIV)
            return GTQTMetaBlocks.blockMultiblockGlass.getState(BlockMultiblockGlass.CasingType.TECH_FUSION_GLASS_VI);

        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        if (this.recipeMapWorkable.isActive()) {
            switch (tier) {
                case UHV -> {
                    return GTQTTextures.ADVANCED_FUSION_TEXTURE;
                }
                case UEV -> {
                    return GTQTTextures.ULTIMATE_FUSION_TEXTURE;
                }
                case UIV -> {
                    return GTQTTextures.END_FUSION_TEXTURE;
                }
                default -> {
                    return Textures.ACTIVE_FUSION_TEXTURE;
                }
            }
        } else {
            switch (tier) {
                case UHV -> {
                    return GTQTTextures.ADVANCED_FUSION_TEXTURE;
                }
                case UEV -> {
                    return GTQTTextures.ULTIMATE_FUSION_TEXTURE;
                }
                case UIV -> {
                    return GTQTTextures.END_FUSION_TEXTURE;
                }
                default -> {
                    return Textures.FUSION_TEXTURE;
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        long energyStored = this.energyContainer.getEnergyStored();
        super.formStructure(context);
        this.initializeAbilities();
        ((EnergyContainerHandler) this.energyContainer).setEnergyStored(energyStored);
    }

    @Override
    protected void initializeAbilities() {
        //  Common ability
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));

        //  Energy Input ability
        List<IEnergyContainer> energyInputs = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyInputs.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyInputs);

        //  EU Capacity = Energy Hatch amount * Energy Stored (half of original Fusion Reactor).
        long euCapacity = calculateEnergyStorageFactor(energyInputs.size());
        this.energyContainer = new EnergyContainerHandler(this, euCapacity, V[tier], 0, 0, 0) {
            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
    }

    private long calculateEnergyStorageFactor(int energyInputAmount) {
        long[] energyStored = {5000000L, 10000000L, 20000000L, 80000000L, 320000000L, 1280000000L};
        return energyInputAmount * energyStored[tier - 6];
    }

    @Override
    protected void updateFormedValid() {
        if (this.inputEnergyContainers.getEnergyStored() > 0) {
            long energyAdded = this.energyContainer.addEnergy(this.inputEnergyContainers.getEnergyStored());
            if (energyAdded > 0)
                this.inputEnergyContainers.removeEnergy(energyAdded);
        }
        super.updateFormedValid();
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        //  Background
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 198, 236);

        //  Display
        builder.image(4, 4, 190, 138, GuiTextures.DISPLAY);

        //  Energy Bar
        builder.widget(new ProgressWidget(() -> this.energyContainer.getEnergyCapacity() > 0L ? 1.0 * this.energyContainer.getEnergyStored() / (double) this.energyContainer.getEnergyCapacity() : 0.0,
                4, 144, 94, 7, GuiTextures.PROGRESS_BAR_FUSION_ENERGY, ProgressWidget.MoveType.HORIZONTAL)
                .setHoverTextConsumer(this::addEnergyBarHoverText));

        //  Heat Bar
        builder.widget(new ProgressWidget(() -> this.energyContainer.getEnergyCapacity() > 0L ? 1.0 * this.heat / (double) this.energyContainer.getEnergyCapacity() : 0.0,
                100, 144, 94, 7, GuiTextures.PROGRESS_BAR_FUSION_HEAT, ProgressWidget.MoveType.HORIZONTAL)
                .setHoverTextConsumer(this::addHeatBarHoverText));

        //  Indicator Image Widget (logo)
        builder.widget(new IndicatorImageWidget(174, 122, 17, 17, this.getLogo())
                .setWarningStatus(this.getWarningLogo(), this::addWarningText)
                .setErrorStatus(this.getErrorLogo(), this::addErrorText));

        //  Title (MK1 to MK5)
        switch (this.tier) {
            case LuV -> builder.widget(new ImageWidget(66, 9, 67, 12, GuiTextures.FUSION_REACTOR_MK1_TITLE)
                    .setIgnoreColor(true));
            case ZPM -> builder.widget(new ImageWidget(66, 9, 67, 12, GuiTextures.FUSION_REACTOR_MK2_TITLE)
                    .setIgnoreColor(true));
            case UV -> builder.widget(new ImageWidget(66, 9, 67, 12, GuiTextures.FUSION_REACTOR_MK3_TITLE)
                    .setIgnoreColor(true));
            case UHV -> builder.widget(new ImageWidget(66, 9, 67, 12, GTQTGuiTextures.FUSION_REACTOR_MK4_TITLE)
                    .setIgnoreColor(true));
            case UEV -> builder.widget(new ImageWidget(66, 9, 67, 12, GTQTGuiTextures.FUSION_REACTOR_MK5_TITLE)
                    .setIgnoreColor(true));
            case UIV -> builder.widget(new ImageWidget(66, 9, 67, 12, GTQTGuiTextures.FUSION_REACTOR_MK6_TITLE)
                    .setIgnoreColor(true));
        }

        //  Fusion Diagram + Progress Bar
        builder.widget(new ImageWidget(55, 24, 89, 101, GuiTextures.FUSION_REACTOR_DIAGRAM)
                .setIgnoreColor(true));
        builder.widget(FusionProgressSupplier.Type.BOTTOM_LEFT.getWidget(this));
        builder.widget(FusionProgressSupplier.Type.TOP_LEFT.getWidget(this));
        builder.widget(FusionProgressSupplier.Type.TOP_RIGHT.getWidget(this));
        builder.widget(FusionProgressSupplier.Type.BOTTOM_RIGHT.getWidget(this));

        //  Fusion Legend
        builder.widget(new ImageWidget(7, 98, 108, 41, GuiTextures.FUSION_REACTOR_LEGEND)
                .setIgnoreColor(true));

        //  Power Button + Detail
        builder.widget(new ImageCycleButtonWidget(173, 211, 18, 18, GuiTextures.BUTTON_POWER,
                this.recipeMapWorkable::isWorkingEnabled, this.recipeMapWorkable::setWorkingEnabled));

        //  Voiding Mode Button
        builder.widget(new ImageCycleButtonWidget(173, 189, 18, 18, GuiTextures.BUTTON_VOID_MULTIBLOCK, 4,
                this::getVoidingMode, this::setVoidingMode)
                .setTooltipHoverString(MultiblockWithDisplayBase::getVoidingModeTooltip));

        //  Distinct Buses Unavailable Image
        builder.widget(new ImageWidget(173, 171, 18, 18, GuiTextures.BUTTON_NO_DISTINCT_BUSES)
                .setTooltip("gregtech.multiblock.universal.distinct_not_supported"));

        //  Flex Unavailable Image
        builder.widget(this.getFlexButton(173, 153, 18, 18));

        //  Player Inventory
        builder.bindPlayerInventory(entityPlayer.inventory, 153);
        return builder;
    }

    private void addEnergyBarHoverText(List<ITextComponent> hoverList) {
        ITextComponent energyInfo = TextComponentUtil.stringWithColor(
                TextFormatting.AQUA,
                TextFormattingUtil.formatNumbers(this.energyContainer.getEnergyStored()) + " / "
                        + TextFormattingUtil.formatNumbers(this.energyContainer.getEnergyCapacity()) + " EU");

        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.energy_stored",
                energyInfo));
    }

    private void addHeatBarHoverText(List<ITextComponent> hoverList) {
        ITextComponent heatInfo = TextComponentUtil.stringWithColor(
                TextFormatting.RED,
                TextFormattingUtil.formatNumbers(this.heat) + " / "
                        + TextFormattingUtil.formatNumbers(this.energyContainer.getEnergyCapacity()));

        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "gregtech.multiblock.fusion_reactor.heat",
                heatInfo));
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        long actuallyEnergyStored = calculateEnergyStorageFactor(32) / 1000000L;
        super.addInformation(stack, player, tooltip, advanced);
        switch (this.tier) {
            case LuV -> {
                tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.compressed_fusion_reactor.luv.tooltip.1"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.luv.tooltip.2"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.luv.tooltip.3"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.luv.tooltip.4"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.luv.tooltip.5"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.luv.tooltip.6"));
                tooltip.add(I18n.format("gregtech.machine.fusion_reactor.capacity", actuallyEnergyStored));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.common_oc"));
            }
            case ZPM -> {
                tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.compressed_fusion_reactor.zpm.tooltip.1"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.zpm.tooltip.2"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.zpm.tooltip.3"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.zpm.tooltip.4"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.zpm.tooltip.5"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.zpm.tooltip.6"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.zpm.tooltip.7"));
                tooltip.add(I18n.format("gregtech.machine.fusion_reactor.capacity", actuallyEnergyStored));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.common_oc"));
            }
            case UV -> {
                tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.1"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.2"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.3"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.4"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.5"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.6"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.7"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uv.tooltip.8"));
                tooltip.add(I18n.format("gregtech.machine.fusion_reactor.capacity", actuallyEnergyStored));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.common_oc"));
            }
            case UHV -> {
                tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.1"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.2"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.3"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.4"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.5"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.6"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.7"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.8"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uhv.tooltip.9"));
                tooltip.add(I18n.format("gregtech.machine.fusion_reactor.capacity", actuallyEnergyStored));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc"));
            }
            case UEV -> {
                tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.1"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.2"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.3"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.4"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.5"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.6"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.7"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.8"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.9"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uev.tooltip.10"));
                tooltip.add(I18n.format("gregtech.machine.fusion_reactor.capacity", actuallyEnergyStored));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc"));
            }
            case UIV -> {
                tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.1"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.2"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.3"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.4"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.5"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.6"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.7"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.8"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.9"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.10"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.uiv.tooltip.11"));
                tooltip.add(I18n.format("gregtech.machine.fusion_reactor.capacity", actuallyEnergyStored));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc"));
            }
        }

    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public long getHeat() {
        return heat;
    }

    private static class FusionProgressSupplier {

        private final AtomicDouble tracker = new AtomicDouble(0.0);
        private final ProgressWidget.TimedProgressSupplier bottomLeft;
        private final DoubleSupplier topLeft;
        private final DoubleSupplier topRight;
        private final DoubleSupplier bottomRight;

        public FusionProgressSupplier() {

            //  Bottom Left, fill on [0, 0.25)
            bottomLeft = new ProgressWidget.TimedProgressSupplier(200, 164, false) {

                @Override
                public double getAsDouble() {
                    double val = super.getAsDouble();
                    tracker.set(val);
                    if (val >= 0.25) {
                        return 1;
                    }
                    return 4 * val;
                }

                @Override
                public void resetCountdown() {
                    super.resetCountdown();
                    tracker.set(0);
                }
            };

            // Top Left, fill on [0.25, 0.5)
            topLeft = () -> {
                double val = tracker.get();
                if (val < 0.25) {
                    return 0;
                } else if (val >= 0.5) {
                    return 1;
                }
                return 4 * (val - 0.25);
            };

            // Top Right, fill on [0.5, 0.75)
            topRight = () -> {
                double val = tracker.get();
                if (val < 0.5) {
                    return 0;
                } else if (val >= 0.75) {
                    return 1;
                }
                return 4 * (val - 0.5);
            };

            // Bottom Right, fill on [0.75, 1.0]
            bottomRight = () -> {
                double val = tracker.get();
                if (val < 0.75) {
                    return 0;
                } else if (val >= 1) {
                    return 1;
                }
                return 4 * (val - 0.75);
            };
        }

        @SuppressWarnings("unused")
        public void resetCountdown() {
            bottomLeft.resetCountdown();
        }

        public DoubleSupplier getSupplier(Type type) {
            return switch (type) {
                case BOTTOM_LEFT -> bottomLeft;
                case TOP_LEFT -> topLeft;
                case TOP_RIGHT -> topRight;
                case BOTTOM_RIGHT -> bottomRight;
            };
        }

        private enum Type {

            BOTTOM_LEFT(61, 66, 35, 41,
                    GuiTextures.PROGRESS_BAR_FUSION_REACTOR_DIAGRAM_BL, ProgressWidget.MoveType.VERTICAL),
            TOP_LEFT(61, 30, 41, 35,
                    GuiTextures.PROGRESS_BAR_FUSION_REACTOR_DIAGRAM_TL, ProgressWidget.MoveType.HORIZONTAL),
            TOP_RIGHT(103, 30, 35, 41,
                    GuiTextures.PROGRESS_BAR_FUSION_REACTOR_DIAGRAM_TR, ProgressWidget.MoveType.VERTICAL_DOWNWARDS),
            BOTTOM_RIGHT(97, 72, 41, 35,
                    GuiTextures.PROGRESS_BAR_FUSION_REACTOR_DIAGRAM_BR, ProgressWidget.MoveType.HORIZONTAL_BACKWARDS);

            private final int x;
            private final int y;
            private final int width;
            private final int height;
            private final TextureArea texture;
            private final ProgressWidget.MoveType moveType;

            Type(int x, int y, int width, int height, TextureArea texture, ProgressWidget.MoveType moveType) {
                this.x = x;
                this.y = y;
                this.width = width;
                this.height = height;
                this.texture = texture;
                this.moveType = moveType;
            }

            public ProgressWidget getWidget(MetaTileEntityCompressedFusionReactor instance) {
                return new ProgressWidget(
                        () -> instance.recipeMapWorkable.isActive() ?
                                instance.progressBarSupplier.getSupplier(this).getAsDouble() : 0,
                        x, y, width, height, texture, moveType)
                        .setIgnoreColor(true)
                        .setHoverTextConsumer(
                                tl -> MultiblockDisplayText.builder(tl, instance.isStructureFormed())
                                        .setWorkingStatus(instance.recipeMapWorkable.isWorkingEnabled(),
                                                instance.recipeMapWorkable.isActive())
                                        .addWorkingStatusLine());
            }
        }
    }

    private class CompressedFusionReactorRecipeLogic extends MultiblockRecipeLogic {

        public CompressedFusionReactorRecipeLogic(MetaTileEntityCompressedFusionReactor tileEntity) {
            super(tileEntity);
        }

        /**
         * OC Duration Divisor Getter.
         *
         * <p>
         * Get {@code 2.0D} (Duration / 2) when {@code tier} less than 4.
         * Get {@code 4.0D} (Duration / 4) when {@code tier} bigger than or equal to 4.
         * </p>
         *
         * @return OC Duration Divisor.
         */
        @Override
        protected double getOverclockingDurationDivisor() {
            if (tier >= 4) {
                return 4.0D;
            } else {
                return 2.0D;
            }
        }

        /**
         * OC Voltage Multiplier Getter.
         *
         * <p>
         * Get {@code 2.0D} (Energy consumed ×2) when {@code tier} less than 4.
         * Get {@code 4.0D} (Energy consumed ×4) when {@code tier} bigger than or equal to 4.
         *
         *     <ul>
         *         <li>Mark 1-3: 2/2 Perfect OC.</li>
         *         <li>Mark 4 and Mark 5: 4/4 Perfect OC.</li>
         *     </ul>
         * </p>
         *
         * @return OC Voltage Multiplier.
         */
        @Override
        protected double getOverclockingVoltageMultiplier() {
            if (tier >= 4) {
                return 4.0D;
            } else {
                return 2.0D;
            }
        }

        @Override
        public long getMaxVoltage() {
            return Math.min(V[tier], super.getMaxVoltage());
        }

        /**
         * Allowed CFR get higher Maximum Parallel Voltage to parallel recipe.
         * Used to allowed {@link #setParallelLimit(int)} in {@link #checkRecipe(Recipe)} use more energy,
         * whether some high energy required recipes cannot paralle.
         *
         * @return Max Parallel Voltage.
         */
        @Override
        public long getMaxParallelVoltage() {
            IEnergyContainer container = ((MetaTileEntityCompressedFusionReactor) this.metaTileEntity).inputEnergyContainers;
            return Math.min(GTValues.V[tier] * getParallelLimit(), container.getInputVoltage());
        }


        @Override
        public void updateWorkable() {
            super.updateWorkable();
            // Drain heat when the reactor is not active, is paused via soft mallet, or does not have enough energy and has fully wiped recipe progress
            // Don't drain heat when there is not enough energy and there is still some recipe progress, as that makes it doubly hard to complete the recipe
            // (Will have to recover heat and recipe progress)
            if (heat > 0) {
                if (!isActive || !workingEnabled || (hasNotEnoughEnergy && progressTime == 0)) {
                    heat = heat <= 10000 ? 0 : (heat - 10000);
                }
            }
        }

        @Override
        public boolean checkRecipe(Recipe recipe) {
            if (!super.checkRecipe(recipe))
                return false;

            //  At first, check if CFR has enough energy to run recipe (this check is very rough),
            //  if {@code EUToStart} property of {@link RecipeMaps#FUSION_RECIPES} is bigger than energy stored, then return false.
            long startCost = recipe.getProperty(FusionEUToStartProperty.getInstance(), 0L);
            if (startCost > energyContainer.getEnergyCapacity())
                return false;

            //  An Extended check of CFR, check tier of CFR and {@code EUToStart} of recipes,
            //  CFR cannot run recipes higher than its {@link #tier}.
            final long[] euToStart = {160000000L, 320000000L, 640000000L, 1280000000L, 2560000000L, 5120000000L};
            if (startCost > euToStart[tier - 6])
                return false;

            //  Set Parallel to Recipe Map
            int parallelBase = 64;

            switch (tier) {
                case LuV -> {
                    if (startCost <= 160000000L) {
                        this.setParallelLimit(parallelBase);
                    }
                }
                case ZPM -> {
                    if (startCost <= 160000000L) {
                        this.setParallelLimit(parallelBase * 2);
                    } else if (startCost <= 320000000L) {
                        this.setParallelLimit(parallelBase);
                    }
                }
                case UV -> {
                    if (startCost <= 160000000L) {
                        this.setParallelLimit(parallelBase * 3);
                    } else if (startCost <= 320000000L) {
                        this.setParallelLimit(parallelBase * 2);
                    } else if (startCost <= 640000000L) {
                        this.setParallelLimit(parallelBase);
                    }
                }
                case UHV -> {
                    if (startCost <= 160000000L) {
                        this.setParallelLimit(parallelBase * 4);
                    } else if (startCost <= 320000000L) {
                        this.setParallelLimit(parallelBase * 3);
                    } else if (startCost <= 640000000L) {
                        this.setParallelLimit(parallelBase * 2);
                    } else if (startCost <= 1280000000L) {
                        this.setParallelLimit(parallelBase);
                    }
                }
                case UEV -> {
                    if (startCost <= 160000000L) {
                        this.setParallelLimit(parallelBase * 5);
                    } else if (startCost <= 320000000L) {
                        this.setParallelLimit(parallelBase * 4);
                    } else if (startCost <= 640000000L) {
                        this.setParallelLimit(parallelBase * 3);
                    } else if (startCost <= 1280000000L) {
                        this.setParallelLimit(parallelBase * 2);
                    } else if (startCost <= 2560000000L) {
                        this.setParallelLimit(parallelBase);
                    }
                }
                case UIV -> {
                    if (startCost <= 160000000L) {
                        this.setParallelLimit(parallelBase * 6);
                    } else if (startCost <= 320000000L) {
                        this.setParallelLimit(parallelBase * 5);
                    } else if (startCost <= 640000000L) {
                        this.setParallelLimit(parallelBase * 4);
                    } else if (startCost <= 1280000000L) {
                        this.setParallelLimit(parallelBase * 3);
                    } else if (startCost <= 2560000000L) {
                        this.setParallelLimit(parallelBase * 2);
                    } else if (startCost <= 5120000000L) {
                        this.setParallelLimit(parallelBase);
                    }
                }
            }

            //  Differential of Heat.
            long heatDiff = startCost - heat;

            //  If Heat Stored is bigger than or equal to Energy required, then return true.
            if (heatDiff <= 0)
                return true;

            //  If the remaining energy needed is more than stored, do not run
            if (energyContainer.getEnergyStored() < heatDiff)
                return false;

            //  Remove the energy needed
            energyContainer.removeEnergy(heatDiff);
            //  Increase the stored heat
            heat += heatDiff;
            return true;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = super.serializeNBT();
            tag.setLong("Heat", heat);
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound compound) {
            super.deserializeNBT(compound);
            heat = compound.getLong("Heat");
        }
    }
}