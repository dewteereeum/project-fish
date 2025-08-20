package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.fluid.ModFluids;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModFluidTagsProvider extends FluidTagsProvider {
    public ModFluidTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, AquaticAspirationsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(FluidTags.WATER)
                .add(ModFluids.SOURCE_TANK_FLUID.get())
                .add(ModFluids.FLOWING_TANK_FLUID.get());

        tag(ModTags.Fluids.FISHTANK_FLUID)
                .add(Fluids.WATER)
                .add(Fluids.LAVA)
                .add(ModFluids.SOURCE_TANK_FLUID.get());
    }
}
