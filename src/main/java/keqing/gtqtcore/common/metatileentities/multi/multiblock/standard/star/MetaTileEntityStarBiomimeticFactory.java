package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.star;

import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.impl.*;
import gregtech.api.metatileentity.IFastRenderMetaTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.*;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.recipeproperties.FusionEUToStartProperty;
import gregtech.api.util.RelativeDirection;
import gregtech.api.util.interpolate.Eases;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.IRenderSetup;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;
import gregtech.client.shader.postprocessing.BloomEffect;
import gregtech.client.shader.postprocessing.BloomType;
import gregtech.client.utils.*;
import gregtech.common.ConfigHolder;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTMultiblockCasing;
import keqing.gtqtcore.common.block.blocks.GTQTQuantumCasing;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static gregtech.api.GTValues.UXV;

public class MetaTileEntityStarBiomimeticFactory extends RecipeMapMultiblockController
        implements IFastRenderMetaTileEntity, IBloomEffect {

    protected static final int NO_COLOR = 0;


    private EnergyContainerList inputEnergyContainers;
    private long heat = 0; // defined in TileEntityFusionReactor but serialized in FusionRecipeLogic
    private int fusionRingColor = NO_COLOR;


    @SideOnly(Side.CLIENT)
    private boolean registeredBloomRenderTicket;

    public MetaTileEntityStarBiomimeticFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, RecipeMaps.FUSION_RECIPES);
        this.recipeMapWorkable = new FusionRecipeLogic(this);
        this.energyContainer = new EnergyContainerHandler(this, 0, 0, 0, 0, 0) {


            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityStarBiomimeticFactory(metaTileEntityId);
    }

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



    @SideOnly(Side.CLIENT)
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

    protected int getFusionRingColor() {
        return this.fusionRingColor;
    }

    protected boolean hasFusionRingColor() {
        return this.fusionRingColor != NO_COLOR;
    }

    protected void setFusionRingColor(int fusionRingColor) {
        if (this.fusionRingColor != fusionRingColor) {
            this.fusionRingColor = fusionRingColor;
            writeCustomData(GregtechDataCodes.UPDATE_COLOR, buf -> buf.writeVarInt(fusionRingColor));
        }
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        long energyStored = this.energyContainer.getEnergyStored();
        super.formStructure(context);
        this.initializeAbilities();
        ((EnergyContainerHandler) this.energyContainer).setEnergyStored(energyStored);
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.energyContainer = new EnergyContainerHandler(this, 0, 0, 0, 0, 0) {


            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
        this.inputEnergyContainers = new EnergyContainerList(Lists.newArrayList());
        this.heat = 0;
        this.setFusionRingColor(NO_COLOR);
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
        this.energyContainer = new EnergyContainerHandler(this, euCapacity, GTValues.V[UXV], 0, 0, 0) {


            @Override
            public String getName() {
                return GregtechDataCodes.FUSION_REACTOR_ENERGY_CONTAINER_TRAIT;
            }
        };
    }

    private long calculateEnergyStorageFactor(int energyInputAmount) {
        return energyInputAmount * (long) Math.pow(2, UXV - 6) * 10000000L;
    }

    @Override
    protected void updateFormedValid() {
        if (this.inputEnergyContainers.getEnergyStored() > 0) {
            long energyAdded = this.energyContainer.addEnergy(this.inputEnergyContainers.getEnergyStored());
            if (energyAdded > 0) this.inputEnergyContainers.removeEnergy(energyAdded);
        }
        super.updateFormedValid();
        if (recipeMapWorkable.isWorking() && fusionRingColor == NO_COLOR) {
            if (recipeMapWorkable.getPreviousRecipe() != null &&
                    !recipeMapWorkable.getPreviousRecipe().getFluidOutputs().isEmpty()) {
                setFusionRingColor(0xFF000000 |
                        recipeMapWorkable.getPreviousRecipe().getFluidOutputs().get(0).getFluid().getColor());
            }
        } else if (!recipeMapWorkable.isWorking() && isStructureFormed()) {
            setFusionRingColor(NO_COLOR);
        }
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeVarInt(this.fusionRingColor);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.fusionRingColor = buf.readVarInt();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        if (dataId == GregtechDataCodes.UPDATE_COLOR) {
            this.fusionRingColor = buf.readVarInt();
        } else {
            super.receiveCustomData(dataId, buf);
        }
    }


    public long getHeat() {
        return heat;
    }

    private class FusionRecipeLogic extends MultiblockRecipeLogic {

        public FusionRecipeLogic(MetaTileEntityStarBiomimeticFactory tileEntity) {
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
        public long getMaxVoltage() {
            return Math.min(GTValues.V[UXV], super.getMaxVoltage());
        }

        @Override
        public void updateWorkable() {
            super.updateWorkable();
            // Drain heat when the reactor is not active, is paused via soft mallet, or does not have enough energy and
            // has fully wiped recipe progress
            // Don't drain heat when there is not enough energy and there is still some recipe progress, as that makes
            // it doubly hard to complete the recipe
            // (Will have to recover heat and recipe progress)
            if (heat > 0) {
                if (!isActive || !workingEnabled || (hasNotEnoughEnergy && progressTime == 0)) {
                    heat = heat <= 10000 ? 0 : (heat - 10000);
                }
            }
        }

        @Override
        public boolean checkRecipe( Recipe recipe) {
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


        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound tag = super.serializeNBT();
            tag.setLong("Heat", heat);
            return tag;
        }

        @Override
        public void deserializeNBT( NBTTagCompound compound) {
            super.deserializeNBT(compound);
            heat = compound.getLong("Heat");
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

    @Override
    @SideOnly(Side.CLIENT)
    public void renderBloomEffect( BufferBuilder buffer,  EffectRenderContext context) {
        if (!this.hasFusionRingColor()) return;
        int color = RenderUtil.interpolateColor(this.getFusionRingColor(), -1, Eases.QUAD_IN.getInterpolation(
                Math.abs((Math.abs(getOffsetTimer() % 50) + context.partialTicks()) - 25) / 25));
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        EnumFacing relativeBack = RelativeDirection.BACK.getRelativeFacing(getFrontFacing(), getUpwardsFacing(),
                isFlipped());
        EnumFacing.Axis axis = RelativeDirection.UP.getRelativeFacing(getFrontFacing(), getUpwardsFacing(), isFlipped())
                .getAxis();

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *  16 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()  + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 16 + 0.5,
                9, 0.2, 10, 20,
                r, g, b, a, EnumFacing.Axis.X);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *  16 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()  + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 16 + 0.5,
                9, 0.2, 10, 20,
                r, g, b, a, EnumFacing.Axis.Y);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *  16 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()  + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 16 + 0.5,
                9, 0.2, 10, 20,
                r, g, b, a, EnumFacing.Axis.Z);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *  16 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()  + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 16 + 0.5,
                1, 6, 10, 20,
                r, g, b, a, EnumFacing.Axis.X);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *  16 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()  + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 16 + 0.5,
                1, 6, 10, 20,
                r, g, b, a, EnumFacing.Axis.Z);

        RenderBufferHelper.renderRing(buffer,
                getPos().getX() - context.cameraX() + relativeBack.getXOffset() *  16 + 0.5,
                getPos().getY() - context.cameraY() + relativeBack.getYOffset()  + 0.5,
                getPos().getZ() - context.cameraZ() + relativeBack.getZOffset() * 16 + 0.5,
                1, 6, 10, 20,
                r, g, b, a, EnumFacing.Axis.Y);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRenderBloomEffect( EffectRenderContext context) {
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

    private static BloomType getBloomType() {
        ConfigHolder.FusionBloom fusionBloom = ConfigHolder.client.shader.fusionBloom;
        return BloomType.fromValue(fusionBloom.useShader ? fusionBloom.bloomStyle : -1);
    }

    @SideOnly(Side.CLIENT)
    private static final class FusionBloomSetup implements IRenderSetup {

        private static final FusionBloomSetup INSTANCE = new FusionBloomSetup();

        float lastBrightnessX;
        float lastBrightnessY;

        @Override
        public void preDraw( BufferBuilder buffer) {
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
        public void postDraw( BufferBuilder buffer) {
            Tessellator.getInstance().draw();

            GlStateManager.enableTexture2D();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastBrightnessX, lastBrightnessY);
        }
    }
}