package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.endGame;


import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.MultiblockRecipeLogic;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.MultiblockShapeInfo;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.metatileentities.MetaTileEntities;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.GTQTDataCode;
import keqing.gtqtcore.api.capability.ICatalystHatch;
import keqing.gtqtcore.api.capability.IWarpSwarm;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.CatalystProperties;
import keqing.gtqtcore.api.recipes.properties.QFTCasingTierProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.client.utils.BloomEffectUtil;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockQuantumForceTransformerCasing;
import keqing.gtqtcore.common.metatileentities.GTQTMetaTileEntities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static gregtech.api.GTValues.UHV;
import static gregtech.api.unification.material.Materials.Fermium;
import static gregtech.api.unification.material.Materials.Neptunium;
import static keqing.gtqtcore.api.GTQTAPI.*;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.WARP_SWARM_MULTIBLOCK_ABILITY;
import static keqing.gtqtcore.api.unification.GTQTMaterials.SuperDimensionalCatalystMKI;
import static keqing.gtqtcore.common.block.blocks.BlockQuantumForceTransformerCasing.CasingType.NEUTRON_PULSE_MANIPULATOR_CASING;

public class MetaTileEntityQuantumForceTransformer extends RecipeMapMultiblockController implements IFastRenderMetaTileEntity {

    private int manioulatorTier;
    private int coreTier;
    private int glassTier;

    //消耗 对应聚焦等级数量的等离子 消耗一轮置为true 配方完成后设置为false
    boolean canOverclocking;
    boolean canBoosterOutput;

