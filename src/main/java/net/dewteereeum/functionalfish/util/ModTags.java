package net.dewteereeum.functionalfish.util;

import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SUBSTRATE = createTag("substrate");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(FunctionalFishMod.MOD_ID,name));
        }
    }

    public static class Items {
        public static final TagKey<Item> FUNCTIONAL_FISH = createTag("functional_fish");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(FunctionalFishMod.MOD_ID,name));
        }
    }
}
