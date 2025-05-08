package net.dewteereeum.functionalfish.component;

import com.mojang.serialization.Codec;
import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.xml.crypto.Data;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(FunctionalFishMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> FISH_TIER = register("fish_tier",
            builder -> builder.persistent(Codec.STRING));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> FISH_QUALITY = register("fish_quality",
            builder -> builder.persistent(Codec.STRING));


    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
