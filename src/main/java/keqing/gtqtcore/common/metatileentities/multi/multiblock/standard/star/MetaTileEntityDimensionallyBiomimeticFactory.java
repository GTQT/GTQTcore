package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.star;

import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.RelativeDirection;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.EffectRenderContext;
import gregtech.client.utils.IBloomEffect;
import gregtech.client.utils.RenderBufferHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockComputerCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTQuantumCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import static gregtech.api.util.RelativeDirection.*;
import static net.minecraft.util.EnumFacing.Axis.*;

public class MetaTileEntityDimensionallyBiomimeticFactory extends RecipeMapMultiblockController  implements IBloomEffect, IFastRenderMetaTileEntity {

    public MetaTileEntityDimensionallyBiomimeticFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDimensionallyBiomimeticFactory(metaTileEntityId);
    }

    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT, FRONT, UP)
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "              BBBBB              ", "             BBABABB             ", "         AAAABAABAABAAAA         ", "             BBBBBBB             ", "         AAAABAABAABAAAA         ", "             BBABABB             ", "              BBBBB              ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "                B                ", "                B                ", "             BBBBBBB             ", "            BB     BB            ", "            B  CCC  B            ", "       AAA  B CDDDC B  AAA       ", "          BBB CDDDC BBB          ", "       AAA  B CDDDC B  AAA       ", "            B  CCC  B            ", "            BB     BB            ", "             BBBBBBB             ", "                B                ", "                B                ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      AA                 AA      ", "        BB             BB        ", "      AA                 AA      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "      A                   A      ", "     AA                   AA     ", "      ABDD             DDBA      ", "     AA                   AA     ", "      A                   A      ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "       E                 E       ", "       C                 C       ", "    AA C                 C AA    ", "      BD                 DB      ", "    AA C                 C AA    ", "       C                 C       ", "       E                 E       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "    A                       A    ", "   AA                       AA   ", "    ABD                   DBA    ", "   AA                       AA   ", "    A                       A    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "     C                     C     ", "  AA C                     C AA  ", "    BD                     DB    ", "  AA C                     C AA  ", "     C                     C     ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  A                           A  ", "   BD                       DB   ", "  A                           A  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "               A A               ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " AA                           AA ", "   BD                       DB   ", " AA                           AA ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("             AAAAAAA             ", "               A A               ", "             BBBBBBB             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             BBBBBBB             ", "               A A               ", "               A A               ")
                .aisle("            AAFFFFFAA            ", "              BBBBB              ", "            BB     BB            ", "                                 ", "                                 ", "       E                 E       ", "                                 ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", " B                             B ", "AB                             BA", " B                             B ", "AB                             BA", " B                             B ", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "                                 ", "       E                 E       ", "                                 ", "                                 ", "            BB     BB            ", "              BBBBB              ", "               A A               ")
                .aisle("            AFFFFFFFA            ", "             BBABABB             ", "            B  CCC  B            ", "                                 ", "      A                   A      ", "       C                 C       ", "    A                       A    ", "     C                     C     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", " B                             B ", " B                             B ", "AAC                           CAA", " BC                           CB ", "AAC                           CAA", " B                             B ", " B                             B ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     C                     C     ", "    A                       A    ", "       C                 C       ", "      A                   A      ", "                                 ", "            B  CCC  B            ", "             BBABABB             ", "               A A               ")
                .aisle("            AFFAAAFFA            ", "         AAAABAABAABAAAA         ", "       AAA  B CDDDC B  AAA       ", "      AA                 AA      ", "     AA                   AA     ", "    AA C                 C AA    ", "   AA                       AA   ", "  AA C                     C AA  ", "  A                           A  ", " AA                           AA ", " A                             A ", " A                             A ", "AAB                           BAG", "AB                             BA", "AAC                           CAA", "AAD                           DAA", "ABD                           DBA", "AAD                           DAA", "AAC                           CAA", "AB                             BA", "AAB                           BAA", " A                             A ", " A                             A ", " AA                           AA ", "  A                           A  ", "  AA C                     C AA  ", "   AA                       AA   ", "    AA C                 C AA    ", "     AA                   AA     ", "      AA                 AA      ", "       AAA  B CDDDC B  AAA       ", "         AAAABAABAABAAAA         ", "            AAAAAAAAA            ")
                .aisle("            AFFAMAFFA            ", "             BBBBBBB             ", "          BBB CDDDC BBB          ", "        BB             BB        ", "      ABDD             DDBA      ", "      BD                 DB      ", "    ABD                   DBA    ", "    BD                     DB    ", "   BD                       DB   ", "   BD                       DB   ", "  B                           B  ", "  B                           B  ", "  B                           B  ", " B                             B ", " BC                           CB ", "ABD                           DBA", " BD                           DB ", "ABD                           DBA", " BC                           CB ", " B                             B ", "  B                           B  ", "  B                           B  ", "  B                           B  ", "   BD                       DB   ", "   BD                       DB   ", "    BD                     DB    ", "    ABD                   DBA    ", "      BD                 DB      ", "      ABDD             DDBA      ", "        BB             BB        ", "          BBB CDDDC BBB          ", "             BBBBBBB             ", "               A A               ")
                .aisle("            AFFAAAFFA            ", "         AAAABAABAABAAAA         ", "       AAA  B CDDDC B  AAA       ", "      AA                 AA      ", "     AA                   AA     ", "    AA C                 C AA    ", "   AA                       AA   ", "  AA C                     C AA  ", "  A                           A  ", " AA                           AA ", " A                             A ", " A                             A ", "AAB                           BAG", "AB                             BA", "AAC                           CAA", "AAD                           DAA", "ABD                           DBA", "AAD                           DAA", "AAC                           CAA", "AB                             BA", "AAB                           BAA", " A                             A ", " A                             A ", " AA                           AA ", "  A                           A  ", "  AA C                     C AA  ", "   AA                       AA   ", "    AA C                 C AA    ", "     AA                   AA     ", "      AA                 AA      ", "       AAA  B CDDDC B  AAA       ", "         AAAABAABAABAAAA         ", "            AAAAAAAAA            ")
                .aisle("            AFFFFFFFA            ", "             BBABABB             ", "            B  CCC  B            ", "                                 ", "      A                   A      ", "       C                 C       ", "    A                       A    ", "     C                     C     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", " B                             B ", " B                             B ", "AAC                           CAA", " BC                           CB ", "AAC                           CAA", " B                             B ", " B                             B ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     C                     C     ", "    A                       A    ", "       C                 C       ", "      A                   A      ", "                                 ", "            B  CCC  B            ", "             BBABABB             ", "               A A               ")
                .aisle("            AAFFFFFAA            ", "              BBBBB              ", "            BB     BB            ", "                                 ", "                                 ", "       E                 E       ", "                                 ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", " B                             B ", "AB                             BA", " B                             B ", "AB                             BA", " B                             B ", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "                                 ", "       E                 E       ", "                                 ", "                                 ", "            BB     BB            ", "              BBBBB              ", "               A A               ")
                .aisle("             AAAAAAA             ", "               A A               ", "             BBBBBBB             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  B                           B  ", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "AAB                           BAA", "  B                           B  ", "  B                           B  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             BBBBBBB             ", "               A A               ", "               A A               ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " A                             A ", "  B                           B  ", " A                             A ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "               A A               ", "                                 ")
                .aisle("                                 ", "               A A               ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", " AA                           AA ", "   BD                       DB   ", " AA                           AA ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "               A A               ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "  A                           A  ", "   BD                       DB   ", "  A                           A  ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "     E                     E     ", "     C                     C     ", "  AA C                     C AA  ", "    BD                     DB    ", "  AA C                     C AA  ", "     C                     C     ", "     E                     E     ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "    A                       A    ", "   AA                       AA   ", "    ABD                   DBA    ", "   AA                       AA   ", "    A                       A    ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "             ECCDCCE             ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "       E                 E       ", "       C                 C       ", "    AA C                 C AA    ", "      BD                 DB      ", "    AA C                 C AA    ", "       C                 C       ", "       E                 E       ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "             ECCDCCE             ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "              AAAAA              ", "                B                ", "                D                ", "                D                ", "                                 ", "                                 ", "                                 ", "                                 ", "      A                   A      ", "     AA                   AA     ", "      ABDD             DDBA      ", "     AA                   AA     ", "      A                   A      ", "                                 ", "                                 ", "                                 ", "                                 ", "                D                ", "                D                ", "                B                ", "              AAAAA              ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "                B                ", "                B                ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "      AA                 AA      ", "        BB             BB        ", "      AA                 AA      ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                B                ", "                B                ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "                B                ", "                B                ", "             BBBBBBB             ", "            BB     BB            ", "            B  CCC  B            ", "       AAA  B CDDDC B  AAA       ", "          BBB CDDDC BBB          ", "       AAA  B CDDDC B  AAA       ", "            B  CCC  B            ", "            BB     BB            ", "             BBBBBBB             ", "                B                ", "                B                ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "              BBBBB              ", "             BBABABB             ", "         AAAABAABAABAAAA         ", "             BBBBBBB             ", "         AAAABAABAABAAAA         ", "             BBABABB             ", "              BBBBB              ", "               A A               ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")
                .aisle("                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "               A A               ", "               A A               ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "            AAAAAAAAA            ", "               A A               ", "               A A               ", "               A A               ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ", "                                 ")

                .where('M', this.selfPredicate())
                .where('F', states(getCommonState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3)))
                .where('A', states(this.getCasingState()))
                .where('B', states(this.getCasingState1()))

                .where('C', states(this.getCasingState2()))
                .where('D', states(this.getCasingState3()))
                .where('E', states(this.getCasingState4()))
                .where('G', states(this.getCasingState5()))
                .where(' ', any());
        return pattern.build();
    }
    private static IBlockState getCommonState() {
        return MetaBlocks.COMPUTER_CASING.getState(BlockComputerCasing.CasingType.HIGH_POWER_CASING);
    }
    private IBlockState getCasingState() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.HIGH_ENERGY_CASING);
    }

    private IBlockState getCasingState1() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.ULTIMATE_HIGH_ENERGY_CASING);
    }


    private IBlockState getCasingState2() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.FIELD_GENERATOR_CASING);
    }

    private IBlockState getCasingState3() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.DIMENSIONAL_BRIDGE_CASING);
    }

    private IBlockState getCasingState4() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.SPACETIME_CASING);
    }

    private IBlockState getCasingState5() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.ANNIHILATION_CASING);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return iMultiblockPart == null ? GTQTTextures.ULTIMATE_HIGH_ENERGY_CASING : Textures.HIGH_POWER_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
        return GTQTTextures.COLLIDER_OVERLAY;
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    protected static final int NO_COLOR = 0;
    private int fusionRingColor = NO_COLOR;
    private boolean registeredBloomRenderTicket;

    @Override
    protected boolean shouldShowVoidingModeButton() {
        return false;
    }

    protected int getFusionRingColor() {
        return this.fusionRingColor;
    }

    protected boolean hasFusionRingColor() {
        return true;
    }

    protected void setFusionRingColor(int fusionRingColor) {
        if (this.fusionRingColor != fusionRingColor) {
            this.fusionRingColor = fusionRingColor;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderMetaTileEntity(double x, double y, double z, float partialTicks) {
        if (this.hasFusionRingColor() && !this.registeredBloomRenderTicket) {
            this.registeredBloomRenderTicket = true;
            BloomEffectUtil.registerBloomRender(FusionBloomSetup.INSTANCE, getBloomType(), this, this);
        }
    }

    private static BloomType getBloomType() {
        ConfigHolder.FusionBloom fusionBloom = ConfigHolder.client.shader.fusionBloom;
        return BloomType.fromValue(fusionBloom.useShader ? fusionBloom.bloomStyle : -1);
    }

    boolean backA;
    int RadomTime;

    @Override
    public void update() {
        super.update();
        if (!backA) if (RadomTime <= 10) RadomTime++;
        if (backA) if (RadomTime >= -10) RadomTime--;
        if (RadomTime == 10) {
            backA = true;
        }
        if (RadomTime == -10) {
            backA = false;
        }
        setFusionRingColor(0xFF000000 + RadomTime * 1250 * 50);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect(BufferBuilder buffer, EffectRenderContext context) {
        int color = this.getFusionRingColor();


        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());



            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() *8 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() *8 + 0.5,
                    2, 2, 10, 20,
                    r, g, b, a, X);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() *8 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 8+ 0.5,
                    2, 2, 10, 20,
                    r, g, b, a, Y);

            RenderBufferHelper.renderRing(buffer,
                    getPos().getX() - context.cameraX() + relativeBack.getXOffset() *8 + 0.5,
                    getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                    getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() *8 + 0.5,
                    2, 2, 10, 20,
                    r, g, b, a, Z);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *8 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() *8 + 0.5,
                6, 0.8, 10, 20,
                r, g, b, a, X);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *8 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 8+ 0.5,
                6, 0.8, 10, 20,
                r, g, b, a, Y);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *8 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset() + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() *8 + 0.5,
                6, 0.8, 10, 20,
                r, g, b, a, Z);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderBloomEffect(EffectRenderContext context) {
        return this.hasFusionRingColor();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        EnumFacing relativeRight = RelativeDirection.RIGHT.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());

        return new AxisAlignedBB(
                this.getPos().offset(relativeBack).offset(relativeRight, 6),
                this.getPos().offset(relativeBack, 13).offset(relativeRight.getOpposite(), 6));
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 0;
    }

    @Override
    public boolean isGlobalRenderer() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    private static final class FusionBloomSetup implements IRenderSetup {

        private static final FusionBloomSetup INSTANCE = new FusionBloomSetup();

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
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

            buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
        }

        @Override
        public void postDraw(BufferBuilder buffer) {
            Tessellator.getInstance().draw();

            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    }
}
