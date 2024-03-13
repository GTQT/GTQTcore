package keqing.gtqtcore.common.metatileentities.multi.multiblock;

import gregtech.api.gui.ModularUI;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IPrimitivePump;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.LocalizationUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.BlockSteamCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.MetaTileEntities;

import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MetaTileEntityIndustryWaterPump extends MultiblockControllerBase implements IPrimitivePump {

    private IFluidTank waterTank;
    private int biomeModifier = 0;
    private int hatchModifier = 0;

    public MetaTileEntityIndustryWaterPump(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        resetTileAbilities();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityIndustryWaterPump(metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote && getOffsetTimer() % 20 == 0 && isStructureFormed()) {
            if (biomeModifier == 0) {
                biomeModifier = getAmount();
            } else if (biomeModifier > 0) {
                waterTank.fill(Materials.Water.getFluid(getFluidProduction()), true);
            }
        }
    }

    private int getAmount() {
        WorldProvider provider = getWorld().provider;
        if (provider.isNether() || provider.doesWaterVaporize()) {
            return -1; // Disabled
        }
        Biome biome = getWorld().getBiome(getPos());
        Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);
        if (biomeTypes.contains(BiomeDictionary.Type.NETHER)) {
            return -1; // Disabled
        }
        if (biomeTypes.contains(BiomeDictionary.Type.WATER)) {
            return 1000;
        } else if (biomeTypes.contains(BiomeDictionary.Type.SWAMP) || biomeTypes.contains(BiomeDictionary.Type.WET)) {
            return 800;
        } else if (biomeTypes.contains(BiomeDictionary.Type.JUNGLE)) {
            return 350;
        } else if (biomeTypes.contains(BiomeDictionary.Type.SNOWY)) {
            return 300;
        } else
        if (biomeTypes.contains(BiomeDictionary.Type.PLAINS) || biomeTypes.contains(BiomeDictionary.Type.FOREST)) {
            return 250;
        } else if (biomeTypes.contains(BiomeDictionary.Type.COLD)) {
            return 175;
        } else if (biomeTypes.contains(BiomeDictionary.Type.BEACH)) {
            return 170;
        }
        return 100;
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        return null;
    }

    @Override
    protected boolean openGUIOnRightClick() {
        return false;
    }

    @Override
    protected void updateFormedValid() {}

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
    }

    private void initializeAbilities() {
        List<IFluidTank> tanks = getAbilities(MultiblockAbility.PUMP_FLUID_HATCH);
        if (tanks == null || tanks.size() == 0) {
            tanks = getAbilities(MultiblockAbility.EXPORT_FLUIDS);
            this.hatchModifier = tanks.get(0).getCapacity() == 8000 ? 2 : 4;
        } else {
            this.hatchModifier = 1;
        }
        this.waterTank = tanks.get(0);
    }

    private void resetTileAbilities() {
        this.waterTank = new FluidTank(0);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("F*F","F*F", "FEF", "FEF", "FEF", "FEF")
                .aisle("***","***", "EOE", "EEE", "EEE", "EEE")
                .aisle("F*F","F*F", "FSF","FEF",  "FEF", "FEF")
                .where('S', selfPredicate())
                .where('E', states(getCasingState()))
                .where('F', frames(Materials.Steel))
                .where('O', (abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1)))
                .where('*', any())
                .build();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.GALVANIZE_STEEL_CASING;
    }
    private IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.PRIMITIVE_PUMP_OVERLAY;
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, getFrontFacing(), true, true);
    }

    @Override
    public String[] getDescription() {
        List<String> list = new ArrayList<>();
        list.add(I18n.format("四倍出水量于原始水泵！所以下边的数据你自己乘六就行"));
        list.add(I18n.format("gregtech.multiblock.primitive_water_pump.description"));
        Collections.addAll(list, LocalizationUtils.formatLines("gregtech.multiblock.primitive_water_pump.extra1"));
        Collections.addAll(list, LocalizationUtils.formatLines("gregtech.multiblock.primitive_water_pump.extra2"));
        return list.toArray(new String[0]);
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("原始水泵Plus（加强六倍）", new Object[0]));
    }
    private boolean isRainingInBiome() {
        World world = getWorld();
        if (!world.isRaining()) {
            return false;
        }
        return world.getBiome(getPos()).canRain();
    }

    @Override
    public int getFluidProduction() {
        return (int) (biomeModifier * hatchModifier * (isRainingInBiome() ? 9 : 6));
    }

    @Override
    public boolean allowsExtendedFacing() {
        return false;
    }
}