package net.dewteereeum.functionalfish.block.custom;

import net.dewteereeum.functionalfish.component.ModDataComponentTypes;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class SubstrateBlock extends Block {
    public SubstrateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()){
           tooltipComponents.add(Component.literal("you forgot to add substrate tooltips"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.functionalfish.tooltip.substrate_tier"));
            tooltipComponents.add(Component.translatable("tooltip.functionalfish.tooltip.substrate_type"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
