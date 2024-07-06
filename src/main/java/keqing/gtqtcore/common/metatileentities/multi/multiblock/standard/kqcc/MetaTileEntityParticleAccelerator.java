package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.*;
import gregtech.api.capability.impl.*;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityFusionReactor;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.GTQTCapabilities;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.EUToStartProperty;
import keqing.gtqtcore.api.recipes.properties.PAProperty;
import keqing.gtqtcore.api.recipes.properties.ScatteringProperty;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.common.metatileentities.single.electric.MetaTileEntityParticleAcceleratorIO;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.*;

import static gregtech.api.GTValues.EV;
import static gregtech.api.recipes.RecipeMaps.ASSEMBLER_RECIPES;
import static gregtech.api.unification.material.Materials.Lava;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.*;
import static keqing.gtqtcore.api.unification.GTQTMaterials.LiquidNitrogen;
import static keqing.gtqtcore.common.items.GTQTMetaItems.ALPHA;

public class MetaTileEntityParticleAccelerator extends GTQTRecipeMapMultiblockController implements IOpticalComputationReceiver {

    private IOpticalComputationProvider computationProvider;
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    private float speed;       //速度
    private int angle;       //角度

    private int Mode;        //加速 稳定 减速模式

    Boolean shuliu;
    Boolean bashi;
    Boolean hehecheng;

    int time;
    //粒子加速 基础
    //靶室  拓展1（直线）
    //核合成  拓展2（环形）
    //拓展粒子源 拓展3
    //束流收集 拓展4（直线）
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if(this.getRecipeMap() == TARGET_CHAMBER||this.getRecipeMap() == NUCLEOSYNTHESIS) {
            if(recipe.getProperty(ScatteringProperty.getInstance(), 0) == angle)
                if(recipe.getProperty(EUToStartProperty.getInstance(), 0) <=speed)
            return super.checkRecipe(recipe, consumeIfSuccess);
        }

