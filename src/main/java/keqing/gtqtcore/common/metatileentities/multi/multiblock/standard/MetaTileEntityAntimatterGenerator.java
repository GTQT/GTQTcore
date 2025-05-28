package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.GTValues;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.fluids.store.FluidStorageKeys;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.resources.TextureArea;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.IProgressBarMultiblock;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.api.capability.IParticle;
import keqing.gtqtcore.api.capability.impl.GTQTCoreLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing4;
import keqing.gtqtcore.common.block.blocks.BlockMultiblockCasing7;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static gregtech.api.GTValues.*;
import static gregtech.api.GTValues.VN;
import static gregtech.api.util.RelativeDirection.*;
import static keqing.gtqtcore.GTQTCoreConfig.MachineSwitch;
import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.ANTIMATTER_GENERATOR;
import static keqing.gtqtcore.common.block.blocks.BlockMultiblockGlass.CasingType.ANTIMATTER_CONTAINMENT_CASING;

public class MetaTileEntityAntimatterGenerator extends RecipeMapMultiblockController implements IProgressBarMultiblock {

    private static final FluidStack HydrogenStack = Materials.Hydrogen.getPlasma(100);
    private static final FluidStack HeliumStack = Materials.Helium.getPlasma(100);
    private static final FluidStack NitrogenStack = Materials.Nitrogen.getPlasma(100);
    private static final FluidStack OxygenStack = Materials.Oxygen.getPlasma(100);
    private static final FluidStack ArgonStack = Materials.Argon.getPlasma(100);
    private static final FluidStack KrStack = Materials.Krypton.getPlasma(100);

    private static final FluidStack ColdNitrogenStack = Materials.Nitrogen.getFluid(FluidStorageKeys.LIQUID, 1000);
    private static final FluidStack ColdHeliumStack = Materials.Helium.getFluid(FluidStorageKeys.LIQUID, 1000);
    private static final FluidStack ColdNeonStack = Materials.Neon.getFluid(FluidStorageKeys.LIQUID, 1000);

    int heat;
    double euBooster;
    double euBase;