    public MetaTileEntityQuantumForceTransformer(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.QUANTUM_FORCE_TRANSFORMER_RECIPES);
        this.recipeMapWorkable = new MetaTileEntityQuantumForceTransformerHandler(this);
    }


    @Override
    public void update() {
        super.update();
        if(!canOverclocking)getBoosterByFluidStackA();
        if(!canBoosterOutput)getBoosterByFluidStackB();
    }

    public void getBoosterByFluidStackA() {
        FluidStack CatalystMKI = Neptunium.getPlasma((int) (100 *Math.pow(2,coreTier)));
        IMultipleTankHandler inputTank = this.getInputFluidInventory();
        if (CatalystMKI.isFluidStackIdentical(inputTank.drain(CatalystMKI, false))) {
            inputTank.drain(CatalystMKI, true);
            canOverclocking=true;
        }
    }
    public void getBoosterByFluidStackB() {
        FluidStack CatalystMKII = Fermium.getPlasma((int) (100 * Math.pow(2,coreTier)));
        IMultipleTankHandler inputTank = this.getInputFluidInventory();
        if (CatalystMKII.isFluidStackIdentical(inputTank.drain(CatalystMKII, false))) {
            inputTank.drain(CatalystMKII, true);
            canBoosterOutput=true;
        }
    }
    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityQuantumForceTransformer(metaTileEntityId);
    }


    @SuppressWarnings("SpellCheckingInspection")
    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("    A     A    ", "    A     A    ", "    A     A    ", "   BA     AB   ", "   BABBABBAB   ", "   BAAAAAAAB   ", "   BBBBABBBB   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", "  A         A  ", "  A         A  ", "  B         B  ", "  BAAAAAAAAAB  ", "   AAABBBAAA   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", " A           A ", " A           A ", " B           B ", " BAA       AAB ", "  AA       AA  ", "    AA   AA    ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("A             A", "A             A", "A             A", "A             A", "A             A", "B             B", "BAA         AAB", " AA         AA ", "   AA     AA   ", "     BAAAB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("      HHH      ", "      EEE      ", "      EEE      ", "      EEE      ", "B     DDD     B", "B     EEE     B", "BA    DDD    AB", " A    EEE    A ", "  AA  EEE  AA  ", "    BAEEEAB    ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      DDD      ", "      EEE      ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      HHH      ")
                .aisle("     HHHHH     ", "     ECCCE     ", "     ECCCE     ", "B    ECCCE    B", "B    D   D    B", "B    ECCCE    B", "BA   D   D   AB", " A   ECCCE   A ", "  A  ECCCE  A  ", "   BAECCCEAB   ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     HHHHH     ")
                .aisle("    HHHHHHH    ", "    EC   CE    ", "A   EC   CE   A", "A   EC   CE   A", "A   D     D   A", "A   EC   CE   A", "BA  D     D  AB", "BB  EC   CE  BB", " B  EC   CE  B ", "  BAEC   CEAB  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                .aisle("    HHHHHHH    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "A   D     D   A", "A   EC   CE   A", "AA  D     D  AA", "AB  EC   CE  BA", " A  EC   CE  A ", "  AAEC   CEAA  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                .aisle("    HHHHHHH    ", "    EC   CE    ", "    EC   CE    ", "A   EC   CE   A", "A   D     D   A", "A   EC   CE   A", "BA  D     D  AB", "BB  EC   CE  BB", " B  EC   CE  B ", "  BAEC   CEAB  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                .aisle("     HHHHH     ", "     ECCCE     ", "     ECCCE     ", "B    ECCCE    B", "B    D   D    B", "B    ECCCE    B", "BA   D   D   AB", " A   ECCCE   A ", "  A  ECCCE  A  ", "   BAECCCEAB   ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     HHHHH     ")
                .aisle("      HSH      ", "      EEE      ", "      EEE      ", "      EEE      ", "B     DDD     B", "B     EEE     B", "BA    DDD    AB", " A    EEE    A ", "  AA  EEE  AA  ", "    BAEEEAB    ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      DDD      ", "      EEE      ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      HHH      ")
                .aisle("A             A", "A             A", "A             A", "A             A", "A             A", "B             B", "BAA          AB", " AA         AA ", "   AA     AA   ", "     BAAAB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", " A           A ", " A           A ", " B           B ", " BA         AB ", "  AA       AA  ", "    AA   AA    ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("               ", "               ", "               ", "  A         A  ", "  A         A  ", "  B         B  ", "  BAAAAAAAAAB  ", "   AAABBBAAA   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .aisle("    A     A    ", "    A     A    ", "    A     A    ", "   BA     AB   ", "   BABBABBAB   ", "   BAAAAAAAB   ", "   BBBBABBBB   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                .where('S', this.selfPredicate())
                .where('A', TiredTraceabilityPredicate.QFT_MANIPULATOR.get())
                .where('B', TiredTraceabilityPredicate.QFT_SHIELDING_CORE.get())
                .where('C', states(getCoilState()))
                .where('D', states(getCasingState()))
                .where('E', TiredTraceabilityPredicate.QFT_GLASS.get())
                .where('H', states(getCasingState())
                        .setMinGlobalLimited(55)
                        .or(abilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY).setExactLimit(1))
                        .or(abilities(MultiblockAbility.INPUT_LASER).setMaxGlobalLimited(1))
                        .or(abilities(WARP_SWARM_MULTIBLOCK_ABILITY).setExactLimit(1))
                        .or(autoAbilities()))
                .build();
    }

    public ICatalystHatch getCatalystHatch() {
        List<ICatalystHatch> abilities = getAbilities(GTQTMultiblockAbility.CATALYST_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(0);
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.QUANTUM_CONSTRAINT_CASING);
    }

    private IBlockState getCoilState() {
        return GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(BlockQuantumForceTransformerCasing.CasingType.QUANTUM_FORCE_TRANSFORMER_COIL);
    }

    private static IBlockState getSecondCasingState() {
        return GTQTMetaBlocks.blockQuantumForceTransformerCasing.getState(NEUTRON_PULSE_MANIPULATOR_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.QUANTUM_CONSTRAINT_CASING;
    }

    @Override
    public List<MultiblockShapeInfo> getMatchingShapes() {
        ArrayList<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        MultiblockShapeInfo.Builder builder = null;
        if (Blocks.AIR != null) {
            builder = MultiblockShapeInfo.builder()
                    .aisle("    A     A    ", "    A     A    ", "    A     A    ", "   BA     AB   ", "   BABBABBAB   ", "   BAAAAAAAB   ", "   BBBBABBBB   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .aisle("               ", "               ", "               ", "  A         A  ", "  A         A  ", "  B         B  ", "  BAAAAAAAAAB  ", "   AAABBBAAA   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .aisle("               ", "               ", "               ", " A           A ", " A           A ", " B           B ", " BAA       AAB ", "  AA       AA  ", "    AA   AA    ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .aisle("A             A", "A             A", "A             A", "A             A", "A             A", "B             B", "BAA         AAB", " AA         AA ", "   AA     AA   ", "     BAAAB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .aisle("      MWP      ", "      EEE      ", "      EEE      ", "      EEE      ", "B     DDD     B", "B     EEE     B", "BA    DDD    AB", " A    EEE    A ", "  AA  EEE  AA  ", "    BAEEEAB    ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      DDD      ", "      EEE      ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      HHH      ")
                    .aisle("     HHHHH     ", "     ECCCE     ", "     ECCCE     ", "B    ECCCE    B", "B    D   D    B", "B    ECCCE    B", "BA   D   D   AB", " A   ECCCE   A ", "  A  ECCCE  A  ", "   BAECCCEAB   ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     HHHHH     ")
                    .aisle("    HHHHHHH    ", "    EC   CE    ", "A   EC   CE   A", "A   EC   CE   A", "A   D     D   A", "A   EC   CE   A", "BA  D     D  AB", "BB  EC   CE  BB", " B  EC   CE  B ", "  BAEC   CEAB  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                    .aisle("    VHHHHHU    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "A   D     D   A", "A   EC   CE   A", "AA  D     D  AA", "AB  EC   CE  BA", " A  EC   CE  A ", "  AAEC   CEAA  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                    .aisle("    HHHHHHH    ", "    EC   CE    ", "    EC   CE    ", "A   EC   CE   A", "A   D     D   A", "A   EC   CE   A", "BA  D     D  AB", "BB  EC   CE  BB", " B  EC   CE  B ", "  BAEC   CEAB  ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    D     D    ", "    EC   CE    ", "    EC   CE    ", "    ECCCCCE    ", "    HHHHHHH    ")
                    .aisle("     HHHHI     ", "     ECCCE     ", "     ECCCE     ", "B    ECCCE    B", "B    D   D    B", "B    ECCCE    B", "BA   D   D   AB", " A   ECCCE   A ", "  A  ECCCE  A  ", "   BAECCCEAB   ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     D   D     ", "     ECCCE     ", "     ECCCE     ", "     ECCCE     ", "     HHHHH     ")
                    .aisle("      XSY      ", "      EEE      ", "      EEE      ", "      EEE      ", "B     DDD     B", "B     EEE     B", "BA    DDD    AB", " A    EEE    A ", "  AA  EEE  AA  ", "    BAEEEAB    ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      DDD      ", "      EEE      ", "      DDD      ", "      EEE      ", "      EEE      ", "      EEE      ", "      HHH      ")
                    .aisle("A             A", "A             A", "A             A", "A             A", "A             A", "B             B", "BAA          AB", " AA         AA ", "   AA     AA   ", "     BAAAB     ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .aisle("               ", "               ", "               ", " A           A ", " A           A ", " B           B ", " BA         AB ", "  AA       AA  ", "    AA   AA    ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .aisle("               ", "               ", "               ", "  A         A  ", "  A         A  ", "  B         B  ", "  BAAAAAAAAAB  ", "   AAABBBAAA   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .aisle("    A     A    ", "    A     A    ", "    A     A    ", "   BA     AB   ", "   BABBABBAB   ", "   BAAAAAAAB   ", "   BBBBABBBB   ", "      BAB      ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ", "               ")
                    .where('S', GTQTMetaTileEntities.QUANTUM_FORCE_TRANSFORMER, EnumFacing.SOUTH)
                    .where('A', getSecondCasingState())
                    .where('C', getCoilState())
                    .where('D', getCasingState())
                    .where('H', getCasingState())
                    .where('P', GTQTMetaTileEntities.CATALYST_HATCH[0], EnumFacing.NORTH)
                    .where('I', GTQTMetaTileEntities.SWARM_HATCH, EnumFacing.SOUTH)
                    .where('X', MetaTileEntities.ITEM_IMPORT_BUS[UHV], EnumFacing.SOUTH)
                    .where('Y', MetaTileEntities.ITEM_EXPORT_BUS[UHV], EnumFacing.SOUTH)
                    .where('U', MetaTileEntities.FLUID_IMPORT_HATCH[UHV], EnumFacing.WEST)
                    .where('V', MetaTileEntities.FLUID_EXPORT_HATCH[UHV], EnumFacing.EAST)
                    .where('W', MetaTileEntities.ENERGY_INPUT_HATCH[UHV], EnumFacing.NORTH)
                    .where('M', MetaTileEntities.MAINTENANCE_HATCH, EnumFacing.NORTH)
                    .where(' ', Blocks.AIR.getDefaultState());
        }
        if (builder == null) return shapeInfo;

        // 按层级排序组件
        TreeMap<Integer, IBlockState> manipulators = new TreeMap<>();
        MAP_QFT_MANIPULATOR.forEach((block, tier) ->
                manipulators.put((Integer) tier.getTier(), block));

        TreeMap<Integer, IBlockState> cores = new TreeMap<>();
        MAP_QFT_SHIELDING_CORE.forEach((block, tier) ->
                cores.put((Integer) tier.getTier(), block));

        TreeMap<Integer, IBlockState> glass = new TreeMap<>();
        MAP_QFT_GLASS.forEach((block, tier) ->
                glass.put((Integer) tier.getTier(), block));

        // 获取所有组件的最高层级
        int maxManipulatorTier = manipulators.isEmpty() ? 0 : manipulators.lastKey();
        int maxCoreTier = cores.isEmpty() ? 0 : cores.lastKey();
        int maxGlassTier = glass.isEmpty() ? 0 : glass.lastKey();

        // 计算整体最高层级（所有组件最高级的最大值）
        int maxTier = Math.max(Math.max(maxManipulatorTier, maxCoreTier), maxGlassTier);

        // 为每个层级创建预览
        for (int tier = 1; tier <= maxTier; tier++) {
            MultiblockShapeInfo.Builder tierBuilder = builder.shallowCopy(); // 重要：创建builder的副本

            // 设置当前层级的MANIPULATOR
            int actualManipulatorTier = Math.min(tier, maxManipulatorTier);
            if (actualManipulatorTier > 0) {
                tierBuilder = tierBuilder.where('A', manipulators.get(actualManipulatorTier));
            }

            // 设置当前层级的SHIELDING_CORE
            int actualCoreTier = Math.min(tier, maxCoreTier);
            if (actualCoreTier > 0) {
                tierBuilder = tierBuilder.where('B', cores.get(actualCoreTier));
            }

            // 设置当前层级的GLASS
            int actualGlassTier = Math.min(tier, maxGlassTier);
            if (actualGlassTier > 0) {
                tierBuilder = tierBuilder.where('E', glass.get(actualGlassTier));
            }

            shapeInfo.add(tierBuilder.build());
        }

        return shapeInfo;
    }

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.BLINKING_RED + I18n.format("上帝的化学反应釜"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("gtqtcore.machine.quantum_force_transformer.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.quantum_force_transformer.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.quantum_force_transformer.tooltip.3"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("本设备支持催化剂仓，在运行含催化剂的配方时需将催化剂放置在仓内"));
        tooltip.add(I18n.format("会在配方检查时无视并行数量消耗一点耐久，加工中途失败需重新消耗耐久"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("本设备支持纳米蜂群仓，每完成一次配方会消耗一点耐久（无视并行）"));
        tooltip.add(I18n.format("配方等级：由脉冲控制器等级决定"));
        tooltip.add(I18n.format("并行数量：Math.pow(4,聚焦等级与纳米蜂群等级的较小值)"));
        tooltip.add(I18n.format("耗时减免：每等级玻璃与纳米蜂群（取较小值）减免10%%"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("每次消耗100 *Math.pow(2,聚焦等级)mb的镎等离子提供一次无损超频"));
        tooltip.add(I18n.format("每次消耗100 *Math.pow(2,聚焦等级)mb的镄等离子提供一次双倍产出"));
        tooltip.add(I18n.format("=============================================="));
    }
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (this.isStructureFormed()) {

            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.level", coreTier));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.md.glass", glassTier));
            textList.add(new TextComponentTranslation("gtqtcore.casingTire", manioulatorTier));
            textList.add(new TextComponentTranslation("gregtech.multiblock.cracking_unit.energy", 100 - 10 * this.glassTier));
            textList.add(new TextComponentTranslation("gtqtcore.multiblock.fu.level", 10 * coreTier));
        }

    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object manioulatorTier = context.get("ManipulatprTieredStats");
        Object coreTier = context.get("CoreTieredStats");
        Object glassTier = context.get("QFTGlassTieredStats");
        //脉冲控制器 决定配方等级
        this.manioulatorTier = GTQTUtil.getOrDefault(() -> manioulatorTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) manioulatorTier).getIntTier(),
                0);
        //聚焦等级
        this.coreTier = GTQTUtil.getOrDefault(() -> coreTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) coreTier).getIntTier(),
                0);
        //玻璃等级
        this.glassTier = GTQTUtil.getOrDefault(() -> glassTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glassTier).getIntTier(),
                0);

    }



    public IWarpSwarm getAbility() {
        if (this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY) != null)
            return this.getAbilities(WARP_SWARM_MULTIBLOCK_ABILITY).get(0);
        return null;
    }

    private class MetaTileEntityQuantumForceTransformerHandler extends MultiblockRecipeLogic {

        public MetaTileEntityQuantumForceTransformerHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        //催化剂与外壳等级复合检查逻辑
        @Override
        public boolean checkRecipe(@Nonnull Recipe recipe) {
            if (!super.checkRecipe(recipe)) {
                return false;
            }
            QFTCasingTierProperty qftCasingTierProperty = QFTCasingTierProperty.getInstance();
            CatalystProperties catalystProps = CatalystProperties.getInstance();

            // Check chemical plant properties
            if (recipe.hasProperty(qftCasingTierProperty)) {
                Integer tierValue = recipe.getProperty(qftCasingTierProperty, 0);
                if (tierValue == null || tierValue > manioulatorTier) {
                    return false;
                }
            }

            // Check catalyst properties
            if (recipe.hasProperty(catalystProps)) {
                ItemStack catalystStack = recipe.getProperty(catalystProps, ItemStack.EMPTY);
                if (catalystStack != null && !catalystStack.isEmpty()) {
                    if (getCatalystHatch().hasCatalyst(catalystStack)) {
                        getCatalystHatch().consumeCatalyst(catalystStack, 1);
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }

        //并行由 纳米蜂群等级与聚焦等级较小值决定
        @Override
        public int getParallelLimit() {
            return (int) Math.pow(4,Math.min(getAbility().getWarpSwarmTier(),coreTier));
        }

        //耗时减免由 纳米蜂群等级与玻璃等级较小值决定
        @Override
        public void setMaxProgress(int maxProgress) {
            super.setMaxProgress((int) (maxProgress * getTimeBound()));
        }

        public double getTimeBound() {
            if (getAbility().isAvailable()) return (10 - Math.min(getAbility().getWarpSwarmTier(),glassTier)) / 10.0;
            return 1;
        }

        //消耗 对应聚焦等级数量的等离子
        protected double getOverclockingDurationFactor() {
            return canOverclocking ? 0.25 : 0.5;
        }

        //纳米蜂群仓的固有逻辑
        @Override
        protected void updateRecipeProgress() {
            if (this.canRecipeProgress && this.drawEnergy(this.recipeEUt, true)) {
                this.drawEnergy(this.recipeEUt, false);
                if (++this.progressTime > this.maxProgressTime) {
                    getAbility().applyDamage(1);
                    if(canBoosterOutput)this.outputRecipeOutputs();;
                    this.completeRecipe();
                    canBoosterOutput=false;
                    canOverclocking=false;
                }
                if (this.hasNotEnoughEnergy && this.getEnergyInputPerSecond() > 19L * this.recipeEUt) {
                    this.hasNotEnoughEnergy = false;
                }
            } else if (this.recipeEUt > 0L) {
                this.hasNotEnoughEnergy = true;
                this.decreaseProgress();
            }
        }
    }

    static BloomEffectUtil.IBloomRenderFast RENDER_HANDLER = new BloomEffectUtil.IBloomRenderFast() {
        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        public int customBloomStyle() {
            return 2;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void preDraw(BufferBuilder buffer) {
            BloomEffect.strength = 1.5F;
            BloomEffect.baseBrightness = 0.0F;
            BloomEffect.highBrightnessThreshold = 1.3F;
            BloomEffect.lowBrightnessThreshold = 0.3F;
            BloomEffect.step = 1;

            lastBrightnessX = OpenGlHelper.lastBrightnessX;
            lastBrightnessY = OpenGlHelper.lastBrightnessY;
            GlStateManager.color(1, 1, 1, 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void postDraw(BufferBuilder buffer) {
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    };

    @SideOnly(Side.CLIENT)
    private void renderForceField(BufferBuilder buffer, double x, double y, double z, int side, double minU, double maxU, double minV, double maxV) {

        switch (side) {
            case 0 -> {
                buffer.pos(x + 3, y, z + 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z + 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 3, y + 4, z + 7).tex(minU, minV).endVertex();
                buffer.pos(x - 3, y, z + 7).tex(minU, maxV).endVertex();
            }
            case 1 -> {
                buffer.pos(x + 7, y, z + 4).tex(maxU, maxV).endVertex();
                buffer.pos(x + 7, y + 4, z + 4).tex(maxU, minV).endVertex();
                buffer.pos(x + 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x + 7, y, z - 4).tex(minU, maxV).endVertex();
            }
            case 2 -> {
                buffer.pos(x + 3, y, z - 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z - 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 3, y + 4, z - 7).tex(minU, minV).endVertex();
                buffer.pos(x - 3, y, z - 7).tex(minU, maxV).endVertex();
            }
            case 3 -> {
                buffer.pos(x - 7, y, z + 4).tex(maxU, maxV).endVertex();
                buffer.pos(x - 7, y + 4, z + 4).tex(maxU, minV).endVertex();
                buffer.pos(x - 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x - 7, y, z - 4).tex(minU, maxV).endVertex();
            }
            case 4 -> {
                buffer.pos(x - 3, y, z + 7).tex(maxU, maxV).endVertex();
                buffer.pos(x - 3, y + 4, z + 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 7, y + 4, z + 4).tex(minU, minV).endVertex();
                buffer.pos(x - 7, y, z + 4).tex(minU, maxV).endVertex();
            }
            case 5 -> {
                buffer.pos(x - 3, y, z - 7).tex(maxU, maxV).endVertex();
                buffer.pos(x - 3, y + 4, z - 7).tex(maxU, minV).endVertex();
                buffer.pos(x - 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x - 7, y, z - 4).tex(minU, maxV).endVertex();
            }
            case 6 -> {
                buffer.pos(x + 3, y, z + 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z + 7).tex(maxU, minV).endVertex();
                buffer.pos(x + 7, y + 4, z + 4).tex(minU, minV).endVertex();
                buffer.pos(x + 7, y, z + 4).tex(minU, maxV).endVertex();
            }
            case 7 -> {
                buffer.pos(x + 3, y, z - 7).tex(maxU, maxV).endVertex();
                buffer.pos(x + 3, y + 4, z - 7).tex(maxU, minV).endVertex();
                buffer.pos(x + 7, y + 4, z - 4).tex(minU, minV).endVertex();
                buffer.pos(x + 7, y, z - 4).tex(minU, maxV).endVertex();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        TextureAtlasSprite forceField = GTQTTextures.FORCE_FIELD;
        if (isActive() && MinecraftForgeClient.getRenderPass() == 0) {

            BloomEffectUtil.requestCustomBloom(RENDER_HANDLER, (buffer) -> {
                Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
                if (entity != null) {
                    double minU = forceField.getMinU();
                    double maxU = forceField.getMaxU();
                    double minV = forceField.getMinV();
                    double maxV = forceField.getMaxV();
                    double xBaseOffset = 3 * getFrontFacing().getOpposite().getXOffset();
                    double zBaseOffset = 3 * getFrontFacing().getOpposite().getZOffset();
                    GlStateManager.pushMatrix();
                    GlStateManager.disableCull();
                    GlStateManager.disableAlpha();
                    GlStateManager.enableBlend();
                    Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    //Center O:  0,  0         1 ------- 8
                    //Corner 1:  7, -2        /           \
                    //Corner 2:  3, -6     2 /             \ 7
                    //Corner 3: -2, -6      |               |
                    //Corner 4: -6, -2      |       O       |
                    //Corner 5: -6,  3      |               |
                    //Corner 6: -2,  7     3 \             / 6
                    //Corner 7:  3,  7        \           /
                    //Corner 8:  7,  3         4 ------- 5
                    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    GlStateManager.translate(x + xBaseOffset + 0.5, 0, z + zBaseOffset + 0.5);
                    if (zBaseOffset == 0) {
                        GlStateManager.rotate(90F, 0F, 1F, 0F);
                    }
                    for (int i = 0; i < 8; i++) {
                        renderForceField(buffer, 0, y, 0, i, minU, maxU, minV, maxV);
                    }
                    Tessellator.getInstance().draw();
                    GlStateManager.disableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.enableCull();
                    GlStateManager.popMatrix();
                }
            });
        }
    }

    public AxisAlignedBB getRenderBoundingBox() {
        double xBaseOffset = 3 * getFrontFacing().getOpposite().getXOffset();
        double zBaseOffset = 3 * getFrontFacing().getOpposite().getZOffset();
        BlockPos pos = new BlockPos(this.getPos().getX() + xBaseOffset + 0.5, this.getPos().getY(), this.getPos().getZ() + zBaseOffset + 0.5);
        return new AxisAlignedBB(pos).grow(6, 6, 6);
    }

    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    public boolean isGlobalRenderer() {
        return true;
    }
}
