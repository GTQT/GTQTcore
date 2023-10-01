package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.*;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.interpolate.Eases;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.RenderBufferHelper;
import gregtech.client.utils.RenderUtil;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockFusionCasing;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class MetaTileEntityCompressedFusionReactor extends RecipeMapMultiblockController implements IFastRenderMetaTileEntity {
    protected int heatingCoilLevel;
    protected int heatingCoilDiscount;
    
    protected int tier;
    private EnergyContainerList inputEnergyContainers;
    private long heat = 0; // defined in TileEntityFusionReactor but serialized in FusionRecipeLogic
    private Integer color;

    public int getMaxParallel(int heatingCoilLevel) {
        if (tier == GTValues.UHV)
            return  4* heatingCoilLevel;
        if (tier == GTValues.UEV)
            return  16* heatingCoilLevel;
        return  64* heatingCoilLevel;
    }
    public MetaTileEntityCompressedFusionReactor(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
        this.recipeMapWorkable = new FusionRecipeLogic(this);
        this.tier = tier;
        this.recipeMapWorkable = new MetaTileEntityCompressedFusionReactorWorkable(this);
        this.energyContainer = new EnergyContainerHandler(this, Integer.MAX_VALUE, 0, 0, 0, 0) {
            @Nonnull
            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityCompressedFusionReactor(metaTileEntityId, tier);
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(
                        "                                               ",
                        "                                               ",
                        "                    FCICICF                    ",
                        "                    PCIBICP                    ",
                        "                    FCICICF                    ",
                        "                                               ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "                    FCIBICF                    ",
                        "                   CC     CC                   ",
                        "                   PC     CP                   ",
                        "                   CC     CC                   ",
                        "                    FCIBICF                    ",
                        "                                               ")
                .aisle(
                        "                    FCIBICF                    ",
                        "                   CC     CC                   ",
                        "                CCCCC     CCCCC                ",
                        "                PPPHHHHHHHHHPPP                ",
                        "                CCCCC     CCCCC                ",
                        "                   CC     CC                   ",
                        "                    FCIBICF                    ")
                .aisle(
                        "                    FCIBICF                    ",
                        "                CCCCC     CCCCC                ",
                        "              CCCCCHHHHHHHHHCCCCC              ",
                        "              PPHHHHHHHHHHHHHHHPP              ",
                        "              CCCCCHHHHHHHHHCCCCC              ",
                        "                CCCCC     CCCCC                ",
                        "                    FCIBICF                    ")
                .aisle(
                        "                    FCIBICF                    ",
                        "              CCCCCCC     CCCCCCC              ",
                        "            CCCCHHHCC     CCHHHCCCC            ",
                        "            PCHHHHHHHHHHHHHHHHHHHCP            ",
                        "            CCCCHHHCC     CCHHHCCCC            ",
                        "              CCCCCCC     CCCCCCC              ",
                        "                    FCIBICF                    ")
                .aisle(
                        "                                               ",
                        "            CCCCCCC FCIBICF CCCCCCC            ",
                        "           CCCHHCCCCC     CCCCCHHCCC           ",
                        "           PHHHHHHHPC     CPHHHHHHHP           ",
                        "           CCCHHCCCCC     CCCCCHHCCC           ",
                        "            CCCCCCC FCIBICF CCCCCCC            ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "           CCCCC               CCCCC           ",
                        "          ECHHCCCCC FCICICF CCCCCHHCE          ",
                        "          PHHHHHPPP FCIBICF PPPHHHHHP          ",
                        "          ECHHCCCCC FCICICF CCCCCHHCE          ",
                        "           CCCCC               CCCCC           ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "          CCCC                   CCCC          ",
                        "         CCHCCCC               CCCCHCC         ",
                        "         PHHHHPP               PPHHHHP         ",
                        "         CCHCCCC               CCCCHCC         ",
                        "          CCCC                   CCCC          ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "         CCC                       CCC         ",
                        "        CCHCCC                   CCCHCC        ",
                        "        PHHHPP                   PPHHHP        ",
                        "        CCHCCC                   CCCHCC        ",
                        "         CCC                       CCC         ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "        CCC                         CCC        ",
                        "       CCHCE                       ECHCC       ",
                        "       PHHHP                       PHHHP       ",
                        "       CCHCE                       ECHCC       ",
                        "        CCC                         CCC        ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "       CCC                           CCC       ",
                        "      ECHCC                         CCHCE      ",
                        "      PHHHP                         PHHHP      ",
                        "      ECHCC                         CCHCE      ",
                        "       CCC                           CCC       ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "      CCC                             CCC      ",
                        "     CCHCE                           ECHCC     ",
                        "     PHHHP                           PHHHP     ",
                        "     CCHCE                           ECHCC     ",
                        "      CCC                             CCC      ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "     CCC                               CCC     ",
                        "    CCHCC                             CCHCC    ",
                        "    PHHHC                             PHHHP    ",
                        "    CCHCC                             CCHCC    ",
                        "     CCC                               CCC     ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "     CCC                               CCC     ",
                        "    CCHCC                             CCHCC    ",
                        "    PHHHP                             PHHHP    ",
                        "    CCHCC                             CCHCC    ",
                        "     CCC                               CCC     ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "    CCC                                 CCC    ",
                        "   CCHCC                               CCHCC   ",
                        "   PHHHP                               PHHHP   ",
                        "   CCHCC                               CCHCC   ",
                        "    CCC                                 CCC    ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "    CCC                                 CCC    ",
                        "   CCHCC                               CCHCC   ",
                        "   PHHHP                               PHHHP   ",
                        "   CCHCC                               CCHCC   ",
                        "    CCC                                 CCC    ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "   CCC                                   CCC   ",
                        "  CCHCC                                 CCHCC  ",
                        "  PHHHP                                 PHHHP  ",
                        "  CCHCC                                 CCHCC  ",
                        "   CCC                                   CCC   ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "   CCC                                   CCC   ",
                        "  CCHCC                                 CCHCC  ",
                        "  PHHHP                                 PHHHP  ",
                        "  CCHCC                                 CCHCC  ",
                        "   CCC                                   CCC   ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "   CCC                                   CCC   ",
                        "  CCHCC                                 CCHCC  ",
                        "  PHHHP                                 PHHHP  ",
                        "  CCHCC                                 CCHCC  ",
                        "   CCC                                   CCC   ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "  CCC                                     CCC  ",
                        " CCHCC                                   CCHCC ",
                        " PHHHP                                   PHHHP ",
                        " CCHCC                                   CCHCC ",
                        "  CCC                                     CCC  ",
                        "                                               ")
                .aisle(
                        "  FFF                                     FFF  ",
                        " FCCCF                                   FCCCF ",
                        "FCCHCCF                                 FCCHCCF",
                        "PCHHHCP                                 PCHHHCP",
                        "FCCHCCF                                 FCCHCCF",
                        " FCCCF                                   FCCCF ",
                        "  FFF                                     FFF  ")
                .aisle(
                        "  CCC                                     CCC  ",
                        " C   C                                   C   C ",
                        "C  H  C                                 C  H  C",
                        "P HHH P                                 P HHH P",
                        "C  H  C                                 C  H  C",
                        " C   C                                   C   C ",
                        "  CCC                                     CCC  ")
                .aisle(
                        "  III                                     III  ",
                        " I   I                                   I   I ",
                        "I  H  I                                 I  H  I",
                        "I HHH I                                 I HHH I",
                        "I  H  I                                 I  H  I",
                        " I   I                                   I   I ",
                        "  III                                     III  ")
                .aisle(
                        "  CBC                                     CBC  ",
                        " B   B                                   B   B ",
                        "C  H  C                                 C  H  C",
                        "P HHH P                                 P HHH P",
                        "C  H  C                                 C  H  C",
                        " B   B                                   B   B ",
                        "  CBC                                     CBC  ")
                .aisle(
                        "  III                                     III  ",
                        " I   I                                   I   I ",
                        "I  H  I                                 I  H  I",
                        "I HHH I                                 I HHH I",
                        "I  H  I                                 I  H  I",
                        " I   I                                   I   I ",
                        "  III                                     III  ")
                .aisle(
                        "  CCC                                     CCC  ",
                        " C   C                                   C   C ",
                        "C  H  C                                 C  H  C",
                        "P HHH P                                 P HHH P",
                        "C  H  C                                 C  H  C",
                        " C   C                                   C   C ",
                        "  CCC                                     CCC  ")
                .aisle(
                        "  FFF                                     FFF  ",
                        " FCCCF                                   FCCCF ",
                        "FCCHCCF                                 FCCHCCF",
                        "PCHHHCP                                 PCHHHCP",
                        "FCCHCCF                                 FCCHCCF",
                        " FCCCF                                   FCCCF ",
                        "  FFF                                     FFF  ")
                .aisle(
                        "                                               ",
                        "  CCC                                     CCC  ",
                        " CCHCC                                   CCHCC ",
                        " PHHHP                                   PHHHP ",
                        " CCHCC                                   CCHCC ",
                        "  CCC                                     CCC  ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "   CCC                                   CCC   ",
                        "  CCHCC                                 CCHCC  ",
                        "  PHHHP                                 PHHHP  ",
                        "  CCHCC                                 CCHCC  ",
                        "   CCC                                   CCC   ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "   CCC                                   CCC   ",
                        "  CCHCC                                 CCHCC  ",
                        "  PHHHP                                 PHHHP  ",
                        "  CCHCC                                 CCHCC  ",
                        "   CCC                                   CCC   ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "   CCC                                   CCC   ",
                        "  CCHCC                                 CCHCC  ",
                        "  PHHHP                                 PHHHP  ",
                        "  CCHCC                                 CCHCC  ",
                        "   CCC                                   CCC   ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "    CCC                                 CCC    ",
                        "   CCHCC                               CCHCC   ",
                        "   PHHHP                               PHHHP   ",
                        "   CCHCC                               CCHCC   ",
                        "    CCC                                 CCC    ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "    CCC                                 CCC    ",
                        "   CCHCC                               CCHCC   ",
                        "   PHHHP                               PHHHP   ",
                        "   CCHCC                               CCHCC   ",
                        "    CCC                                 CCC    ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "     CCC                               CCC     ",
                        "    CCHCC                             CCHCC    ",
                        "    PHHHP                             PHHHP    ",
                        "    CCHCC                             CCHCC    ",
                        "     CCC                               CCC     ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "     CCC                               CCC     ",
                        "    CCHCC                             CCHCC    ",
                        "    PHHHP                             PHHHP    ",
                        "    CCHCC                             CCHCC    ",
                        "     CCC                               CCC     ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "      CCC                             CCC      ",
                        "     CCHCE                           ECHCC     ",
                        "     PHHHP                           PHHHP     ",
                        "     CCHCE                           ECHCC     ",
                        "      CCC                             CCC      ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "       CCC                           CCC       ",
                        "      ECHCC                         CCHCE      ",
                        "      PHHHP                         PHHHP      ",
                        "      ECHCC                         CCHCE      ",
                        "       CCC                           CCC       ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "        CCC                         CCC        ",
                        "       CCHCE                       ECHCC       ",
                        "       PHHHP                       PHHHP       ",
                        "       CCHCE                       ECHCC       ",
                        "        CCC                         CCC        ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "         CCC                       CCC         ",
                        "        CCHCCC                   CCCHCC        ",
                        "        PHHHPP                   PPHHHP        ",
                        "        CCHCCC                   CCCHCC        ",
                        "         CCC                       CCC         ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "          CCCC                   CCCC          ",
                        "         CCHCCCC               CCCCHCC         ",
                        "         PHHHHPP               PPHHHHP         ",
                        "         CCHCCCC               CCCCHCC         ",
                        "          CCCC                   CCCC          ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "           CCCCC               CCCCC           ",
                        "          ECHHCCCCC FCICICF CCCCCHHCE          ",
                        "          PHHHHHPPP FCIBICF PPPHHHHHP          ",
                        "          ECHHCCCCC FCICICF CCCCCHHCE          ",
                        "           CCCCC               CCCCC           ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "            CCCCCCC FCIBICF CCCCCCC            ",
                        "           CCCHHCCCCC     CCCCCHHCCC           ",
                        "           PHHHHHHHPC     CPHHHHHHHP           ",
                        "           CCCHHCCCCC     CCCCCHHCCC           ",
                        "            CCCCCCC FCIBICF CCCCCCC            ",
                        "                                               ")
                .aisle(
                        "                    FCIBICF                    ",
                        "              CCCCCCC     CCCCCCC              ",
                        "            CCCCHHHCC     CCHHHCCCC            ",
                        "            PPHHHHHHHHHHHHHHHHHHHPP            ",
                        "            CCCCHHHCC     CCHHHCCCC            ",
                        "              CCCCCCC     CCCCCCC              ",
                        "                    FCIBICF                    ")
                .aisle(
                        "                    FCIBICF                    ",
                        "                CCCCC     CCCCC                ",
                        "              CCCCCHHHHHHHHHCCCCC              ",
                        "              PPHHHHHHHHHHHHHHHPP              ",
                        "              CCCCCHHHHHHHHHCCCCC              ",
                        "                CCCCC     CCCCC                ",
                        "                    FCIBICF                    ")
                .aisle(
                        "                    FCIBICF                    ",
                        "                   CC     CC                   ",
                        "                CCCCC     CCCCC                ",
                        "                PPPHHHHHHHHHPPP                ",
                        "                CCCCC     CCCCC                ",
                        "                   CC     CC                   ",
                        "                    FCIBICF                    ")
                .aisle(
                        "                                               ",
                        "                    FCIBICF                    ",
                        "                   CC     CC                   ",
                        "                   PC     CP                   ",
                        "                   CC     CC                   ",
                        "                    FCIBICF                    ",
                        "                                               ")
                .aisle(
                        "                                               ",
                        "                                               ",
                        "                    FCICICF                    ",
                        "                    FCI~ICF                    ",
                        "                    FCICICF                    ",
                        "                                               ",
                        "                                               ")
                .where('~', selfPredicate())
                .where('C', states(getCasingState()))
                .where('E', states(getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('F', states(MetaBlocks.FRAMES.get(Materials.NaquadahAlloy).getBlock(Materials.NaquadahAlloy)))
                .where('H', states(getCasingState1()))
                .where('P', states(getCasingState2()))
                .where('I', heatingCoils())
                .where('B', states(this.getGlassState()))
                .where(' ', any())
                .build();
    }
    private IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }
    private static IBlockState getCasingState1() {
        return MetaBlocks.FUSION_CASING.getState(BlockFusionCasing.CasingType.FUSION_COIL);
    }


    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        if (tier == GTValues.UHV)
            return  GTQTTextures.COMPRESSED_FUSION_REACTOR_MKI_CASING;
        if (tier == GTValues.UEV)
            return  GTQTTextures.COMPRESSED_FUSION_REACTOR_MKII_CASING;
        return  GTQTTextures.COMPRESSED_FUSION_REACTOR_MKIII_CASING;
    }

    private IBlockState getCasingState() {
        if (tier == GTValues.UHV)
            return  GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.COMPRESSED_FUSION_REACTOR_MKI_CASING);
        if (tier == GTValues.UEV)
            return  GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.COMPRESSED_FUSION_REACTOR_MKII_CASING);
        return  GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.COMPRESSED_FUSION_REACTOR_MKIII_CASING);
    }

    private IBlockState getCasingState2() {
        if (tier == GTValues.UHV)
            return  GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_2);
        if (tier == GTValues.UEV)
            return  GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_0);

        return  GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_4);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        long energyStored = this.energyContainer.getEnergyStored();
        super.formStructure(context);
        this.initializeAbilities();
        ((EnergyContainerHandler) this.energyContainer).setEnergyStored(energyStored);
        Object coilType = context.get("CoilType");
        if (coilType instanceof IHeatingCoilBlockStats) {
            this.heatingCoilLevel = ((IHeatingCoilBlockStats) coilType).getLevel();
            this.heatingCoilDiscount = ((IHeatingCoilBlockStats) coilType).getEnergyDiscount();
        } else {
            this.heatingCoilLevel = BlockWireCoil.CoilType.CUPRONICKEL.getLevel();
            this.heatingCoilDiscount = BlockWireCoil.CoilType.CUPRONICKEL.getEnergyDiscount();
        }
    }
    @Override
    protected void initializeAbilities() {
        this.inputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.IMPORT_ITEMS));
        this.inputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.IMPORT_FLUIDS));
        this.outputInventory = new ItemHandlerList(getAbilities(MultiblockAbility.EXPORT_ITEMS));
        this.outputFluidInventory = new FluidTankList(true, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
        List<IEnergyContainer> energyInputs = getAbilities(MultiblockAbility.INPUT_ENERGY);
        this.inputEnergyContainers = new EnergyContainerList(energyInputs);
        long euCapacity = calculateEnergyStorageFactor(energyInputs.size());
        this.energyContainer = new EnergyContainerHandler(this, euCapacity, GTValues.V[tier], 0, 0, 0) {
            @Nonnull
            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
    }

    private long calculateEnergyStorageFactor(int energyInputAmount) {
        return energyInputAmount * (long) Math.pow(2, tier - 6) * 10000000L;
    }

    @Override
    protected void updateFormedValid() {
        if (this.inputEnergyContainers.getEnergyStored() > 0) {
            long energyAdded = this.energyContainer.addEnergy(this.inputEnergyContainers.getEnergyStored());
            if (energyAdded > 0) this.inputEnergyContainers.removeEnergy(energyAdded);
        }
        super.updateFormedValid();
        if (recipeMapWorkable.isWorking() && color == null) {
            if (recipeMapWorkable.getPreviousRecipe() != null && recipeMapWorkable.getPreviousRecipe().getFluidOutputs().size() > 0) {
                int newColor = 0xFF000000 | recipeMapWorkable.getPreviousRecipe().getFluidOutputs().get(0).getFluid().getColor();
                if (!Objects.equals(color, newColor)) {
                    color = newColor;
                    writeCustomData(GregtechDataCodes.UPDATE_COLOR, this::writeColor);
                }
            }
        } else if (!recipeMapWorkable.isWorking() && isStructureFormed() && color != null) {
            color = null;
            writeCustomData(GregtechDataCodes.UPDATE_COLOR, this::writeColor);
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        writeColor(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        readColor(buf);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.UPDATE_COLOR) {
            readColor(buf);
        }
    }

    private void readColor(PacketBuffer buf) {
        color = buf.readBoolean() ? buf.readVarInt() : null;
    }

    private void writeColor(PacketBuffer buf) {
        buf.writeBoolean(color != null);
        if (color != null) {
            buf.writeVarInt(color);
        }
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", heatingCoilLevel));
            textList.add(new TextComponentTranslation("gregtech.multiblock.fusion_reactor.energy", this.energyContainer.getEnergyStored(), this.energyContainer.getEnergyCapacity()));
            textList.add(new TextComponentTranslation("gregtech.multiblock.fusion_reactor.heat", heat));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, @Nonnull List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.fusion_reactor.capacity", calculateEnergyStorageFactor(16) / 1000000L));
        tooltip.add(I18n.format("gregtech.machine.fusion_reactor.overclocking"));
        tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.1"));
        if (tier == GTValues.UHV){
            tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 64));
            tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("惊 鸿 万 物", new Object[0]));}
        if (tier == GTValues.UEV){
            tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 256));
            tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("破 碎 亘 古", new Object[0]));}
        if (tier == GTValues.UIV){
            tooltip.add(I18n.format("gtqtcore.multiblock.ab.tooltip.2", 1024));
            tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("凌 驾 虚 无", new Object[0]));}
    }


    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    public long getHeat() {
        return heat;
    }

    private class FusionRecipeLogic extends MultiblockRecipeLogic {

        public FusionRecipeLogic(MetaTileEntityCompressedFusionReactor tileEntity) {
            super(tileEntity);
        }

        @Override
        protected double getOverclockingDurationDivisor() {
            return 2.0D;
        }

        @Override
        protected double getOverclockingVoltageMultiplier() {
            return 2.0D;
        }

        @Override
        protected long getMaxVoltage() {
            return Math.min(GTValues.V[tier], super.getMaxVoltage());
        }

        @Override
        public void updateWorkable() {
            super.updateWorkable();
            // Drain heat when the reactor is not active, is paused via soft mallet, or does not have enough energy and has fully wiped recipe progress
            // Don't drain heat when there is not enough energy and there is still some recipe progress, as that makes it doubly hard to complete the recipe
            // (Will have to recover heat and recipe progress)
            if ((!(isActive || workingEnabled) || (hasNotEnoughEnergy && progressTime == 0)) && heat > 0) {
                heat = heat <= 10000 ? 0 : (heat - 10000);
            }
        }

        @Override
        public boolean checkRecipe(@Nonnull Recipe recipe) {
            if (!super.checkRecipe(recipe))
                return false;

            // if the reactor is not able to hold enough energy for it, do not run the recipe
            if (recipe.getProperty(FusionEUToStartProperty.getInstance(), 0L) > energyContainer.getEnergyCapacity())
                return false;

            long heatDiff = recipe.getProperty(FusionEUToStartProperty.getInstance(), 0L) - heat;
            // if the stored heat is >= required energy, recipe is okay to run
            if (heatDiff <= 0)
                return true;

            // if the remaining energy needed is more than stored, do not run
            if (energyContainer.getEnergyStored() < heatDiff)
                return false;

            // remove the energy needed
            energyContainer.removeEnergy(heatDiff);
            // increase the stored heat
            heat += heatDiff;
            return true;
        }

        @Nonnull
        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = super.serializeNBT();
            tag.setLong("Heat", heat);
            return tag;
        }

        @Override
        public void deserializeNBT(@Nonnull NBTTagCompound compound) {
            super.deserializeNBT(compound);
            heat = compound.getLong("Heat");
        }
    }

    @Override
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (color != null && MinecraftForgeClient.getRenderPass() == 0) {
            final int c = color;
            BloomEffectUtil.requestCustomBloom(RENDER_HANDLER, (buffer) -> {
                int color = RenderUtil.colorInterpolator(c, -1).apply(Eases.EaseQuadIn.getInterpolation(Math.abs((Math.abs(getOffsetTimer() % 50) + partialTicks) - 25) / 25));
                float a = (float) (color >> 24 & 255) / 255.0F;
                float r = (float) (color >> 16 & 255) / 255.0F;
                float g = (float) (color >> 8 & 255) / 255.0F;
                float b = (float) (color & 255) / 255.0F;
                Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 7 +0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 7 - 15.5,
                            6, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.X);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 7 +0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 7 - 15.5,
                            12, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Y);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 7 +0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 7 - 15.5,
                            6, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Y);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 7 +0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 7 - 15.5,
                            6, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Z);
                    Tessellator.getInstance().draw();
                }
            });
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.getPos().offset(getFrontFacing().getOpposite()).offset(getFrontFacing().rotateY(), 6),
                this.getPos().offset(getFrontFacing().getOpposite(), 13).offset(getFrontFacing().rotateY().getOpposite(), 6));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }

    static BloomEffectUtil.IBloomRenderFast RENDER_HANDLER = new BloomEffectUtil.IBloomRenderFast() {
        @Override
        public int customBloomStyle() {
            return ConfigHolder.client.shader.fusionBloom.useShader ? ConfigHolder.client.shader.fusionBloom.bloomStyle : -1;
        }

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        @SideOnly(Side.CLIENT)
        public void preDraw(BufferBuilder buffer) {
            BloomEffect.strength = (float) ConfigHolder.client.shader.fusionBloom.strength;
            BloomEffect.baseBrightness = (float) ConfigHolder.client.shader.fusionBloom.baseBrightness;
            BloomEffect.highBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.highBrightnessThreshold;
            BloomEffect.lowBrightnessThreshold = (float) ConfigHolder.client.shader.fusionBloom.lowBrightnessThreshold;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableTexture2D();
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void postDraw(BufferBuilder buffer) {
            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    };
    protected class MetaTileEntityCompressedFusionReactorWorkable extends MultiblockRecipeLogic {
        public MetaTileEntityCompressedFusionReactorWorkable(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public int getParallelLimit() {
            return getMaxParallel(heatingCoilLevel);
        }
    }
}