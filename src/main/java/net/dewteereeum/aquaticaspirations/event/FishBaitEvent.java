package net.dewteereeum.aquaticaspirations.event;

import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.minecraft.world.item.Item;


import java.util.List;

public class FishBaitEvent {

    public static final List<Item> fishPool = List.of(
            ModItems.DIAMOND_FISH.get(),
            ModItems.GOLD_FISH.get(),
            ModItems.IRON_FISH.get(),
            ModItems.SAND_FISH.get()
    );
}
