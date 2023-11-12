package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.util.interpolate.Eases;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.utils.BloomEffectUtil;
import gregtech.client.utils.RenderBufferHelper;
import gregtech.client.utils.RenderUtil;
import gregtech.common.ConfigHolder;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing;
import keqing.gtqtcore.common.block.blocks.GTQTQuantumCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.Objects;
public class MetaTileEntityStarBiomimeticFactory extends RecipeMapMultiblockController implements IFastRenderMetaTileEntity {
    private Integer color;

    public MetaTileEntityStarBiomimeticFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.STAR_BIOMIMETIC_FACTORY);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity metaTileEntityHolder) {
        return new MetaTileEntityStarBiomimeticFactory(this.metaTileEntityId);
    }

    @Nonnull
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "            CCCCCCCCC            ",
                        "               C C               ",
                        "            CCCCCCCCC            ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "              DDDDD              ",
                        "             DDCDCDD             ",
                        "         CCCCDCCDCCDCCCC         ",
                        "             DDDDDDD             ",
                        "         CCCCDCCDCCDCCCC         ",
                        "             DDCDCDD             ",
                        "              DDDDD              ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "                D                ",
                        "             DDDDDDD             ",
                        "            DD     DD            ",
                        "            D  EEE  D            ",
                        "       CCC  D EAAAE D  CCC       ",
                        "          DDD EAAAE DDD          ",
                        "       CCC  D EAAAE D  CCC       ",
                        "            D  EEE  D            ",
                        "            DD     DD            ",
                        "             DDDDDDD             ",
                        "                D                ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "                D                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "      CC                 CC      ",
                        "        DD             DD        ",
                        "      CC                 CC      ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                D                ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "              CCCCC              ",
                        "                D                ",
                        "                A                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "      C                   C      ",
                        "     CC                   CC     ",
                        "      CDAA             AADC      ",
                        "     CC                   CC     ",
                        "      C                   C      ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                A                ",
                        "                D                ",
                        "              CCCCC              ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "             SEEAEES             ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "       S                 S       ",
                        "       E                 E       ",
                        "    CC E                 E CC    ",
                        "      DA                 AD      ",
                        "    CC E                 E CC    ",
                        "       E                 E       ",
                        "       S                 S       ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "             SEEAEES             ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "              CCCCC              ",
                        "                D                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "    C                       C    ",
                        "   CC                       CC   ",
                        "    CDA                   ADC    ",
                        "   CC                       CC   ",
                        "    C                       C    ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                D                ",
                        "              CCCCC              ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "             SEEAEES             ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "     S                     S     ",
                        "     E                     E     ",
                        "  CC E                     E CC  ",
                        "    DA                     AD    ",
                        "  CC E                     E CC  ",
                        "     E                     E     ",
                        "     S                     S     ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "             SEEAEES             ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "                D                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  C                           C  ",
                        "   DA                       AD   ",
                        "  C                           C  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                D                ",
                        "               C C               ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        " CC                           CC ",
                        "   DA                       AD   ",
                        " CC                           CC ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "               C C               ",
                        "                D                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        " C                             C ",
                        "  D                           D  ",
                        " C                             C ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                D                ",
                        "               C C               ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "               C C               ",
                        "                D                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        " C                             C ",
                        "  D                           D  ",
                        " C                             C ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                D                ",
                        "               C C               ",
                        "                                 ")
                .aisle(
                        "             CCCCCCC             ",
                        "               C C               ",
                        "             DDDDDDD             ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  D                           D  ",
                        "  D                           D  ",
                        "CCD                           DCC",
                        "  D                           D  ",
                        "CCD                           DCC",
                        "  D                           D  ",
                        "  D                           D  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "             DDDDDDD             ",
                        "               C C               ",
                        "               C C               ")
                .aisle(
                        "            CCHHHHHCC            ",
                        "              DDDDD              ",
                        "            DD     DD            ",
                        "                                 ",
                        "                                 ",
                        "       S                 S       ",
                        "                                 ",
                        "     S                     S     ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  D                           D  ",
                        "  D                           D  ",
                        " D                             D ",
                        "CD                             DC",
                        " D                             D ",
                        "CD                             DC",
                        " D                             D ",
                        "  D                           D  ",
                        "  D                           D  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "     S                     S     ",
                        "                                 ",
                        "       S                 S       ",
                        "                                 ",
                        "                                 ",
                        "            DD     DD            ",
                        "              DDDDD              ",
                        "               C C               ")
                .aisle(
                        "            CHHHHHHHC            ",
                        "             DDCDCDD             ",
                        "            D  EEE  D            ",
                        "                                 ",
                        "      C                   C      ",
                        "       E                 E       ",
                        "    C                       C    ",
                        "     E                     E     ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  D                           D  ",
                        " D                             D ",
                        " D                             D ",
                        "CCE                           ECC",
                        " DE                           ED ",
                        "CCE                           ECC",
                        " D                             D ",
                        " D                             D ",
                        "  D                           D  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "     E                     E     ",
                        "    C                       C    ",
                        "       E                 E       ",
                        "      C                   C      ",
                        "                                 ",
                        "            D  EEE  D            ",
                        "             DDCDCDD             ",
                        "               C C               ")
                .aisle(
                        "            CHHCCCHHC            ",
                        "         CCCCDCCDCCDCCCC         ",
                        "       CCC  D EAAAE D  CCC       ",
                        "      CC                 CC      ",
                        "     CC                   CC     ",
                        "    CC E                 E CC    ",
                        "   CC                       CC   ",
                        "  CC E                     E CC  ",
                        "  C                           C  ",
                        " CC                           CC ",
                        " C                             C ",
                        " C                             C ",
                        "CCD                           DCC",
                        "CD                             DC",
                        "CCE                           ECC",
                        "CCA                           ACC",
                        "CDA                           ADC",
                        "CCA                           ACC",
                        "CCE                           ECC",
                        "CD                             DC",
                        "CCD                           DCC",
                        " C                             C ",
                        " C                             C ",
                        " CC                           CC ",
                        "  C                           C  ",
                        "  CC E                     E CC  ",
                        "   CC                       CC   ",
                        "    CC E                 E CC    ",
                        "     CC                   CC     ",
                        "      CC                 CC      ",
                        "       CCC  D EAAAE D  CCC       ",
                        "         CCCCDCCDCCDCCCC         ",
                        "            CCCCCCCCC            ")
                .aisle(
                        "            CHHCCCHHC            ",
                        "             DDDDDDD             ",
                        "          DDD EAAAE DDD          ",
                        "        DD             DD        ",
                        "      CDAA             AADC      ",
                        "      DA                 AD      ",
                        "    CDA                   ADC    ",
                        "    DA                     AD    ",
                        "   DA                       AD   ",
                        "   DA                       AD   ",
                        "  D                           D  ",
                        "  D                           D  ",
                        "  D                           D  ",
                        " D                             D ",
                        " DE                           ED ",
                        "CDA                           ADC",
                        " DA                           AD ",
                        "CDA                           ADC",
                        " DE                           ED ",
                        " D                             D ",
                        "  D                           D  ",
                        "  D                           D  ",
                        "  D                           D  ",
                        "   DA                       AD   ",
                        "   DA                       AD   ",
                        "    DA                     AD    ",
                        "    CDA                   ADC    ",
                        "      DA                 AD      ",
                        "      CDAA             AADC      ",
                        "        DD             DD        ",
                        "          DDD EAAAE DDD          ",
                        "             DDDDDDD             ",
                        "               C C               ")
                .aisle(
                        "            CHHCCCHHC            ",
                        "         CCCCDCCDCCDCCCC         ",
                        "       CCC  D EAAAE D  CCC       ",
                        "      CC                 CC      ",
                        "     CC                   CC     ",
                        "    CC E                 E CC    ",
                        "   CC                       CC   ",
                        "  CC E                     E CC  ",
                        "  C                           C  ",
                        " CC                           CC ",
                        " C                             C ",
                        " C                             C ",
                        "CCD                           DCC",
                        "CD                             DC",
                        "CCE                           ECC",
                        "CCA                           ACC",
                        "CDA                           ADC",
                        "CCA                           ACC",
                        "CCE                           ECC",
                        "CD                             DC",
                        "CCD                           DCC",
                        " C                             C ",
                        " C                             C ",
                        " CC                           CC ",
                        "  C                           C  ",
                        "  CC E                     E CC  ",
                        "   CC                       CC   ",
                        "    CC E                 E CC    ",
                        "     CC                   CC     ",
                        "      CC                 CC      ",
                        "       CCC  D EAAAE D  CCC       ",
                        "         CCCCDCCDCCDCCCC         ",
                        "            CCCCCCCCC            ")
                .aisle(
                        "            CHHHHHHHC            ",
                        "             DDCDCDD             ",
                        "            D  EEE  D            ",
                        "                                 ",
                        "      C                   C      ",
                        "       E                 E       ",
                        "    C                       C    ",
                        "     E                     E     ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  D                           D  ",
                        " D                             D ",
                        " D                             D ",
                        "CCE                           ECC",
                        " DE                           ED ",
                        "CCE                           ECC",
                        " D                             D ",
                        " D                             D ",
                        "  D                           D  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "     E                     E     ",
                        "    C                       C    ",
                        "       E                 E       ",
                        "      C                   C      ",
                        "                                 ",
                        "            D  EEE  D            ",
                        "             DDCDCDD             ",
                        "               C C               ")
                .aisle(
                        "            CCHHHHHCC            ",
                        "              DDDDD              ",
                        "            DD     DD            ",
                        "                                 ",
                        "                                 ",
                        "       S                 S       ",
                        "                                 ",
                        "     S                     S     ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  D                           D  ",
                        "  D                           D  ",
                        " D                             D ",
                        "CD                             DC",
                        " D                             D ",
                        "CD                             DC",
                        " D                             D ",
                        "  D                           D  ",
                        "  D                           D  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "     S                     S     ",
                        "                                 ",
                        "       S                 S       ",
                        "                                 ",
                        "                                 ",
                        "            DD     DD            ",
                        "              DDDDD              ",
                        "               C C               ")
                .aisle(
                        "             CCCCCCC             ",
                        "               C C               ",
                        "             DDDDDDD             ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  D                           D  ",
                        "  D                           D  ",
                        "CCD                           DCC",
                        "  D                           D  ",
                        "CCD                           DCC",
                        "  D                           D  ",
                        "  D                           D  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "             DDDDDDD             ",
                        "               C C               ",
                        "               C C               ")
                .aisle(
                        "                                 ",
                        "               C C               ",
                        "                D                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        " C                             C ",
                        "  D                           D  ",
                        " C                             C ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                D                ",
                        "               C C               ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "               C C               ",
                        "                D                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        " C                             C ",
                        "  D                           D  ",
                        " C                             C ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                D                ",
                        "               C C               ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        " CC                           CC ",
                        "   DA                       AD   ",
                        " CC                           CC ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "                D                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "  C                           C  ",
                        "   DA                       AD   ",
                        "  C                           C  ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                D                ",
                        "               C C               ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "             SEEAEES             ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "     S                     S     ",
                        "     E                     E     ",
                        "  CC E                     E CC  ",
                        "    DA                     AD    ",
                        "  CC E                     E CC  ",
                        "     E                     E     ",
                        "     S                     S     ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "             SEEAEES             ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "              CCCCC              ",
                        "                D                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "    C                       C    ",
                        "   CC                       CC   ",
                        "    CDA                   ADC    ",
                        "   CC                       CC   ",
                        "    C                       C    ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                D                ",
                        "              CCCCC              ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "             SEEAEES             ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "       S                 S       ",
                        "       E                 E       ",
                        "    CC E                 E CC    ",
                        "      DA                 AD      ",
                        "    CC E                 E CC    ",
                        "       E                 E       ",
                        "       S                 S       ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "             SEEAEES             ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "              CCCCC              ",
                        "                D                ",
                        "                A                ",
                        "                A                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "      C                   C      ",
                        "     CC                   CC     ",
                        "      CDAA             AADC      ",
                        "     CC                   CC     ",
                        "      C                   C      ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                A                ",
                        "                A                ",
                        "                D                ",
                        "              CCCCC              ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "                D                ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "      CC                 CC      ",
                        "        DD             DD        ",
                        "      CC                 CC      ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                D                ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                D                ",
                        "                D                ",
                        "             DDDDDDD             ",
                        "            DD     DD            ",
                        "            D  EEE  D            ",
                        "       CCC  D EAAAE D  CCC       ",
                        "          DDD EAAAE DDD          ",
                        "       CCC  D EAAAE D  CCC       ",
                        "            D  EEE  D            ",
                        "            DD     DD            ",
                        "             DDDDDDD             ",
                        "                D                ",
                        "                D                ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "              DDDDD              ",
                        "             DDCDCDD             ",
                        "         CCCCDCCDCCDCCCC         ",
                        "             DDDDDDD             ",
                        "         CCCCDCCDCCDCCCC         ",
                        "             DDCDCDD             ",
                        "              DDDDD              ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .aisle(
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "            CCCCCCCCC            ",
                        "               CMC               ",
                        "            CCCCCCCCC            ",
                        "               C C               ",
                        "               C C               ",
                        "               C C               ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ",
                        "                                 ")
                .where('M', this.selfPredicate())
                .where('C', states(this.getCasingState())
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(4).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMaxGlobalLimited(8).setPreviewCount(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(3))
                )
                .where('A', states(this.getCasingState1()))
                .where('D', states(this.getCasingState2()))
                .where('E', states(this.getCasingState3()))
                .where('S', states(this.getCasingState4()))
                .where('H', states(this.getCasingState5()))
                .where(' ', any())
                .build();
    }

    private IBlockState getCasingState() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.QUANTUM_CASING);
    }

    private IBlockState getCasingState1() {
        return GTQTMetaBlocks.MULTI_CASING.getState(GTQTMultiblockCasing.CasingType.BEAM_CORE_2);
    }

    private IBlockState getCasingState2() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.QUANTUM_COMPUTER_CASING);
    }

    private IBlockState getCasingState3() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.MOTOR_BLOCK);
    }

    private IBlockState getCasingState4() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.ANNIHILATION_CASING);
    }
    private IBlockState getCasingState5() {
        return GTQTMetaBlocks.QUANTUM_CASING.getState(GTQTQuantumCasing.CasingType.DIMENSIONAL_CASING);
    }


    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.QUANTUM_CASING;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    protected OrientedOverlayRenderer getFrontOverlay() {
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

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();
        int newColor = 0xFF000000;
        if (!Objects.equals(color, newColor)) {
            color = newColor;
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
                            x + getFrontFacing().getOpposite().getXOffset() * 16 + 0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 16 + 0.5,
                            9, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Y);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 16 + 0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 16 + 0.5,
                            9, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.X);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 16 + 0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 16 + 0.5,
                            9, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Z);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 16 + 0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 16 + 0.5,
                            1, 6, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Y);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 16 + 0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 16 + 0.5,
                            1, 6, 10, 20,
                            r, g, b, a, EnumFacing.Axis.X);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 16 + 0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 16 + 0.5,
                            1, 6, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Z);
                    Tessellator.getInstance().draw();
                }
                if (entity != null) {
                    buffer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION_COLOR);
                    RenderBufferHelper.renderRing(buffer,
                            x + getFrontFacing().getOpposite().getXOffset() * 16 + 0.5,
                            y + 0.5,
                            z + getFrontFacing().getOpposite().getZOffset() * 16 + 0.5,
                            12, 0.2, 10, 20,
                            r, g, b, a, EnumFacing.Axis.Y);
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
}