package net.dewteereeum.aquaticaspirations.util;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SUBSTRATE = createTag("substrate");
        public static final TagKey<Block> EARTHLY = createTag("earthly");
        public static final TagKey<Block> HELLISH = createTag("hellish");
        public static final TagKey<Block> ENDERIC = createTag("enderic");
        public static final TagKey<Block> COSMIC = createTag("cosmic");

        public static final TagKey<Block> BASIC = createTag("basic");
        public static final TagKey<Block> IMPROVED = createTag("improved");
        public static final TagKey<Block> ADVANCED = createTag("advanced");
        public static final TagKey<Block> SUPERIOR = createTag("superior");
        public static final TagKey<Block> TRANSCENDENT = createTag("transcendent");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(AquaticAspirationsMod.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> FUNCTIONAL_FISH = createTag("functional_fish");

        //Substrate tags
        public static final TagKey<Item> SUBSTRATE = createTag("substrate");
        public static final TagKey<Item> EARTHLY = createTag("earthly");
        public static final TagKey<Item> HELLISH = createTag("hellish");
        public static final TagKey<Item> ENDERIC = createTag("enderic");
        public static final TagKey<Item> COSMIC = createTag("cosmic");
        public static final TagKey<Item> BASIC = createTag("basic");
        public static final TagKey<Item> IMPROVED = createTag("improved");
        public static final TagKey<Item> ADVANCED = createTag("advanced");
        public static final TagKey<Item> SUPERIOR = createTag("superior");
        public static final TagKey<Item> CATALYTIC = createTag("catalytic");



        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(AquaticAspirationsMod.MOD_ID, name));
        }
    }

    public static class Fluids {

        public static final TagKey<Fluid> FISHTANK_FLUID = createTag("fishtank_fluid");


        private static TagKey<Fluid> createTag(String name) {
            return FluidTags.create(ResourceLocation.fromNamespaceAndPath(AquaticAspirationsMod.MOD_ID, name));
        }
    }
}
