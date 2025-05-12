package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, AquaticAspirationsMod.MOD_ID, existingFileHelper);
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
