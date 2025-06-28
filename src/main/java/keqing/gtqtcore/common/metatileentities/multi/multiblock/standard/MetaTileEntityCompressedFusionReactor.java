package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import com.cleanroommc.modularui.api.drawable.IDrawable;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.value.sync.DoubleSyncValue;
import com.cleanroommc.modularui.value.sync.LongSyncValue;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.layout.Column;
import gregtech.api.GTValues;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.EnergyContainerHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.ItemHandlerList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.ProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.ui.*;
import gregtech.api.mui.GTGuiTextures;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.properties.impl.FusionEUToStartProperty;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.api.util.TextComponentUtil;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityLaserHatch;
import keqing.gtqtcore.api.metatileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import static gregtech.api.GTValues.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;

public class MetaTileEntityCompressedFusionReactor extends GTQTNoTierMultiblockController implements IOpticalComputationReceiver, ProgressBarMultiblock {
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
    int requestCWUt;
    //  Internal Energy Container, just like common Fusion Reactors.
    private EnergyContainerList inputEnergyContainers;
    //  Heat, like common Fusion Reactors.
    //  TODO Delete Heat system of CFR?
    private long heat = 0;
    private IOpticalComputationProvider computationProvider;

    public MetaTileEntityCompressedFusionReactor(ResourceLocation metaTileEntityId,
                                                 int tier,
                                                 IBlockState casingState,
                                                 IBlockState coilState,
                                                 IBlockState frameState) {
        super(metaTileEntityId, new RecipeMap[]{
                RecipeMaps.FUSION_RECIPES
        });

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

        //setMaxParallel(auto);
        setMaxParallelFlag(true);
        //setTimeReduce(none);
        setTimeReduceFlag(false);

        if (tier <= 9) setOverclocking(3);
        else setOverclocking(4);
    }

