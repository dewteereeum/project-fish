package net.dewteereeum.functionalfish.datagen;

import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.dewteereeum.functionalfish.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FunctionalFishMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.DIAMOND_FISH.get());
        basicItem(ModItems.GOLD_FISH.get());
        basicItem(ModItems.IRON_FISH.get());
        basicItem(ModItems.IRON_SCALE.get());
        basicItem(ModItems.SAND_FISH.get());
        basicItem(ModItems.ANGLOWFISH.get());


        basicItem(ModItems.IMPROVED_SUBSTRATE.get());
    }
}
