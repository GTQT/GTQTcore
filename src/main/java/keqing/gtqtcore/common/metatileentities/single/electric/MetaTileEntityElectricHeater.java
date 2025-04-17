package keqing.gtqtcore.common.metatileentities.single.electric;

import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.AdvancedTextWidget;
import gregtech.api.gui.widgets.DynamicLabelWidget;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.TieredMetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.util.GTUtility;
import gregtech.client.utils.TooltipHelper;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.metatileentities.multi.multiblockpart.MetaTileEntityHeatHatch;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import java.util.List;

import static gregtech.api.GTValues.VA;

public class MetaTileEntityElectricHeater extends TieredMetaTileEntity {
    int tier;

    public MetaTileEntityElectricHeater(ResourceLocation metaTileEntityId, int tier) {
        super(metaTileEntityId, tier);
        this.tier = tier;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntityElectricHeater(metaTileEntityId, getTier());
    }

    private BlockPos getBackPos(BlockPos pos, EnumFacing facing) {
        return pos.offset(facing.getOpposite());
    }

    @Override
    public void update() {
        super.update();
        if (!getWorld().isRemote) {
            BlockPos backPos = getBackPos(this.getPos(), this.getFrontFacing());
            MetaTileEntity mte = GTUtility.getMetaTileEntity(this.getWorld(), backPos);
            if (mte instanceof MetaTileEntityHeatHatch) {
                if (energyContainer.getEnergyStored() > VA[tier]) {
                    energyContainer.changeEnergy(-VA[tier]);

                    ((MetaTileEntityHeatHatch) mte).balanceHeat(500 * tier, 1);
                }
            }
        }
    }

    @Override
    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 129)
                .bindPlayerInventory(entityPlayer.inventory, 46)
                .widget(new DynamicLabelWidget(7, 7, () -> "电加热器"))
                .widget((new AdvancedTextWidget(7, 17, this::addDisplayText, 2302755)).setMaxWidthLimit(181));
        return builder.build(this.getHolder(), entityPlayer);
    }

    protected void addDisplayText(List<ITextComponent> textList) {
        textList.add(new TextComponentString("接触热源温度： " + 500 * tier + " 接触率：1"));
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(TooltipHelper.RAINBOW_SLOW + I18n.format("烫！烫！烫！", new Object[0]));
        tooltip.add(I18n.format("配合需要 热源仓（Heat Hatch）的设备使用"));
        tooltip.add(I18n.format("需要将仓背面紧贴热源仓，可热源仓进行加热操作"));
        tooltip.add(I18n.format("加热器提供的热量为：500k*加热器等级"));
    }

    @Override
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        GTQTTextures.HEAT.renderSided(getFrontFacing(), renderState, translation, pipeline);
    }

    @Override
    protected boolean isEnergyEmitter() {
        return false;
    }
}
