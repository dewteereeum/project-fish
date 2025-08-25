package net.dewteereeum.aquaticaspirations.event;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.dewteereeum.aquaticaspirations.item.custom.FunctionalFishItem;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = AquaticAspirationsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void addTooltips(ItemTooltipEvent event){
        if(event.getEntity() == null) return;

        if (event.getItemStack().is(ModTags.Items.SUBSTRATE)) {
            if(!event.getFlags().hasShiftDown()){
                event.getToolTip().add(Component.translatable("tooltip.aquaticaspirations.tooltip.substrate_shift"));
            } else {
                if(event.getItemStack().get(ModDataComponentTypes.SUBSTRATE_TYPE.get()) == null){
                    event.getToolTip().add(Component.literal("Has no components"));
                } else {
                    event.getToolTip().add(Component.translatable(event.getItemStack().get(ModDataComponentTypes.SUBSTRATE_TIER.get()).getTierString()));
                    event.getToolTip().add(Component.translatable(event.getItemStack().get(ModDataComponentTypes.SUBSTRATE_TYPE.get()).getTypeString()));
                }
            }
                }
        if(FunctionalFishItem.vanillaFish.contains(event.getItemStack().getItem())){
            var stack = event.getItemStack();
            var dirtPref = stack.get(ModDataComponentTypes.DIRTINESS.get()).likesDirtyWater() ?
                    Component.translatable("tooltip.aquaticaspirations.tooltip.prefers_dirty") :
                    Component.translatable("tooltip.aquaticaspirations.tooltip.prefers_clean");
            var dirtThreshold = Component.translatable("tooltip.aquaticaspirations.tooltip.dirt_threshold")
                    .append(" " + stack.get(ModDataComponentTypes.DIRTINESS.get()).dirtThreshold());
            var subStats = Component.translatable("tooltip.aquaticaspirations.tooltip.substrate_stats").getString().formatted(
                    Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TIER.get()).getTierString()).getString(),
                    Component.translatable(stack.get(ModDataComponentTypes.SUBSTRATE_TYPE.get()).getTypeString()).getString()
            );

            if (event.getFlags().hasShiftDown()) {

                event.getToolTip().add(Component.literal(subStats));
                event.getToolTip().add(dirtPref);
                event.getToolTip().add(dirtThreshold);
            } else {
                if(stack.get(ModDataComponentTypes.FISH_QUALITY.get()) != null) {
                    event.getToolTip().add(Component.translatable(stack.get(ModDataComponentTypes.FISH_QUALITY.get()).getQualityString()));
                }
                event.getToolTip().add(Component.translatable("tooltip.aquaticaspirations.tooltip.requirements"));

            }
        }

    }



}
