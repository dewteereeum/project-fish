package net.dewteereeum.aquaticaspirations.block.entity.custom;

import net.dewteereeum.aquaticaspirations.block.ModBlockProperties;
import net.dewteereeum.aquaticaspirations.block.custom.Fishtank;
import net.dewteereeum.aquaticaspirations.block.entity.ModBlockEntities;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.dewteereeum.aquaticaspirations.item.custom.FunctionalFishItem;
import net.dewteereeum.aquaticaspirations.recipe.FishtankRecipe;
import net.dewteereeum.aquaticaspirations.recipe.FishtankRecipeInput;
import net.dewteereeum.aquaticaspirations.recipe.ModRecipes;
import net.dewteereeum.aquaticaspirations.screen.custom.FishtankMenu;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

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
        public int getSlotLimit(int slot) {
            return switch(slot){
                case 0, 1, 2 -> 1;

                default -> super.getSlotLimit(slot);

            };


        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return switch(slot){
                case 0 -> stack.is(ModTags.Items.FUNCTIONAL_FISH);
                case 1 -> stack.is(ModTags.Items.SUBSTRATE);
                case 2 -> true;
                case 3 -> false;
                //case 4 -> false;
                //case 5 -> false;
                default -> super.isItemValid(slot, stack);


            };

        }
    };


    /*
    private void dyingFish(){
        if (fishOutOfWater() && dyingFishCounter == 0){
            this.getLevel().playSound(null, this.getBlockPos(), SoundEvents.TROPICAL_FISH_FLOP, SoundSource.BLOCKS, 1f, 1f);
            System.out.println("A fish is dying. Time: " + this.dyingFishCounter);
            dyingFishCounter = dyingFishCounter +1;
            setChanged(level, this.getBlockPos(), this.getBlockState());

            if (this.dyingFishCounter >= 200){
                this.dyingFishCounter = 0;
            }
        }
        else this.dyingFishCounter = 0;


    }

     */



    public IItemHandler getItemHandler(Direction direction){
        return this.itemHandler;
    }

    private final FluidTank FLUID_TANK = createFluidTank();
    private FluidTank createFluidTank(){
        return new FluidTank(1000){
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }
    public int substrateAdjustment = (this.itemHandler.getStackInSlot(1).isEmpty()) ? 0 : 4;

    public FluidStack getFluid(){
        return FLUID_TANK.getFluid();
    }

    public IFluidHandler getTank(@Nullable Direction direction) {
        return FLUID_TANK;
    }



    private static final int FISH_SLOT = 0;
    //private static final int FLUID_SLOT = 1;
    private static final int SUBSTRATE_SLOT = 1;
    private static final int ACCESSORY_SLOT = 2;
    private static final int OUTPUT_SLOT1 = 3;
    //private static final int OUTPUT_SLOT2 = 4;
    //private static final int OUTPUT_SLOT3 = 5;
    public int outputSlots = 1;

    private final ContainerData data;
    private int progress = 0;
    private final int DEFAULT_MAX_PROGRESS = 80;
    private int maxProgress = 80;

    private int FishTier;
    private int SubTier;

    private ModBlockProperties.ContainedFluid lastFluidState = ModBlockProperties.ContainedFluid.EMPTY;


    private boolean hasFilterBlock = false;

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
        pTag = FLUID_TANK.writeToNBT(pRegistries, pTag);
        pTag.putInt("fishtank.progress", progress);
        pTag.putInt("fishtank.max_progress", maxProgress);
        pTag.putInt("fishtank.dying_fish_counter", dyingFishCounter);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override

    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        FLUID_TANK.readFromNBT(pRegistries, pTag);
        progress = pTag.getInt("fishtank.progress");
        maxProgress = pTag.getInt("fishtank.max_progress");
        dyingFishCounter = pTag.getInt("fishtank.dying_fish_counter");
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
        return Component.translatable("blockentity.aquaticaspirations.fishtank");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FishtankMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void tick(Level level, BlockPos pPos, BlockState pState){
        if(fishOutOfWater()){
            if(dyingFishCounter == 0){
                dyingFish();
            }
            deathCounterIncrease();
            setChanged(level, pPos, pState);
            if (deathCounterElapsed()){
                resetDyingFishCounter();
            }
        } else {
            resetDyingFishCounter();
        }

        //fluidChangedCheck(level, pPos, pState);
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
        progress = progress + 1 ;
    }

    int availableSlot;
    private boolean OutputIsEmptyOrReceivable() {
        for (int i = OUTPUT_SLOT1; i <= OUTPUT_SLOT1 + outputSlots -1; i++) {
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
        if(recipe.isEmpty() || FLUID_TANK.isEmpty()) {
            return false;
        }

        ItemStack output = recipe.get().value().getResultItem(null);

        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    boolean fishIs(Item fish){
        return itemHandler.getStackInSlot(FISH_SLOT).is(fish);
    }

    boolean fishIsAlive(Item fish){
        return (!(fishIs(ModItems.SKELETAL_FISH.get()) || fishIs(ModItems.UNDEAD_FISH.get())));
    }

    boolean fishOutOfWater(){
        if (!itemHandler.getStackInSlot(FISH_SLOT).isEmpty() && FLUID_TANK.isEmpty()){
            if (!fishIsAlive(itemHandler.getStackInSlot(FISH_SLOT).getItem())){
                return false;
            }
            else return true;
        }
        else return false;
    }
    private int dyingFishCounter = 0;

    private void dyingFish(){

        Random rand = new Random();
        float pitch = rand.nextFloat(0.7f, 1.0f);
        this.getLevel().playSound(null, this.getBlockPos(),
                SoundEvents.COD_FLOP, SoundSource.BLOCKS, 1f, pitch);
        System.out.println("A fish is dying. Timer: " + dyingFishCounter);
    }

    private void deathCounterIncrease(){
        dyingFishCounter++;
    }

    private boolean deathCounterElapsed(){
        return this.dyingFishCounter >= 10;
    }

    private void resetDyingFishCounter(){
        this.dyingFishCounter = 0;
    }





    private void fluidChangedCheck(Level level, BlockPos blockPos, BlockState blockState){
        Fluid polledStateFluid = blockState.getValue(ModBlockProperties.CONTAINED_FLUID).getFluid();

        System.out.println("succesful poll: " + polledStateFluid.toString());
        if(polledStateFluid.isSame(this.lastFluidState.getFluid())) return;

        for(ModBlockProperties.ContainedFluid var : ModBlockProperties.ContainedFluid.values()){
            if (var.getFluid().isSame(this.getFluid().getFluid())){
                level.setBlockAndUpdate(blockPos, blockState.setValue(Fishtank.FLUID, var));
                this.lastFluidState = var;
                System.out.println("Fluid state changed to: " + var.toString());
                return;
            }
        }

        level.setBlockAndUpdate(blockPos, blockState.setValue(Fishtank.FLUID, ModBlockProperties.ContainedFluid.EMPTY));
        System.out.println("No matching fluid found; Defaulted to EMPTY");

    }


    /*
    private void transferFluidToTank() {
        FluidActionResult result = FluidUtil.tryEmptyContainer(itemHandler.getStackInSlot(0), this.FLUID_TANK, Integer.MAX_VALUE, null, true);
        if(result.result != ItemStack.EMPTY) {
            itemHandler.setStackInSlot(1, result.result);
        }
    }
    private void transferFluidFromTankToHandler() {
        FluidActionResult result = FluidUtil.tryFillContainer(itemHandler.getStackInSlot(1), this.FLUID_TANK, Integer.MAX_VALUE, null, true);
        if(result.result != ItemStack.EMPTY) {
            itemHandler.setStackInSlot(1, result.result);
        }
    }

    private boolean hasFluidStackInFirstSlot() {
        return !itemHandler.getStackInSlot(1).isEmpty()
                && itemHandler.getStackInSlot(1).getCapability(Capabilities.FluidHandler.ITEM, null) != null
                && !itemHandler.getStackInSlot(1).getCapability(Capabilities.FluidHandler.ITEM, null).getFluidInTank(0).isEmpty();
    }

     */

    private Optional<RecipeHolder<FishtankRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.FISHTANK_TYPE.get(), new FishtankRecipeInput(itemHandler.getStackInSlot(FISH_SLOT)), level);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        for(int i = OUTPUT_SLOT1; i<= OUTPUT_SLOT1 + outputSlots -1; i++){
            if (itemHandler.getStackInSlot(i).isEmpty() || itemHandler.getStackInSlot(i).getItem() == output.getItem()) {
                return true;
            }
        }
        return false;
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        for (int i = OUTPUT_SLOT1; i <= OUTPUT_SLOT1 + outputSlots -1; i++) {
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
