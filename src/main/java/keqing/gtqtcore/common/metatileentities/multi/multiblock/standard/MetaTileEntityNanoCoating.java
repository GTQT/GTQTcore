package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;


import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.metatileentity.multiblock.ui.KeyManager;
import gregtech.api.metatileentity.multiblock.ui.UISyncer;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.RecipeMap;
import gregtech.api.util.KeyUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.metatileentity.GTQTOCMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps.SPINNER_RECIPES;

public class MetaTileEntityNanoCoating extends GTQTOCMultiblockController implements IOpticalComputationReceiver {
    private int glass_tier;
    private int clean_tier;
    private int radio_tier;
    private int laser_tier;
    private int casing_tier;

    //设备：纳米喷涂器 机制（同光刻 追加效能模式，模式下并行*2 耗时/2 限制电压为正常的-2）
    // 配方：3D打印
    // 精密喷涂（用于加工透镜玻璃 或者其他需要覆膜的精密材料 算力配方）
    // 精密喷丝（编制滤网 催化剂框架 激光型算力配方）
    private IOpticalComputationProvider computationProvider;

    public MetaTileEntityNanoCoating(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, new RecipeMap[]{GTQTcoreRecipeMaps.TD_PRINT_RECIPES, GTQTcoreRecipeMaps.AUTO_CHISEL_RECIPES, GTQTcoreRecipeMaps.PRECISION_SPRAYING, GTQTcoreRecipeMaps.PRECISION_SPINNING, SPINNER_RECIPES});
        this.recipeMapWorkable = new LaserEngravingWorkableHandler(this);

        setTierFlag(true);
        //setTier(auto);
        setMaxParallel(1);//初始化
        setMaxParallelFlag(true);
        //setMaxVoltage(auto);
        setMaxVoltageFlag(true);
        setTimeReduce(1);//初始化
        setTimeReduceFlag(true);
        setOverclocking(3);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("天罗地网"));
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityNanoCoating(metaTileEntityId);
    }

    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if (dataId == GTQTValue.UPDATE_TIER8) {
            this.casing_tier = buf.readInt();
        }
        if (dataId == GTQTValue.REQUIRE_DATA_UPDATE8) {
            this.writeCustomData(GTQTValue.UPDATE_TIER8, buf1 -> buf1.writeInt(this.casing_tier));
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("casing_tier", casing_tier);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        casing_tier = data.getInteger("casing_tier");
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casing_tier);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casing_tier = buf.readInt();
    }


    @Override
    public void addCustomData(KeyManager keyManager, UISyncer syncer) {
        super.addCustomData(keyManager, syncer);
        if (isStructureFormed()){
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.eleTire2", syncer.syncInt(tier), syncer.syncInt(glass_tier), syncer.syncInt(laser_tier)));
            keyManager.add(KeyUtil.lang(TextFormatting.GRAY, "gtqtcore.eleTire4", syncer.syncInt(clean_tier), syncer.syncInt(radio_tier)));
        }
    }
    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XJXXXXXXX", "XJXGGGGXX", "XJXGGGGXX")
                .aisle("XJXXXXXXX", "XJPZZZZPX", "XJXGGGGXX")
                .aisle("XJXXXXXXX", "XJPZZZZPX", "XJXGGGGXX")
                .aisle("CJXXXXXXX", "SJXGGGGXX", "XJXGGGGXX")
                .where('S', selfPredicate())
                .where('C', abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION))
                .where('X', TiredTraceabilityPredicate.CP_CASING.get().setMinGlobalLimited(40).or(autoAbilities()))
                .where('Z', TiredTraceabilityPredicate.CP_ZW_CASING.get())
                .where('G', TiredTraceabilityPredicate.CP_LGLASS.get())
                .where('J', TiredTraceabilityPredicate.CP_ZJ_CASING.get())
                .where('P', TiredTraceabilityPredicate.CP_TJ_CASING.get())
                .where('#', air())
                .build();
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casing_tier) {
            case (2) -> {
                return Textures.SOLID_STEEL_CASING;
            }
            case (3) -> {
                return Textures.FROST_PROOF_CASING;
            }
            case (4) -> {
                return Textures.CLEAN_STAINLESS_STEEL_CASING;
            }
            case (5) -> {
                return Textures.STABLE_TITANIUM_CASING;
            }
            case (6) -> {
                return Textures.ROBUST_TUNGSTENSTEEL_CASING;
            }
            case (7) -> {
                return GTQTTextures.PD_CASING;
            }
            case (8) -> {
                return GTQTTextures.NQ_CASING;
            }
            case (9) -> {
                return GTQTTextures.ST_CASING;
            }
            case (10) -> {
                return GTQTTextures.AD_CASING;
            }
            default -> {
                return Textures.BRONZE_PLATED_BRICKS;
            }
        }
    }

    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }

    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }

    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public SoundEvent getSound() {
        return GTSoundEvents.ELECTROLYZER;
    }

    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && providers.size() >= 1) {
            this.computationProvider = providers.get(0);
        }
        Object laser_tier = context.get("ZWTieredStats");
        Object tier = context.get("ChemicalPlantCasingTieredStats");
        Object glass_tier = context.get("LGLTieredStats");
        Object clean_tier = context.get("ZJTieredStats");
        Object radio_tier = context.get("TJTieredStats");

        this.laser_tier = GTQTUtil.getOrDefault(() -> laser_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) laser_tier).getIntTier(), 0);
        this.casing_tier = GTQTUtil.getOrDefault(() -> tier instanceof WrappedIntTired, () -> ((WrappedIntTired) tier).getIntTier(), 0);
        this.glass_tier = GTQTUtil.getOrDefault(() -> glass_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) glass_tier).getIntTier(), 0);
        this.clean_tier = GTQTUtil.getOrDefault(() -> clean_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) clean_tier).getIntTier(), 0);
        this.radio_tier = GTQTUtil.getOrDefault(() -> radio_tier instanceof WrappedIntTired, () -> ((WrappedIntTired) radio_tier).getIntTier(), 0);

        setTier(Math.min(this.casing_tier, this.glass_tier));
        setMaxVoltage(Math.min(this.casing_tier, this.clean_tier * 2));

        this.writeCustomData(GTQTValue.UPDATE_TIER8, buf -> buf.writeInt(this.casing_tier));
    }


    @Override
    public void invalidateStructure() {
        super.invalidateStructure();
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }


    protected class LaserEngravingWorkableHandler extends GTQTOCMultiblockLogic {
        public LaserEngravingWorkableHandler(RecipeMapMultiblockController tileEntity) {
            super(tileEntity);
        }

        @Override
        public void update() {
            super.update();
            if (radio_tier == laser_tier) {
                setTimeReduce((100 - glass_tier * 8) / 100.0);
                setMaxParallel(4 * clean_tier * radio_tier);
            } else {
                setTimeReduce((100 - glass_tier * 4) / 100.0);
                setMaxParallel(clean_tier * radio_tier);
            }
        }
    }
}