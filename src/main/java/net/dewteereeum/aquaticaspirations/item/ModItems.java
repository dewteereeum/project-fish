package net.dewteereeum.aquaticaspirations.item;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.component.*;
import net.dewteereeum.aquaticaspirations.item.custom.FunctionalFishItem;
import net.dewteereeum.aquaticaspirations.item.custom.accessory.EmptyTreasureChestItem;
import net.dewteereeum.aquaticaspirations.item.custom.accessory.EnderTreasureChestItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AquaticAspirationsMod.MOD_ID);

    //FunctionalFish Types:
    //EARTHLY: 0
    //HELLISH: 1
    //ENDERIC: 2
    //COSMIC: 3

    //FunctionalFish Tiers:
    //BASIC: 0
    //IMPROVED: 1
    //ADVANCED: 2
    //SUPERIOR: 3
    //CATALYTIC: 4

    public static final DeferredItem<Item> DIAMOND_FISH =
            ITEMS.register("diamond_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY)
                    )
            );
    public static final DeferredItem<Item> IRON_FISH =
            ITEMS.register("iron_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY)
                    )
            );
    public static final DeferredItem<Item> GOLD_FISH =
            ITEMS.register("gold_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY)
                    )
            );
    public static final DeferredItem<Item> SAND_FISH =
            ITEMS.register("sand_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY)
                    )
            );
    public static final DeferredItem<Item> ANGLOWFISH =
            ITEMS.register("anglowfish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.ENDERIC)
                    )
            );
    public static final DeferredItem<Item> UNDEAD_FISH =
            ITEMS.register("undead_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY)
                    )
            );
    public static final DeferredItem<Item> SKELETAL_FISH =
            ITEMS.register("skeletal_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.SUPERIOR)
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY)
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
