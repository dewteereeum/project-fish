package net.dewteereeum.functionalfish.item;

import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FunctionalFishMod.MOD_ID);

    public static final DeferredItem<Item> DIAMOND_FISH = ITEMS.registerSimpleItem("diamond_fish");
    public static final DeferredItem<Item> IRON_FISH = ITEMS.registerSimpleItem("iron_fish");
    public static final DeferredItem<Item> IRON_SCALE = ITEMS.registerSimpleItem("iron_scale");
    public static final DeferredItem<Item> SAND_FISH = ITEMS.registerSimpleItem("sand_fish");
    public static final DeferredItem<Item> ANGLOWFISH = ITEMS.registerSimpleItem("anglowfish");
    public static final DeferredItem<Item> GOLD_FISH = ITEMS.registerItem("gold_fish", Item::new, new Item.Properties());
    public static final DeferredItem<Item> IMPROVED_SUBSTRATE = ITEMS.registerSimpleItem("improved_substrate");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
