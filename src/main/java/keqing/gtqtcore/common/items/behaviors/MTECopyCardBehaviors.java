package keqing.gtqtcore.common.items.behaviors;

import gregtech.api.capability.IActiveOutputSide;
import gregtech.api.capability.IGhostSlotConfigurable;
import gregtech.api.capability.impl.GhostCircuitItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.widgets.*;
import gregtech.api.items.gui.ItemUIFactory;
import gregtech.api.items.gui.PlayerInventoryHolder;
import gregtech.api.items.metaitem.stats.IItemBehaviour;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.SimpleMachineMetaTileEntity;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.util.GTUtility;
import gregtech.common.entities.DynamiteEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Function;
import java.util.function.IntSupplier;

public class MTECopyCardBehaviors implements IItemBehaviour , ItemUIFactory {
    public MTECopyCardBehaviors() {}
    boolean isAutoOutputItems;
    boolean isAutoOutputFluids;
    boolean isAllowInputFromOutputSideItems;
    boolean isAllowInputFromOutputSideFluids;
    private EnumFacing frontFacing;
    private EnumFacing outputFacingItems;
    private EnumFacing outputFacingFluids;

    int circuit=0;
    public void addInformation(ItemStack stack, List<String> lines) {

    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (player.isSneaking()) {
            if (GTUtility.getMetaTileEntity(world, pos) instanceof MetaTileEntity) {

                MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(world, pos);

                frontFacing=mte.getFrontFacing();
                if (mte instanceof SimpleMachineMetaTileEntity) {
                    isAutoOutputItems=((SimpleMachineMetaTileEntity) mte).isAutoOutputItems();
                    isAutoOutputFluids=((SimpleMachineMetaTileEntity) mte).isAutoOutputFluids();

                    isAllowInputFromOutputSideItems=((SimpleMachineMetaTileEntity) mte).isAllowInputFromOutputSideItems();
                    isAllowInputFromOutputSideFluids=((SimpleMachineMetaTileEntity) mte).isAllowInputFromOutputSideFluids();

                    outputFacingItems=((SimpleMachineMetaTileEntity) mte).getOutputFacingItems();
                    outputFacingFluids=((SimpleMachineMetaTileEntity) mte).getOutputFacingFluids();


                    player.sendMessage(new TextComponentTranslation("复制"));
                }
            }
            else
            {
                if (!world.isRemote) {
                    PlayerInventoryHolder holder = new PlayerInventoryHolder(player, hand);
                    holder.openUI();
                }
            }
        } else {
                if (GTUtility.getMetaTileEntity(world, pos) instanceof MetaTileEntity) {

                    MetaTileEntity mte = (MetaTileEntity) GTUtility.getMetaTileEntity(world, pos);

                    if(frontFacing!=null)mte.setFrontFacing(frontFacing);
                    if (mte instanceof SimpleMachineMetaTileEntity) {
                        ((SimpleMachineMetaTileEntity) mte).setAutoOutputItems(isAutoOutputItems);
                        ((SimpleMachineMetaTileEntity) mte).setAutoOutputFluids(isAutoOutputFluids);

                        ((SimpleMachineMetaTileEntity) mte).setAllowInputFromOutputSideItems(isAllowInputFromOutputSideItems);
                        ((SimpleMachineMetaTileEntity) mte).setAllowInputFromOutputSideFluids(isAllowInputFromOutputSideFluids);

                        if(outputFacingItems!=null)((SimpleMachineMetaTileEntity) mte).setOutputFacingItems(outputFacingItems);
                        if(outputFacingFluids!=null)((SimpleMachineMetaTileEntity) mte).setOutputFacingFluids(outputFacingFluids);

                        ((SimpleMachineMetaTileEntity) mte).setGhostCircuitConfig(circuit);
                        player.sendMessage(new TextComponentTranslation("粘贴"));
                    }
                }
        }
        return EnumActionResult.SUCCESS;
    }

    public ModularUI createUI(PlayerInventoryHolder playerInventoryHolder, EntityPlayer entityPlayer) {
        return ModularUI.builder(GuiTextures.BACKGROUND, 176, 120)
                .image(10, 8, 156, 50, GuiTextures.DISPLAY)
                .dynamicLabel(15, 13, () -> I18n.format("%s", circuit), 0xFAF9F6)
                .widget(new ClickButtonWidget(10, 68, 77, 20, I18n.format("+1"), clickData -> addCircuit(1)))
                .widget(new ClickButtonWidget(90, 68, 77, 20, I18n.format("+5"), clickData -> addCircuit(5)))
                .widget(new ClickButtonWidget(10, 91, 77, 20, I18n.format("-1"), clickData -> addCircuit(-1)))
                .widget(new ClickButtonWidget(90, 91, 77, 20, I18n.format("-5"), clickData -> addCircuit(-5)))
                .build(playerInventoryHolder, entityPlayer);
    }
    public void addCircuit(int i)
    {
        if(i>0&&circuit+i>32) {
            circuit=32;
            return;
        }
        if(i<0&&circuit+i<0) {
            circuit=0;
            return;
        }

        circuit+=i;
    }
}
