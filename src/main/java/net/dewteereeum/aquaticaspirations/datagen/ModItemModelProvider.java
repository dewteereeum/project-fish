package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.fluid.ModFluids;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.common.Mod;
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
        basicItem(ModItems.UNDEAD_FISH.get());
        basicItem(ModItems.SKELETAL_FISH.get());

        //basicItem(ModItems.EMPTY_TREASURE_CHEST.get());

        basicItem(ModItems.IMPROVED_SUBSTRATE.get());

        basicItem(ModFluids.TANK_FLUID_BUCKET.get());
    }
}
