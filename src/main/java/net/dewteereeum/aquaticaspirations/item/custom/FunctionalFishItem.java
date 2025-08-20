package net.dewteereeum.aquaticaspirations.item.custom;


import net.dewteereeum.aquaticaspirations.component.Dirtiness;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FunctionalFishItem extends Item {

    public FunctionalFishItem(Properties properties, int dirtProd, int dirtThresh, boolean likesDirt) {
        super(properties
                .component(ModDataComponentTypes.DIRTINESS.get(), new Dirtiness(dirtProd, dirtThresh, likesDirt)));
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
        var dirtPref = stack.get(ModDataComponentTypes.DIRTINESS.get()).likesDirtyWater() ?
                Component.translatable("tooltip.aquaticaspirations.tooltip.prefers_dirty") :
                Component.translatable("tooltip.aquaticaspirations.tooltip.prefers_clean");
        var dirtThreshold = Component.translatable("tooltip.aquaticaspirations.tooltip.dirt_threshold")
                .append(" " + stack.get(ModDataComponentTypes.DIRTINESS.get()).dirtThreshold());
        var subStats = Component.translatable("tooltip.aquaticaspirations.tooltip.substrate_stats").getString().formatted(
                Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TIER.get()).getTierString()).getString(),
                Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TYPE.get()).getTypeString()).getString()
        );

        if (Screen.hasShiftDown()) {
//            tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TIER.get()).getTierString()));
//            tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TYPE.get()).getTypeString()));
            tooltipComponents.add(Component.literal(subStats));
            tooltipComponents.add(dirtPref);
            tooltipComponents.add(dirtThreshold);
        } else {
            if(stack.get(ModDataComponentTypes.FISH_QUALITY.get()) != null) {
                tooltipComponents.add(Component.translatable(stack.get(ModDataComponentTypes.FISH_QUALITY.get()).getQualityString()));
            }
            tooltipComponents.add(Component.translatable("tooltip.aquaticaspirations.tooltip.requirements"));

        }


        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
