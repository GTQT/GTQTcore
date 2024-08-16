package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard;

import codechicken.lib.raytracer.CuboidRayTraceResult;
import gregtech.api.capability.GregtechCapabilities;
import gregtech.api.capability.IEnergyContainer;
import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.Widget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.WidgetGroup;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.PatternMatchContext;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.blocks.BlockGlassCasing;
import gregtech.common.blocks.MetaBlocks;
import keqing.gtqtcore.api.GTQTValue;
import keqing.gtqtcore.api.blocks.impl.WrappedIntTired;
import keqing.gtqtcore.api.utils.GTQTLog;
import keqing.gtqtcore.api.utils.GTQTUtil;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.block.GTQTMetaBlocks;
import keqing.gtqtcore.common.block.blocks.GTQTParticleAccelerator;
import keqing.gtqtcore.common.block.blocks.GTQTTurbineCasing;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.kqcc.MetaTileEntityParticleAccelerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import keqing.gtqtcore.api.utils.GTQTCPUHelper;
import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

import static gregtech.api.GTValues.V;
import static gregtech.api.GTValues.VA;
import static keqing.gtqtcore.api.predicate.TiredTraceabilityPredicate.CP_PAF_CASING;
import static keqing.gtqtcore.api.unification.GTQTMaterials.*;
import static keqing.gtqtcore.api.unification.TJMaterials.HydrogenSilsesquioxane;
import static keqing.gtqtcore.api.unification.TJMaterials.SU8_Photoresist;
import static keqing.gtqtcore.api.utils.GTQTCPUHelper.item;

public class MetaTileEntityPhotolithographyFactory extends MetaTileEntityBaseWithControl{

    int laserTier;
    int laserKind;
    int LaserAmount;
    int []wafer={0,0,0,0};
    int sum;
    boolean work;
    boolean balance;
    boolean check;
    boolean []coreWork= {false,false,false,false,false};
    public int [][]core={{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}};
    int updatetime=1;
    boolean make;

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setInteger("laserTier", laserTier);
        data.setInteger("laserKind", laserKind);
        data.setInteger("LaserAmount", LaserAmount);

        data.setIntArray("wafer", wafer);

        data.setIntArray("core0", core[0]);
        data.setIntArray("core1", core[1]);
        data.setIntArray("core2", core[2]);
        data.setIntArray("core3", core[3]);

        data.setBoolean("work",work);
        data.setBoolean("balance",balance);
        data.setBoolean("check",check);

