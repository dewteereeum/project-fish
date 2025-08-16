package net.dewteereeum.aquaticaspirations.item.custom.accessory;

import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class EmptyTreasureChestItem extends Item implements IFishTankAccessory, BlockLinkable{
    public EmptyTreasureChestItem(Properties properties) {
        super(properties);
    }

    @Override
    public void accessoryFunction(FishtankBlockEntity fishtankBlockEntity) {

       // assert fishtankBlockEntity.level != null;

        for(Integer i : fishtankBlockEntity.getOutputSlots()){
            ItemStack outputStack = fishtankBlockEntity.itemHandler.getStackInSlot(i);
            if(!outputStack.isEmpty()) {
                IItemHandler handler = fishtankBlockEntity.getAttachedInventory();
                if(handler != null){
                    ItemStack leftover = ItemHandlerHelper.insertItemStacked(handler, outputStack, false);

                    if (leftover.isEmpty()) {
                        fishtankBlockEntity.itemHandler.setStackInSlot(i, ItemStack.EMPTY);

                    } else {
                        fishtankBlockEntity.itemHandler.setStackInSlot(i, leftover);
                        break;
                    }
                }
            }

        }


    }

    @Override
    public BlockPos getLinkedBlock(FishtankBlockEntity blockEntity) {
        return blockEntity.getBlockPos().below();
    }

    @Override
    public Direction getDirection(FishtankBlockEntity fishtankBlockEntity) {
        return Direction.UP;
    }


}
