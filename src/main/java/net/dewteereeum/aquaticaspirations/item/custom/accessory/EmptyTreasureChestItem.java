package net.dewteereeum.aquaticaspirations.item.custom.accessory;

import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.List;

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
    public float[] getTranslationVector() {
        return new float[]{3 / 16f, 0, 1 / 16f};
    }

    @Override
    public int[] getRotationVector() {
        return new int[]{0, -6, 0};
    }


    @Override
    public BlockPos getLinkedBlock(FishtankBlockEntity blockEntity) {
        return blockEntity.getBlockPos().below();
    }

    @Override
    public Direction getDirection(FishtankBlockEntity fishtankBlockEntity) {
        return Direction.UP;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.aquaticaspirations.tooltip.empty_treasure_chest_info"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.aquaticaspirations.tooltip.hold_shift_for_info"));
        }
    }
}
