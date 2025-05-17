package net.dewteereeum.aquaticaspirations.component;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(AquaticAspirationsMod.MOD_ID);

   public static final DeferredHolder<DataComponentType<?>, DataComponentType<FishQuality>> FISH_QUALITY = register("fish_quality",
           fishQualityBuilder -> fishQualityBuilder.persistent(FishQuality.CODEC));

   public static final DeferredHolder<DataComponentType<?>, DataComponentType<SubstrateTier>> SUBSTRATE_TIER = register("substrate_tier",
           substrateTierBuilder -> substrateTierBuilder.persistent(SubstrateTier.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SubstrateType>> SUBSTRATE_TYPE = register("substrate_type",
            substrateTypeBuilder -> substrateTypeBuilder.persistent(SubstrateType.CODEC));


    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
