package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AquaticAspirationsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(ModTags.Items.SUBSTRATE)
                .add(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get().asItem())
                .add(Blocks.SAND.asItem())
                .add(Blocks.GRAVEL.asItem());

        this.tag(ModTags.Items.FUNCTIONAL_FISH)
                .add(ModItems.DIAMOND_FISH.get())
                .add(ModItems.GOLD_FISH.get())
                .add(ModItems.IRON_FISH.get())
                .add(ModItems.ANGLOWFISH.get())
                .add(Items.TROPICAL_FISH);

        this.tag(ModTags.Items.EARTHLY)
                .add(ModItems.DIAMOND_FISH.get());

        this.tag(ModTags.Items.ADVANCED)
                .add(ModItems.DIAMOND_FISH.get());

    }
}

