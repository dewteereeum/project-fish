package net.dewteereeum.functionalfish.item.custom;

import net.dewteereeum.functionalfish.component.FishData;
import net.dewteereeum.functionalfish.component.ModDataComponentTypes;
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

    /* public void upgradeQuality(ItemStack stack){
        stack.get(ModDataComponentTypes.FISH_STATS.get()).quality().set();

    }*/

    public int getQuality(ItemStack stack){
        return stack.get(ModDataComponentTypes.FISH_STATS.get()).quality();
    }

    public int getType(ItemStack stack){
        return stack.get(ModDataComponentTypes.FISH_STATS.get()).type();
    }

    public int getTier(ItemStack stack){
        return stack.get(ModDataComponentTypes.FISH_STATS.get()).tier();
    }


    @Override
    public boolean isFoil(ItemStack stack) {
        return stack.get(ModDataComponentTypes.FISH_STATS.get()).quality() > 2;
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()){
            tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.FISH_STATS.get()).getTierString()));
            tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.FISH_STATS.get()).getTypeString()));
        }
        else {
            tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.FISH_STATS.get()).getQualityString()));
            tooltipComponents.add(Component.translatable("tooltip.functionalfish.tooltip.requirements"));

        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
