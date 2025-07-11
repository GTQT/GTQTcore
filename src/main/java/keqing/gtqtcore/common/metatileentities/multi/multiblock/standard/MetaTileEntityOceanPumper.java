package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import com.google.common.collect.Lists;
import gregtech.api.GTValues;
import gregtech.api.capability.GregtechTileCapabilities;
import gregtech.api.capability.IControllable;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.capability.impl.EnergyContainerList;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockWithDisplayBase;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.MultiblockUIBuilder;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.*;
import keqing.gtqtcore.api.unification.GTQTMaterials;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static gregtech.api.capability.GregtechDataCodes.WORKABLE_ACTIVE;
import static keqing.gtqtcore.api.utils.GTQTUtil.getAccelerateByCWU;

public class MetaTileEntityOceanPumper extends MultiblockWithDisplayBase implements IControllable {

    private final int BASE_EU_CONSUMPTION_PER_PUMP = 128;
    private IEnergyContainer energyContainers;
    private IMultipleTankHandler outputTankInventory;
    private int drainRate;
    private boolean isWorking;
    private boolean isWorkingEnabled = true;

    public MetaTileEntityOceanPumper(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityOceanPumper(metaTileEntityId);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(isWorking);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isWorking = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == WORKABLE_ACTIVE) {
            this.isWorking = buf.readBoolean();
            scheduleRenderUpdate();
        }
    }


    private void resetTileAbilities() {
        this.outputTankInventory = new FluidTankList(true);
        this.energyContainers = new EnergyContainerList(Lists.newArrayList());
    }

    @Override
    protected void updateFormedValid() {
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        initializeAbilities();
    }

    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
        resetTileAbilities();
        if (this.isActive())
            this.setActive(false);
    }

    private void initializeAbilities() {
        List<IEnergyContainer> energyInputs = getAbilities(MultiblockAbility.INPUT_ENERGY);
        this.energyContainers = new EnergyContainerList(energyInputs);
        this.outputTankInventory = new FluidTankList(false, getAbilities(MultiblockAbility.EXPORT_FLUIDS));
    }

    protected int getEnergyConsumedPerPump() {
        return BASE_EU_CONSUMPTION_PER_PUMP * (1 << (GTUtility.getTierByVoltage(this.energyContainers.getInputVoltage()) - 1) * 2);
    }

    public boolean drainEnergy(boolean simulate) {
        long energyToDrain = getEnergyConsumedPerPump();
        long resultEnergy = energyContainers.getEnergyStored() - energyToDrain;
        if (resultEnergy > 0L && resultEnergy <= energyContainers.getEnergyCapacity()) {
            if (!simulate) {
                energyContainers.changeEnergy(-energyToDrain);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMultiblockPartWeatherResistant(@Nonnull IMultiblockPart part) {
        return true;
    }

    public boolean isInValidLocation() {
        Biome biome = getWorld().getBiome(getPos());
        Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biome);

        if (getPos().getY() < 70 || getPos().getY() > 75) {
            return false;
        }

        return biomeTypes.contains(BiomeDictionary.Type.WATER);
    }

    public boolean insertFluid(boolean simulate) {
        if (!isInValidLocation()) return false;
        int fillamount = (int) Math.min(Integer.MAX_VALUE, 500L * (1L << (GTUtility.getTierByVoltage(this.energyContainers.getInputVoltage()) - 1) * 2));
        FluidStack Seawater = GTQTMaterials.SeaWater.getFluid(fillamount);
        int caninsertamount = outputTankInventory.fill(Seawater, false);
        if (!simulate) {
            Seawater.amount = Math.min(fillamount, caninsertamount);
            outputTankInventory.fill(Seawater, true);
            drainRate = Math.min(fillamount, caninsertamount);
        }

        return (fillamount == caninsertamount);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        this.getFrontOverlay().renderOrientedState(renderState, translation, pipeline, this.getFrontFacing(), isWorking, isWorkingEnabled);
    }

    @Override
    public void update() {
        super.update();

        if (getWorld().isRemote || getOffsetTimer() % 20 != 0 || !isStructureFormed()) return;

        boolean isWorkingNow = energyContainers.getEnergyStored() >= getEnergyConsumedPerPump() && this.isWorkingEnabled();


        if (isWorkingNow != isWorking) {
            setActive(isWorkingNow);
        }

        if (isWorkingNow) {
            if (drainEnergy(true)) {
                insertFluid(false);
                drainEnergy(false);
            }
        }

    }

    @Override
    public boolean isWorkingEnabled() {
        return isWorkingEnabled;
    }

    @Override
    public void setWorkingEnabled(boolean b) {
        isWorkingEnabled = b;
        notifyBlockUpdate();
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == GregtechTileCapabilities.CAPABILITY_CONTROLLABLE) {
            return GregtechTileCapabilities.CAPABILITY_CONTROLLABLE.cast(this);
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected void configureDisplayText(MultiblockUIBuilder builder) {
        builder.addCustom(this::addCustomCapacity);

    }
    private void addCustomCapacity(KeyManager keyManager, UISyncer syncer) {
        if (energyContainers != null && energyContainers.getEnergyCapacity() > 0) {
            int energyContainer = GTUtility.getTierByVoltage(this.energyContainers.getInputVoltage());
            long maxVoltage = GTValues.V[energyContainer] / 2;
            String voltageName = GTValues.VNF[energyContainer];
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY ,"gregtech.multiblock.max_energy_per_tick", syncer.syncLong(maxVoltage), syncer.syncString(voltageName)));
        }
        if (this.isActive() && drainEnergy(true)) {
            keyManager.add((KeyUtil.lang("gregtech.machine.miner.working")));
            keyManager.add((KeyUtil.lang("当前抽取速率：%s", syncer.syncInt(drainRate))));
        } else if (!drainEnergy(true))
            keyManager.add((KeyUtil.lang(TextFormatting.RED,"gregtech.machine.miner.needspower")));
        else if (!this.isWorkingEnabled())
            keyManager.add((KeyUtil.lang(TextFormatting.RED,"gregtech.multiblock.work_paused")));
        else if (!insertFluid(true))
            keyManager.add((KeyUtil.lang(TextFormatting.RED,"输出仓已满！")));
    }
    @Override
    protected BlockPattern createStructurePattern() {
        // BLYAAAAAAAAAAAAAAAT
        return FactoryBlockPattern.start()
                .aisle("FF***********FF", "FF***********FF", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("FF***********FF", "FF***********FF", "*FF*********FF*", "*FF*********FF*", "***************", "*****FFFFF*****", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "*FF*********FF*", "*FF*********FF*", "**FFFFFFFFFFF**", "**FFFFFFFFFFF**", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "***************", "***************", "**FFF*****FFF**", "**FFFFFFFFFFF**", "***FF*****FF***", "***FF*****FF***", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "***************", "***************", "**FF*******FF**", "**FF*******FF**", "***FF*****FF***", "***FF*****FF***", "****FF***FF****", "****FF***FF****", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "***************", "***************", "**F*********F**", "*FFF**CCC**FFF*", "******CCC******", "******CCC******", "****FFCCCFF****", "****FFF*FFF****", "*****F*F*F*****", "*****FF*FF*****", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "*******C*******", "*******C*******", "**F***CCC***F**", "*FFF*CCCCC*FFF*", "*****CPPPC*****", "*****CPPPC*****", "*****CCPCC*****", "*****F*C*F*****", "*******C*******", "*****F*C*F*****", "*******C*******", "*******G*******", "*******G*******", "******GGG******")
                //
                .aisle("*******P*******", "*******P*******", "******CPC******", "******CPC******", "**F***CPC***F**", "*FFF*CCPCC*FFF*", "*****CPPPC*****", "*****CPPPC*****", "*****CPPPC*****", "******CPC******", "*****FCPCF*****", "******CPC******", "******CPC******", "******GPG******", "******GPG******", "******GPG******")
                //
                .aisle("***************", "***************", "*******C*******", "*******C*******", "**F***CCC***F**", "*FFF*CCCCC*FFF*", "*****CPPPC*****", "*****CPPPC*****", "*****CCPCC*****", "*****F*C*F*****", "*******C*******", "*****F*C*F*****", "*******C*******", "*******G*******", "*******G*******", "******GGG******")
                .aisle("***************", "***************", "***************", "***************", "**F*********F**", "*FFF**CCC**FFF*", "******CCC******", "******CSC******", "****FFCCCFF****", "****FFF*FFF****", "*****F*F*F*****", "*****FF*FF*****", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "***************", "***************", "**FF*******FF**", "**FF*******FF**", "***FF*****FF***", "***FF*****FF***", "****FF***FF****", "****FF***FF****", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "***************", "***************", "**FFF*****FFF**", "**FFFFFFFFFFF**", "***FF*****FF***", "***FF*****FF***", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("***************", "***************", "*FF*********FF*", "*FF*********FF*", "**FFFFFFFFFFF**", "**FFFFFFFFFFF**", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("FF***********FF", "FF***********FF", "*FF*********FF*", "*FF*********FF*", "***************", "*****FFFFF*****", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .aisle("FF***********FF", "FF***********FF", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************", "***************")
                .where('S', selfPredicate())
                .where('C', states(getCasingState()).or(abilities(MultiblockAbility.EXPORT_FLUIDS)).or(abilities(MultiblockAbility.INPUT_ENERGY)).or(abilities(MultiblockAbility.MAINTENANCE_HATCH)))
                .where('P', states(getPipeCasingState()))
                .where('F', frames(Materials.Steel))
                .where('G', states(getGrateState()))
                .where('L', states(getConcreteState()))
                .where('*', any())
                .build();
    }

    @Override
    public String[] getDescription() {
        return Stream.of(
                new String[]{I18n.format("gregtech.multiblock.ocean_pumper.description")}).flatMap(Stream::of).toArray(String[]::new);
    }

    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gregtech.machine.perfect_oc", new Object[0]));
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Nonnull
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return GTQTTextures.LARGE_ROCKET_ENGINE_OVERLAY;
    }

    protected IBlockState getCasingState() {
        return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    protected IBlockState getGrateState() {
        return MetaBlocks.MULTIBLOCK_CASING.getState(BlockMultiblockCasing.MultiblockCasingType.GRATE_CASING);
    }

    protected IBlockState getPipeCasingState() {
        return MetaBlocks.BOILER_CASING.getState(BlockBoilerCasing.BoilerCasingType.STEEL_PIPE);
    }

    protected IBlockState getConcreteState() {
        return MetaBlocks.STONE_BLOCKS.get(StoneVariantBlock.StoneVariant.SMOOTH).getState(StoneVariantBlock.StoneType.CONCRETE_LIGHT);
    }

    @Override
    public boolean isActive() {
        return isWorking;
    }

    public void setActive(boolean Value) {
        this.isWorking = Value;
        if (!getWorld().isRemote) {
            markDirty();
            this.writeCustomData(WORKABLE_ACTIVE, b -> b.writeBoolean(this.isWorking));
        }
    }


}