        return super.checkRecipe(recipe, consumeIfSuccess) ;
    }

    public MetaTileEntityParticleAccelerator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[] {
                PARTICLE_ACCELERATOR_RECIPES,//粒子加速(单纯的算力消耗+随机路径)
                TARGET_CHAMBER,//靶室（启动耗能+散射截面）
                NUCLEOSYNTHESIS,//核合成（启动耗能+散射截面）
                BEAM_COLLECTION//束流收集


        });
        this.energyContainer = new EnergyContainerHandler(this, 0L, 0L, 0L, 0L, 0L) {
            public  String getName() {
                return "EnergyContainerInternal";
            }
        };
        //this.recipeMapWorkable = new ChemicalPlantLogic(this);
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("                                               ", "                                               ", "                    CAAAAAC                    ", "                    CAADAAC                    ", "                    CAAAAAC                    ", "                                               ", "                                               ")
                .aisle("                                               ", "                    CADDDAC                    ", "                   AA     AAfffffffffffffdddddf", "                   AA     AAfffffffffffffdddddf", "                   AA     AAfffffffffffffdddddf", "                    CADDDAC                    ", "                                               ")
                .aisle("                    CADDDAC                    ", "                   AA     AAffffffffffffddddddf", "                AAAAA     AAAAA          gggggf", "                AAAGGGGGGGGGAAAhhhhhhhhhhgggggf", "                AAAAA     AAAAA          gggggf", "                   AA     AAffffffffffffddddddf", "                    CADDDAC                    ")
                .aisle("                    CAADAAC                    ", "                AAAAA     AAAAAfffffffffddddddf", "              AAAAAGGGGGGGGGAAAAAhhhhhhhhgggggf", "              AAGGGGGGGGGGGGGGGAA             f", "              AAAAAGGGGGGGGGAAAAAhhhhhhhhgggggf", "                AAAAA     AAAAAfffffffffddddddf", "                    CAAAAAC                    ")
                .aisle("                    CADDDAC                    ", "              AAAAAAA     AAAAAAAfffffffddddddf", "            AAAAGGGAA     AAGGGAAAA      gggggf", "            AAGGGGGGGGGGGGGGGGGGGAAhhhhhhgggggf", "            AAAAGGGAA     AAGGGAAAA      gggggf", "              AAAAAAA     AAAAAAAfffffffddddddf", "                    CADDDAC                    ")
                .aisle("                                               ", "            AAAAAAA CADDDAC AAAAAAA            ", "           AAAGGAAAAA     AAAAAGGAAAfffffdddddf", "           AGGGGGGGAA     AAGGGGGGGAfffffdddddf", "           AAAGGAAAAA     AAAAAGGAAAfffffdddddf", "            AAAAAAA CADDDAC AAAAAAA            ", "                                               ")
                .aisle("                                               ", "           AAAAA               AAAAA           ", "          AAGGAAAAA CAAAAAC AAAAAGGAA          ", "          AGGGGGAAAA AADAA AAAAGGGGGA          ", "          AAGGAAAAA CAAAAAC AAAAAGGAA          ", "           AAAAA               AAAAA           ", "                                               ")
                .aisle("                                               ", "          AAAA                   AAAA          ", "         AAGAAAA               AAAAGAA         ", "         AGGGGAA               AAGGGGA         ", "         AAGAAAA               AAAAGAA         ", "          AAAA                   AAAA          ", "                                               ")
                .aisle("                                               ", "         AAA                       AAA         ", "        AAGAAA                   AAAGAA        ", "        AGGGAA                   AAGGGA        ", "        AAGAAA                   AAAGAA        ", "         AAA                       AAA         ", "                                               ")
                .aisle("                                               ", "        AAA                         AAA        ", "       AAGAA                       AAGAA       ", "       AGGGA                       AGGGA       ", "       AAGAA                       AAGAA       ", "        AAA                         AAA        ", "                                               ")
                .aisle("                                               ", "       AAA                           AAA       ", "      AAGAA                         AAGAA      ", "      AGGGA                         AGGGA      ", "      AAGAA                         AAGAA      ", "       AAA                           AAA       ", "                                               ")
                .aisle("                                               ", "      AAA                             AAA      ", "     AAGAA                           AAGAA     ", "     AGGGA                           AGGGA     ", "     AAGAA                           AAGAA     ", "      AAA                             AAA      ", "                                               ")
                .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA                                 AAGAA  ", "  AGGGA   uuu                           AGGGA  ", "  AAGAA                                 AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA   uuu                           AAGAA  ", "  AGGGA uu   uu                         AGGGA  ", "  AAGAA   uuu                           AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA uu   uu                         AAGAA  ", "  AGGGAu HuuuH u                        AGGGA  ", "  AAGAA uu   uu                         AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                .aisle("                                               ", "  AAA                                     AAA  ", " AAGAA u       u                         AAGAA ", " AGGGAuIuu   uuIu                        AGGGA ", " AAGAA u       u                         AAGAA ", "  AAA                                     AAA  ", "                                               ")
                .aisle("  CCC                                     CCC  ", " CAAAC                                   CAAAC ", "CAAGAAA         u                       CAAGAAC", "CAGGGAAu       u u                      CAGGGAC", "CAAGAAA         u                       CAAGAAC", " CAAAC                                   CAAAC ", "  CCC                                     CCC  ")
                .aisle("  AAA                                     AAA  ", " A   A                                   A   A ", "A  G  A         u                       A  G  A", "A GGG Au       uHu                      A GGG A", "A  G  A         u                       A  G  A", " A   A                                   A   A ", "  AAA                                     AAA  ")
                .aisle("  AAA                                     AAA  ", " A   A                                   D   D ", "A  G  A          u                      A  G  A", "A GGG A         u u                     A GGG A", "A  G  A          u                      A  G  A", " A   A                                   D   D ", "  AAA                                     AAA  ")
                .aisle("  AAA                                     ADA  ", " A   A                                   D   D ", "A  G  A          u                      A  G  A", "A GGG A         u u                     D GGG D", "A  G  A          u                      A  G  A", " A   A                                   D   D ", "  AAA                                     ADA  ")
                .aisle("  AAA                                     AAA  ", " A   A                                   D   D ", "A  G  A          u                      A  G  A", "A GGG A         u u                     A GGG A", "A  G  A          u                      A  G  A", " A   A                                   D   D ", "  AAA                                     AAA  ")
                .aisle("  AAA                                     AAA  ", " A   A                                   A   A ", "A  G  A         u                       A  G  A", "A GGG Au       uHu                      A GGG A", "A  G  A         u                       A  G  A", " A   A                                   A   A ", "  AAA                                     AAA  ")
                .aisle("  CCC                                     CCC  ", " CAAAC                                   CAAAC ", "CAAGAAA         u                       CAAGAAC", "CAGGGAAu       u u                      CAGGGAC", "CAAGAAA         u                       CAAGAAC", " CAAAC                                   CAAAC ", "  CCC                                     CCC  ")
                .aisle("                                               ", "  AAA                                     AAA  ", " AAGAA u       u                         AAGAA ", " AGGGAuIuu   uuIu                        AGGGA ", " AAGAA u       u                         AAGAA ", "  AAA                                     AAA  ", "                                               ")
                .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA uu   uu                         AAGAA  ", "  AGGGAu HuuuH u                        AGGGA  ", "  AAGAA uu   uu                         AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA   uuu                           AAGAA  ", "  AGGGA uu   uu                         AGGGA  ", "  AAGAA   uuu                           AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA                                 AAGAA  ", "  AGGGA   uuu                           AGGGA  ", "  AAGAA                                 AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                .aisle("                                               ", "      AAA                             AAA      ", "     AAGAA                           AAGAA     ", "     AGGGA                           AGGGA     ", "     AAGAA                           AAGAA     ", "      AAA                             AAA      ", "                                               ")
                .aisle("                                               ", "       AAA                           AAA       ", "      AAGAA                         AAGAA      ", "      AGGGA                         AGGGA      ", "      AAGAA                         AAGAA      ", "       AAA                           AAA       ", "                                               ")
                .aisle("                                               ", "        AAA                         AAA        ", "       AAGAA                       AAGAA       ", "       AGGGA                       AGGGA       ", "       AAGAA                       AAGAA       ", "        AAA                         AAA        ", "                                               ")
                .aisle("                                               ", "         AAA                       AAA         ", "        AAGAAA                   AAAGAA        ", "        AGGGAA                   AAGGGA        ", "        AAGAAA                   AAAGAA        ", "         AAA                       AAA         ", "                                               ")
                .aisle("                                               ", "          AAAA                   AAAA          ", "         AAGAAAA               AAAAGAA         ", "         AGGGGAA               AAGGGGA         ", "         AAGAAAA               AAAAGAA         ", "          AAAA                   AAAA          ", "                                               ")
                .aisle("                                               ", "           AAAAA               AAAAA           ", "          AAGGAAAAAAAAAAAAAAAAAAAGGAA          ", "          AGGGGGAAAA AAAAA AAAAGGGGGA          ", "          AAGGAAAAAAAAAAAAAAAAAAAGGAA          ", "           AAAAA               AAAAA           ", "                                               ")
                .aisle("                                               ", "            AAAAAAA CADDDAC AAAAAAA            ", "           AAAGGAAAAA     AAAAAGGAAAeeeeeeeeeee", "           AGGGGGGGAA     AAGGGGGGGAeeeeeeeeeee", "           AAAGGAAAAA     AAAAAGGAAAeeeeeeeeeee", "            AAAAAAA CADDDAC AAAAAAA            ", "                                               ")
                .aisle("                    CADDDAC                    ", "              AAAAAAA     AAAAAAAeeeeeeeeeeeeee", "            AAAAGGGAA     AAGGGAAAAiiiiiiiiiiie", "            AAGGGGGGGGGGGGGGGGGGGAAiiiiiiiiiiie", "            AAAAGGGAA     AAGGGAAAAiiiiiiiiiiie", "              AAAAAAA     AAAAAAAeeeeeeeeeeeeee", "                    CADDDAC                    ")
                .aisle("                    CAADAAC                    ", "                AAAAA     AAAAAeeeeeeeeeeeeeeee", "              AAAAAGGGGGGGGGAAAAAiiiiiiiiiiiiie", "              AAGGGGGGGGGGGGGGGAA             e", "              AAAAAGGGGGGGGGAAAAAiiiiiiiiiiiiie", "                AAAAA     AAAAAeeeeeeeeeeeeeeee", "                    CAADAAC                    ")
                .aisle("                    CADDDAC                    ", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                AAAAA     AAAAAiiiiiiiiiiiiiiie", "                AAAGGGGGGGGGAAAiiiiiiiiiiiiiiie", "                AAAAA     AAAAAiiiiiiiiiiiiiiie", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                    CADDDAC                    ")
                .aisle("                                               ", "                    CADDDAC                    ", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                    CADDDAC                    ", "                                               ")
                .aisle("                                               ", "                                               ", "                   A AAAAA A                   ", "                   A AABXA A                   ", "                   A AAAAA A                   ", "                                               ", "                                               ")
/*
主体：
//A外壳
//B控制器
//C框架
//D玻璃
//G电磁扼

束流
//e外壳
//i电磁扼

靶室
//d外壳
//f玻璃
//h探针
//g束流

核合成
//u外壳
//I束流
//H电磁扼

 */
// .where('F', GTQTTraceabilityPredicate.optionalStates("AuxiliaryBlastFurnace2", getFireBoxState()))

                .where('B', selfPredicate())
                .where('X', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('A', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(4))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(4)))
                .where('C', states(getFrameState()))
                .where('D', states(getGlassState()))
                .where('G', states(getCasingState1()))
                //束流
                .where('e', GTQTTraceabilityPredicate.optionalStates("shuliu", getCasingState()))
                .where('i', GTQTTraceabilityPredicate.optionalStates("shuliu", getCasingState1()))
                //靶室
                .where('d', GTQTTraceabilityPredicate.optionalStates("bashi", getCasingState()))
                .where('f', GTQTTraceabilityPredicate.optionalStates("bashi", getGlassState()))
                .where('h', GTQTTraceabilityPredicate.optionalStates("bashi", getCasingState2()))
                .where('g', GTQTTraceabilityPredicate.optionalStates("bashi", getCasingState3()))
                //核合成
                .where('u', GTQTTraceabilityPredicate.optionalStates("hehecheng", getCasingState()))
                .where('I', GTQTTraceabilityPredicate.optionalStates("hehecheng", getCasingState3()))
                .where('H', GTQTTraceabilityPredicate.optionalStates("hehecheng", getCasingState1()))

                .where(' ', any())
                .build();
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("                                               ", "                                               ", "                    CAAAAAC                    ", "                    CAADAAC                    ", "                    CAAAAAC                    ", "                                               ", "                                               ")
                    .aisle("                                               ", "                    CADDDAC                    ", "                   AA     AAfffffffffffffdddddf", "                   AA     AAfffffffffffffdddddf", "                   AA     AAfffffffffffffdddddf", "                    CADDDAC                    ", "                                               ")
                    .aisle("                    CADDDAC                    ", "                   AA     AAffffffffffffddddddf", "                AAAAA     AAAAA          gggggf", "                AAAGGGGGGGGGAAAhhhhhhhhhhgggggf", "                AAAAA     AAAAA          gggggf", "                   AA     AAffffffffffffddddddf", "                    CADDDAC                    ")
                    .aisle("                    CAADAAC                    ", "                AAAAA     AAAAAfffffffffddddddf", "              AAAAAGGGGGGGGGAAAAAhhhhhhhhgggggf", "              AAGGGGGGGGGGGGGGGAA             f", "              AAAAAGGGGGGGGGAAAAAhhhhhhhhgggggf", "                AAAAA     AAAAAfffffffffddddddf", "                    CAAAAAC                    ")
                    .aisle("                    CADDDAC                    ", "              AAAAAAA     AAAAAAAfffffffddddddf", "            AAAAGGGAA     AAGGGAAAA      gggggf", "            AAGGGGGGGGGGGGGGGGGGGAAhhhhhhgggggf", "            AAAAGGGAA     AAGGGAAAA      gggggf", "              AAAAAAA     AAAAAAAfffffffddddddf", "                    CADDDAC                    ")
                    .aisle("                                               ", "            AAAAAAA CADDDAC AAAAAAA            ", "           AAAGGAAAAA     AAAAAGGAAAfffffdddddf", "           AGGGGGGGAA     AAGGGGGGGAfffffdddddf", "           AAAGGAAAAA     AAAAAGGAAAfffffdddddf", "            AAAAAAA CADDDAC AAAAAAA            ", "                                               ")
                    .aisle("                                               ", "           AAAAA               AAAAA           ", "          AAGGAAAAA CAAAAAC AAAAAGGAA          ", "          AGGGGGAAAA AADAA AAAAGGGGGA          ", "          AAGGAAAAA CAAAAAC AAAAAGGAA          ", "           AAAAA               AAAAA           ", "                                               ")
                    .aisle("                                               ", "          AAAA                   AAAA          ", "         AAGAAAA               AAAAGAA         ", "         AGGGGAA               AAGGGGA         ", "         AAGAAAA               AAAAGAA         ", "          AAAA                   AAAA          ", "                                               ")
                    .aisle("                                               ", "         AAA                       AAA         ", "        AAGAAA                   AAAGAA        ", "        AGGGAA                   AAGGGA        ", "        AAGAAA                   AAAGAA        ", "         AAA                       AAA         ", "                                               ")
                    .aisle("                                               ", "        AAA                         AAA        ", "       AAGAA                       AAGAA       ", "       AGGGA                       AGGGA       ", "       AAGAA                       AAGAA       ", "        AAA                         AAA        ", "                                               ")
                    .aisle("                                               ", "       AAA                           AAA       ", "      AAGAA                         AAGAA      ", "      AGGGA                         AGGGA      ", "      AAGAA                         AAGAA      ", "       AAA                           AAA       ", "                                               ")
                    .aisle("                                               ", "      AAA                             AAA      ", "     AAGAA                           AAGAA     ", "     AGGGA                           AGGGA     ", "     AAGAA                           AAGAA     ", "      AAA                             AAA      ", "                                               ")
                    .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                    .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                    .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                    .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                    .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA                                 AAGAA  ", "  AGGGA   uuu                           AGGGA  ", "  AAGAA                                 AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                    .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA   uuu                           AAGAA  ", "  AGGGA uu   uu                         AGGGA  ", "  AAGAA   uuu                           AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                    .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA uu   uu                         AAGAA  ", "  AGGGAu HuuuH u                        AGGGA  ", "  AAGAA uu   uu                         AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                    .aisle("                                               ", "  AAA                                     AAA  ", " AAGAA u       u                         AAGAA ", " AGGGAuIuu   uuIu                        AGGGA ", " AAGAA u       u                         AAGAA ", "  AAA                                     AAA  ", "                                               ")
                    .aisle("  CCC                                     CCC  ", " CAAAC                                   CAAAC ", "CAAGAAA         u                       CAAGAAC", "CAGGGAAu       u u                      CAGGGAC", "CAAGAAA         u                       CAAGAAC", " CAAAC                                   CAAAC ", "  CCC                                     CCC  ")
                    .aisle("  AAA                                     AAA  ", " A   A                                   A   A ", "A  G  A         u                       A  G  A", "A GGG Au       uHu                      A GGG A", "A  G  A         u                       A  G  A", " A   A                                   A   A ", "  AAA                                     AAA  ")
                    .aisle("  AAA                                     AAA  ", " A   A                                   D   D ", "A  G  A          u                      A  G  A", "A GGG A         u u                     A GGG A", "A  G  A          u                      A  G  A", " A   A                                   D   D ", "  AAA                                     AAA  ")
                    .aisle("  AAA                                     ADA  ", " A   A                                   D   D ", "A  G  A          u                      A  G  A", "A GGG A         u u                     D GGG D", "A  G  A          u                      A  G  A", " A   A                                   D   D ", "  AAA                                     ADA  ")
                    .aisle("  AAA                                     AAA  ", " A   A                                   D   D ", "A  G  A          u                      A  G  A", "A GGG A         u u                     A GGG A", "A  G  A          u                      A  G  A", " A   A                                   D   D ", "  AAA                                     AAA  ")
                    .aisle("  AAA                                     AAA  ", " A   A                                   A   A ", "A  G  A         u                       A  G  A", "A GGG Au       uHu                      A GGG A", "A  G  A         u                       A  G  A", " A   A                                   A   A ", "  AAA                                     AAA  ")
                    .aisle("  CCC                                     CCC  ", " CAAAC                                   CAAAC ", "CAAGAAA         u                       CAAGAAC", "CAGGGAAu       u u                      CAGGGAC", "CAAGAAA         u                       CAAGAAC", " CAAAC                                   CAAAC ", "  CCC                                     CCC  ")
                    .aisle("                                               ", "  AAA                                     AAA  ", " AAGAA u       u                         AAGAA ", " AGGGAuIuu   uuIu                        AGGGA ", " AAGAA u       u                         AAGAA ", "  AAA                                     AAA  ", "                                               ")
                    .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA uu   uu                         AAGAA  ", "  AGGGAu HuuuH u                        AGGGA  ", "  AAGAA uu   uu                         AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                    .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA   uuu                           AAGAA  ", "  AGGGA uu   uu                         AGGGA  ", "  AAGAA   uuu                           AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                    .aisle("                                               ", "   AAA                                   AAA   ", "  AAGAA                                 AAGAA  ", "  AGGGA   uuu                           AGGGA  ", "  AAGAA                                 AAGAA  ", "   AAA                                   AAA   ", "                                               ")
                    .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                    .aisle("                                               ", "    AAA                                 AAA    ", "   AAGAA                               AAGAA   ", "   AGGGA                               AGGGA   ", "   AAGAA                               AAGAA   ", "    AAA                                 AAA    ", "                                               ")
                    .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                    .aisle("                                               ", "     AAA                               AAA     ", "    AAGAA                             AAGAA    ", "    AGGGA                             AGGGA    ", "    AAGAA                             AAGAA    ", "     AAA                               AAA     ", "                                               ")
                    .aisle("                                               ", "      AAA                             AAA      ", "     AAGAA                           AAGAA     ", "     AGGGA                           AGGGA     ", "     AAGAA                           AAGAA     ", "      AAA                             AAA      ", "                                               ")
                    .aisle("                                               ", "       AAA                           AAA       ", "      AAGAA                         AAGAA      ", "      AGGGA                         AGGGA      ", "      AAGAA                         AAGAA      ", "       AAA                           AAA       ", "                                               ")
                    .aisle("                                               ", "        AAA                         AAA        ", "       AAGAA                       AAGAA       ", "       AGGGA                       AGGGA       ", "       AAGAA                       AAGAA       ", "        AAA                         AAA        ", "                                               ")
                    .aisle("                                               ", "         AAA                       AAA         ", "        AAGAAA                   AAAGAA        ", "        AGGGAA                   AAGGGA        ", "        AAGAAA                   AAAGAA        ", "         AAA                       AAA         ", "                                               ")
                    .aisle("                                               ", "          AAAA                   AAAA          ", "         AAGAAAA               AAAAGAA         ", "         AGGGGAA               AAGGGGA         ", "         AAGAAAA               AAAAGAA         ", "          AAAA                   AAAA          ", "                                               ")
                    .aisle("                                               ", "           AAAAA               AAAAA           ", "          AAGGAAAAAAAAAAAAAAAAAAAGGAA          ", "          AGGGGGAAAA AAAAA AAAAGGGGGA          ", "          AAGGAAAAAAAAAAAAAAAAAAAGGAA          ", "           AAAAA               AAAAA           ", "                                               ")
                    .aisle("                                               ", "            AAAAAAA CADDDAC AAAAAAA            ", "           AAAGGAAAAA     AAAAAGGAAAeeeeeeeeeee", "           AGGGGGGGAA     AAGGGGGGGAeeeeeeeeeee", "           AAAGGAAAAA     AAAAAGGAAAeeeeeeeeeee", "            AAAAAAA CADDDAC AAAAAAA            ", "                                               ")
                    .aisle("                    CADDDAC                    ", "              AAAAAAA     AAAAAAAeeeeeeeeeeeeee", "            AAAAGGGAA     AAGGGAAAAiiiiiiiiiiie", "            AAGGGGGGGGGGGGGGGGGGGAAiiiiiiiiiiie", "            AAAAGGGAA     AAGGGAAAAiiiiiiiiiiie", "              AAAAAAA     AAAAAAAeeeeeeeeeeeeee", "                    CADDDAC                    ")
                    .aisle("                    CAADAAC                    ", "                AAAAA     AAAAAeeeeeeeeeeeeeeee", "              AAAAAGGGGGGGGGAAAAAiiiiiiiiiiiiie", "              AAGGGGGGGGGGGGGGGAA             e", "              AAAAAGGGGGGGGGAAAAAiiiiiiiiiiiiie", "                AAAAA     AAAAAeeeeeeeeeeeeeeee", "                    CAADAAC                    ")
                    .aisle("                    CADDDAC                    ", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                AAAAA     AAAAAiiiiiiiiiiiiiiie", "                AAAGGGGGGGGGAAAiiiiiiiiiiiiiiie", "                AAAAA     AAAAAiiiiiiiiiiiiiiie", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                    CADDDAC                    ")
                    .aisle("                                               ", "                    CADDDAC                    ", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                   AA     AAeeeeeeeeeeeeeeeeeee", "                    CADDDAC                    ", "                                               ")
                    .aisle("                                               ", "                                               ", "                   A XYZWO A                   ", "                   A AABTA A                   ", "                   A AAAAA A                   ", "                                               ", "                                               ")

                    .where('B', GTQTMetaTileEntities.PARTICLE_ACCELERATOR, EnumFacing.SOUTH)
                    .where('A', getCasingState())
                    .where('C', getFrameState())
                    .where('D', getGlassState())
                    .where('G', getCasingState1())
                    .where('T', MetaTileEntities.COMPUTATION_HATCH_RECEIVER, EnumFacing.SOUTH)
                    .where('X', MetaTileEntities.ITEM_IMPORT_BUS[GTValues.ULV], EnumFacing.SOUTH)
                    .where('Y', MetaTileEntities.ITEM_EXPORT_BUS[GTValues.ULV], EnumFacing.SOUTH)
                    .where('Z', MetaTileEntities.FLUID_IMPORT_HATCH[GTValues.ULV], EnumFacing.SOUTH)
                    .where('W', MetaTileEntities.FLUID_EXPORT_HATCH[GTValues.ULV], EnumFacing.SOUTH)
                    .where('O', MetaTileEntities.ENERGY_INPUT_HATCH[GTValues.ULV], EnumFacing.SOUTH)
                    .where(' ', Blocks.AIR.getDefaultState());
            shapeInfo.add(builder.build());
            shapeInfo.add(builder
                    .where('e', getCasingState())
                    .where('i', getCasingState1())
                    .build());
            shapeInfo.add(builder
                    .where('d', getCasingState())
                    .where('f', getGlassState())
                    .where('h', getCasingState2())
                    .where('g', getCasingState3())
                    .build());
            shapeInfo.add(builder
                    .where('u', getCasingState())
                    .where('I', getCasingState3())
                    .where('H', getCasingState1())
                    .build());
        }
        return shapeInfo;
    }
    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_CASING);
    }

    private static IBlockState getCasingState1() {
        return GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNET_MKI);
    }

    private static IBlockState getCasingState2() {
        return GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_ELECTROMAGNETV_MKI);
    }

    private static IBlockState getCasingState3() {
        return GTQTMetaBlocks.PARTICLE_ACCELERATOR.getState(GTQTParticleAccelerator.MachineType.ACCELERATOR_FIRM_MKI);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.IRIDIUM_CASING;
    }
    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.CHEMICAL_PLANT_OVERLAY;
    }
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    public boolean hasMufflerMechanics() {
        return false;
    }
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), this.recipeMapWorkable.isActive(), this.recipeMapWorkable.isWorkingEnabled());
    }

    int d;


    public ItemStack GTToutput(int id,int amount) {
        time++;
        if(time==20) {
            time=0;
            //质子
            //氘
            //氚
            //阿法粒子
            if (id == 1) return new ItemStack(ALPHA.getMetaItem(), amount, 2500);
            if (id == 2) return new ItemStack(GTQTMetaItems.ELECTRON.getMetaItem(), amount, 2531);
            if (id == 3) return new ItemStack(GTQTMetaItems.PHOTON.getMetaItem(), amount, 2547);
        }
        return null;
    }

    public boolean getParticle(int x,int y,int z)
    {
        if (GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(x, y, z)) instanceof MetaTileEntity) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(x, y, z));
            int tier=((MetaTileEntityParticleAcceleratorIO) mte).tier;
            if (mte instanceof MetaTileEntityParticleAcceleratorIO SimpleMachineMetaTileEntity) {
                if(SimpleMachineMetaTileEntity.isActive()) if(((MetaTileEntityParticleAcceleratorIO) mte).circuit==1) GTTransferUtils.insertItem(this.outputInventory, GTToutput(1,tier), false);
                if(SimpleMachineMetaTileEntity.isActive()) if(((MetaTileEntityParticleAcceleratorIO) mte).circuit==2) GTTransferUtils.insertItem(this.outputInventory, GTToutput(2,tier), false);
                if(SimpleMachineMetaTileEntity.isActive()) if(((MetaTileEntityParticleAcceleratorIO) mte).circuit==3) GTTransferUtils.insertItem(this.outputInventory, GTToutput(3,tier), false);
                return true;
            }
        }
        return false;
    }
    public void update() {
        super.update();
        if (getWorld().isRemote)
        if(shuliu&&this.getRecipeMap() == BEAM_COLLECTION)
        {
            getParticle(3,0,0);
            getParticle(-3,0,0);
            getParticle(0,0,3);
            getParticle(0,0,-3);
        }
        else {
            long energyToDrain = GTValues.VA[EV];
            long resultEnergy = energyContainer.getEnergyStored() - energyToDrain;
            FluidStack HEAT_STACK = LiquidNitrogen.getFluid(d);
            //待机默认减速
            if (speed >= 0) {
                speed = speed - 1;
                d = 1;
            }
            if (speed > 20000) {
                speed = speed - 1;
                d = 2;
            }
            if (speed > 40000) {
                speed = speed - 1;
                d = 4;
            }
            if (speed > 60000) {
                speed = speed - 1;
                d = 6;
            }
            if (speed > 80000) {
                speed = speed - 1;
                d = 8;
            }

            //加速模式
            if (Mode == 1) {
                IMultipleTankHandler inputTank = getInputFluidInventory();
                if (speed < 99000) {
                    if (HEAT_STACK.isFluidStackIdentical(inputTank.drain(HEAT_STACK, false))) {
                        if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
                            inputTank.drain(HEAT_STACK, true);
                            speed = speed + 10;
                            energyContainer.changeEnergy(-energyToDrain);
                        }
                    }
                }
            }
            //稳定模式
            if (Mode == 2) {
                if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
                    energyContainer.changeEnergy(-energyToDrain);
                    if (speed > 0) speed = speed + 1;
                    if (speed > 20000) speed = speed + 2;
                    if (speed > 40000) speed = speed + 4;
                    if (speed > 60000) speed = speed + 6;
                    if (speed > 80000) speed = speed + 8;
                }
            }
            //减速模式
            if (Mode == 3) {
                if (speed > 0) speed = speed - 20;
            }
        }


    }


    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::decrementThresholdS)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.pam.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::incrementThresholdS)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.pam.threshold_increment"));

        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::decrementThresholdA)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("gtqtcore.multiblock.paa.threshold_decrement"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::incrementThresholdA)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_PLUS)
                .setTooltipText("gtqtcore.multiblock.paa.threshold_increment"));
        return group;
    }

    private void incrementThresholdS(Widget.ClickData clickData) {
        this.Mode = MathHelper.clamp(Mode + 1, 1, 3);
    }

    private void decrementThresholdS(Widget.ClickData clickData) {
        this.Mode = MathHelper.clamp(Mode - 1, 1, 3);
    }

    private void incrementThresholdA(Widget.ClickData clickData) {
        this.angle = MathHelper.clamp(angle + 1, 0, 8);
    }

    private void decrementThresholdA(Widget.ClickData clickData) {
        this.angle = MathHelper.clamp(angle - 1, 0, 8);
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("部件束流：%s 靶室：%s 核合成：%s",shuliu,bashi,hehecheng));
        if(this.getRecipeMap() == PARTICLE_ACCELERATOR_RECIPES)
        {
            textList.add(new TextComponentTranslation("科研模式启动"));
        }
        if(this.getRecipeMap() == BEAM_COLLECTION)
        {
            textList.add(new TextComponentTranslation("束流收集模式启动"));

            textList.add(new TextComponentTranslation("束流IO %s",getParticle(3,0,0)));
            textList.add(new TextComponentTranslation("束流IO %s",getParticle(-3,0,0)));
            textList.add(new TextComponentTranslation("束流IO %s",getParticle(0,0,3)));
            textList.add(new TextComponentTranslation("束流IO %s",getParticle(0,0,-3)));
        }
        /*
        if(Mode==1) textList.add(new TextComponentTranslation("gtqtcore.pa.mode1",angle,speed/100000));
        if(Mode==2) textList.add(new TextComponentTranslation("gtqtcore.pa.mode2",angle,speed/100000));
        if(Mode==3) textList.add(new TextComponentTranslation("gtqtcore.pa.mode3",angle,speed/100000));


        textList.add(new TextComponentTranslation("gtqtcore.pa.agp1"));
        if(angle==1)textList.add(new TextComponentTranslation("gtqtcore.pa.ag1"));
        else {
            if (angle == 2) textList.add(new TextComponentTranslation("gtqtcore.pa.ag2"));
                else
                {
                    if (angle == 8) textList.add(new TextComponentTranslation("gtqtcore.pa.ag8"));
                    else textList.add(new TextComponentTranslation("gtqtcore.pa.agp2"));
                }
            }

        if(angle==0)textList.add(new TextComponentTranslation("gtqtcore.pa.ag0"));
        else {
            if (angle == 3) textList.add(new TextComponentTranslation("gtqtcore.pa.ag3"));
            else {if (angle == 7) textList.add(new TextComponentTranslation("gtqtcore.pa.ag7"));
            else textList.add(new TextComponentTranslation("gtqtcore.pa.agp3"));
        }}

        if(angle==4)textList.add(new TextComponentTranslation("gtqtcore.pa.ag4"));
        else {
            if (angle == 5) textList.add(new TextComponentTranslation("gtqtcore.pa.ag5"));
            else
            {if (angle == 6) textList.add(new TextComponentTranslation("gtqtcore.pa.ag6"));
            else textList.add(new TextComponentTranslation("gtqtcore.pa.agp4"));
        }}
        textList.add(new TextComponentTranslation("gtqtcore.pa.agp1"));

        textList.add(new TextComponentTranslation("gtqtcore.pa.level", coilLevel,casingTier,tubeTier));

        if (getInputFluidInventory() != null) {
            FluidStack STACK = getInputFluidInventory().drain(Lava.getFluid(Integer.MAX_VALUE), false);
            int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.pa.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
        }

        */
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityParticleAccelerator(metaTileEntityId);
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && providers.size() >= 1) {
            this.computationProvider = (IOpticalComputationProvider)providers.get(0);
        }
        if (context.get("shuliu") != null) {
            shuliu =true;
        }
        if (context.get("bashi") != null) {
            bashi =true;
        }
        if (context.get("hehecheng") != null) {
            hehecheng =true;
        }

    }

}