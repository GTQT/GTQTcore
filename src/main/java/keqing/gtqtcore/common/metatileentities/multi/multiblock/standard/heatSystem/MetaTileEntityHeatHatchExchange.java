package keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.heatSystem;

import gregtech.api.capability.IMultipleTankHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.ClickButtonWidget;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockDisplayText;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.util.GTTransferUtils;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityHeatHatch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static gregtech.api.unification.material.Materials.Steam;
import static gregtech.api.unification.material.Materials.Water;

public class MetaTileEntityHeatHatchExchange extends MetaTileEntityBaseWithControl {

    private final ItemStackHandler containerInventory;
    // 定义流体栈
    private final FluidStack WATER = Water.getFluid(1);
    private final FluidStack STEAM = Steam.getFluid(500);



    int select;
    int[] tempList = new int[]{300, 300, 300, 300, 300, 300, 300, 300};
    int[] exchangeRate = new int[]{1, 1, 1, 1, 1, 1, 1, 1};
    boolean modelHeat;
    MetaTileEntityHeatHatch targetMte = null;


    public MetaTileEntityHeatHatchExchange(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.containerInventory = new GTItemStackHandler(this, 8);
    }

    @Override
    protected void updateFormedValid() {
        for (int i = 0; i < containerInventory.getSlots(); i++) {
            ItemStack stack = containerInventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() == GTQTMetaItems.GTQT_META_ITEM && stack.getMetadata() == GTQTMetaItems.HEAT_SHIELD_MKI.getMetaValue()) {
                    exchangeRate[i] = 2;
                } else if (stack.getItem() == GTQTMetaItems.GTQT_META_ITEM && stack.getMetadata() == GTQTMetaItems.HEAT_SHIELD_MKII.getMetaValue()) {
                    exchangeRate[i] = 3;
                } else if (stack.getItem() == GTQTMetaItems.GTQT_META_ITEM && stack.getMetadata() == GTQTMetaItems.HEAT_SHIELD_MKIII.getMetaValue()) {
                    exchangeRate[i] = 4;
                } else if (stack.getItem() == GTQTMetaItems.GTQT_META_ITEM && stack.getMetadata() == GTQTMetaItems.HEAT_SHIELD_MKIV.getMetaValue()) {
                    exchangeRate[i] = 5;
                } else if (stack.getItem() == GTQTMetaItems.GTQT_META_ITEM && stack.getMetadata() == GTQTMetaItems.HEAT_SHIELD_MKV.getMetaValue()) {
                    exchangeRate[i] = 6;
                } else {
                    exchangeRate[i] = 1;
                }
            } else {
                exchangeRate[i] = 1;
            }
        }
        BlockPos backPos = getBackPos(this.getPos(), this.getFrontFacing());
        MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), backPos);
        if (mte instanceof MetaTileEntityHeatHatch) {
            targetMte = (MetaTileEntityHeatHatch) mte;

            if (STEAM.isFluidStackIdentical(inputFluidInventory.drain(STEAM, false))) {
                STEAM.isFluidStackIdentical(inputFluidInventory.drain(STEAM, true));
                modelHeat = true;

                tempList[0] = 600;
                // 计算总换热比重
                int totalExchangeRate = 0;
                for (int rate : exchangeRate) {
                    totalExchangeRate += rate;
                }
                // 如果总换热比重为0，则所有中间温度与初始温度相同
                if (totalExchangeRate == 0) {
                    for (int i = 1; i < 7; i++) {
                        tempList[i] = tempList[0];
                    }
                } else {
                    // 根据换热率线性插值计算中间温度
                    for (int i = 1; i < 7; i++) {
                        double weight = (double) exchangeRate[i] / totalExchangeRate;
                        tempList[i] = (int) (tempList[0] + (targetMte.getHeat() - tempList[0]) * (i / 6.0) * weight);
                    }
                }
                tempList[7] = (int)targetMte.getHeat();

                targetMte.balanceHeat(tempList[select], select+1);
                fillTanks(WATER, false);
            }

            if (WATER.isFluidStackIdentical(inputFluidInventory.drain(WATER, false))) {
                WATER.isFluidStackIdentical(inputFluidInventory.drain(WATER, true));
                modelHeat = false;

                tempList[0] = 300;
                // 计算总换热比重
                int totalExchangeRate = 0;
                for (int rate : exchangeRate) {
                    totalExchangeRate += rate;
                }

                // 如果总换热比重为0，则所有中间温度与初始温度相同
                if (totalExchangeRate == 0) {
                    for (int i = 1; i < 7; i++) {
                        tempList[i] = tempList[0];
                    }
                } else {
                    // 根据换热率线性插值计算中间温度
                    for (int i = 1; i < 7; i++) {
                        double weight = (double) exchangeRate[i] / totalExchangeRate;
                        tempList[i] = (int) (tempList[0] + (targetMte.getHeat() - tempList[0]) * (i / 6.0) * weight);
                    }
                }

                // 设置最终温度为目标温度
                tempList[7] = (int)targetMte.getHeat();

                targetMte.balanceHeat(tempList[select], select+1);
                if(tempList[7]>400)fillTanks(STEAM, false);
            }
        } else {
            targetMte = null;
            Arrays.fill(tempList, 300);
        }
    }
    private BlockPos getBackPos(BlockPos pos, EnumFacing facing) {
        return pos.offset(facing.getOpposite());
    }
    public void fillTanks(FluidStack stack, boolean simulate) {
        GTTransferUtils.addFluidsToFluidHandler(outputFluidInventory, simulate, Collections.singletonList(stack));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound data) {
        data.setTag("ContainerInventory", this.containerInventory.serializeNBT());
        return super.writeToNBT(data);
    }

    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.containerInventory.deserializeNBT(data.getCompoundTag("ContainerInventory"));
    }

    protected void addInputDisplay(List<ITextComponent> textList) {
        MultiblockDisplayText.builder(textList, isStructureFormed()).addCustom(tl -> {
            tl.add(new TextComponentTranslation("%s", select));

            // 添加 tempList 信息
            StringBuilder tempListStr = new StringBuilder("Temp List: ");
            for (int temp : tempList) {
                tempListStr.append(temp).append(", ");
            }
            if (tempListStr.length() > 0) {
                tempListStr.setLength(tempListStr.length() - 2); // 去掉最后一个逗号和空格
            }
            tl.add(new TextComponentString(tempListStr.toString()));

            // 添加 exchangeRate 信息
            StringBuilder exchangeRateStr = new StringBuilder("Exchange Rate: ");
            for (int rate : exchangeRate) {
                exchangeRateStr.append(rate).append(", ");
            }
            if (exchangeRateStr.length() > 0) {
                exchangeRateStr.setLength(exchangeRateStr.length() - 2); // 去掉最后一个逗号和空格
            }
            tl.add(new TextComponentString(exchangeRateStr.toString()));

            // 添加 modelHeat 信息
            tl.add(new TextComponentString("Model Heat: " + modelHeat));
        });
    }
    @Override
    protected ModularUI.Builder createUITemplate(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 380, 240);

        builder.widget((new AdvancedTextWidget(4, 4, this::addInputDisplay, 16777215)).setMaxWidthLimit(380).setClickHandler(this::handleDisplayClick));

        builder.widget(new ClickButtonWidget(175, 160, 20, 20, "+1", data ->
        {
            if (select + 1 < 8)
                select += 1;
        }));
        builder.widget(new ClickButtonWidget(175, 180, 20, 20, "-1", data ->
        {
            if (select > 0)
                select -= 1;
        }));

        builder.widget(new SlotWidget(containerInventory, 0, 140, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.widget(new SlotWidget(containerInventory, 1, 170, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.widget(new SlotWidget(containerInventory, 2, 200, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.widget(new SlotWidget(containerInventory, 3, 230, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.widget(new SlotWidget(containerInventory, 4, 260, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.widget(new SlotWidget(containerInventory, 5, 290, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.widget(new SlotWidget(containerInventory, 6, 320, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.widget(new SlotWidget(containerInventory, 7, 350, 140, true, true)
                .setBackgroundTexture(GuiTextures.SLOT)
                .setTooltipText("换热原件插槽"));

        builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 205, 160);
        return builder;
    }


    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("ISO")
                .where('S', this.selfPredicate())
                .where('I', abilities(MultiblockAbility.IMPORT_FLUIDS).setExactLimit(1))
                .where('O', abilities(MultiblockAbility.EXPORT_FLUIDS).setExactLimit(1))
                .build();
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityHeatHatchExchange(metaTileEntityId);
    }


    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }

    @Override
    public boolean hasMaintenanceMechanics() {
        return false;
    }

    @Override
    public boolean hasMufflerMechanics() {
        return false;
    }

}
