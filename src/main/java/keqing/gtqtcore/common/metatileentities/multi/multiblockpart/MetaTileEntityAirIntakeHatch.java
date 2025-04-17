package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.capability.impl.FilteredItemHandler;
import gregtech.api.capability.impl.FluidTankList;
import gregtech.api.capability.impl.NotifiableFluidTank;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.AbilityInstances;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.properties.impl.DimensionProperty;
import gregtech.api.unification.material.Materials;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import it.unimi.dsi.fastutil.ints.IntLists;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityGasCollector;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static gregtech.api.GTValues.*;

public class MetaTileEntityAirIntakeHatch extends MetaTileEntityMultiblockNotifiablePart implements IMultiblockAbilityPart<IFluidTank> {

    private final FluidTank fluidTank; // Initialized at constructor.
    private boolean isWorkingEnabled;
    private Fluid airType; // Air type of Air Intake Hatch, checking at update().

    public MetaTileEntityAirIntakeHatch(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier, false);
        this.fluidTank = new NotifiableFluidTank(
                tier == 5 ? 256_000 // IV: 256000L cap, 1000L/t fill.
                        : (tier == 7 ? 512_000 // ZPM: 512000L cap, 8000L/t fill.
                        : (tier == 9 ? 1_024_000 : 10)), // UHV: 1024000L cap, 64000L/t fill.
                this, false);
        this.initializeInventory();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityAirIntakeHatch(metaTileEntityId, this.getTier());
    }

    @Override
    public void update() {
        super.update();
        // Predicate air type of Air Intake Hatch from Gas Collector recipes.
        if (this.isFirstTick() && !this.getWorld().isRemote) {
            // 获取气体收集器的配方列表
            Collection<Recipe> recipeCollection = RecipeMaps.GAS_COLLECTOR_RECIPES.getRecipeList();
            List<Recipe> recipes = new ArrayList<>(recipeCollection);

            // 过滤出具有GasCollectorDimensionProperty属性的配方
            List<Recipe> filteredRecipes = recipes.stream()
                    .filter(recipe -> recipe.hasProperty(DimensionProperty.getInstance()))
                    .collect(Collectors.toList()); // 先收集为普通列表

            // 将普通列表转换为不可修改列表
            filteredRecipes = Collections.unmodifiableList(filteredRecipes);

             // 将配方映射为Pair<Recipe, DimensionID>
            List<Pair<Recipe, DimensionProperty.DimensionPropertyList>> recipeDimensionPairs = filteredRecipes.stream()
                    .map(recipe -> Pair.of(recipe,
                            recipe.getProperty(DimensionProperty.getInstance(), DimensionProperty.DimensionPropertyList.EMPTY_LIST)))
                    .collect(Collectors.toList()); // 同样先收集为普通列表

             // 将普通列表转换为不可修改列表
            recipeDimensionPairs = Collections.unmodifiableList(recipeDimensionPairs);

            // 过滤出维度ID与当前世界维度ID匹配的配方
            Optional<Pair<Recipe, DimensionProperty.DimensionPropertyList>> matchingRecipe = recipeDimensionPairs.stream()
                    .filter(pair -> pair.getRight().equals(this.getWorld().provider.getDimension())) // 使用 getRight() 方法
                    .findFirst();

            // 获取匹配的配方的流体输出，如果没有匹配的配方则返回默认的空气流体
            this.airType = matchingRecipe.map(pair -> pair.getKey().getFluidOutputs().get(0).getFluid())
                    .orElse(Materials.Air.getFluid());
        }
        // Workable predicate.
        final EnumFacing facing = this.getFrontFacing();
        final BlockPos blockPos = new BlockPos(
                this.getPos().getX() + facing.getXOffset(),
                this.getPos().getY() + facing.getYOffset(),
                this.getPos().getZ() + facing.getZOffset());
        if (this.getOffsetTimer() % (5 * TICK) == 0 && this.getWorld().isAirBlock(blockPos)) {
            if (!this.getWorld().isRemote) {
                int fillAmount = this.fluidTank.fill(new FluidStack(this.airType,
                        this.getTier() == 5 ? 1000 : ( // IV
                                this.getTier() == 7 ? 8000 : ( // ZPM
                                        this.getTier() == 9 ? 64000 : 10 // UHV
                                ))), true);
                if (fillAmount == 0 && this.isWorkingEnabled) {
                    this.isWorkingEnabled = false;
                    this.writeCustomData(GregtechDataCodes.WORKING_ENABLED,
                            buf -> buf.writeBoolean(this.isWorkingEnabled));
                } else if (fillAmount > 0 && !this.isWorkingEnabled) {
                    this.isWorkingEnabled = true;
                    this.writeCustomData(GregtechDataCodes.WORKING_ENABLED,
                            buf -> buf.writeBoolean(this.isWorkingEnabled));
                }
            }
            // Renderer.
            if (this.getWorld().isRemote && this.isWorkingEnabled) {
                // Port from GregTech++ (miscutils), thanks for Alkalus for these renderer codes.
                final EnumParticleTypes particleType = EnumParticleTypes.CLOUD;
                final float rand1 = RNG.nextFloat();
                float rand2 = RNG.nextFloat();
                float rand3 = RNG.nextFloat();

                final BlockPos pos = this.getPos();
                final EnumFacing dir = this.getFrontFacing();

                final float posX = pos.getX() + 0.25f + dir.getXOffset() * 0.76f;
                float posY = pos.getY() + 0.65f + dir.getYOffset() * 0.76f;
                final float posZ = pos.getZ() + 0.25f + dir.getZOffset() * 0.76f;

                float spdX;
                float spdY = dir.getYOffset() * 0.1f + 0.2f + 0.1f * RNG.nextFloat();
                float spdZ;

                if (dir.getYOffset() == -1) {
                    final float temp = (float) (RNG.nextFloat() * 2.0f * Math.PI);
                    spdX = (float) Math.sin(temp) * 0.1f;
                    spdZ = (float) Math.cos(temp) * 0.1f;
                    spdY = -spdY;
                    posY = posY - 0.8f;
                } else {
                    spdX = -(dir.getXOffset() * (0.1f + 0.2f * RNG.nextFloat()));
                    spdZ = -(dir.getZOffset() * (0.1f + 0.2f * RNG.nextFloat()));
                }

                this.getWorld().spawnParticle(particleType,
                        posX + rand1 * 0.5f,
                        posY + RNG.nextFloat() * 0.5f,
                        posZ + RNG.nextFloat() * 0.5f,
                        spdX, -spdY, spdZ);
                this.getWorld().spawnParticle(particleType,
                        posX + rand2 * 0.5f,
                        posY + RNG.nextFloat() * 0.5f,
                        posZ + RNG.nextFloat() * 0.5f,
                        spdX, -spdY, spdZ);
                this.getWorld().spawnParticle(particleType,
                        posX + rand3 * 0.5f,
                        posY + RNG.nextFloat() * 0.5f,
                        posZ + RNG.nextFloat() * 0.5f,
                        spdX, -spdY, spdZ);
            }
        }
        this.fillContainerFromInternalTank(this.fluidTank);
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeBoolean(this.isWorkingEnabled);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.isWorkingEnabled = buf.readBoolean();
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GregtechDataCodes.WORKING_ENABLED) {
            this.isWorkingEnabled = buf.readBoolean();
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer player) {
        return this.createTankUI(this.fluidTank, this.getMetaFullName(), player)
                .build(this.getHolder(), player);
    }

    private ModularUI.Builder createTankUI(IFluidTank fluidTank, String title, EntityPlayer player) {
        TankWidget tankWidget = new TankWidget(fluidTank, 69, 52, 18, 18)
                .setAlwaysShowFull(true)
                .setDrawHoveringText(false)
                .setContainerClicking(true, false);
        return ModularUI.defaultBuilder()
                .image(7, 16, 81, 55, GuiTextures.DISPLAY)
                .widget(new ImageWidget(91, 36, 14, 15, GuiTextures.TANK_ICON))
                .widget(new SlotWidget(this.exportItems, 0, 90, 53, true, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.OUT_SLOT_OVERLAY))
                .label(6, 6, title)
                .label(11, 20, "gregtech.gui.fluid_amount", 0xFFFFFF)
                .widget(new AdvancedTextWidget(11, 30, this.getFluidAmountText(tankWidget), 0xFFFFFF))
                .widget(new AdvancedTextWidget(11, 40, this.getFluidNameText(tankWidget), 0xFFFFFF))
                .widget(tankWidget)
                .widget(new FluidContainerSlotWidget(this.importItems, 0, 90, 16, false)
                        .setBackgroundTexture(GuiTextures.SLOT, GuiTextures.IN_SLOT_OVERLAY))
                .bindPlayerInventory(player.inventory);
    }

    private Consumer<List<ITextComponent>> getFluidNameText(TankWidget tankWidget) {
        return (t) -> {
            if (tankWidget.getFluidTextComponent() != null) {
                t.add(tankWidget.getFluidTextComponent());
            }
        };
    }

    private Consumer<List<ITextComponent>> getFluidAmountText(TankWidget tankWidget) {
        return (t) -> {
            if (!tankWidget.getFormattedFluidAmount().isEmpty()) {
                t.add(new TextComponentString(tankWidget.getFormattedFluidAmount()));
            }
        };
    }

    @Override
    public void addInformation(ItemStack stack,
                               World world,
                               List<String> tooltip,
                               boolean advanced) {
        tooltip.add(I18n.format("gtqtcore.machine.air_intake_hatch.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.air_intake_hatch.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.air_intake_hatch.tooltip.3",
                this.getTier() == IV ? 1000
                        : (this.getTier() == ZPM ? 8000
                        : (this.getTier() == UHV ? 64000 : 10))));
        tooltip.add(I18n.format("gtqtcore.machine.air_intake_hatch.tooltip.4",
                this.getTier() == IV ? 256000
                        : (this.getTier() == ZPM ? 512000
                        : (this.getTier() == UHV ? 1024000 : 10))));
    }

    @Override
    public MultiblockAbility<IFluidTank> getAbility() {
        return MultiblockAbility.IMPORT_FLUIDS;
    }

    @Override
    public void registerAbilities(AbilityInstances abilityInstances) {
        abilityInstances.add(this.fluidTank);
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState,
                                     Matrix4 translation,
                                     IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.getTier() == 5) { // IV
            Textures.MUFFLER_OVERLAY.renderSided(this.getFrontFacing(),
                    renderState, translation, pipeline);
        } else if (this.getTier() == 7) { // ZPM
            GTQTTextures.ADVANCED_MUFFELR_OVERLAY.renderSided(this.getFrontFacing(),
                    renderState, translation, pipeline);
        } else { // UHV
            GTQTTextures.ULTIMATE_MUFFLER_OVERLAY.renderSided(this.getFrontFacing(),
                    renderState, translation, pipeline);
        }
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing side) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.fluidTank);
        }
        return super.getCapability(capability, side);
    }

    @Override
    protected FluidTankList createImportFluidHandler() {
        return new FluidTankList(false, this.fluidTank);
    }

    @Override
    protected IItemHandlerModifiable createImportItemHandler() {
        return new FilteredItemHandler(this)
                .setFillPredicate(FilteredItemHandler.getCapabilityFilter(
                        CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY));
    }

    @Override
    protected IItemHandlerModifiable createExportItemHandler() {
        return new ItemStackHandler(1);
    }

}
