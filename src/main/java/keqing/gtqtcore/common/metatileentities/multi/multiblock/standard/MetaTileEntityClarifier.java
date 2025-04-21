package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.impl.PrimitiveRecipeLogic;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.RecipeMapPrimitiveMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockBoilerCasing.BoilerCasingType;
import gregtech.common.blocks.BlockMetalCasing.MetalCasingType;
import gregtech.common.blocks.BlockTurbineCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.blocks.StoneVariantBlock;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing5;
import keqing.gtsteam.api.metatileentity.multiblock.RecipeMapNoEnergyMultiblockController;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

public class MetaTileEntityClarifier extends RecipeMapNoEnergyMultiblockController {
    int heat;

    public MetaTileEntityClarifier(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CLARIFIER);
        this.recipeMapWorkable = new ClarifierLogic(this, GTQTcoreRecipeMaps.CLARIFIER);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityClarifier(this.metaTileEntityId);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("heat", heat);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        heat = data.getInteger("heat");
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("时间：%s 阳光直射：%s", getWorld().isDaytime(), this.getWorld().canSeeSky(getPos().up())));
            textList.add(new TextComponentTranslation("反应池稳定度：%s", heat));
        }
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("古埃及掌管放置Play的神", new Object[0]));
        tooltip.add(I18n.format("如果处于白天或者阳光直射会导致稳定度下降"));
        tooltip.add(I18n.format("稳定度每大于2000，则耗时减免10%%（可重复计算），减免后降低2000稳定度"));
    }

    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("      AAAA      ", "      AAAA      ", "      AAAA      ", "                ")
                .aisle("    AAAAAAAA    ", "    AADDDDAA    ", "    AA    AA    ", "                ")
                .aisle("   AAAAAAAAAA   ", "   ADDDDDDDDA   ", "   A        A   ", "                ")
                .aisle("  AAAAAAAAAAAA  ", "  ADDDDDDDDDDA  ", "  A          A  ", "                ")
                .aisle(" AAAAAAAAAAAAAA ", " ADDDDDDDDDDDDA ", " A            A ", "                ")
                .aisle(" AAAAAAAAAAAAAA ", " ADDDDDDDDDDDDA ", " A            A ", "                ")
                .aisle("AAAAAAAAAAAAAAAA", "ADDDDDDDDDDDDDDA", "A              A", "                ")
                .aisle("AAAAAAAAAAAAAAAA", "ADDDDDDBBDDDDDDA", "A      BB      A", "       EE       ")
                .aisle("AAAAAAAAAAAAAAAA", "ADDDDDDBBDDDDDDA", "A      BBF     A", "       EE       ")
                .aisle("AAAAAAAAAAAAAAAA", "ADDDDDDDDDDDDDDA", "A       FFF    A", "                ")
                .aisle(" AAAAAAAAAAAAAA ", " ADDDDDDDDDDDDA ", " A       FFF  A ", "                ")
                .aisle(" AAAAAAAAAAAAAA ", " ADDDDDDDDDDDDA ", " A        FFF A ", "                ")
                .aisle("  AAAAAAAAAAAA  ", "  ADDDDDDDDDDA  ", "  A        FFA  ", "                ")
                .aisle("   AAAAAAAAAC   ", "   ADDDDDDDDA   ", "   A        A   ", "                ")
                .aisle("    AAAAAAAAC   ", "    AADDDDAA    ", "    AA    AA    ", "                ")
                .aisle("      AAAABBBB  ", "      AAAABBSB  ", "      AAAABBBB  ", "                ")
                .where('S', selfPredicate())
                .where('A', states(MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT)).setMinGlobalLimited(250))
                .where('B', states(MetaBlocks.METAL_CASING.getState(MetalCasingType.STEEL_SOLID)).or(autoAbilities()))
                .where('C', states(MetaBlocks.BOILER_CASING.getState((BoilerCasingType.STEEL_PIPE))))
                .where('D', states(GTQTMetaBlocks.blockMultiblockCasing5.getState((BlockMultiblockCasing5.TurbineCasingType.CLARIFIER_CASING))))
                .where('E', states(MetaBlocks.TURBINE_CASING.getState(BlockTurbineCasing.TurbineCasingType.STEEL_GEARBOX)))
                .where('F', states(MetaBlocks.FRAMES.get(Materials.Steel).getBlock(Materials.Steel)))
                .where(' ', any())
                .build();
    }

    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.COKING_TOWER_OVERLAY;
    }

    @Override
    public boolean getIsWeatherOrTerrainResistant() {
        return true;
    }

    protected class ClarifierLogic extends PrimitiveRecipeLogic {

        public ClarifierLogic(RecipeMapNoEnergyMultiblockController tileEntity, RecipeMap<?> recipeMap) {
            super(tileEntity, recipeMap);
        }

        @Override
        public void update() {
            super.update();
            if (!getWorld().isDaytime() && heat < 10000) heat += 2;
            if (!getWorld().canSeeSky(getPos()) && heat < 10000) heat += 2;
            else if (heat > 0) heat--;
        }

        protected void updateRecipeProgress() {
            if (canRecipeProgress) {
                if (heat > 2000) {
                    maxProgressTime = (int) (maxProgressTime * 0.9);
                    heat -= 2000;
                }
                if (++progressTime > maxProgressTime) {
                    completeRecipe();
                    heat = heat * 3 / 4;
                }
            }
        }
    }
}