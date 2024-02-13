package keqing.gtqtcore.common.metatileentities.multi.multiblockpart;


import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.pipeline.IVertexOperation;
import codechicken.lib.vec.Matrix4;
import gregtech.api.capability.IDataAccessHatch;
import gregtech.api.capability.impl.NotifiableItemStackHandler;
import gregtech.api.gui.GuiTextures;
import gregtech.api.gui.ModularUI;
import gregtech.api.gui.resources.IGuiTexture;
import gregtech.api.gui.widgets.SlotWidget;
import gregtech.api.metatileentity.IDataInfoProvider;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.metatileentity.interfaces.IGregTechTileEntity;
import gregtech.api.metatileentity.multiblock.IMultiblockAbilityPart;
import gregtech.api.metatileentity.multiblock.MultiblockAbility;
import gregtech.api.metatileentity.multiblock.MultiblockControllerBase;
import gregtech.api.recipes.Recipe;
import gregtech.api.recipes.RecipeMaps;
import gregtech.api.recipes.machines.IResearchRecipeMap;
import gregtech.api.util.AssemblyLineManager;
import gregtech.api.util.ItemStackHashStrategy;
import gregtech.api.util.LocalizationUtils;
import gregtech.client.renderer.texture.Textures;
import gregtech.client.utils.TooltipHelper;
import gregtech.common.ConfigHolder;
import gregtech.common.metatileentities.multi.electric.MetaTileEntityDataBank;
import gregtech.common.metatileentities.multi.multiblockpart.MetaTileEntityMultiblockNotifiablePart;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandlerModifiable;

public class MetaTileEntityDataAccessHatch extends MetaTileEntityMultiblockNotifiablePart implements IMultiblockAbilityPart<IDataAccessHatch>, IDataAccessHatch, IDataInfoProvider {
    private final Set<Recipe> recipes;
    private final boolean isCreative;

    public MetaTileEntityDataAccessHatch(ResourceLocation metaTileEntityId, int tier, boolean isCreative) {
        super(metaTileEntityId, tier, false);
        this.isCreative = isCreative;
        this.recipes = (Set)(isCreative ? Collections.emptySet() : new ObjectOpenHashSet());
        this.rebuildData(this.getController() instanceof MetaTileEntityDataBank);
    }

    public MetaTileEntity createMetaTileEntity(IGregTechTileEntity tileEntity) {
        return new MetaTileEntityDataAccessHatch(this.metaTileEntityId, this.getTier(), this.isCreative());
    }

