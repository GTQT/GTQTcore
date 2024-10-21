package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import gregtech.api.block.IHeatingCoilBlockStats;
import gregtech.api.capability.GregtechDataCodes;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTUtility;
import gregtech.client.particle.GTParticleManager;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.core.sound.GTSoundEvents;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.capability.impl.MultiblockLaserRecipeLogic;
import keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility;
import keqing.gtqtcore.api.metaileentity.multiblock.RecipeMapLaserMultiblockController;
import keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.particle.LaserBeamParticle;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTHCasing;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.metatileentities.multi.generators.MetaTileEntityHyperReactorMkII;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.LaserSystem.MetaTileEntityLaserCutter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;
import java.util.List;

import static gregtech.common.blocks.BlockFusionCasing.CasingType.FUSION_COIL;
import static keqing.gtqtcore.api.metaileentity.multiblock.GTQTMultiblockAbility.LASER_INPUT;

public class MetaTileEntityParticleAggregator extends RecipeMapLaserMultiblockController {
    private int casingTier;
    private long LaserStore;
    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("casingTier", casingTier);
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        casingTier = data.getInteger("casingTier");
    }

    @Override
    public void update() {
        super.update();
        if(!isStructureFormed())return;

        writeCustomData(GregtechDataCodes.UPDATE_PARTICLE, this::writeParticles);

        MultiblockLaserRecipeLogic recipeLogic = this.recipeMapWorkable;
        if(!recipeLogic.isWorkingEnabled())
        {
            LaserStore+=LaserToEu();
        }
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation("gtqtcore.casingTire", casingTier));
    }
    //.aisle("             ", "             ", "             ", "             ", "      A      ", "      A      ", "    AABAA    ", "      A      ", "      A      ", "             ", "             ", "             ", "             ")
    //.aisle("             ", "             ", "      A      ", "      A      ", "    CCACC    ", "    C   C    ", "  AAA   AAA  ", "    C   C    ", "    CCACC    ", "      A      ", "      A      ", "             ", "             ")
    //.aisle("             ", "      A      ", "      A      ", "             ", "             ", "             ", " AA       AA ", "             ", "             ", "             ", "      A      ", "      A      ", "             ")
    //.aisle("             ", "      A      ", "             ", "             ", "      A      ", "     AAA     ", " A  AADAA  A ", "     AAA     ", "      A      ", "             ", "             ", "      A      ", "             ")
    //.aisle("      A      ", "    CCACC    ", "             ", "      A      ", " C   DAD   C ", " C  D   D  C ", "AA AA   AA AA", " C  D   D  C ", " C   DAD   C ", "      A      ", "             ", "    CCACC    ", "      A      ")
    //.aisle("      A      ", "    C   C    ", "             ", "     AAA     ", " C  D   D  C ", "   A     A   ", "A  A     A  A", "   A     A   ", " C  D   D  C ", "     AAA     ", "             ", "    C   C    ", "      A      ")
    //.aisle("    AABAA    ", "  AAA   AAA  ", " AA       AA ", " A  AADAA  A ", "AA AA   AA AA", "A  A     A  A", "B  D     D  B", "A  A     A  A", "AA AA   AA AA", " A  AADAA  A ", " AA       AA ", "  AAA   AAA  ", "    AABAA    ")
    //.aisle("      A      ", "    C   C    ", "             ", "     AAA     ", " C  D   D  C ", "   A     A   ", "A  A     A  A", "   A     A   ", " C  D   D  C ", "     AAA     ", "             ", "    C   C    ", "      A      ")
    //.aisle("      A      ", "    CCACC    ", "             ", "      A      ", " C   DAD   C ", " C  D   D  C ", "AA AA   AA AA", " C  D   D  C ", " C   DAD   C ", "      A      ", "             ", "    CCACC    ", "      A      ")
    //.aisle("             ", "      A      ", "             ", "             ", "      A      ", "     AAA     ", " A  AADAA  A ", "     AAA     ", "      A      ", "             ", "             ", "      A      ", "             ")
    //.aisle("             ", "      A      ", "      A      ", "             ", "             ", "             ", " AA       AA ", "             ", "             ", "             ", "      A      ", "      A      ", "             ")
    //.aisle("             ", "             ", "      A      ", "      A      ", "    CCACC    ", "    C   C    ", "  AAA   AAA  ", "    C   C    ", "    CCACC    ", "      A      ", "      A      ", "             ", "             ")
    //.aisle("             ", "             ", "             ", "             ", "      A      ", "      A      ", "    AABAA    ", "      A      ", "      A      ", "             ", "             ", "             ", "             ")
    //不是聚变的特殊 物品/方块 生产途径
    //需要安装5个激光接收仓
    //蓄能模式：不工作时将激光积聚到多方块内部，在工作时消耗积聚的激光对配方进行超频
    //使用激光类多方块的配方逻辑
    public MetaTileEntityParticleAggregator(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId, GTQTcoreRecipeMaps.CW_LASER_ENGRAVER_RECIPES);
        this.recipeMapWorkable = new MultiblockLaserRecipeLogic(this);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.FUSION_GLASS);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.H_CASING.getState(GTQTHCasing.CasingType.MACHINE_CASING_FUSION);
    }

    private static IBlockState getSecondCasingState() {
        return MetaBlocks.FUSION_CASING.getState(FUSION_COIL);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("             ", "             ", "             ", "             ", "      A      ", "      A      ", "    AABAA    ", "      A      ", "      A      ", "             ", "             ", "             ", "             ")
                .aisle("             ", "             ", "      A      ", "      A      ", "    CCACC    ", "    C   C    ", "  AAA   AAA  ", "    C   C    ", "    CCACC    ", "      A      ", "      A      ", "             ", "             ")
                .aisle("             ", "      A      ", "      A      ", "             ", "             ", "             ", " AA       AA ", "             ", "             ", "             ", "      A      ", "      A      ", "             ")
                .aisle("             ", "      A      ", "             ", "             ", "      A      ", "     AAA     ", " A  AADAA  A ", "     AAA     ", "      A      ", "             ", "             ", "      A      ", "             ")
                .aisle("      A      ", "    CCACC    ", "             ", "      A      ", " C   DAD   C ", " C  D   D  C ", "AA AA   AA AA", " C  D   D  C ", " C   DAD   C ", "      A      ", "             ", "    CCACC    ", "      A      ")
                .aisle("      A      ", "    C   C    ", "             ", "     AAA     ", " C  D   D  C ", "   A     A   ", "A  A     A  A", "   A     A   ", " C  D   D  C ", "     AAA     ", "             ", "    C   C    ", "      A      ")
                .aisle("    AABAA    ", "  AAA   AAA  ", " AA       AA ", " A  AADAA  A ", "AA AA   AA AA", "A  A     A  A", "B  D     D  B", "A  A     A  A", "AA AA   AA AA", " A  AADAA  A ", " AA       AA ", "  AAA   AAA  ", "    AABAA    ")
                .aisle("      A      ", "    C   C    ", "             ", "     AAA     ", " C  D   D  C ", "   A     A   ", "A  A     A  A", "   A     A   ", " C  D   D  C ", "     AAA     ", "             ", "    C   C    ", "      A      ")
                .aisle("      A      ", "    CCACC    ", "             ", "      A      ", " C   DAD   C ", " C  D   D  C ", "AA AA   AA AA", " C  D   D  C ", " C   DAD   C ", "      A      ", "             ", "    CCACC    ", "      A      ")
                .aisle("             ", "      A      ", "             ", "             ", "      A      ", "     AAA     ", " A  AADAA  A ", "     AAA     ", "      A      ", "             ", "             ", "      A      ", "             ")
                .aisle("             ", "      A      ", "      A      ", "             ", "             ", "             ", " AA       AA ", "             ", "             ", "             ", "      A      ", "      A      ", "             ")
                .aisle("             ", "             ", "      A      ", "      A      ", "    CCACC    ", "    C   C    ", "  AAA   AAA  ", "    C   C    ", "    CCACC    ", "      A      ", "      A      ", "             ", "             ")
                .aisle("             ", "             ", "             ", "             ", "      A      ", "      A      ", "    AASQA    ", "      A      ", "      A      ", "             ", "             ", "             ", "             ")
                .where('S', this.selfPredicate())
                .where('X', TiredTraceabilityPredicate.CP_FU_CASING.get()
                        .or(autoAbilities(false, false, true, true, true, false, false))
                )
                .where('B', abilities(GTQTMultiblockAbility.LASER_INPUT).setExactLimit(5))
                .where('A', TiredTraceabilityPredicate.CP_FU_CASING.get())

                .where('D', states(getGlassState()))
                .where('C', states(getSecondCasingState()))
                .where('Q', abilities(MultiblockAbility.MAINTENANCE_HATCH))

                .where(' ', any())
                .build();
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityParticleAggregator(metaTileEntityId);
    }

    @SideOnly(Side.CLIENT)
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        switch (this.casingTier) {
            case (1) -> {
                return GTQTTextures.FUSION_MKI;
            }
            case (2) -> {
                return GTQTTextures.FUSION_MKII;
            }
            default -> {
                return GTQTTextures.FUSION_MKIII;
            }
        }
    }
    @Override
    public void receiveCustomData(int dataId, PacketBuffer buf) {
        super.receiveCustomData(dataId, buf);
        if(dataId == GTQTValue.UPDATE_TIER26){
            this.casingTier = buf.readInt();
        }
        if(dataId == GTQTValue.REQUIRE_DATA_UPDATE26){
            this.writeCustomData(GTQTValue.UPDATE_TIER26,buf1 -> buf1.writeInt(this.casingTier));
        }

        if (dataId == GregtechDataCodes.UPDATE_PARTICLE) {
            try {
                readParticles(buf);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object casingTier = context.get("FUTieredStats");

        this.casingTier = GTQTUtil.getOrDefault(() -> casingTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)casingTier).getIntTier(),
                0);

        this.writeCustomData(GTQTValue.UPDATE_TIER26,buf -> buf.writeInt(this.casingTier));
    }

    @Override
    public void writeInitialSyncData(PacketBuffer buf) {
        super.writeInitialSyncData(buf);
        buf.writeInt(this.casingTier);
        writeParticles(buf);
    }

    @Override
    public void receiveInitialSyncData(PacketBuffer buf) {
        super.receiveInitialSyncData(buf);
        this.casingTier = buf.readInt();
        try {
            readParticles(buf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @SideOnly(Side.CLIENT)
    @Override
    protected ICubeRenderer getFrontOverlay() {
        return Textures.FUSION_REACTOR_OVERLAY;
    }

    @Override
    public boolean canBeDistinct() {
        return true;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    public SoundEvent getBreakdownSound() {
        return GTSoundEvents.BREAKDOWN_ELECTRICAL;
    }
    //////////////////////////////////////////////////////////////////////////////////////
    public int getTier() {
        if (!isStructureFormed()) return 0;
        int maxTier = 0;

        for (int i = 0; i < 5; i++) {
            int tier = GTUtility.getTierByVoltage(this.getAbilities(LASER_INPUT).get(i).Laser());
            if (tier > maxTier) {
                maxTier = tier;
            }
        }
        return maxTier;

    }

    public int getMaxLaser() {
        if (!isStructureFormed()) return 0;
        int maxTier = 0;

        for (int i = 0; i < 5; i++) {
            int tier = GTUtility.getTierByVoltage(this.getAbilities(LASER_INPUT).get(i).MaxLaser());
            if (tier > maxTier) {
                maxTier = tier;
            }
        }
        return maxTier;
    }

    public int getTemp() {
        return getTier() * 900 + 900;
    }

    public long LaserToEu() {
        if (!isStructureFormed()) return 0;
        long EU=0;
        for (int i = 0; i < 5; i++) {
            EU+= GTUtility.getTierByVoltage(this.getAbilities(LASER_INPUT).get(i).Laser());
        }
        return EU;
    }

    public int getParallelLimit() {
        return (int) Math.pow(2, getTier());
    }

    private void writeParticles(PacketBuffer buf) {
    }
    @SideOnly(Side.CLIENT)
    private void readParticles( PacketBuffer buf) throws IOException {
        operateClient();
    }
    @SideOnly(Side.CLIENT)
    public void operateClient() {
        final int xDir = this.getFrontFacing().getOpposite().getXOffset() * 6;
        final int zDir = this.getFrontFacing().getOpposite().getZOffset() * 6;
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this,this.getPos().add(xDir,0,zDir),this.getPos().add(xDir,6,zDir),20));
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this,this.getPos().add(xDir,0,zDir),this.getPos().add(xDir,-6,zDir),20));
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this,this.getPos().add(xDir,0,zDir),this.getPos().add(xDir+6,0,zDir),20));
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this,this.getPos().add(xDir,0,zDir),this.getPos().add(xDir-6,0,zDir),20));
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this,this.getPos().add(xDir,0,zDir),this.getPos().add(xDir,0,zDir+6),20));
        GTParticleManager.INSTANCE.addEffect(new LaserBeamParticle(this,this.getPos().add(xDir,0,zDir),this.getPos().add(xDir,0,zDir-6),20));
    }


}
