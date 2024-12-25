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
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.*;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.api.util.LocalizationUtils;
import gregtech.api.util.TextFormattingUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockWireCoil;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.metaileentity.GTQTRecipeMapMultiblockController;
import keqing.gtqtcore.api.pattern.GTQTTraceabilityPredicate;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.EUToStartProperty;
import keqing.gtqtcore.api.recipes.properties.PAProperty;
import keqing.gtqtcore.api.recipes.properties.ScatteringProperty;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import keqing.gtqtcore.common.metatileentities.single.electric.MetaTileEntityParticleAcceleratorIO;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
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
import static keqing.gtqtcore.common.items.GTQTMetaItems.*;

public class MetaTileEntityParticleAccelerator extends MultiMapMultiblockController implements IOpticalComputationReceiver {

    private IOpticalComputationProvider computationProvider;
    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }
    private double speed;       //速度
    private int angle;       //角度

    private int Mode=1;        //加速 稳定 减速模式

    boolean shuliu;
    boolean bashi;
    boolean hehecheng;


    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("angle", angle);
        data.setInteger("Mode", Mode);
        data.setDouble("speed", speed);
        return super.writeToNBT(data);
    }
   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        angle = data.getInteger("angle");
        Mode = data.getInteger("Mode");
        speed = data.getDouble("speed");
    }

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
                    .aisle("                                               ", "                                               ", "                   A XYZWO A                   ", "                   ALAABTALA                   ", "                   A AAAAA A                   ", "                                               ", "                                               ")

                    .where('B', GTQTMetaTileEntities.PARTICLE_ACCELERATOR, EnumFacing.SOUTH)
                    .where('L', getCasingStateTest())
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
    private static IBlockState getCasingStateTest() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.NQ_TURBINE_CASING);
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
        return MetaBlocks.FRAMES.get(Materials.Naquadah).getBlock(Materials.Naquadah);
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
        return true;
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
            //质子
            //氘
            //氚
            //阿法粒子
        if (id == 1) return new ItemStack(PROTON.getMetaItem(), amount, 2552);
        if (id == 2) return new ItemStack(DEUTERON.getMetaItem(), amount, 2529);
        if (id == 3) return new ItemStack(TRITON.getMetaItem(), amount, 2561);
        if (id == 4) return new ItemStack(ALPHA.getMetaItem(), amount, 2500);
        return  ItemStack.EMPTY;
    }

    public boolean getParticle(int x,int y,int z)
    {
        if (GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(x, y, z)) instanceof MetaTileEntity) {
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), this.getPos().add(x, y, z));
            if (mte instanceof MetaTileEntityParticleAcceleratorIO)
            {
                if(((MetaTileEntityParticleAcceleratorIO) mte).output) {
                    GTTransferUtils.insertItem(this.outputInventory, GTToutput(((MetaTileEntityParticleAcceleratorIO) mte).circuit, ((MetaTileEntityParticleAcceleratorIO) mte).tier), false);
                }
                return true;
            }
        }
        return false;
    }
    public void update() {
        super.update();
        if (!isStructureFormed()) return;
        long energyToDrain = GTValues.VA[EV];
        long resultEnergy = energyContainer.getEnergyStored() - energyToDrain;
        if(this.getRecipeMap() == PARTICLE_ACCELERATOR_RECIPES)return;
        if(shuliu&&this.getRecipeMap() == BEAM_COLLECTION)
        {
            if (resultEnergy >= 0L && resultEnergy <= energyContainer.getEnergyCapacity()) {
                energyContainer.changeEnergy(-energyToDrain);
                getParticle(3, 0, 0);
                getParticle(-3, 0, 0);
                getParticle(0, 0, 3);
                getParticle(0, 0, -3);
            }
        }
        if((this.getRecipeMap() == NUCLEOSYNTHESIS&&bashi)||(this.getRecipeMap() == TARGET_CHAMBER&&hehecheng))
        {
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
            if (Mode == 3) {
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
            if (Mode == 1) {
                if (speed > 0) speed = speed - 20;
            }
        }
    }

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 300, 240);

        // Display
        builder.image(132, 4, 162, 115, GuiTextures.DISPLAY);
        builder.dynamicLabel(135, 8, () -> "大型粒子对撞研究复合体", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(135, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(160).setClickHandler(this::handleDisplayClick));

        // Display
        builder.image(3, 4, 130, 115, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8, this::addTotal, 16777215)).setMaxWidthLimit(130).setClickHandler(this::handleDisplayClick));

        // Energy
        builder.image(3, 120, 130, 115, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 128, this::addEnergy, 16777215)).setMaxWidthLimit(130).setClickHandler(this::handleDisplayClick));


        IControllable controllable = getCapability(GregtechTileCapabilities.CAPABILITY_CONTROLLABLE, null);
        if (controllable != null) {
            builder.widget(new ImageCycleButtonWidget(8, 100, 18, 18, GuiTextures.BUTTON_POWER,
                    controllable::isWorkingEnabled, controllable::setWorkingEnabled));
        }

        // Voiding Mode Button
        builder.widget(new ImageCycleButtonWidget(38, 100, 18, 18, GuiTextures.BUTTON_VOID_MULTIBLOCK,
                4, this::getVoidingMode, this::setVoidingMode)
                .setTooltipHoverString(MultiblockWithDisplayBase::getVoidingModeTooltip));

        // Distinct Buses Button
        builder.widget(new ImageCycleButtonWidget(68, 100, 18, 18, GuiTextures.BUTTON_DISTINCT_BUSES,
                this::isDistinct, this::setDistinct)
                .setTooltipHoverString(i -> "gregtech.multiblock.universal.distinct_" +
                        (i == 0 ? "disabled" : "enabled")));

        builder.widget((new ImageCycleButtonWidget(98, 100, 18, 18, GuiTextures.BUTTON_MULTI_MAP, this.getAvailableRecipeMaps().length, this::getRecipeMapIndex, this::setRecipeMapIndex)).shouldUseBaseBackground().singleTexture().setTooltipHoverString((i) -> LocalizationUtils.format("gregtech.multiblock.multiple_recipemaps.header") + " " + LocalizationUtils.format("recipemap." + this.getAvailableRecipeMaps()[i].getUnlocalizedName() + ".name"))
        );

        builder.widget(new ClickButtonWidget(132, 120, 48, 18, "核子合成", data ->this.setRecipeMapIndex(1)));
        builder.widget(new ClickButtonWidget(180, 120, 48, 18, "靶室轰击", data ->this.setRecipeMapIndex(2)));
        builder.widget(new ClickButtonWidget(228, 120, 48, 18, "束流收集", data ->this.setRecipeMapIndex(3)));

        builder.widget(new ClickButtonWidget(132, 140, 24, 18, "V+", data -> this.Mode = MathHelper.clamp(Mode + 1, 1, 3) ));
        builder.widget(new ClickButtonWidget(156, 140, 24, 18, "V-", data -> this.Mode = MathHelper.clamp(Mode - 1, 1, 3)   ));
        builder.widget(new ClickButtonWidget(180, 140, 24, 18, "A+", data -> {
            this.angle = MathHelper.clamp(angle + 1, 0, 8);
            speed=speed*0.8;
        }));
        builder.widget(new ClickButtonWidget(204, 140, 24, 18, "A-", data -> {
            this.angle = MathHelper.clamp(angle - 1, 0, 8);
            speed=speed*0.8;
        }));

        builder.widget(new ClickButtonWidget(228, 140, 48, 18, "科研模式", data ->this.setRecipeMapIndex(0)));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 132, 160);
        return builder;
    }
    protected void addEnergy(List<ITextComponent> textList) {
        if(!isStructureFormed())
        {
            textList.add(new TextComponentTranslation("结构未成型！"));
            return;
        }
        textList.add(new TextComponentTranslation("机器能源终端："));
        textList.add(new TextComponentTranslation("蓄能上限：%s ",this.energyContainer.getEnergyCapacity()));
        textList.add(new TextComponentTranslation("能量缓存：%s", this.energyContainer.getEnergyStored()));
    }
    protected void addTotal(List<ITextComponent> textList) {
        if(!isStructureFormed())
        {
            textList.add(new TextComponentTranslation("结构未成型！"));
            return;
        }
        textList.add(new TextComponentTranslation("拓展结构："));
        textList.add(new TextComponentTranslation("束流收集：%s ",shuliu));
        textList.add(new TextComponentTranslation("靶室轰击：%s", bashi));
        textList.add(new TextComponentTranslation("核子合成：%s", hehecheng));

    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if(!isStructureFormed())
        {
            textList.add(new TextComponentTranslation("结构未成型！"));
            return;
        }
        if(this.getRecipeMap() == PARTICLE_ACCELERATOR_RECIPES)
        {
            textList.add(new TextComponentTranslation("科研模式"));
        }
        if(this.getRecipeMap() == BEAM_COLLECTION)
        {
            if(shuliu) {
                textList.add(new TextComponentTranslation("束流收集模式"));

                textList.add(new TextComponentTranslation("请在多方块的粒子源端口安装对应粒子源机器（名字叫基本了粒子源！ID是15096-15099！！），粒子源机器工作时多方块会自动根据其工作情况输出对应粒子"));

                if (getParticle(3, 0, 0)) textList.add(new TextComponentTranslation("束流IO %s", getParticle(3, 0, 0)));
                if (getParticle(-3, 0, 0))
                    textList.add(new TextComponentTranslation("束流IO %s", getParticle(-3, 0, 0)));
                if (getParticle(0, 0, 3)) textList.add(new TextComponentTranslation("束流IO %s", getParticle(0, 0, 3)));
                if (getParticle(0, 0, -3))
                    textList.add(new TextComponentTranslation("束流IO %s", getParticle(0, 0, -3)));
            }
            else textList.add(new TextComponentTranslation("未安装 束流收集 拓展结构！"));
        }
        if(this.getRecipeMap() == NUCLEOSYNTHESIS) {
            if(bashi)textList.add(new TextComponentTranslation("核子合成"));
            else textList.add(new TextComponentTranslation("未安装 核子合成 拓展结构！"));
        }
        if(this.getRecipeMap() == TARGET_CHAMBER) {
            if(bashi)textList.add(new TextComponentTranslation("靶室轰击"));
            else textList.add(new TextComponentTranslation("未安装 靶室轰击 拓展结构！"));
        }
        if((this.getRecipeMap() == NUCLEOSYNTHESIS&&bashi)||(this.getRecipeMap() == TARGET_CHAMBER&&hehecheng)) {


            textList.add(new TextComponentTranslation("在本模式下加速粒子需要消耗液氮！"));

            if (getInputFluidInventory() != null) {
                FluidStack STACK = getInputFluidInventory().drain(LiquidNitrogen.getFluid(Integer.MAX_VALUE), false);
                int liquidOxygenAmount = STACK == null ? 0 : STACK.amount;
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.pa.amount", TextFormattingUtil.formatNumbers((liquidOxygenAmount))));
            }

            if (Mode == 0) textList.add(new TextComponentTranslation("初始化"));
            if (Mode == 3) textList.add(new TextComponentTranslation("gtqtcore.pa.mode1", angle, (int)speed));
            if (Mode == 2) textList.add(new TextComponentTranslation("gtqtcore.pa.mode2", angle, (int)speed));
            if (Mode == 1) textList.add(new TextComponentTranslation("gtqtcore.pa.mode3", angle, (int)speed));

            textList.add(new TextComponentTranslation("gtqtcore.pa.agp1"));
            if (angle == 1) textList.add(new TextComponentTranslation("gtqtcore.pa.ag1"));
            else {
                if (angle == 2) textList.add(new TextComponentTranslation("gtqtcore.pa.ag2"));
                else {
                    if (angle == 8) textList.add(new TextComponentTranslation("gtqtcore.pa.ag8"));
                    else textList.add(new TextComponentTranslation("gtqtcore.pa.agp2"));
                }
            }

            if (angle == 0) textList.add(new TextComponentTranslation("gtqtcore.pa.ag0"));
            else {
                if (angle == 3) textList.add(new TextComponentTranslation("gtqtcore.pa.ag3"));
                else {
                    if (angle == 7) textList.add(new TextComponentTranslation("gtqtcore.pa.ag7"));
                    else textList.add(new TextComponentTranslation("gtqtcore.pa.agp3"));
                }
            }

            if (angle == 4) textList.add(new TextComponentTranslation("gtqtcore.pa.ag4"));
            else {
                if (angle == 5) textList.add(new TextComponentTranslation("gtqtcore.pa.ag5"));
                else {
                    if (angle == 6) textList.add(new TextComponentTranslation("gtqtcore.pa.ag6"));
                    else textList.add(new TextComponentTranslation("gtqtcore.pa.agp4"));
                }
            }
            textList.add(new TextComponentTranslation("gtqtcore.pa.agp1"));



        }
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
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.pa.tooltip.1", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.pa.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.pa.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.pa.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.pa.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.pa.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.pa.tooltip.7"));
    }
}