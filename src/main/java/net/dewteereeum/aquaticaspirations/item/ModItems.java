package net.dewteereeum.aquaticaspirations.item;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.component.FishQuality;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.dewteereeum.aquaticaspirations.component.SubstrateTier;
import net.dewteereeum.aquaticaspirations.component.SubstrateType;
import net.dewteereeum.aquaticaspirations.item.custom.FunctionalFishItem;
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
                            .component(ModDataComponentTypes.FISH_QUALITY.get(), new FishQuality(0))
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), new SubstrateTier(3))
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), new SubstrateType(0))
                    )
            );
    public static final DeferredItem<Item> IRON_FISH =
            ITEMS.register("iron_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.FISH_QUALITY.get(), new FishQuality(0))
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), new SubstrateTier(1))
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), new SubstrateType(0))
                    )
            );
    public static final DeferredItem<Item> GOLD_FISH =
            ITEMS.register("gold_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.FISH_QUALITY.get(), new FishQuality(0))
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), new SubstrateTier(1))
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), new SubstrateType(0))
                    )
            );
    public static final DeferredItem<Item> SAND_FISH =
            ITEMS.register("sand_fish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.FISH_QUALITY.get(), new FishQuality(0))
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), new SubstrateTier(0))
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), new SubstrateType(0))
                    )
            );
    public static final DeferredItem<Item> ANGLOWFISH =
            ITEMS.register("anglowfish",
                    () -> new FunctionalFishItem(new Item.Properties()
                            .stacksTo(1)
                            .component(ModDataComponentTypes.FISH_QUALITY.get(), new FishQuality(0))
                            .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), new SubstrateTier(2))
                            .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), new SubstrateType(2))
                    )
            );


    public static final DeferredItem<Item> IRON_SCALE = ITEMS.registerSimpleItem("iron_scale");

    public static final DeferredItem<Item> IMPROVED_SUBSTRATE = ITEMS.registerSimpleItem("improved_substrate");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
