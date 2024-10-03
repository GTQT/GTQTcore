package keqing.gtqtcore.common.metatileentities.storage;

import codechicken.lib.colour.ColourRGBA;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.IGuiTexture;
import gregtech.api.gui.widgets.BlockableSlotWidget;
import gregtech.api.items.itemhandlers.GTItemStackHandler;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.recipes.ModHandler;
import gregtech.api.unification.material.Material;
import gregtech.api.util.GTUtility;
import gregtech.client.renderer.texture.Textures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import static gregtech.api.unification.material.Materials.Steel;

public class MetaTileEntityRubbishBin extends MetaTileEntity{
    private final Material material;
    private final int inventorySize;
    protected ItemStackHandler inventory;

    public MetaTileEntityRubbishBin(ResourceLocation metaTileEntityId) {
        super(metaTileEntityId);
        this.material = Steel;
        this.inventorySize = 5;
        this.initializeInventory();
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityRubbishBin(this.metaTileEntityId);
    }

    public boolean hasFrontFacing() {
        return false;
    }

    protected void initializeInventory() {
        super.initializeInventory();
        this.inventory = new GTItemStackHandler(this, this.inventorySize);
        this.itemInventory = this.inventory;
    }

    public void update() {
        super.update();
        if (!this.getWorld().isRemote&& !this.inventory.getStackInSlot(0).isEmpty()) {
            this.inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
    }
    @SideOnly(Side.CLIENT)
    public Pair<TextureAtlasSprite, Integer> getParticleTexture() {

        int color = ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.material.getMaterialRGB()), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering()));color = GTUtility.convertOpaqueRGBA_CLtoRGB(color);return Pair.of(Textures.METAL_CRATE.getParticleTexture(), color);

    }
    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {

        int baseColor = ColourRGBA.multiply(GTUtility.convertRGBtoOpaqueRGBA_CL(this.material.getMaterialRGB()), GTUtility.convertRGBtoOpaqueRGBA_CL(this.getPaintingColorForRendering()));Textures.METAL_CRATE.render(renderState, translation, baseColor, pipeline);
    }
    public int getDefaultPaintingColor() {
        return 16777215;
    }

    protected ModularUI createUI(EntityPlayer entityPlayer) {
        ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 130).label(10, 5, this.getMetaFullName());
        builder.slot(this.inventory, 0, 43 , 18 , GuiTextures.SLOT);
        builder.slot(this.inventory, 1, 61 , 18 , GuiTextures.SLOT);
        builder.slot(this.inventory, 2, 79 , 18 , GuiTextures.SLOT);
        builder.slot(this.inventory, 3, 97 , 18 , GuiTextures.SLOT);
        builder.slot(this.inventory, 4, 115 , 18 , GuiTextures.SLOT);
        return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 48).build(this.getHolder(), entityPlayer);
    }
    public int getItemStackLimit(ItemStack stack) {
        return super.getItemStackLimit(stack);
    }

    protected boolean shouldSerializeInventories() {
        return false;
    }


}
