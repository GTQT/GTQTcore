package keqing.gtqtcore.common.metatileentities.multi.multiblock;

import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.pattern.BlockPattern;
import gregtech.api.pattern.FactoryBlockPattern;
import gregtech.api.pattern.TraceabilityPredicate;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.util.GTTransferUtils;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.Textures;
import gregtech.common.blocks.BlockMetalCasing;
import gregtech.common.blocks.MetaBlocks;
import gregtech.common.items.MetaItems;
import keqing.gtqtcore.client.textures.GTQTTextures;
import keqing.gtqtcore.common.items.GTQTMetaItems;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityBaseWithControl;
import keqing.gtqtcore.common.metatileentities.multi.multiblock.standard.MetaTileEntityMicrowaveEnergyReceiverControl;
import net.minecraft.block.state.IBlockState;
import keqing.gtqtcore.api.utils.GTQTOreHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class MetaTileEntitySeismicDetector extends MetaTileEntityBaseWithControl {

    int kind;
    public MetaTileEntitySeismicDetector(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
    }

    @Override
    protected void updateFormedValid() {
        if(checkTNT(true)&&checkCard(true))
        {
            kind=getKind()+this.getWorld().provider.getDimension()*4;
            checkTNT(false);
            checkCard(false);
            GTTransferUtils.insertItem(this.outputInventory, setCard(), false);
            GTTransferUtils.insertItem(this.outputInventory, GTQTOreHelper.setOre(kind), false);
        }
    }
    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, player, tooltip, advanced);
        tooltip.add(I18n.format("在输入总线内放置两个火药以及一块地质存储模块即可获取当前维度当前区块的矿床信息"));
    }
    @Override
    protected void addDisplayText(List<ITextComponent> textList) {
        super.addDisplayText(textList);
        textList.add(new TextComponentTranslation(GTQTOreHelper.getInfo(kind)));
    }
    public int getKind(){
        int random=0;
        if((this.getPos().getX()/64)%2==0)random+=2;
        if((this.getPos().getY()/64)%2==0)random+=2;
        else random+=1;

        return random+this.getWorld().provider.getDimension()*4;
    }
    public ItemStack setCard() {
        ItemStack card = new ItemStack(GTQTMetaItems.POS_ORE_CARD.getMetaItem(), 1, 180);
        NBTTagCompound nodeTagCompound = new NBTTagCompound();
        nodeTagCompound.setInteger("Kind", kind);
        card.setTagCompound(nodeTagCompound);
        return card;
    }
    public boolean checkCard(boolean sim) {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);

            if (item.getItem() == GTQTMetaItems.GTQT_META_ITEM && item.getMetadata() == GTQTMetaItems.POS_ORE_CARD.getMetaValue()) {
                this.getInputInventory().extractItem(i, 1, sim);
                return true;
            }
        }
        return false;
    }
    public boolean checkTNT(boolean sim) {
        var slots = this.getInputInventory().getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack item = this.getInputInventory().getStackInSlot(i);
            if (item.getMetadata() == MetaItems.DYNAMITE.getMetaValue()) {
                this.getInputInventory().extractItem(i, 2, sim);
                return true;
            }
        }
        return false;
    }

    protected BlockPattern createStructurePattern() {
        return FactoryBlockPattern.start()
                .aisle("XXX", "#F#", "#F#", "#F#", "###", "###", "###")
                .aisle("XXX", "FCF", "FCF", "FCF", "#F#", "#F#", "#F#")
                .aisle("XSX", "#F#", "#F#", "#F#", "###", "###", "###")
                .where('S', this.selfPredicate())
                .where('X', states(this.getCasingState()).setMinGlobalLimited(5)
                        .or(abilities(MultiblockAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.MAINTENANCE_HATCH).setMinGlobalLimited(1).setMaxGlobalLimited(1))
                        .or(abilities(MultiblockAbility.EXPORT_ITEMS).setMaxGlobalLimited(1).setMaxGlobalLimited(1)))
                .where('C', states(this.getCasingState()))
                .where('F', this.getFramePredicate()).where('#', any()).build();
    }
    private IBlockState getCasingState() {
            return MetaBlocks.METAL_CASING.getState(BlockMetalCasing.MetalCasingType.STEEL_SOLID);
    }

    private  TraceabilityPredicate getFramePredicate() {
            return frames(Materials.Steel);
    }

    @Override
    public ICubeRenderer getBaseTexture(IMultiblockPart iMultiblockPart) {
        return Textures.SOLID_STEEL_CASING;
    }

    @Override
    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity iGregTechTileEntity) {
        return new MetaTileEntitySeismicDetector(metaTileEntityId);
    }

    @Override
    public List<ITextComponent> getDataInfo() {
        return Collections.emptyList();
    }
}
