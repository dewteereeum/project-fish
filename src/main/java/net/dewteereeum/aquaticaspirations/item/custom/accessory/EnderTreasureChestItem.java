package net.dewteereeum.aquaticaspirations.item.custom.accessory;

import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.dewteereeum.aquaticaspirations.component.EnderTreasureChestData;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.List;

public class EnderTreasureChestItem extends Item implements IFishTankAccessory, BlockLinkable {
    protected BlockCapabilityCache<IItemHandler, Direction> linkedInventory;


    public EnderTreasureChestItem(Properties properties){
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(context.getPlayer() != null) {
            if (!context.getPlayer().isShiftKeyDown()) {
                return super.useOn(context);
            }
        }
        if(context.getItemInHand().get(ModDataComponentTypes.LINK_BLOCK.get()) != null){
            context.getItemInHand().set(ModDataComponentTypes.LINK_BLOCK.get(), null);
        }
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockState block = level.getBlockState(pos);
        boolean hasItemHandler = false;

        if(block.hasBlockEntity()){
            hasItemHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, direction) != null;
        }
        if(hasItemHandler){
            context.getItemInHand().set(ModDataComponentTypes.LINK_BLOCK.get(), new EnderTreasureChestData(block, pos, direction));
//            linkedInventory = BlockCapabilityCache.create(
//                    Capabilities.ItemHandler.BLOCK, // capability to cache
//                    (ServerLevel) level, // level
//                    pos, // target position
//                    direction // context (The side of the block we're trying to pull/push from?)
//            );
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(tooltipFlag.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.aquaticaspirations.tooltip.ender_treasure_chest_info"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.aquaticaspirations.tooltip.ender_treasure_chest_preview"));
        }
        if(stack.get(ModDataComponentTypes.LINK_BLOCK.get()) != null){
            tooltipComponents.add(Component.literal(stack.get(ModDataComponentTypes.LINK_BLOCK.get()).getLinkString()).withStyle(ChatFormatting.AQUA));
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.get(ModDataComponentTypes.LINK_BLOCK.get()) != null;
    }

    public BlockPos getLinkedBlock(FishtankBlockEntity fishtankBlockEntity) {
        ItemStack stack = fishtankBlockEntity.itemHandler.getStackInSlot(2);
        return stack.get(ModDataComponentTypes.LINK_BLOCK.get()).position();
    }
    public Direction getDirection(FishtankBlockEntity fishtankBlockEntity){
        ItemStack stack = fishtankBlockEntity.itemHandler.getStackInSlot(2);
        return stack.get(ModDataComponentTypes.LINK_BLOCK.get()).direction();
    }

    @Override
    public void accessoryFunction(FishtankBlockEntity fishtankBlockEntity) {
        var stack = fishtankBlockEntity.itemHandler.getStackInSlot(2);
        if(stack.isEmpty()) return;
        var link = stack.get(ModDataComponentTypes.LINK_BLOCK.get());
        if(link == null) return;

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
}
