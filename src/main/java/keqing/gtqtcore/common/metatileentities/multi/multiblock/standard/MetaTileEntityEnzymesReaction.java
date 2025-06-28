package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metatileentity.GTQTNoTierMultiblockController;
import keqing.gtqtcore.api.metatileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.EnzymesReactionProperty;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing1;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.utils.GTQTUtil.splitNumberToDigits;

public class MetaTileEntityEnzymesReaction extends GTQTNoTierMultiblockController {
    int A;
    int B;
    int C;
    int D;
    int E;
    FluidStack BIO1 = Enzymesa.getFluid(1000);
    FluidStack BIO2 = Enzymesb.getFluid(1000);
    FluidStack BIO3 = Enzymesc.getFluid(1000);
    FluidStack BIO4 = Enzymesd.getFluid(1000);
    FluidStack BIO5 = Enzymese.getFluid(1000);
    private int glass_tier;
    private int clean_tier;
    private int tubeTier;


    public MetaTileEntityEnzymesReaction(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{
                GTQTcoreRecipeMaps.ENZYMES_REACTION_RECIPES
        });

        //setMaxParallel(auto);
        setMaxParallelFlag(true);
        //setTimeReduce(auto);
        setTimeReduceFlag(true);
        setOverclocking(3);
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("A", A);
        data.setInteger("B", B);
        data.setInteger("C", C);
        data.setInteger("D", D);
        data.setInteger("E", E);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        A = data.getInteger("A");
        B = data.getInteger("B");
        C = data.getInteger("C");
        D = data.getInteger("D");
        E = data.getInteger("E");
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityEnzymesReaction(metaTileEntityId);
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object glass_tier = context.get("LGLTieredStats");
        Object clean_tier = context.get("ZJTieredStats");
        Object tubeTier = context.get("ChemicalPlantTubeTieredStats");
        this.tubeTier = GTQTUtil.getOrDefault(() -> tubeTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) tubeTier).getIntTier(),
                0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) glass_tier).getIntTier(),
                0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired,
                () -> ((WrappedIntTired) clean_tier).getIntTier(),
                0);


        setMaxParallel(this.clean_tier + this.glass_tier);
        setTimeReduce(((10 - this.clean_tier) / 10.0));
    }

    @Nonnull
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("JCCCJ", "JCCCJ", "GGGGG", "JCCCJ", "JCCCJ")
                .aisle("JCCCJ", "JPPPJ", "G   G", "JPPPJ", "JCCCJ")
                .aisle("JCCCJ", "JPPPJ", "G   G", "JPPPJ", "JCCCJ")
                .aisle("JCCCJ", "JPPPJ", "G   G", "JPPPJ", "JCCCJ")
                .aisle("JCCCJ", "JCSCJ", "GGGGG", "JCCCJ", "JCCCJ")
                .where('S', selfPredicate())
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING.get())
                .where('G', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('C', states(getCasingState()).setMinGlobalLimited(32)
                        .or(autoAbilities()))
                .where('P', TiredTraceabilityPredicate.CP_TUBE.get())
                .where(' ', any())
                .build();
    }

    protected IBlockState getCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing1.getState(BlockMultiblockCasing1.CasingType.Talonite);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.Talonite;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.ALGAE_FARM_OVERLAY;
    }

    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"gtqtcore.multiblock.br.3", syncer.syncInt(glass_tier), syncer.syncInt(tubeTier)));
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"洁净等级:%s", syncer.syncInt(clean_tier)));
        keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"组合因子：%s %s %s %s %s", syncer.syncInt(A), syncer.syncInt(B), syncer.syncInt(C), syncer.syncInt(D), syncer.syncInt(E)));

    }

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        int number = recipe.getProperty(EnzymesReactionProperty.getInstance(), 0);
        int[] digits = splitNumberToDigits(number);
        if (super.checkRecipe(recipe, consumeIfSuccess))
            if (digits[0] >= A && digits[1] >= B && digits[2] >= C && digits[3] >= D && digits[4] >= E) {
                A -= digits[0];
                B -= digits[1];
                C -= digits[2];
                D -= digits[3];
                E -= digits[4];
            }
        return false;
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        this.markDirty();
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.biorea.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.1"));
        tooltip.add(I18n.format("gregtech.machine.bioreb.gtqtupdate.2"));
    }

    @Override
    public void update() {
        super.update();
        IMultipleTankHandler inputTank = getInputFluidInventory();
        if (BIO1.isFluidStackIdentical(inputTank.drain(BIO1, false))) {
            inputTank.drain(BIO1, true);
            A = A + 1;

        }
        if (BIO2.isFluidStackIdentical(inputTank.drain(BIO2, false))) {
            inputTank.drain(BIO2, true);
            B = B + 1;

        }
        if (BIO3.isFluidStackIdentical(inputTank.drain(BIO3, false))) {
            inputTank.drain(BIO3, true);
            C = C + 1;
        }
        if (BIO4.isFluidStackIdentical(inputTank.drain(BIO4, false))) {
            inputTank.drain(BIO4, true);
            D = D + 1;

        }
        if (BIO5.isFluidStackIdentical(inputTank.drain(BIO5, false))) {
            inputTank.drain(BIO5, true);
            E = E + 1;
        }
    }
}