    @Override
    public boolean shouldDelayCheck() {
        return true;
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
                        .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1))
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
                                    return container != null && container.getInputVoltage() <= V[tier];
                                })
                                .toArray(MetaTileEntity[]::new))
                                .setMaxGlobalLimited(32)
                                .setPreviewCount(32)
                        )
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.INPUT_LASER)
                                .stream()
                                .filter(mte -> {
                                    if (mte instanceof MetaTileEntityLaserHatch laserHatch) {
                                        return laserHatch.getTier() <= tier;
                                    }
                                    return false;
                                })
                                .toArray(MetaTileEntity[]::new))
                                .setMaxGlobalLimited(8)
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

        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }

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

        List<IEnergyContainer> energyInputs = new ArrayList<>(this.getAbilities(MultiblockAbility.INPUT_ENERGY));
        energyInputs.addAll(this.getAbilities(MultiblockAbility.INPUT_LASER));
        this.inputEnergyContainers = new EnergyContainerList(energyInputs);

        //  EU Capacity = Energy Hatch amount * Energy Stored (half of original Fusion Reactor).
        long euCapacity = calculateEnergyStorageFactor(energyInputs.size());
        this.energyContainer = new EnergyContainerHandler(this, euCapacity, V[tier], 2L * energyInputs.size(), 0, 0) {
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
        super.updateFormedValid();
        if (!isStructureFormed()) return;
        if (this.inputEnergyContainers.getEnergyStored() > 0) {
            long energyAdded = this.energyContainer.addEnergy(this.inputEnergyContainers.getEnergyStored());
            if (energyAdded > 0)
                this.inputEnergyContainers.removeEnergy(energyAdded);
        }
        if (isActive()) {
            requestCWUt = computationProvider.requestCWUt(2048, false);
        }
    }

    /*
        @Override
        protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
            ///////////////////////////Main GUI
            //  Background
            ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 368, 236);

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
                case UHV -> builder.widget(new ImageWidget(66, 9, 67, 12, GuiTextures.FUSION_REACTOR_MK4_TITLE)
                        .setIgnoreColor(true));
                case UEV -> builder.widget(new ImageWidget(66, 9, 67, 12, GuiTextures.FUSION_REACTOR_MK5_TITLE)
                        .setIgnoreColor(true));
                case UIV -> builder.widget(new ImageWidget(66, 9, 67, 12, GuiTextures.FUSION_REACTOR_MK6_TITLE)
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

            ///////////////////////////智能并行组件
            builder.image(200, 4, 160, 20, GuiTextures.DISPLAY);
            builder.widget((new AdvancedTextWidget(204, 10,  this::addModel, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

            builder.widget(new ClickButtonWidget(200, 28, 80, 20, "gui.mode_switch",
                    clickData -> autoParallelModel = !autoParallelModel));

            //builder.image(280, 4, 80, 20, GuiTextures.DISPLAY);
            builder.widget((new AdvancedTextWidget(284, 10, this::addInfo, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

            builder.widget(new IncrementButtonWidget(280, 28, 40, 20, 1, 4, 16, 64, this::setCurrentParallel)
                    .setDefaultTooltip()
                    .setShouldClientCallback(false));

            builder.widget(new IncrementButtonWidget(320, 28, 40, 20, -1, -4, -16, -64, this::setCurrentParallel)
                    .setDefaultTooltip()
                    .setShouldClientCallback(false));

            builder.widget(
                    new SliderWidget("gui.auto_parallel_limit", 200, 52, 160, 20, 1, getMaxParallel(), limitAutoParallel,
                            this::setLimitAutoParallel).setBackground(SCGuiTextures.DARK_SLIDER_BACKGROUND)
                            .setSliderIcon(SCGuiTextures.DARK_SLIDER_ICON));

            builder.image(200, 76, 160, 20, GuiTextures.DISPLAY);
            builder.widget((new AdvancedTextWidget(204, 82, this::addEnergyHatch, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

            //builder.image(280, 76, 80, 20, GuiTextures.DISPLAY);
            builder.widget((new AdvancedTextWidget(284, 82, this::addOC, 16777215)).setMaxWidthLimit(110).setClickHandler(this::handleDisplayClick));

            builder.widget(new ClickButtonWidget(200, 100, 40, 20, "gui.reset", data ->
                    energyHatchMaxWork = 32).setTooltipText("gui.reset_tooltip"));

            builder.widget(new ClickButtonWidget(240, 100, 40, 20, "gui.recommend", data ->
            {
                energyHatchMaxWork = (int) (this.energyContainer.getEnergyStored() / V[tier]);
                energyHatchMaxWork = Math.max(1, energyHatchMaxWork);
                energyHatchMaxWork = Math.min(energyHatchMaxWork, 128);

            }).setTooltipText("gui.recommend_tooltip"));

            builder.widget(new ClickButtonWidget(280, 100, 80, 20, "gui.oc_parallel_mode",
                    clickData -> OCFirst = !OCFirst).setTooltipText("gui.oc_parallel_mode_tooltip"));

            builder.image(200, 124, 160, 106, GuiTextures.DISPLAY);
            builder.widget((new AdvancedTextWidget(204, 128, this::addDisplayText, 16777215)).setMaxWidthLimit(160).setClickHandler(this::handleDisplayClick));


            //  Player Inventory
            builder.bindPlayerInventory(entityPlayer.inventory, 153);
            return builder;
        }
    */
    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }


    protected void sdfsf(List<ITextComponent> textList) {



    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        long actuallyEnergyStored = calculateEnergyStorageFactor(32) / 1000000L;
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
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc1"));
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
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc1"));
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
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc1"));
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
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.common_oc"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc2"));
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
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.common_oc"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc2"));
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
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.common_oc"));
                tooltip.add(I18n.format("gtqtcore.machine.compressed_fusion_reactor.perfect_oc"));
            }
        }
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.laser.tooltip"));
        tooltip.add(I18n.format("gtqtcore.multiblock.kq.acc.tooltip"));
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public long getHeat() {
        return heat;
    }

    protected long getEnergyStored() {
        return energyContainer.getEnergyStored();
    }

    @Override
    protected MultiblockUIFactory createUIFactory() {
        IDrawable title;
        if (tier == LuV) {
            // MK1
            title = GTGuiTextures.FUSION_REACTOR_MK1_TITLE;
        } else if (tier == ZPM) {
            // MK2
            title = GTGuiTextures.FUSION_REACTOR_MK2_TITLE;
        } else if (tier == GTValues.UV) {
            // MK3
            title = GTGuiTextures.FUSION_REACTOR_MK3_TITLE;
        } else if (tier == GTValues.UHV) {
            // MK1
            title = GTGuiTextures.FUSION_REACTOR_MK4_TITLE;
        } else if (tier == GTValues.UEV) {
            // MK2
            title = GTGuiTextures.FUSION_REACTOR_MK5_TITLE;
        } else {
            // MK3
            title = GTGuiTextures.FUSION_REACTOR_MK6_TITLE;
        }

        DoubleSyncValue progress = new DoubleSyncValue(recipeMapWorkable::getProgressPercent);
        return super.createUIFactory()
                .setScreenHeight(138)
                .disableDisplayText()
                .addScreenChildren((parent, syncManager) -> {
                    var status = MultiblockUIFactory.builder("status", syncManager);
                    status.setAction(b -> b.structureFormed(true)
                            .addEnergyUsageLine(getEnergyContainer())
                            .addEnergyTierLine(GTUtility.getTierByVoltage(recipeMapWorkable.getMaxVoltage()))
                            .addProgressLine(recipeMapWorkable.getProgress(), recipeMapWorkable.getMaxProgress())
                            .setWorkingStatus(recipeMapWorkable.isWorkingEnabled(), recipeMapWorkable.isActive())
                            .addWorkingStatusLine()
                            .addRecipeOutputLine(recipeMapWorkable));
                    parent.child(new Column()
                            .padding(4)
                            .expanded()
                            .child(title.asWidget()
                                    .marginBottom(8)
                                    .size(69, 12))
                            .child(new com.cleanroommc.modularui.widgets.ProgressWidget()
                                    .size(77, 77)
                                    .tooltipAutoUpdate(true)
                                    .tooltipBuilder(status::build)
                                    .background(GTGuiTextures.FUSION_DIAGRAM.asIcon()
                                            .size(89, 101)
                                            .marginTop(11))
                                    .direction(com.cleanroommc.modularui.widgets.ProgressWidget.Direction.CIRCULAR_CW)
                                    .value(progress)
                                    .texture(null, GTGuiTextures.FUSION_PROGRESS, 77))
                            .child(GTGuiTextures.FUSION_LEGEND.asWidget()
                                    .left(4)
                                    .bottom(4)
                                    .size(108, 41)));
                })
                ;
    }

    @Override
    public int getProgressBarCount() {
        return 2;
    }

    @Override
    public void registerBars(List<UnaryOperator<TemplateBarBuilder>> bars, PanelSyncManager syncManager) {
        LongSyncValue capacity = new LongSyncValue(energyContainer::getEnergyCapacity);
        syncManager.syncValue("capacity", capacity);
        LongSyncValue stored = new LongSyncValue(energyContainer::getEnergyStored);
        syncManager.syncValue("stored", stored);
        LongSyncValue heat = new LongSyncValue(this::getHeat);
        syncManager.syncValue("heat", heat);

        bars.add(barTest -> barTest
                .progress(() -> capacity.getLongValue() > 0 ?
                        1.0 * stored.getLongValue() / capacity.getLongValue() : 0)
                .texture(GTGuiTextures.PROGRESS_BAR_FUSION_ENERGY)
                .tooltipBuilder(tooltip -> tooltip
                        .add(KeyUtil.lang(
                                "gregtech.multiblock.energy_stored",
                                stored.getLongValue(), capacity.getLongValue()))));

        bars.add(barTest -> barTest
                .texture(GTGuiTextures.PROGRESS_BAR_FUSION_HEAT)
                .tooltipBuilder(tooltip -> {
                    IKey heatInfo = KeyUtil.string(TextFormatting.AQUA,
                            "%,d / %,d EU",
                            heat.getLongValue(), capacity.getLongValue());
                    tooltip.add(KeyUtil.lang(
                            "gregtech.multiblock.fusion_reactor.heat",
                            heatInfo));
                })
                .progress(() -> capacity.getLongValue() > 0 ?
                        1.0 * heat.getLongValue() / capacity.getLongValue() : 0));
    }

    private class CompressedFusionReactorRecipeLogic extends GTQTMultiblockLogic {

        public CompressedFusionReactorRecipeLogic(MetaTileEntityCompressedFusionReactor tileEntity) {
            super(tileEntity);
        }

        @Override
        public long getMaxVoltage() {
            return Math.min(V[tier], super.getMaxVoltage());
        }

        @Override
        public long getMaxParallelVoltage() {
            if (OCFirst) return super.getMaxParallelVoltage();
            return super.getMaxVoltage() * getParallelLimit();
        }

        @Override
        public long getMaximumOverclockVoltage() {
            if (OCFirst) return inputEnergyContainers.getInputVoltage();
            return super.getMaximumOverclockVoltage();
        }

        @Override
        public int getParallelLimit() {
            return Math.min(autoParallelModel ? autoParallel : customParallel, super.getParallelLimit());
        }

        @Override
        public void setParallelLimit(int amount) {
            super.setParallelLimit(amount);
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
                        setMaxParallel(parallelBase);
                    }
                }
                case ZPM -> {
                    if (startCost <= 160000000L) {
                        setMaxParallel(parallelBase * 2);
                    } else if (startCost <= 320000000L) {
                        setMaxParallel(parallelBase);
                    }
                }
                case UV -> {
                    if (startCost <= 160000000L) {
                        setMaxParallel(parallelBase * 3);
                    } else if (startCost <= 320000000L) {
                        setMaxParallel(parallelBase * 2);
                    } else if (startCost <= 640000000L) {
                        setMaxParallel(parallelBase);
                    }
                }
                case UHV -> {
                    if (startCost <= 160000000L) {
                        setMaxParallel(parallelBase * 4);
                    } else if (startCost <= 320000000L) {
                        setMaxParallel(parallelBase * 3);
                    } else if (startCost <= 640000000L) {
                        setMaxParallel(parallelBase * 2);
                    } else if (startCost <= 1280000000L) {
                        setMaxParallel(parallelBase);
                    }
                }
                case UEV -> {
                    if (startCost <= 160000000L) {
                        setMaxParallel(parallelBase * 5);
                    } else if (startCost <= 320000000L) {
                        setMaxParallel(parallelBase * 4);
                    } else if (startCost <= 640000000L) {
                        setMaxParallel(parallelBase * 3);
                    } else if (startCost <= 1280000000L) {
                        setMaxParallel(parallelBase * 2);
                    } else if (startCost <= 2560000000L) {
                        setMaxParallel(parallelBase);
                    }
                }
                case UIV -> {
                    if (startCost <= 160000000L) {
                        setMaxParallel(parallelBase * 6);
                    } else if (startCost <= 320000000L) {
                        setMaxParallel(parallelBase * 5);
                    } else if (startCost <= 640000000L) {
                        setMaxParallel(parallelBase * 4);
                    } else if (startCost <= 1280000000L) {
                        setMaxParallel(parallelBase * 3);
                    } else if (startCost <= 2560000000L) {
                        setMaxParallel(parallelBase * 2);
                    } else if (startCost <= 5120000000L) {
                        setMaxParallel(parallelBase);
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

        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getAccelerateByCWU(requestCWUt)));
        }
    }
}