    public MetaTileEntityAntimatterGenerator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, ANTIMATTER_GENERATOR);
        this.recipeMapWorkable = new GTQTCoreLogic(this);
    }

    private static IBlockState getFilterState() {
        return GTQTMetaBlocks.blockMultiblockCasing4.getState(BlockMultiblockCasing4.TurbineCasingType.ADVANCED_FILTER_CASING);
    }

    private static IBlockState getGlassState() {
        return GTQTMetaBlocks.blockMultiblockGlass.getState(ANTIMATTER_CONTAINMENT_CASING);
    }

    private static IBlockState getWhiteState() {
        return GTQTMetaBlocks.blockMultiblockCasing7.getState(BlockMultiblockCasing7.CasingType.GRAVITY_STABILIZATION_CASING);
    }

    private static IBlockState getContainmentCoilState() {
        return GTQTMetaBlocks.blockMultiblockCasing7.getState(BlockMultiblockCasing7.CasingType.PROTOMATTER_ACTIVATION_COIL);
    }

    private static IBlockState getAnnihilationCoilState() {
        return GTQTMetaBlocks.blockMultiblockCasing7.getState(BlockMultiblockCasing7.CasingType.ANTIMATTER_ANNIHILATION_MATRIX);
    }

    private static IBlockState getBlackCasingState() {
        return GTQTMetaBlocks.blockMultiblockCasing7.getState(BlockMultiblockCasing7.CasingType.MAGNETIC_FLUX_CASING);
    }

    @Override
    public boolean shouldDelayCheck() {
        return true;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("heat", heat);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        heat = data.getInteger("heat");
    }

    public IParticle getParticleHatch(int index) {
        List<IParticle> abilities = getAbilities(GTQTMultiblockAbility.PARTICLE_MULTIBLOCK_ABILITY);
        if (abilities.isEmpty())
            return null;
        return abilities.get(index);
    }

    @Override
    protected void updateFormedValid() {
        super.updateFormedValid();

        if (isStructureFormed()) {
            if (heat > 300) heat--;
            if (heat > 2000) generateCold();

            if (!recipeMapWorkable.isWorkingEnabled()) {
                euBooster = 0;
                euBase = 0;
                return;
            }
            if (heat > 200000) return;
            if (getParticleHatch(0).isAvailable() && getParticleHatch(1).isAvailable()) {
                if (getParticleHatch(0).getAntiStaticParticle().isItemEqual(getParticleHatch(1).getParticle())
                        && (getParticleHatch(0).getAntiStaticParticle().getMetadata() == getParticleHatch(1).getParticle().getMetadata())
                        && (getParticleHatch(0).getParticle().getMetadata() != getParticleHatch(1).getParticle().getMetadata())) {
                    if (getParticleHatch(0).consumeParticle(true) && getParticleHatch(1).consumeParticle(true)) {
                        getParticleHatch(0).consumeParticle(false);
                        getParticleHatch(1).consumeParticle(false);
                        euBase = Math.min(getParticleHatch(0).getEUAdd(), getParticleHatch(1).getEUAdd());
                        euBooster = getBoosterByFluidStack();

                        if (heat > 100000) {
                            euBase = euBase * (-heat + 200000) / 100000;
                            euBooster = 1;
                        }

                        this.energyContainer.addEnergy((long) (euBase * euBooster));

                        heat += 5;
                    }
                }
            }
        }
    }

    private void generateCold() {
        IMultipleTankHandler inputTank = this.getInputFluidInventory();
        if (ColdNitrogenStack.isFluidStackIdentical(inputTank.drain(ColdNitrogenStack, false))) {
            inputTank.drain(ColdNitrogenStack, true);
            heat -= 20;
        } else if (ColdHeliumStack.isFluidStackIdentical(inputTank.drain(ColdHeliumStack, false))) {
            inputTank.drain(ColdHeliumStack, true);
            heat -= 40;
        } else if (ColdNeonStack.isFluidStackIdentical(inputTank.drain(ColdNeonStack, false))) {
            inputTank.drain(ColdNeonStack, true);
            heat -= 60;
        }
    }

    public double getBoosterByFluidStack() {
        IMultipleTankHandler inputTank = this.getInputFluidInventory();
        if (HydrogenStack.isFluidStackIdentical(inputTank.drain(HydrogenStack, false))) {
            inputTank.drain(HydrogenStack, true);
            heat += 10;
            return 1.25;
        }
        if (HeliumStack.isFluidStackIdentical(inputTank.drain(HeliumStack, false))) {
            inputTank.drain(HeliumStack, true);
            heat += 15;
            return 1.5;
        }
        if (NitrogenStack.isFluidStackIdentical(inputTank.drain(NitrogenStack, false))) {
            inputTank.drain(NitrogenStack, true);
            heat += 20;
            return 1.75;
        }
        if (OxygenStack.isFluidStackIdentical(inputTank.drain(OxygenStack, false))) {
            inputTank.drain(OxygenStack, true);
            heat += 30;
            return 2;
        }
        if (ArgonStack.isFluidStackIdentical(inputTank.drain(ArgonStack, false))) {
            inputTank.drain(ArgonStack, true);
            heat += 40;
            return 2.5;
        }
        if (KrStack.isFluidStackIdentical(inputTank.drain(KrStack, false))) {
            inputTank.drain(KrStack, true);
            heat += 50;
            return 3;
        }

        return 1;
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        if (isStructureFormed()) {
            textList.add(new TextComponentTranslation("正在湮灭: %s %s", getParticleHatch(0).getParticle().getDisplayName(), getParticleHatch(1).getParticle().getDisplayName()));
            textList.add(new TextComponentString("湮灭能量: " + euBase + " " + VN[GTUtility.getTierByVoltage((long) euBase)]));
            textList.add(new TextComponentTranslation("湮灭倍率: %s 倍", euBooster));
            textList.add(new TextComponentString("总产生能量: " + euBase * euBooster + " " + VN[GTUtility.getTierByVoltage((long) (euBase * euBooster))]));

            textList.add(new TextComponentTranslation("反应温度 : %s K/200000 K", heat));
        }
    }

    @Override
    protected void initializeAbilities() {
        super.initializeAbilities();
        List<IEnergyContainer> energyContainer = new ArrayList<>(this.getAbilities(MultiblockAbility.OUTPUT_ENERGY));
        energyContainer.addAll(this.getAbilities(MultiblockAbility.OUTPUT_LASER));
        this.energyContainer = new EnergyContainerList(energyContainer);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        FactoryBlockPattern pattern = FactoryBlockPattern.start(RIGHT, DOWN, BACK)
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                DDD                ", "                DDD                ", "                DDD                ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 D                 ", "                DDD                ", "                DDD                ", "                DDD                ", "                DDD                ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               HCCCH               ", "               HCCCH               ", "               HDDDH               ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "                CCC                ", "               DCCCD               ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "               HCCCH               ", "               DCCCD               ", "               HDDDH               ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "                CCC                ", "               DCCCD               ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               HCCCH               ", "               HCCCH               ", "               HDDDH               ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "                CCC                ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDDDDDD              ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "              DDDDDDD              ", "              DDDDDDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDDDDDD              ", "              DDDDDDD              ", "            FFDDDDDDDFF            ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "            DDDDDDDDDDD            ", "            DDDDDDDDDDD            ", "            FFDDDDDDDFF            ", "              DDDCDDD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "            DDDDDDDDDDD            ", "             DDDDDDDDD             ", "            DDDDDDDDDDD            ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              CCCCCCC              ", "            FFCCCCCCCFF            ", "            DDDDG GDDDD            ", "            DDDDG GDDDD            ", "          FFDDDDDDDDDDDFF          ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "          DDDDDDDDDDDDDDD          ", "          DDDDGGGGGGGDDDD          ", "          FFCCGGGGGGGCCFF          ", "            CCGGGGGGGCC            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "          DDAA   F   AADD          ", "            AA  FCF  AA            ", "          DDAA   F   AADD          ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            CC       CC            ", "          FFCCGGGGGGGCCFF          ", "          DDDDGGGGGGGDDDD          ", "          DDDDGGGGGGGDDDD          ", "         FDDDDDDDDDDDDDDDF         ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "         DDDDDDDDDDDDDDDDD         ", "         DDDGGDDDDDDDGGDDD         ", "         FCCGGDDDCDDDGGCCF         ", "          CCGG       GGCC          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "         DAA     F     AAD         ", "          AA    FCF    AA          ", "         DAA     F     AAD         ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          CC           CC          ", "         FCCGG       GGCCF         ", "         DDCGGDDDCDDDGGDDD         ", "         DDDGGDDDDDDDGGDDD         ", "        FDDDDDDDDDDDDDDDDDF        ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "        DDDDDDDDDDDDDDDDDDD        ", "        DDGGDDDDDDDDDDDGGDD        ", "        FCGGDDDDDCDDDDDGGCF        ", "         CGG           GGC         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "        DA       F       AD        ", "         A      FCF      A         ", "        DA       F       AD        ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         C               C         ", "        FCGG           GGCF        ", "        DDGGDDDDDCDDDDDGGDD        ", "        DDGGDDDDDDDDDDDGGDD        ", "       FDDDDDDDDDDDDDDDDDDDF       ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "       DDDDDDDDDDDDDDDDDDDDD       ", "       DDGDDDDDGGGGGDDDDDGDD       ", "       FCGCDDDDGGGGGDDDDCGCF       ", "        CG               GC        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "       DA       DDD       AD       ", "        A       CCC       A        ", "       DA       DDD       AD       ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        C                 C        ", "       FCG               GCF       ", "       DDGCDDDDGGGGGDDDDCGDD       ", "       DDGDDDDDGGGGGDDDDDGDD       ", "      FDDDDDDDDDDDDDDDDDDDDDF      ")
                .aisle("                                   ", "                CCC                ", "                CCC                ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "       DDDDDDDDDDDDDDDDDDDDD       ", "       DDGDDDGGDDCDDGGDDDGDD       ", "       FCGDCDGG     GGDCDGCF       ", "        CG               GC        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "       DA     DD   DD     AD       ", "        A     CCBBBCC     A        ", "       DA     DD   DD     AD       ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        C                 C        ", "       FCG               GCF       ", "       DDGDCDGG     GGDCDGDD       ", "       DDGDDDGGDDCDDGGDDDGDD       ", "      FDDDDDDDDDDDDDDDDDDDDDF      ")
                .aisle("                CCC                ", "               EEEEE               ", "               EEEEE               ", "               EEEEE               ", "               EEEEE               ", "               EEEEE               ", "      DDDDDDDDDDDDDDDDDDDDDDD      ", "      DDGDDDGDDDDCDDDDGDDDGDD      ", "      FCGDDDG         GDDDGCF      ", "       CG                 GC       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "      DA     D  DDD  D     AD      ", "       A     CBBDDDBBC     A       ", "      DA     D  DDD  D     AD      ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       C                   C       ", "      FCG                 GCF      ", "      DDGDDDG         GDDDGDD      ", "      DDGDDDGDDDDCDDDDGDDDGDD      ", "     FDDDDDDDDDDDDDDDDDDDDDDDF     ")
                .aisle("                CCC                ", "              EEEEEEE              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "      DDDDDDDDDDDDDDDDDDDDDDD      ", "      DDGDDGDCDGGGGGDCDGDDGDD      ", "      FCGDDG           GDDGCF      ", "       CG                 GC       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "      DA    D DD   DD D    AD      ", "      DA    CBDD   DDBC    AD      ", "      DA    D DD   DD D    AD      ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       C                   C       ", "      FCG                 GCF      ", "      DDGDDG           GDDGDD      ", "      DDGDDGDCDGGGGGDCDGDDGDD      ", "     FDDDDDDDDDDDDDDDDDDDDDDDF     ")
                .aisle("                CCC                ", "             EEEEEEEEE             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "     DDDDDDDDDDDDCDDDDDDDDDDDD     ", "     DDGDDDGDDG     GDDGDDDGDD     ", "     DDGDDDG           GDDDGDD     ", "     DDG                   GDD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD    D D       D D    DD     ", "     DD    CBD       DBC    DD     ", "     DD    D D       D D    DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "    DDD                     DDD    ", "    DDC                     CDD    ", "    DDCG                   GCDD    ", "    DDDGDDDG           GDDDGDDD    ", "    DDDGDDDGDDG     GDDGDDDGDDD    ", "    DDDDDDDDDDDDDCDDDDDDDDDDDDD    ")
                .aisle("                CCC                ", "            EEEEEEEEEEE            ", "            E         E            ", "            E         E            ", "      DDDDDDE         EDDDDDD      ", "     DDDDDDDE         EDDDDDDD     ", "    DDDDDDDDDDDCCCCCDDDDDDDDDDD    ", "    DDDGDDGDDG       GDDGDDGDDD    ", "    DDDGDDG             GDDGDDD    ", "    DDDG                   GDDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", " DDDDDD    D D       D D    DDDDDD ", " D   DD    CBD       DBC    DD   D ", " DDDDDD    D D       D D    DDDDDD ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDC                     CDDD   ", "  DDDDCG                   GCDDDD  ", " HHHDDDGDDG             GDDGDDDHHH ", " HDHDDDGDDGDDG       GDDGDDGDDDHDH ", " HHHDDDDDDDDDDDCCCCCDDDDDDDDDDDHHH ")
                .aisle("            CCCCCCCCCCC            ", "           CEEEEEEEEEEEC           ", "           CE         EC           ", "      DDDDDDE         EDDDDDD      ", "     DDDDDDDE         EDDDDDDD     ", "    DDDDDDDDE         EDDDDDDDD    ", "    DDDDDDDDDDDCGGGCDDDDDDDDDDD    ", "  CCCDDGDDGDDG       GDDGDDGDDCCC  ", " CCCCCDGDDG             GDDGDCCCCC ", " CCCCCDG                   GDCCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", "DCCCCCD   D D         D D   DCCCCCD", "DCCCCCDFFFCBD         DBCFFFDCCCCCD", "DCCCCCD   D D         D D   DCCCCCD", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCCC                     CCCCCC ", "DCCCCCCG                   GCCCCCCD", "DCCCCDGGDDG             GDDGGDCCCCD", "DCCCCDGGDDGDDG       GDDGDDGGDCCCCD", "DDDDDDDDDDDDDDDCGGGCDDDDDDDDDDDDDDD")
                .aisle("            CCCCCCCCCCC            ", "           CEEEEEEEEEEEC           ", "           CE         EC           ", "      DDDDDDE         EDDDDDD      ", "     DDDDDDDE         EDDDDDDD     ", "    DDDDDDDDE         EDDDDDDDD    ", "  FFDDDDDDDDDDCCGDGCCDDDDDDDDDDFF  ", " FCCCDDGDDGCCG       GCCGDDGDDCCCF ", "FCCCCCDGCCG             GCCGDCCCCCF", "FCCCCCCG                   GCCCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "DCCCCCDFFFD D         D DFFFDCCCCCD", "DCCCCCDCCCCBD         DBCCCCDCCCCCD", "DCCCCCDFFFD D         D DFFFDCCCCCD", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "FCCCCC                       CCCCCF", "DCCCCCC                     CCCCCCD", "DCCCCCCG                   GCCCCCCD", "DCCCCD GCCG             GCCG DCCCCD", "DCCCCD GDDGCCG       GCCGDDG DCCCCD", "DDDDDDDDDDDDDDCCGDGCCDDDDDDDDDDDDDD")
                .aisle("            CCCCCCCCCCC            ", "           CEEEEEEEEEEEC           ", "           CE         EC           ", "      DDDDDDE         EDDDDDD      ", "     DDDDDDDE         EDDDDDDD     ", "    DDDDDDDDE         EDDDDDDDD    ", "    DDDDDDDDDDDCGGGCDDDDDDDDDDD    ", "  CCCDDGDDGDDG       GDDGDDGDDCCC  ", " CCCCCDGDDG             GDDGDCCCCC ", " CCCCCDG                   GDCCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", "DCCCCCD   D D         D D   DCCCCCD", "DCCCCCDFFFCBD         DBCFFFDCCCCCD", "DCCCCCD   D D         D D   DCCCCCD", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCC                       CCCCC ", " CCCCCC                     CCCCCC ", "DCCCCCCG                   GCCCCCCD", "DCCCCDGGDDG             GDDGGDCCCCD", "DCCCCDGGDDGDDG       GDDGDDGGDCCCCD", "DDDDDDDDDDDDDDDCGGGCDDDDDDDDDDDDDDD")
                .aisle("                CCC                ", "            EEEEEEEEEEE            ", "            E         E            ", "            E         E            ", "      DDDDDDE         EDDDDDD      ", "     DDDDDDDE         EDDDDDDD     ", "    DDDDDDDDDDDCCCCCDDDDDDDDDDD    ", "    DDDGDDGDDG       GDDGDDGDDD    ", "    DDDGDDG             GDDGDDD    ", "    DDDG                   GDDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", " DDDDDD    D D       D D    DDDDDD ", " D   DD    CBD       DBC    DD   D ", " DDDDDD    D D       D D    DDDDDD ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "    DDD                     DDD    ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDD                     DDDD   ", "   DDDC                     CDDD   ", "  DDDDCG                   GCDDDD  ", " HHHDDDGDDG             GDDGDDDHHH ", " HDHDDDGDDGDDG       GDDGDDGDDDHDH ", " HHHDDDDDDDDDDDCCCCCDDDDDDDDDDDHHH ")
                .aisle("                CCC                ", "             EEEEEEEEE             ", "             E       E             ", "             E       E             ", "             E       E             ", "             E       E             ", "     DDDDDDDDDDDDCDDDDDDDDDDDD     ", "     DDGDDDGDDG     GDDGDDDGDD     ", "     DDGDDDG           GDDDGDD     ", "     DDG                   GDD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD    D D       D D    DD     ", "     DD    CBD       DBC    DD     ", "     DD    D D       D D    DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "     DD                     DD     ", "    DDD                     DDD    ", "    DDC                     CDD    ", "    DDCG                   GCDD    ", "    DDDGDDDG           GDDDGDDD    ", "    DDDGDDDGDDG     GDDGDDDGDDD    ", "    DDDDDDDDDDDDDCDDDDDDDDDDDDD    ")
                .aisle("                CCC                ", "              EEEEEEE              ", "              E     E              ", "              E     E              ", "              E     E              ", "              E     E              ", "      DDDDDDDDDDDDDDDDDDDDDDD      ", "      DDGDDGDCDGGGGGDCDGDDGDD      ", "      FCGDDG           GDDGCF      ", "       CG                 GC       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "      DA    D DD   DD D    AD      ", "      DA    CBDD   DDBC    AD      ", "      DA    D DD   DD D    AD      ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       C                   C       ", "      FCG                 GCF      ", "      DDGDDG           GDDGDD      ", "      DDGDDGDCDGGGGGDCDGDDGDD      ", "     FDDDDDDDDDDDDDDDDDDDDDDDF     ")
                .aisle("                CCC                ", "               EEEEE               ", "               EEEEE               ", "               EEEEE               ", "               EEEEE               ", "               EEEEE               ", "      DDDDDDDDDDDDDDDDDDDDDDD      ", "      DDGDDDGDDDDCDDDDGDDDGDD      ", "      FCGDDDG         GDDDGCF      ", "       CG                 GC       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "      DA     D  DDD  D     AD      ", "       A     CBBDDDBBC     A       ", "      DA     D  DDD  D     AD      ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       A                   A       ", "       C                   C       ", "      FCG                 GCF      ", "      DDGDDDG         GDDDG D      ", "      DDGDDDGDDDDCDDDDGDDDG D      ", "     FDDDDDDDDDDDDDDDDDDDDDDDF     ")
                .aisle("                                   ", "                CCC                ", "                CCC                ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "       DDDDDDDDDDDDDDDDDDDDD       ", "       DDGDDDGGDDCDDGGDDDGDD       ", "       FCGDCDGG     GGDCDGCF       ", "        CG               GC        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "       DA     DD   DD     AD       ", "        A     CCBBBCC     A        ", "       DA     DD   DD     AD       ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        C                 C        ", "       FCG               GCF       ", "       DDGDCDGG     GGDCDGDD       ", "       DDGDDDGGDDCDDGGDDDGDD       ", "      FDDDDDDDDDDDDDDDDDDDDDF      ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "       DDDDDDDDDDDDDDDDDDDDD       ", "       DDGDDDDDGGGGGDDDDDGDD       ", "       FCGCDDDDGGGGGDDDDCGCF       ", "        CG               GC        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "       DA       DDD       AD       ", "        A       CCC       A        ", "       DA       DDD       AD       ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        A                 A        ", "        C                 C        ", "       FCG               GCF       ", "       DDGCDDDDGGGGGDDDDCGDD       ", "       DDGDDDDDGGGGGDDDDDGDD       ", "      FDDDDDDDDDDDDDDDDDDDDDF      ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "        DDDDDDDDDDDDDDDDDDD        ", "        DDGGDDDDDDDDDDDGGDD        ", "        FCGGDDDDDCDDDDDGGCF        ", "         CGG           GGC         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "        DA       F       AD        ", "         A      FCF      A         ", "        DA       F       AD        ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         A               A         ", "         C               C         ", "        FCGG           GGCF        ", "        DDGGDDDDDCDDDDDGG D        ", "        DDGGDDDDDDDDDDDGG D        ", "       FDDDDDDDDDDDDDDDDDDDF       ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "         DDDDDDDDDDDDDDDDD         ", "         DDDGGDDDDDDDGGDDD         ", "         FCCGGDDDCDDDGGCCF         ", "          CCGG       GGCC          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "         DAA     F     AAD         ", "          AA    FCF    AA          ", "         DAA     F     AAD         ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          AA           AA          ", "          CC           CC          ", "         FCCGG       GGCCF         ", "         DDDGGDDDCDDDGGDDD         ", "         DDDGGDDDDDDDGGDDD         ", "        FDDDDDDDDDDDDDDDDDF        ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "          DDDDDDDDDDDDDDD          ", "          DDDDGGGGGGGDDDD          ", "          FFCCGGGGGGGCCFF          ", "            CCGGGGGGGCC            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "          DDAA   F   AADD          ", "            AA  FCF  AA            ", "          DDAA   F   AADD          ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            AA       AA            ", "            CC       CC            ", "          FFCCGGGGGGGCCFF          ", "          DDDDGGGGGGGDDDD          ", "          DDDDGGGGGGGDDDD          ", "         FDDDDDDDDDDDDDDDF         ")
                .aisle("                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DDDDD               ", "            DDDDDDDDDDD            ", "            DDDDDDDDDDD            ", "            FFDDDDDDDFF            ", "              DDDCDDD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "            DDDDDDDDDDD            ", "             DDDDDDDDD             ", "            DDDDDDDDDDD            ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              DD   DD              ", "              CCCCCCC              ", "            FFCCCCCCCFF            ", "            DDDDG GDDDD            ", "            DDDDG GDDDD            ", "          FFDDDDDDDDDDDFF          ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "              DDDDDDD              ", "              DDDDDDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDDDDDD              ", "              DDDDDDD              ", "            FFDDDDDDDFF            ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                DDD                ", "               DDDDD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "                CCC                ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDCCCDD              ", "              DDDDDDD              ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "                CCC                ", "               DCCCD               ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "               HCCCH               ", "               HCCCH               ", "               HDDDH               ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "                CCC                ", "               DCCCD               ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "               HCCCH               ", "               DCCCD               ", "               HDDDH               ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               DCCCD               ", "               DCCCD               ", "               DCCCD               ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "                CCC                ", "               HCCCH               ", "               HCCCH               ", "               HDDDH               ")
                .aisle("                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                                   ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                DDD                ", "                DDD                ", "                DDD                ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 F                 ", "                 D                 ", "                DDD                ", "                DDD                ", "                P~P                ", "                DDD                ")

                .where('~', this.selfPredicate())

                .where('F', getFramePredicate())
                .where('D', states(getBlackCasingState())
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1)))

                .where('P', states(getBlackCasingState())
                        .or(abilities(GTQTMultiblockAbility.PARTICLE_MULTIBLOCK_ABILITY).setExactLimit(2)))
                .where('B', states(getContainmentCoilState()))
                .where('C', states(getWhiteState()))
                .where('A', states(getGlassState()))
                .where('E', states(getFilterState()))
                .where('G', states(getAnnihilationCoilState()))
                .where('H', states(getBlackCasingState())
                        .or(metaTileEntities(MultiblockAbility.REGISTRY.get(MultiblockAbility.OUTPUT_ENERGY).stream()
                                .filter(mte -> {
                                    IEnergyContainer container = mte.getCapability(GregtechCapabilities.CAPABILITY_ENERGY_CONTAINER, null);
                                    return container != null && container.getOutputVoltage() >= V[UEV];
                                })
                                .toArray(MetaTileEntity[]::new))
                                .setMaxGlobalLimited(36)
                                .setPreviewCount(12))
                        .or(abilities(MultiblockAbility.OUTPUT_LASER)
                                .setMaxGlobalLimited(12)
                                .setPreviewCount(4)))
                .where(' ', any());
        return pattern.build();
    }

    public TraceabilityPredicate getFramePredicate() {
        return frames(GTQTMaterials.BlackPlutonium);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
        return GTQTTextures.MAGNETIC_FLUX_CASING;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAntimatterGenerator(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }


    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public int getNumProgressBars() {
        return 1;
    }

    @Override
    public double getFillPercentage(int index) {
        return (double) heat / 200000;
    }

    @Override
    public TextureArea getProgressBarTexture(int index) {
        return GuiTextures.PROGRESS_BAR_HPCA_COMPUTATION;
    }

    @Override
    public void addBarHoverText(List<ITextComponent> hoverList, int index) {
        ITextComponent info = TextComponentUtil.stringWithColor(
                getColor(),
                heat + " / " + 200000 + " H");

        hoverList.add(TextComponentUtil.translationWithColor(
                TextFormatting.GRAY,
                "反应温度：%s",
                info));
    }

    private TextFormatting getColor() {
        if (heat < 100000) {
            return TextFormatting.GREEN;
        } else {
            return heat < 150000 ? TextFormatting.YELLOW : TextFormatting.RED;
        }
    }

    //    private static final FluidStack HydrogenStack = Materials.Hydrogen.getPlasma(100);
    //    private static final FluidStack HeliumStack = Materials.Helium.getPlasma(100);
    //    private static final FluidStack NitrogenStack = Materials.Nitrogen.getPlasma(100);
    //    private static final FluidStack OxygenStack = Materials.Oxygen.getPlasma(100);
    //    private static final FluidStack ArgonStack = Materials.Argon.getPlasma(100);
    //    private static final FluidStack KrStack = Materials.Krypton.getPlasma(100);
    //
    //    private static final FluidStack ColdNitrogenStack = Materials.Nitrogen.getFluid(FluidStorageKeys.LIQUID, 1000);
    //    private static final FluidStack ColdHeliumStack = Materials.Helium.getFluid(FluidStorageKeys.LIQUID, 1000);
    //    private static final FluidStack ColdNeonStack = Materials.Neon.getFluid(FluidStorageKeys.LIQUID, 1000);

    @Override
    public void addInformation(ItemStack stack,
                               World player,
                               List<String> tooltip,
                               boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("根据泛银河系公约 ,反物质被禁止用于武器！"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("将粒子与半稳定反粒子湮灭产生的巨大能量通过共振线圈收集转化为电能"));
        tooltip.add(I18n.format("湮灭反应需要对等数量的粒子与半稳定反粒子"));
        tooltip.add(I18n.format("每个发电周期均为1tick，并消耗一对粒子"));
        tooltip.add(I18n.format("基础产能计算：mass/10*V[UEV],mass单位为MeV/c^2"));
        tooltip.add(I18n.format("本发电无视动力仓等级与MAX墙限制！"));
        tooltip.add(I18n.format("任何无法及时输出到动力仓（包括激光源仓）的能量将会被浪费！"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("每湮灭一对粒子固定产热 5 K"));
        tooltip.add(I18n.format("每周期消耗 100mb 氢等离子,提供125%%发电倍率，并额外产生10 K热量"));
        tooltip.add(I18n.format("每周期消耗 100mb 氦等离子,提供150%%的发电倍率，并额外产生15 K热量"));
        tooltip.add(I18n.format("每周期消耗 100mb 氮等离子,提供175%%的发电倍率，并额外产生20 K热量"));
        tooltip.add(I18n.format("每周期消耗 100mb 氧等离子,提供200%%的发电倍率，并额外产生30 K热量"));
        tooltip.add(I18n.format("每周期消耗 100mb 氩等离子,提供250%%的发电倍率，并额外产生40 K热量"));
        tooltip.add(I18n.format("每周期消耗 100mb 氪等离子,提供300%%的发电倍率，并额外产生50 K热量"));
        tooltip.add(I18n.format("当温度大于100000K时，将无视发电倍率，基础发电衰减至(-heat+200000)/100000"));
        tooltip.add(I18n.format("当温度大于200000K时，设备将关机，直到温度下降至符合工作需求"));
        tooltip.add(I18n.format("=============================================="));
        tooltip.add(I18n.format("机器每tick降温 1 K"));
        tooltip.add(I18n.format("每周期消耗 1000mb 液态氮,吸收20 K的热量"));
        tooltip.add(I18n.format("每周期消耗 1000mb 液态氦,吸收40 K的热量"));
        tooltip.add(I18n.format("每周期消耗 1000mb 液态氖,吸收60 K 热量"));
        tooltip.add(I18n.format("=============================================="));
    }

}
