package net.dewteereeum.aquaticaspirations.item.custom;


import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FunctionalFishItem extends Item {

    public FunctionalFishItem(Properties properties) {
        super(properties);
    }



    /*
    public int getType(ItemStack stack){
        return stack.get(ModDataComponentTypes.FISH_STATS.get()).type();
    }

    public int getTier(ItemStack stack){
        return stack.get(ModDataComponentTypes.FISH_STATS.get()).tier();
    }

     */





    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(ModDataComponentTypes.FISH_QUALITY.get()) != null && stack.get(ModDataComponentTypes.SUBSTRATE_TYPE.get()) != null && stack.get(ModDataComponentTypes.SUBSTRATE_TIER.get()) != null) {
            if (Screen.hasShiftDown()) {
                tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TIER.get()).getTierString()));
                tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TYPE.get()).getTypeString()));
            } else {
                tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.FISH_QUALITY.get()).getQualityString()));
                tooltipComponents.add(Component.translatable("tooltip.aquaticaspirations.tooltip.requirements"));

            }
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
