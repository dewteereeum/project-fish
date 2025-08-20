package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.tags.ItemTags.CAT_FOOD;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, AquaticAspirationsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var SAND = Blocks.SAND.asItem();
        var GRAVEL = Blocks.GRAVEL.asItem();
        var SOUL_SAND = Blocks.SOUL_SAND.asItem();

        var IMP_SUBSTRATE = ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get().asItem();

        this.tag(ModTags.Items.SUBSTRATE)
                .add(IMP_SUBSTRATE)

                .add(SAND)
                .add(GRAVEL)
                .add(SOUL_SAND);

        this.tag(ModTags.Items.BASIC)
                .add(SAND)
                .add(GRAVEL)
                .add(SOUL_SAND);
        this.tag(ModTags.Items.IMPROVED)
                .add(IMP_SUBSTRATE);

        this.tag(ModTags.Items.EARTHLY)
                .add(SAND)
                .add(GRAVEL)

                .add(IMP_SUBSTRATE);

        this.tag(ModTags.Items.HELLISH)
                .add(SOUL_SAND);

        this.tag(ModTags.Items.FUNCTIONAL_FISH)
                .add(ModItems.DIAMOND_FISH.get())
                .add(ModItems.GOLD_FISH.get())
                .add(ModItems.IRON_FISH.get())
                .add(ModItems.ANGLOWFISH.get())
                .add(ModItems.UNDEAD_FISH.get())
                .add(ModItems.SKELETAL_FISH.get())
                .add(ModItems.SAND_FISH.get())

                .add(Items.TROPICAL_FISH);



    }
}

