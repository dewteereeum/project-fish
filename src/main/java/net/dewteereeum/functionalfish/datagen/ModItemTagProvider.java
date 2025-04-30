package net.dewteereeum.functionalfish.datagen;

import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.dewteereeum.functionalfish.block.ModBlocks;
import net.dewteereeum.functionalfish.item.ModItems;
import net.dewteereeum.functionalfish.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, FunctionalFishMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Items.FUNCTIONAL_FISH)
                .add(ModItems.DIAMOND_FISH.get())
                .add(ModItems.GOLD_FISH.get())
                .add(ModItems.IRON_FISH.get())
                .add(ModItems.ANGLOWFISH.get())
                .add(Items.TROPICAL_FISH);
        this.tag(ModTags.Items.SUBSTRATE)
                .add(ModItems.IMPROVED_SUBSTRATE.get())
                .add(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.asItem())
                .add(Items.SAND)
                .add(Items.GRAVEL);
    }
}
