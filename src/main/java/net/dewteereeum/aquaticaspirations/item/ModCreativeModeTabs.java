package net.dewteereeum.aquaticaspirations.item;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.fluid.ModFluids;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AquaticAspirationsMod.MOD_ID);

    public static Supplier<CreativeModeTab> AQUATICASPIRATIONS_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("aquaticaspirations_items_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.aquaticaspirations.aquaticaspirations_items_tab"))
                    .icon(() -> new ItemStack(ModItems.GOLD_FISH.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.DIAMOND_FISH);
                        output.accept(ModItems.GOLD_FISH);
                        output.accept(ModItems.IRON_FISH);
                        output.accept(ModItems.SAND_FISH);
                        output.accept(ModItems.ANGLOWFISH);
                        output.accept(ModItems.UNDEAD_FISH);
                        output.accept(ModItems.SKELETAL_FISH);
                        output.accept(ModItems.IRON_SCALE);

                        output.accept(ModItems.EMPTY_TREASURE_CHEST);
                        output.accept(ModItems.ENDER_TREASURE_CHEST);

                        output.accept(ModItems.IMPROVED_SUBSTRATE);

                        output.accept(ModFluids.ABYSSAL_WATER_BUCKET);
                        output.accept(ModFluids.TANK_FLUID_BUCKET);

                    }).build());
    public static Supplier<CreativeModeTab> AQUATICASPIRATIONS_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("aquaticaspirations_blocks_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.aquaticaspirations.aquaticaspirations_blocks_tab"))
                    .icon(() -> new ItemStack(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.IMPROVED_SUBSTRATE_BLOCK);
                        output.accept(ModBlocks.FISHTANK);
                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
