package net.dewteereeum.functionalfish.item;

import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.dewteereeum.functionalfish.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FunctionalFishMod.MOD_ID);

    public static Supplier<CreativeModeTab> FUNCTIONAL_FISH_ITEMS_TAB =
            CREATIVE_MODE_TABS.register("functional_fish_items_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.functionalfish.functional_fish_items_tab"))
                    .icon(() -> new ItemStack(ModItems.GOLD_FISH.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.DIAMOND_FISH);
                        output.accept(ModItems.GOLD_FISH);
                        output.accept(ModItems.IRON_FISH);
                        output.accept(ModItems.SAND_FISH);
                        output.accept(ModItems.ANGLOWFISH);
                        output.accept(ModItems.IRON_SCALE);

                        output.accept(ModItems.IMPROVED_SUBSTRATE);

                    }).build());
    public static Supplier<CreativeModeTab> FUNCTIONAL_FISH_BLOCKS_TAB =
            CREATIVE_MODE_TABS.register("functional_fish_blocks_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.functionalfish.functional_fish_blocks_tab"))
                    .icon(() -> new ItemStack(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.IMPROVED_SUBSTRATE_BLOCK);
                        output.accept(ModBlocks.FISHTANK);
                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
