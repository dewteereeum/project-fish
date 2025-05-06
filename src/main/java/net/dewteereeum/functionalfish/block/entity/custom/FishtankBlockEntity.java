package net.dewteereeum.functionalfish.block.entity.custom;

import net.dewteereeum.functionalfish.block.ModBlocks;
import net.dewteereeum.functionalfish.block.entity.ModBlockEntities;
import net.dewteereeum.functionalfish.recipe.FishtankRecipe;
import net.dewteereeum.functionalfish.recipe.FishtankRecipeInput;
import net.dewteereeum.functionalfish.recipe.ModRecipes;
import net.dewteereeum.functionalfish.screen.custom.FishtankMenu;
import net.dewteereeum.functionalfish.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static java.lang.Math.abs;
import static java.lang.Math.sin;

public class FishtankBlockEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler itemHandler = new ItemStackHandler(7) {

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch(slot){
                case 0 -> stack.is(ModTags.Items.FUNCTIONAL_FISH);
                case 1 -> stack.is(Items.WATER_BUCKET);
                case 2 -> stack.is(ModTags.Items.SUBSTRATE);
                case 3 -> true;
                case 4 -> false;
                case 5 -> false;
                case 6 -> false;
                default -> super.isItemValid(slot, stack);


            };

        }
    };

    private static final int FISH_SLOT = 0;
    private static final int FLUID_SLOT = 1;
    private static final int SUBSTRATE_SLOT = 2;
    private static final int ACCESSORY_SLOT = 3;
    private static final int OUTPUT_SLOT1 = 4;
    private static final int OUTPUT_SLOT2 = 5;
    private static final int OUTPUT_SLOT3 = 6;

    private final ContainerData data;
    private int progress = 0;
    private final int DEFAULT_MAX_PROGRESS = 80;
    private int maxProgress = 80;

    private int FishTier;
    private int SubTier;

    private int progressModifier(){
        Item substrate = itemHandler.getStackInSlot(2).getItem();

        if (Tiers.containsKey(substrate)){
            return Tiers.get(substrate);

        } else return 1;

    }

    private static Map<Item, Integer> Tiers = Map.of(
            Items.SAND, 1,
            Items.GRAVEL, 1,
            ModBlocks.IMPROVED_SUBSTRATE_BLOCK.asItem(), 2);




    private float rotation;

    public FishtankBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FISHTANK_BE.get(), pos, blockState);

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> FishtankBlockEntity.this.progress;
                    case 1 -> FishtankBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0: FishtankBlockEntity.this.progress = pValue;
                    case 1: FishtankBlockEntity.this.maxProgress = pValue;
                }

            }

            @Override
            public int getCount() {

                return 2;
            }
        };
    }

    /*
    public void clearContents(){
        itemHandler.setStackInSlot(0, ItemStack.EMPTY);
    }

     */



    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }
    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("inventory", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("fishtank.progress", progress);
        pTag.putInt("fishtank.max_progress", maxProgress);
    }

    @Override

    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("fishtank.progress");
        maxProgress = pTag.getInt("fishtank.max_progress");
    }

    public float getRenderingRotation(){
        rotation += 0.01f;
        if(rotation >=360) {
            rotation = 0;
        }
        float oscillator = (float) (3*sin(rotation));

        return oscillator;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("blockentity.functionalfish.fishtank");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FishtankMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void tick(Level level, BlockPos pPos, BlockState pState){
        if(hasRecipe() && OutputIsEmptyOrReceivable()){
            increaseCraftingProgress();
            setChanged(level, pPos, pState);
            if(CraftingFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }

    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = maxProgress;
    }

    private void craftItem() {
        Optional<RecipeHolder<FishtankRecipe>> recipe = getCurrentRecipe();

        ItemStack output = recipe.get().value().output();
        itemHandler.setStackInSlot(availableSlot, new ItemStack(output.getItem(), itemHandler.getStackInSlot(availableSlot).getCount() + output.getCount()));
    }

    private boolean CraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress = progress+
                progressModifier();
    }

    int availableSlot;
    private boolean OutputIsEmptyOrReceivable() {
        for (int i = OUTPUT_SLOT1; i <= OUTPUT_SLOT3; i++) {
            if (this.itemHandler.getStackInSlot(i).isEmpty() ||
                    this.itemHandler.getStackInSlot(i).getCount() < this.itemHandler.getStackInSlot(i).getMaxStackSize()){
                availableSlot = i;
                return true;
            }
        }


        return false;

    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<FishtankRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().getResultItem(null);

        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private Optional<RecipeHolder<FishtankRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.FISHTANK_TYPE.get(), new FishtankRecipeInput(itemHandler.getStackInSlot(FISH_SLOT)), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        for(int i = OUTPUT_SLOT1; i<=OUTPUT_SLOT3; i++){
            if (itemHandler.getStackInSlot(i).isEmpty() || itemHandler.getStackInSlot(i).getItem() == output.getItem()) {
                return true;
            }
        }
        return false;
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        for (int i = OUTPUT_SLOT1; i <= OUTPUT_SLOT3; i++) {
            int maxCount = itemHandler.getStackInSlot(i).isEmpty() ? 64 : itemHandler.getStackInSlot(i).getMaxStackSize();
            int currentCount = itemHandler.getStackInSlot(i).getCount();
            if (maxCount >= currentCount + count){
                return true;
            }

        }


        return false;
    }



    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }


}