    protected IItemHandlerModifiable createImportItemHandler() {
        return (IItemHandlerModifiable)(this.isCreative ? super.createImportItemHandler() : new NotifiableItemStackHandler(this, this.getInventorySize(), this.getController(), false) {
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                MetaTileEntityDataAccessHatch.this.rebuildData(MetaTileEntityDataAccessHatch.this.getController() instanceof MetaTileEntityDataBank);
            }

            public  ItemStack insertItem(int slot,  ItemStack stack, boolean simulate) {
                MultiblockControllerBase controller = MetaTileEntityDataAccessHatch.this.getController();
                boolean isDataBank = controller instanceof MetaTileEntityDataBank;
                return AssemblyLineManager.isStackDataItem(stack, isDataBank) && AssemblyLineManager.hasResearchTag(stack) ? super.insertItem(slot, stack, simulate) : stack;
            }
        });
    }

    public void renderMetaTileEntity(CCRenderState renderState, Matrix4 translation, IVertexOperation[] pipeline) {
        super.renderMetaTileEntity(renderState, translation, pipeline);
        if (this.shouldRenderOverlay()) {
            if (this.isCreative) {
                Textures.CREATIVE_DATA_ACCESS_HATCH.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
            } else {
                Textures.DATA_ACCESS_HATCH.renderSided(this.getFrontFacing(), renderState, translation, pipeline);
            }
        }

    }

    protected ModularUI createUI(EntityPlayer entityPlayer) {
        if (this.isCreative) {
            return null;
        } else {
            int rowSize = (int)Math.sqrt((double)this.getInventorySize());
            ModularUI.Builder builder = ModularUI.builder(GuiTextures.BACKGROUND, 176, 18 + 18 * rowSize + 94).label(6, 6, this.getMetaFullName());

            for(int y = 0; y < rowSize; ++y) {
                for(int x = 0; x < rowSize; ++x) {
                    int index = y * rowSize + x;
                    builder.widget((new SlotWidget(this.isExportHatch ? this.exportItems : this.importItems, index, 88 - rowSize * 9 + x * 18, 18 + y * 18, true, !this.isExportHatch)).setBackgroundTexture(new IGuiTexture[]{GuiTextures.SLOT}));
                }
            }

            return builder.bindPlayerInventory(entityPlayer.inventory, GuiTextures.SLOT, 7, 18 + 18 * rowSize + 12).build(this.getHolder(), entityPlayer);
        }
    }

    protected boolean openGUIOnRightClick() {
        return !this.isCreative;
    }

    protected int getInventorySize() {
        switch (this.getTier())
        {
            case (2) -> {
                return 4;
            }
            case (4) -> {
                return 9;
            }
            case (6) -> {
                return 16;
            }
            case (8) -> {
                return 25;
            }
            default -> {return 1;}
        }
    }

    private void rebuildData(boolean isDataBank) {
        if (!this.isCreative && this.getWorld() != null && !this.getWorld().isRemote) {
            this.recipes.clear();

            for(int i = 0; i < this.importItems.getSlots(); ++i) {
                ItemStack stack = this.importItems.getStackInSlot(i);
                String researchId = AssemblyLineManager.readResearchId(stack);
                boolean isValid = AssemblyLineManager.isStackDataItem(stack, isDataBank);
                if (researchId != null && isValid) {
                    Collection<Recipe> collection = ((IResearchRecipeMap)RecipeMaps.ASSEMBLY_LINE_RECIPES).getDataStickEntry(researchId);
                    if (collection != null) {
                        this.recipes.addAll(collection);
                    }
                }
            }

        }
    }

    public boolean isRecipeAvailable( Recipe recipe,  Collection<IDataAccessHatch> seen) {
        seen.add(this);
        return this.recipes.contains(recipe);
    }

    public boolean isCreative() {
        return this.isCreative;
    }

    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> subItems) {
        if (ConfigHolder.machines.enableResearch) {
            super.getSubItems(creativeTab, subItems);
        }

    }

    public void addInformation(ItemStack stack,  World world,  List<String> tooltip, boolean advanced) {
        super.addInformation(stack, world, tooltip, advanced);
        tooltip.add(I18n.format("gregtech.machine.data_access_hatch.tooltip.1", new Object[0]));
        if (this.isCreative) {
            tooltip.add(I18n.format("gregtech.creative_tooltip.1", new Object[0]) + TooltipHelper.RAINBOW + I18n.format("gregtech.creative_tooltip.2", new Object[0]) + I18n.format("gregtech.creative_tooltip.3", new Object[0]));
        } else {
            tooltip.add(I18n.format("gregtech.machine.data_access_hatch.tooltip.2", new Object[]{this.getInventorySize()}));
        }

    }

    public  List<ITextComponent> getDataInfo() {
        if (this.recipes.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<ITextComponent> list = new ArrayList();
            list.add(new TextComponentTranslation("behavior.data_item.assemblyline.title", new Object[0]));
            list.add(new TextComponentString(""));
            Collection<ItemStack> itemsAdded = new ObjectOpenCustomHashSet(ItemStackHashStrategy.comparingAll());
            Iterator var3 = this.recipes.iterator();

            while(var3.hasNext()) {
                Recipe recipe = (Recipe)var3.next();
                ItemStack stack = (ItemStack)recipe.getOutputs().get(0);
                if (!itemsAdded.contains(stack)) {
                    itemsAdded.add(stack);
                    list.add(new TextComponentTranslation("behavior.data_item.assemblyline.data", new Object[]{LocalizationUtils.format(stack.getTranslationKey(), new Object[0])}));
                }
            }

            return list;
        }
    }

    public boolean canPartShare() {
        return false;
    }

    public MultiblockAbility<IDataAccessHatch> getAbility() {
        return MultiblockAbility.DATA_ACCESS_HATCH;
    }

    public void registerAbilities(List<IDataAccessHatch> abilityList) {
        abilityList.add(this);
    }

    public void addToMultiBlock(MultiblockControllerBase controllerBase) {
        this.rebuildData(controllerBase instanceof MetaTileEntityDataBank);
        super.addToMultiBlock(controllerBase);
    }
}