        data.setBoolean("coreWork1",coreWork[0]);
        data.setBoolean("coreWork2",coreWork[1]);
        data.setBoolean("coreWork3",coreWork[2]);
        data.setBoolean("coreWork4",coreWork[3]);
        data.setBoolean("coreWork5",coreWork[4]);
        return super.writeToNBT(data);
    }
   
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        laserTier = data.getInteger("laserTier");
        laserKind = data.getInteger("laserKind");
        LaserAmount = data.getInteger("LaserAmount");

        wafer=data.getIntArray("wafer");

        core[0]=data.getIntArray("core0");
        core[1]=data.getIntArray("core1");
        core[2]=data.getIntArray("core2");
        core[3]=data.getIntArray("core3");

        work=data.getBoolean("work");
        balance=data.getBoolean("balance");
        check=data.getBoolean("check");

        coreWork[0]=data.getBoolean("coreWork1");
        coreWork[1]=data.getBoolean("coreWork2");
        coreWork[2]=data.getBoolean("coreWork3");
        coreWork[3]=data.getBoolean("coreWork4");
        coreWork[4]=data.getBoolean("coreWork5");
    }


    public boolean onScrewdriverClick(EntityPlayer playerIn, EnumHand hand, EnumFacing facing, CuboidRayTraceResult hitResult) {
        make=!make;
        if(!make)return false;
        if(updatetime<=19) updatetime++;
        else updatetime=1;
        playerIn.sendMessage(new TextComponentTranslation("输入频率：%s 次/tick",updatetime));
        return true;
    }
    //核心缓存： wafer等级0 wafer数量1 光刻胶等级2 耗时3
    public MetaTileEntityPhotolithographyFactory(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentTranslation("%s/%s/%s/%s|%s/%s|开关%s 均分%s 保险%s", wafer[0],wafer[1], wafer[2], wafer[3],laserKind,LaserAmount/1000,work,balance,check));

        for(int i=0;i<4;i++)
        {
            textList.add(new TextComponentTranslation(">>线程:%s 耗时 %s/%s",i+1,core[i][4],core[i][3]));
            textList.add(new TextComponentTranslation("晶圆等级 %s/光刻胶等级 %s/配方数量 %s",core[i][0],core[i][2],core[i][1]));
        }
    }

    @Override
    @Nonnull
    protected Widget getFlexButton(int x, int y, int width, int height) {
        WidgetGroup group = new WidgetGroup(x, y, width, height);
        group.addWidget(new ClickButtonWidget(0, 0, 9, 9, "", this::work)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("工作(手动开关)"));
        group.addWidget(new ClickButtonWidget(9, 0, 9, 9, "", this::balance)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("均分(智能线程)"));
        group.addWidget(new ClickButtonWidget(0, 9, 9, 9, "", this::check)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("保险(强制持续运行)"));
        group.addWidget(new ClickButtonWidget(9, 9, 9, 9, "", this::outputlaser)
                .setButtonTexture(GuiTextures.BUTTON_THROTTLE_MINUS)
                .setTooltipText("退回缓存(返回缓存光刻胶以及晶圆, 不含正在工作的)"));
        return group;
    }
    private void outputlaser(Widget.ClickData clickData) {
        if(laserKind==1) this.getOutputFluidInventory().fill(HydrogenSilsesquioxane.getFluid(LaserAmount),true);
        if(laserKind==2) this.getOutputFluidInventory().fill(SU8_Photoresist.getFluid(LaserAmount),true);
        if(laserKind==3) this.getOutputFluidInventory().fill(Vinylcinnamate.getFluid(LaserAmount),true);
        if(laserKind==4) this.getOutputFluidInventory().fill(Xmt.getFluid(LaserAmount),true);
        if(laserKind==5) this.getOutputFluidInventory().fill(Zrbtmst.getFluid(LaserAmount),true);
        LaserAmount=0;
        for(int j=0;j<this.wafer.length;j++) {
            if (this.wafer[j] > 0)
                GTTransferUtils.insertItem(this.outputInventory, new ItemStack(GTQTCPUHelper.wafer[j].getMetaItem(), wafer[j], GTQTCPUHelper.wafer[j].getMetaValue()), false);
        }
    }
    private void check(Widget.ClickData clickData) {
        check=!check;
    }
    private void balance(Widget.ClickData clickData) {
        balance=!balance;
    }
    private void work(Widget.ClickData clickData) {
        work=!work;
    }

    @Override
    protected void updateFormedValid() {
        InputWafer();//检查
        //光刻胶操作
        IMultipleTankHandler inputTank = getInputFluidInventory();
        for(int time=0;time<updatetime;time++)if(LaserAmount<640000) {
            if (LASER1.isFluidStackIdentical(inputTank.drain(LASER1, true))) {
                addLaserAmount(1);
            }
            if (LASER2.isFluidStackIdentical(inputTank.drain(LASER2, true))) {
                addLaserAmount(2);
            }
            if (LASER3.isFluidStackIdentical(inputTank.drain(LASER3, true))) {
                addLaserAmount(3);
            }
            if (LASER4.isFluidStackIdentical(inputTank.drain(LASER4, true))) {
                addLaserAmount(4);
            }
            if (LASER5.isFluidStackIdentical(inputTank.drain(LASER5, true))) {
                addLaserAmount(5);
            }
        }
        if(check)work= coreWork[0] || coreWork[1] || coreWork[2] || coreWork[3];
        if(coreWork[0]&&coreWork[1]&&coreWork[2]&&coreWork[3]&&check)check=false;
        //多核心
        for(int i=0;i<4;i++)//容量上限
        {
            //规划核心
            //核心缓存： wafer等级0 wafer数量1 光刻胶等级2 耗时3

            //无任务=无任务
            coreWork[i]=(core[i][0]!=0);

            //专职负责
            if ((core[i][0] == i+1||core[i][0] == 0)&&Math.min(LaserAmount / 1000, wafer[i]) > 1) {
                core[i][0] = i+1;
                if(core[i][1]+Math.min(LaserAmount / 1000, wafer[i])<65) {
                    core[i][1] += Math.min(LaserAmount / 1000, wafer[i]);
                    LaserAmount -= Math.min(LaserAmount / 1000, wafer[i]) * 1000;
                    wafer[i] -= Math.min(LaserAmount / 1000, wafer[i]);
                    core[i][2] = laserKind;
                }
            }
            else if(balance) for (int j = 0; j < 4; j++) if (j != i)//帮助其他核心
            {
                if ((core[i][1] == 0 || (core[i][0] == core[j][0] && Math.abs(core[i][1] - core[j][1]) >= 1)) && core[j][1] > 2)//核心2的任务很多 核心一没有自己的专职任务或者核心一处理的任务与核心二相
                {
                    core[i][2] = core[j][2];
                    core[i][0] = core[j][0];
                    sum=core[i][1]+core[j][1];
                    core[i][1]=sum/2;
                    core[j][1]=sum-core[i][1];sum=0;
                }
            }
            core[i][3] = TimeConsume(core[i][0],core[i][1], core[i][2]);
            //总规划 计算耗时
        }

        if(work)
        {
            //这里开始工作了
            for(int i=0;i<4;i++)if(coreWork[i]&&this.energyContainer.getEnergyStored()- ((long) core[i][1] *VA[core[i][0]+core[i][2]])/laserTier>0)
            {
                this.energyContainer.removeEnergy(((long) core[i][1] *VA[core[i][0]+core[i][2]])/laserTier);
                core[i][4]++;
                if (core[i][4] >= core[i][3]) {
                    for (int number = 0; number < item.length; number++)//辅助 数量 wafer等级 光刻胶等级
                        GTTransferUtils.insertItem(this.outputInventory, GTQTCPUHelper.getStack(number, core[i][1], core[i][0], core[i][2]), false);

                    core[i][0] = core[i][1] = core[i][2] = core[i][3] = core[i][4] = 0;
                    coreWork[i]=false;
                }
            }
        }
    }
    public int TimeConsume(int tier,int amount,int LaserTier)
    {
        return 20*amount*(10-LaserTier)*(10-tier)/laserTier;
    }
    public void InputWafer() {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);

            for(int j=0;j<GTQTCPUHelper.wafer.length;j++)
            {
                if(GTQTCPUHelper.checkWafer(item,GTQTCPUHelper.wafer[j])) {
                    this.getInputInventory().extractItem(i, 1, false);
                    wafer[j]++;
                }
            }
        }
    }

    //光刻胶等级
    FluidStack LASER1 = HydrogenSilsesquioxane.getFluid(1000);
    FluidStack LASER2 = SU8_Photoresist .getFluid(1000);
    FluidStack LASER3 = Vinylcinnamate.getFluid(1000);
    FluidStack LASER4 = Xmt.getFluid(1000);
    FluidStack LASER5 = Zrbtmst.getFluid(1000);
    public void addLaserAmount(int n)
    {
        if(laserKind==n)LaserAmount += 1000;
        if(laserKind==0||LaserAmount==0) {
            LaserAmount= 1000;
            laserKind=n;
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return GTQTTextures.IRIDIUM_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityPhotolithographyFactory(metaTileEntityId);
    }

    @Override
    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCCBCCF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                .aisle("                    FCCBCCF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCCBCCF                    ")
                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCCBCCF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCC                       CCHCC       ", "       CHHHC                       CHHHC       ", "       CCHCC                       CCHCC       ", "        CCC                         CCC        ", "                                               ")
                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCC                           CCHCC     ", "     CHHHC                           CHHHC     ", "     CCHCC                           CCHCC     ", "      CCC                             CCC      ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                .aisle("  CCC                                     CCC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CCC                                     CCC  ")
                .aisle("  CBC                                     CBC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "B#HHH#B                                 B#HHH#B", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CBC                                     CBC  ")
                .aisle("  CCC                                     CCC  ", " B###B                                   B###B ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " B###B                                   B###B ", "  CCC                                     CCC  ")
                .aisle("  CCC                                     CCC  ", " C###C                                   C###C ", "C##H##C                                 C##H##C", "C#HHH#C                                 C#HHH#C", "C##H##C                                 C##H##C", " C###C                                   C###C ", "  CCC                                     CCC  ")
                .aisle("  FFF                                     FFF  ", " FCCCF                                   FCCCF ", "FCCHCCF                                 FCCHCCF", "FCHHHCF                                 FCHHHCF", "FCCHCCF                                 FCCHCCF", " FCCCF                                   FCCCF ", "  FFF                                     FFF  ")
                .aisle("                                               ", "  CCC                                     CCC  ", " CCHCC                                   CCHCC ", " CHHHC                                   CHHHC ", " CCHCC                                   CCHCC ", "  CCC                                     CCC  ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "   CCC                                   CCC   ", "  CCHCC                                 CCHCC  ", "  CHHHC                                 CHHHC  ", "  CCHCC                                 CCHCC  ", "   CCC                                   CCC   ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "    CCC                                 CCC    ", "   CCHCC                               CCHCC   ", "   CHHHC                               CHHHC   ", "   CCHCC                               CCHCC   ", "    CCC                                 CCC    ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "     CCC                               CCC     ", "    CCHCC                             CCHCC    ", "    CHHHC                             CHHHC    ", "    CCHCC                             CCHCC    ", "     CCC                               CCC     ", "                                               ")
                .aisle("                                               ", "      CCC                             CCC      ", "     CCHCC                           CCHCC     ", "     CHHHC                           CHHHC     ", "     CCHCC                           CCHCC     ", "      CCC                             CCC      ", "                                               ")
                .aisle("                                               ", "       CCC                           CCC       ", "      CCHCC                         CCHCC      ", "      CHHHC                         CHHHC      ", "      CCHCC                         CCHCC      ", "       CCC                           CCC       ", "                                               ")
                .aisle("                                               ", "        CCC                         CCC        ", "       CCHCC                       CCHCC       ", "       CHHHC                       CHHHC       ", "       CCHCC                       CCHCC       ", "        CCC                         CCC        ", "                                               ")
                .aisle("                                               ", "         CCC                       CCC         ", "        CCHCCC                   CCCHCC        ", "        CHHHCC                   CCHHHC        ", "        CCHCCC                   CCCHCC        ", "         CCC                       CCC         ", "                                               ")
                .aisle("                                               ", "          CCCC                   CCCC          ", "         CCHCCCC               CCCCHCC         ", "         CHHHHCC               CCHHHHC         ", "         CCHCCCC               CCCCHCC         ", "          CCCC                   CCCC          ", "                                               ")
                .aisle("                                               ", "           CCCCC               CCCCC           ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "          CHHHHHCCC FCCBCCF CCCHHHHHC          ", "          CCHHCCCCC FCCCCCF CCCCCHHCC          ", "           CCCCC               CCCCC           ", "                                               ")
                .aisle("                                               ", "            CCCCCCC FCBBBCF CCCCCCC            ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "           CHHHHHHHCC#####CCHHHHHHHC           ", "           CCCHHCCCCC#####CCCCCHHCCC           ", "            CCCCCCC FCBBBCF CCCCCCC            ", "                                               ")
                .aisle("                    FCBBBCF                    ", "              CCCCCCC#####CCCCCCC              ", "            CCCCHHHCC#####CCHHHCCCC            ", "            CCHHHHHHHHHHHHHHHHHHHCC            ", "            CCCCHHHCC#####CCHHHCCCC            ", "              CCCCCCC#####CCCCCCC              ", "                    FCBBBCF                    ")
                .aisle("                    FCCBCCF                    ", "                CCCCC#####CCCCC                ", "              CCCCCHHHHHHHHHCCCCC              ", "              CCHHHHHHHHHHHHHHHCC              ", "              CCCCCHHHHHHHHHCCCCC              ", "                CCCCC#####CCCCC                ", "                    FCCBCCF                    ")
                .aisle("                    FCBBBCF                    ", "                   CC#####CC                   ", "                CCCCC#####CCCCC                ", "                CCCHHHHHHHHHCCC                ", "                CCCCC#####CCCCC                ", "                   CC#####CC                   ", "                    FCBBBCF                    ")
                .aisle("                                               ", "                    FCBBBCF                    ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                   CC#####CC                   ", "                    FCBBBCF                    ", "                                               ")
                .aisle("                                               ", "                                               ", "                    FCCCCCF                    ", "                    FCCSCCF                    ", "                    FCCCCCF                    ", "                                               ", "                                               ")
                .where('S', this.selfPredicate())
                .where('B', states(getGlassState()))
                .where('C', states(getCasingState())
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2))
                        .or(abilities(MultiblockAbility.IMPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_FLUIDS).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(4)))
                .where('F', states(getFrameState()))
                .where('H', CP_PAF_CASING.get())
                .where('#', air())
                .where(' ', any())
                .build();
    }
    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }
    @Override
    protected void formStructure(PatternMatchContext context) {
        super.formStructure(context);
        Object laserTier = context.get("PAFTieredStats");
        this.laserTier = GTQTUtil.getOrDefault(() -> laserTier instanceof WrappedIntTired,
                () -> ((WrappedIntTired)laserTier).getIntTier(),
                0);
    }

    private static IBlockState getCasingState() {
        return GTQTMetaBlocks.TURBINE_CASING.getState(GTQTTurbineCasing.TurbineCasingType.IRIDIUM_CASING);
    }

    private static IBlockState getFrameState() {
        return MetaBlocks.FRAMES.get(Materials.Naquadah).getBlock(Materials.Naquadah);
    }

    private static IBlockState getGlassState() {
        return MetaBlocks.TRANSPARENT_CASING.getState(BlockGlassCasing.CasingType.LAMINATED_GLASS);
    }
    @Nonnull
    @Override
    public List<ITextComponent> getDataInfo() {
        return new LinkedList<>();
    }

    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("gtqtcore.pf.tooltip.1", new Object[0]));
        tooltip.add(TooltipHelper.BLINKING_CYAN+I18n.format("gtqtcore.pf.tooltip.2", 4));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.3"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.4"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.5"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.6"));
        tooltip.add(I18n.format("gtqtcore.pf.tooltip.7"));
        //tooltip.add(I18n.format("=================================="));
        //tooltip.add(I18n.format("如宇宙间最精密的织梦者，以光年丈量着世界的边界，悄然模糊科技与艺术的边界。"));
        //tooltip.add(I18n.format("如星辰运转于浩瀚银河之中，光线织工在超高速微电路上起舞绘制出纳米级的电路蓝图。"));
        //tooltip.add(I18n.format("如同晨曦中露珠滑过蜘蛛网的细腻，每一滴都承载着光的意志，编织着电子世界的经纬。"));
        //tooltip.add(I18n.format("如乐团中的每一位乐手，虽各自独立却又完美同步，演奏出超越物理极限的乐章。"));
    }
}
