package net.dewteereeum.aquaticaspirations.recipe;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, AquaticAspirationsMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, AquaticAspirationsMod.MOD_ID);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FishtankRecipe>> FISHTANK_SERIALIZER =
            SERIALIZERS.register("fishtank_production", FishtankRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FishtankRecipe>> FISHTANK_TYPE =
            TYPES.register("fishtank_production", () -> new RecipeType<FishtankRecipe>() {
                @Override
                public String toString() {
                    return "fishtank_production";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
