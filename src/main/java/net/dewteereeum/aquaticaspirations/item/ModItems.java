package net.dewteereeum.aquaticaspirations.item;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.dewteereeum.aquaticaspirations.component.SubstrateTiers;
import net.dewteereeum.aquaticaspirations.component.SubstrateTypes;
import net.dewteereeum.aquaticaspirations.item.custom.FunctionalFishItem;
import net.dewteereeum.aquaticaspirations.item.custom.accessory.EmptyTreasureChestItem;
import net.dewteereeum.aquaticaspirations.item.custom.accessory.EnderTreasureChestItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AquaticAspirationsMod.MOD_ID);


    public static final DeferredItem<Item> DIAMOND_FISH =
            ITEMS.register("diamond_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.ABYSSAL),
                            1, 20, false
                    )
            );
    public static final DeferredItem<Item> IRON_FISH =
            ITEMS.register("iron_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY),
                            5, 90, false
                    )
            );
    public static final DeferredItem<Item> GOLD_FISH =
            ITEMS.register("gold_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY),
                            3, 60, false
                    )
            );
    public static final DeferredItem<Item> SAND_FISH =
            ITEMS.register("sand_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY),
                            15, 40, true
                    )
            );
    public static final DeferredItem<Item> ANGLOWFISH =
            ITEMS.register("anglowfish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.ABYSSAL),
                            1, 10, false
                    )
            );
    public static final DeferredItem<Item> UNDEAD_FISH =
            ITEMS.register("undead_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY),
                            8, 20, true
                    )
            );
    public static final DeferredItem<Item> SKELETAL_FISH =
            ITEMS.register("skeletal_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY),
                            2, 20, true

                    )
            );

    public static final DeferredItem<Item> EMPTY_TREASURE_CHEST =
            ITEMS.register("empty_treasure_chest",
                    () -> new EmptyTreasureChestItem(new Item.Properties()));
    public static final DeferredItem<Item> ENDER_TREASURE_CHEST = ITEMS.register("ender_treasure_chest",
            () -> new EnderTreasureChestItem(new Item.Properties().stacksTo(1)));


    public static final DeferredItem<Item> IRON_SCALE = ITEMS.registerSimpleItem("iron_scale");

    public static final DeferredItem<Item> IMPROVED_SUBSTRATE = ITEMS.registerSimpleItem("improved_substrate");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
