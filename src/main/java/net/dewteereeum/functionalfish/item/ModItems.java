package net.dewteereeum.functionalfish.item;

import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.dewteereeum.functionalfish.component.FishData;
import net.dewteereeum.functionalfish.item.custom.FunctionalFishItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.dewteereeum.functionalfish.component.ModDataComponentTypes.FISH_STATS;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FunctionalFishMod.MOD_ID);

    public static final DeferredItem<Item> DIAMOND_FISH =
            ITEMS.registerItem("diamond_fish", FunctionalFishItem::new, new Item.Properties().component(FISH_STATS.get(), new FishData(0,0,0)));


    public static final DeferredItem<Item> IRON_FISH = ITEMS.registerSimpleItem("iron_fish");
    public static final DeferredItem<Item> IRON_SCALE = ITEMS.registerSimpleItem("iron_scale");
    public static final DeferredItem<Item> SAND_FISH = ITEMS.registerSimpleItem("sand_fish");
    public static final DeferredItem<Item> ANGLOWFISH = ITEMS.registerSimpleItem("anglowfish");
    public static final DeferredItem<Item> GOLD_FISH = ITEMS.registerSimpleItem("gold_fish");
    public static final DeferredItem<Item> IMPROVED_SUBSTRATE = ITEMS.registerSimpleItem("improved_substrate");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
