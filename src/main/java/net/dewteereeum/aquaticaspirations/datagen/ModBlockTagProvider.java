package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AquaticAspirationsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ModTags.Blocks.SUBSTRATE)

                .add(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get())

                .add(Blocks.SAND)
                .add(Blocks.GRAVEL)
                .add(Blocks.ANDESITE)
                .add(Blocks.GRANITE)
                .add(Blocks.STONE)
                .add(Blocks.DIORITE)

                //Logs for Bark Fish
                .add(Blocks.OAK_LOG)
                .add(Blocks.SPRUCE_LOG)
                .add(Blocks.JUNGLE_LOG)
                .add(Blocks.ACACIA_LOG)
                .add(Blocks.BIRCH_LOG)
                .add(Blocks.DARK_OAK_LOG)
                .add(Blocks.MANGROVE_LOG)
                .add(Blocks.CHERRY_LOG);

    }
}
