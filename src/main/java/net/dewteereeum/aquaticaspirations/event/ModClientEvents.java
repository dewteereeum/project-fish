package net.dewteereeum.aquaticaspirations.event;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
    public static void addSubstrateTooltips(ItemTooltipEvent event){
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
    }

    private static @NotNull List<String> getSubToolTips(ItemTooltipEvent event) {
        List<String> subToolTips = new ArrayList<>(2);
        ItemStack stack = event.getItemStack();
        var tags = stack.getTags().filter(tag -> tag.location().getNamespace().equals("aquaticaspirations")).distinct();

        var tagList = tags.toList();

        tagList.forEach(tag -> {
            if (TIER_MAP.containsKey(tag)) {
                subToolTips.addFirst(TIER_MAP.get(tag));
            }
            if (TYPE_MAP.containsKey(tag)) {
                subToolTips.addLast(TYPE_MAP.get(tag));
            }
        });
        return subToolTips;
    }

    private static final Map<TagKey<Item>, String> TIER_MAP = Map.of(
            ModTags.Items.BASIC, "tooltip.aquaticaspirations.tooltip.basic",
            ModTags.Items.IMPROVED, "tooltip.aquaticaspirations.tooltip.improved",
            ModTags.Items.ADVANCED, "tooltip.aquaticaspirations.tooltip.advanced",
            ModTags.Items.SUPERIOR, "tooltip.aquaticaspirations.tooltip.superior",
            ModTags.Items.CATALYTIC, "tooltip.aquaticaspirations.tooltip.catalytic"
    );
    private static final Map<TagKey<Item>, String> TYPE_MAP = Map.of(
            ModTags.Items.EARTHLY, "tooltip.aquaticaspirations.tooltip.earthly",
            ModTags.Items.HELLISH, "tooltip.aquaticaspirations.tooltip.hellish",
            ModTags.Items.ENDERIC, "tooltip.aquaticaspirations.tooltip.enderic",
            ModTags.Items.COSMIC, "tooltip.aquaticaspirations.tooltip.cosmic"
    );

}
