package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc;

import gregtech.api.capability.IObjectHolder;
import gregtech.api.capability.IOpticalComputationHatch;
import gregtech.api.capability.IOpticalComputationProvider;
import gregtech.api.capability.IOpticalComputationReceiver;
import gregtech.api.capability.impl.ComputationRecipeLogic;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.metatileentity.multiblock.RecipeMapMultiblockController;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.recipes.Recipe;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.TextComponentUtil;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityDataBank;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityHPCA;
import keqing.gtqtcore.api.recipes.GTQTcoreRecipeMaps;
import keqing.gtqtcore.api.recipes.properties.ELEProperties;
import keqing.gtqtcore.api.recipes.properties.KQKindProperty;
import keqing.gtqtcore.api.recipes.properties.KQNetProperty;
import keqing.gtqtcore.api.utils.GTQTKQnetHelper;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityElectronMicroscope;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityEnzymesReaction;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityGeneMutagenesis;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityPhotolithographyFactory;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;


public class MetaTileEntitykeQingNet extends RecipeMapMultiblockController implements IOpticalComputationReceiver {
    private IOpticalComputationProvider computationProvider;
    double tier;
    int page=0;
    int x;
    int y;
    int z;
    int card;
    boolean preLoad=false;
    int [][] io=new int[40][5];

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("page", page);
        for(int i=0;i<40;i++) data.setIntArray("io"+i,io[i]);

        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);

        page = data.getInteger("page");
        for(int i=0;i<40;i++) io[i]=data.getIntArray("io"+i);
    }

    public MetaTileEntitykeQingNet(ResourceLocation metaTileEntityId) {
            super(metaTileEntityId, GTQTcoreRecipeMaps.KEQING_NET_RECIES);
            this.recipeMapWorkable = new ResearchStationRecipeLogic(this);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntitykeQingNet(this.metaTileEntityId);
    }

    @Override
    public void update() {
        super.update();
        //更新 如果有输入坐标
        if(checkLoacl(true))for(int i=0;i<40;i++) {
            //有空位&&找到结构
            if (io[i][0] == 0 && MachineCheck(i, x, y, z)&&localCheck()) {
                //赋值操作
                io[i][0] = 1;
                io[i][1] = x;
                io[i][2] = y;
                io[i][3] = z;
                checkLoacl(false);
                GTTransferUtils.insertItem(this.outputInventory, setCard(), false);
                x = 0;
                y = 0;
                z = 0;
            }
        }

        if(!preLoad)for(int i=0;i<40;i++)if (io[i][0] == 1&&MachineCheck(i, io[i][1], io[i][2], io[i][3]))preLoad=true;

        //检查 已经赋值的还在不在
        double helpTier = 1;

        if(!preLoad)return;
        for(int i=0;i<40;i++) if (io[i][0] == 1) {
            //如果检查失败
            if (!this.getWorld().isRemote&&!MachineCheck(i, io[i][1], io[i][2], io[i][3])) {
                io[i][0] = 0;
                io[i][1] = 0;
                io[i][2] = 0;
                io[i][3] = 0;
                io[i][4] = 0;
            }
            //量子计算机 HPCA
            if (io[i][4] == 3) helpTier += 1.0;

            if (io[i][4] == 30) helpTier += 0.25;
            if (io[i][4] == 31) helpTier += 0.5;
            if (io[i][4] == 32) helpTier += 1.0;

            if (io[i][4] == 33) helpTier += 0.25;
            if (io[i][4] == 34) helpTier += 0.5;
            if (io[i][4] == 35) helpTier += 1.0;
        }

        if (helpTier != tier) tier = helpTier;

    }
    public boolean localCheck()
    {
        for(int j=0;j<40;j++)if(io[j][0]==1)
        {
            //已绑定过了
            if(x==io[j][1]&&y==io[j][2]&&z==io[j][3])return false;
        }
        return true;
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("我是真理", new Object[0]));
        tooltip.add(I18n.format("gtqtcore.machine.kqnet.tooltip.1"));
        tooltip.add(I18n.format("gtqtcore.machine.kqnet.tooltip.2"));
        tooltip.add(I18n.format("gtqtcore.machine.kqnet.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.machine.kqnet.tooltip.4"));
    }

    int thresholdPercentage=0;

    @Override
    public boolean checkRecipe(@Nonnull Recipe recipe, boolean consumeIfSuccess) {
        if(recipe.getProperty(KQNetProperty.getInstance(), 0)==thresholdPercentage&&
                recipe.getProperty(ELEProperties.getInstance(), 0)<=tier&&
                checkKind(recipe.getProperty(KQKindProperty.getInstance(), 0)))
        {
            return super.checkRecipe(recipe, consumeIfSuccess);
        }
        return false;
    }

    protected void addInfo1(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed()&&thresholdPercentage-2>0) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", thresholdPercentage - 2));
                        if(card==0) {
                            tl.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.nb%s", thresholdPercentage - 2)));
                        }
                        if(card==1)
                        {
                            if(thresholdPercentage-2<=15&&thresholdPercentage-2>0) {
                                textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.bio1"));
                            }
                        }
                    }});
    }

    protected void addInfo2(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed()&&thresholdPercentage-1>0) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", thresholdPercentage -1));
                        if(card==0) {
                            tl.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.nb%s", thresholdPercentage - 1)));
                        }
                        if(card==1)
                        {
                            if(thresholdPercentage-1<=15&&thresholdPercentage-1>0) {
                                textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.bio1"));
                            }
                        }
                    }});
    }

    protected void addInfo3(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed()) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GOLD, "序号：%s", thresholdPercentage));
                        if(card==0) {
                            tl.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.nb%s", thresholdPercentage)));
                        }
                        if(card==1)
                        {
                            if(thresholdPercentage<=15&&thresholdPercentage>0) {
                                textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.bio1"));
                            }
                        }
                    }});
    }

    protected void addInfo4(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", thresholdPercentage +1));
                    if (isStructureFormed()&&thresholdPercentage+1<100) {
                        if(card==0) {
                            tl.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.nb%s", thresholdPercentage +1)));
                        }
                        if(card==1)
                        {
                            if(thresholdPercentage+1<=15&&thresholdPercentage+1>0) {
                                textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.bio1"));
                            }
                        }
                    }});
    }


    protected void addInfo5(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed())
                .addCustom(tl -> {
                    if (isStructureFormed()&&thresholdPercentage+2<100) {
                        tl.add(TextComponentUtil.translationWithColor(TextFormatting.GRAY, "序号：%s", thresholdPercentage + 2));
                        if(card==0) {
                            tl.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.nb%s", thresholdPercentage + 2)));
                        }
                        if(card==1)
                        {
                            if(thresholdPercentage+2<=15&&thresholdPercentage+2>0) {
                                textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.bio1"));
                            }
                        }
                    }});
    }
    

    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 300, 240);

        // Display
        builder.image(132, 4, 162, 115, GuiTextures.DISPLAY);
        builder.dynamicLabel(135, 8, () -> "科研计算机", 0xFFFFFF);
        builder.widget((new AdvancedTextWidget(135, 20, this::addDisplayText, 16777215)).setMaxWidthLimit(160).setClickHandler(this::handleDisplayClick));

        int j=0;
        //1号
        builder.image(3, 4+j*30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8+j*30, this::addInfo1, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4+j*30, 15, 30, "-2", data -> this.thresholdPercentage = MathHelper.clamp(thresholdPercentage -2, 0, 100)  ));
        j++;
        //2号
        builder.image(3, 4+j*30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8+j*30, this::addInfo2, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4+j*30, 15, 30, "-1", data ->  this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 1, 0, 100) ));
        j++;
        //3号
        builder.image(3, 4+j*30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8+j*30, this::addInfo3, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4+j*30, 15, 30, "->", data -> thresholdPercentage+=0));
        j++;
        //4号
        builder.image(3, 4+j*30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8+j*30, this::addInfo4, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4+j*30, 15, 30, "+1", data -> this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 1, 0, 100)   ));
        j++;
        //5号
        builder.image(3, 4+j*30, 130, 30, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 8+j*30, this::addInfo5, 16777215)).setMaxWidthLimit(120).setClickHandler(this::handleDisplayClick));
        builder.widget(new ClickButtonWidget(118, 4+j*30, 15, 30, "+2", data -> this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 2, 0, 100)  ));

        // Display
        builder.image(3, 154, 130, 82, GuiTextures.DISPLAY);
        builder.widget((new AdvancedTextWidget(7, 158, this::addTotal, 16777215)).setMaxWidthLimit(130).setClickHandler(this::handleDisplayClick));


        builder.widget(new ClickButtonWidget(132, 120, 48, 18, "研究项目", data -> page = 0));
        builder.widget(new ClickButtonWidget(180, 120, 48, 18, "接入设备", data -> page = 1));
        builder.widget(new ClickButtonWidget(228, 120, 48, 18, "科研等级",data -> page = 2));

        builder.widget(new ClickButtonWidget(132, 140, 24, 18, "+1", data -> this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 1, 0, 100)));
        builder.widget(new ClickButtonWidget(156, 140, 24, 18, "+5", data -> this.thresholdPercentage = MathHelper.clamp(thresholdPercentage + 5, 0, 100)));
        builder.widget(new ClickButtonWidget(180, 140, 24, 18, "-1", data -> this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 1, 0, 100)));
        builder.widget(new ClickButtonWidget(204, 140, 24, 18, "-5", data -> this.thresholdPercentage = MathHelper.clamp(thresholdPercentage - 5, 0, 100)));

        builder.widget(new ClickButtonWidget(228, 140, 48, 18, "研究类型",data->{
            if(card<2)card++;
            else card=0;
        }));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 132,160);
        return builder;
    }

    protected void addTotal(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("科研系统-等级：%s",tier));
        textList.add(new TextComponentTranslation("接入设备列表："));
        for(int i=0;i<40;i++)
        {
            if(io[i][0] == 1)
            {
                textList.add(new TextComponentTranslation(String.format(GTQTKQnetHelper.getInfo(io[i][4]))));
            }
        }
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        if(page==0) {

            if(card==0) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.thresholdPercentage", thresholdPercentage,"传统科研"));
                textList.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.nb%s", thresholdPercentage)));
                textList.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.nx%s", thresholdPercentage)));
            }
            if(card==1) {
                textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.thresholdPercentage", thresholdPercentage,"基因定向"));
                if(thresholdPercentage==0)textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.bio0"));
                if(thresholdPercentage<=15&&thresholdPercentage>0) {
                    textList.add(new TextComponentTranslation("gtqtcore.multiblock.kqn.bio1"));
                    textList.add(new TextComponentTranslation(String.format("metaitem.bio.%s.tooltip",thresholdPercentage)));
                }
                if(thresholdPercentage>15) textList.add(new TextComponentTranslation(String.format("gtqtcore.multiblock.kqn.bio%s", thresholdPercentage)));
            }
        }
        if(page==1)
        {
            textList.add(new TextComponentTranslation("接入拓展设备列表："));
            for(int i=0;i<40;i++)
            {
                if(io[i][0] == 1&&io[i][4]<30)
                {
                    textList.add(new TextComponentTranslation(String.format("X:"+io[i][1]+" Y:"+io[i][2]+" Z:"+io[i][3]+"/"+GTQTKQnetHelper.getInfo(io[i][4]))));
                }
            }

        }
        if(page==2)
        {
            textList.add(new TextComponentTranslation("科研计算机系统-科研等级水平：%s",tier));
            for(int i=0;i<40;i++)
            {
                if(io[i][0] == 1&&(io[i][4]>=30||io[i][4]==3))
                {
                    textList.add(new TextComponentTranslation(String.format("X:"+io[i][1]+" Y:"+io[i][2]+" Z:"+io[i][3]+"/"+GTQTKQnetHelper.getInfo(io[i][4]))));
                }
            }
        }
    }

        @Nonnull
        protected  BlockPattern createStructurePattern() {
            return FactoryBlockPattern.start()
                    .aisle("PPP", "PPP", "PPP")
                    .aisle("PPP", "PSP", "PPP")
                    .where('S', this.selfPredicate())
                    .where('P', states(new IBlockState[]{getCasingState()})
                            .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                            .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setExactLimit(1))
                            .or(abilities(MultiblockAbility.IMPORT_ITEMS).setExactLimit(1))
                            .or(abilities(MultiblockAbility.EXPORT_ITEMS).setExactLimit(1))
                            .or(abilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION).setExactLimit(1)))
                    .build();
        }


        protected IBlockState getCasingState() {
             return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.PTFE_INERT_CASING);
        }

        @SideOnly(Side.CLIENT)
        public ICubeRenderer getBaseTexture(IMultiblockPart sourcePart) {
            return Textures.INERT_PTFE_CASING;
        }

        @Nonnull
        @SideOnly(Side.CLIENT)
        protected  ICubeRenderer getFrontOverlay() {
            return Textures.RESEARCH_STATION_OVERLAY;
        }

        protected boolean shouldShowVoidingModeButton() {
            return false;
        }

        public boolean canVoidRecipeItemOutputs() {
            return true;
        }

    public IOpticalComputationProvider getComputationProvider() {
        return this.computationProvider;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        List<IOpticalComputationHatch> providers = this.getAbilities(MultiblockAbility.COMPUTATION_DATA_RECEPTION);
        if (providers != null && !providers.isEmpty()) {
            this.computationProvider = providers.get(0);
        }
        tier=1;
    }

    private static class ResearchStationRecipeLogic extends ComputationRecipeLogic {
            public ResearchStationRecipeLogic(MetaTileEntitykeQingNet metaTileEntity) {
                super(metaTileEntity, ComputationType.SPORADIC);
            }

            @Nonnull
            public  MetaTileEntitykeQingNet getMetaTileEntity() {
                return (MetaTileEntitykeQingNet)super.getMetaTileEntity();
            }
    }
    public boolean MachineCheck(int i,int x,int y,int z)
    {
        if(getDistance(x,y,z)>48)return false;
        //触发加载
        TileEntity te = this.getWorld().getTileEntity(new BlockPos(x,y,z));
        if (te instanceof IGregTechTileEntity igtte) {
            MetaTileEntity mte = igtte.getMetaTileEntity();
            //找到加速器
            if (mte instanceof MetaTileEntityElectronMicroscope) {
                if(((MetaTileEntityElectronMicroscope) mte).isStructureFormed()) {
                    io[i][4] = 1;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityParticleAccelerator) {
                if(((MetaTileEntityParticleAccelerator) mte).isStructureFormed()) {
                    io[i][4] = 2;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityEnzymesReaction) {
                if(((MetaTileEntityEnzymesReaction) mte).isStructureFormed()) {
                    io[i][4] = 11;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityGeneMutagenesis) {
                if(((MetaTileEntityGeneMutagenesis) mte).isStructureFormed()) {
                    io[i][4] = 12;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityKQCC) {
                if(((MetaTileEntityKQCC) mte).isStructureFormed()) {
                    io[i][4] = 30;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityADVKQCC) {
                if(((MetaTileEntityADVKQCC) mte).isStructureFormed()) {
                    io[i][4] = 31;
                    return true;
                }
            }
            //量子计算机
            if (mte instanceof MetaTileEntityHPCA) {
                if(((MetaTileEntityHPCA) mte).isStructureFormed()) {
                    io[i][4] = 3;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityMiniDataBank) {
                if(((MetaTileEntityMiniDataBank) mte).isStructureFormed()) {
                    io[i][4] = 33;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityDataBank) {
                if(((MetaTileEntityDataBank) mte).isStructureFormed()) {
                    io[i][4] = 34;
                    return true;
                }
            }
            if (mte instanceof MetaTileEntityADVDataBank) {
                if(((MetaTileEntityADVDataBank) mte).isStructureFormed()) {
                    io[i][4] = 35;
                    return true;
                }
            }
        }
        return false;
    }
    public ItemStack setCard() {
        ItemStack card = new ItemStack(GTQTMetaItems.POS_BINDING_CARD.getMetaItem(), 1, 417);
        NBTTagCompound nodeTagCompound = new NBTTagCompound();
        nodeTagCompound.setInteger("x", x);
        nodeTagCompound.setInteger("y", y);
        nodeTagCompound.setInteger("z", z);
        card.setTagCompound(nodeTagCompound);
        return card;
    }
    //通过物品获取坐标
    public boolean checkLoacl(boolean sim) {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_BINDING_CARD.getMetaValue()) {
                NBTTagCompound compound = item.getTagCompound();
                if (compound != null && compound.hasKey("x") && compound.hasKey("y") && compound.hasKey("z")) {
                    x = compound.getInteger("x");
                    y = compound.getInteger("y");
                    z = compound.getInteger("z");
                }
                this.getInputInventory().extractItem(i, 1, sim);
                return true;
            }
        }
        return false;
    }

    public boolean checkKind(int n)
    {
        if(n==0)return true;
        for(int i=0;i<30;i++) {
            if(io[i][4] == n)return true;
        }
        return false;

    }
    public int getDistance(int x,int y,int z)
    {
        double dx=x-this.getPos().getX();
        double dy=y-this.getPos().getY();
        double dz=z-this.getPos().getZ();
        return (int)Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
