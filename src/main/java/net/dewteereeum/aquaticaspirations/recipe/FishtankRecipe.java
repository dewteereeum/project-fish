package net.dewteereeum.aquaticaspirations.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public record FishtankRecipe(Ingredient inputItem, Map<String, ItemStack> outputs) implements Recipe<FishtankRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(FishtankRecipeInput pInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItem.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(FishtankRecipeInput pInput, HolderLookup.Provider provider) {
        var quality = inputItem.getItems()[0].get(ModDataComponentTypes.FISH_QUALITY.get());
        if(quality == null) return ItemStack.EMPTY;

        else return outputs.getOrDefault(quality.name(), ItemStack.EMPTY).copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        var quality = inputItem.getItems()[0].get(ModDataComponentTypes.FISH_QUALITY.get());
        if(quality == null) return ItemStack.EMPTY;

        else return outputs.getOrDefault(quality.name(), ItemStack.EMPTY);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FISHTANK_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FISHTANK_TYPE.get();
    }
    public static class Serializer implements RecipeSerializer<FishtankRecipe> {

        public static final MapCodec<FishtankRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(FishtankRecipe::inputItem),
                QUALITY_OUTPUT_MAP_CODEC.fieldOf("outputs").forGetter(FishtankRecipe::outputs)
        ).apply(inst, FishtankRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FishtankRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, FishtankRecipe::inputItem,
                        QUALITY_OUTPUT_MAP_STREAM_CODEC, FishtankRecipe::outputs,
                        FishtankRecipe::new);
        @Override
        public MapCodec<FishtankRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FishtankRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static final Codec<Map<String, ItemStack>> QUALITY_OUTPUT_MAP_CODEC = Codec.unboundedMap(Codec.STRING, ItemStack.CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Map<String, ItemStack>> QUALITY_OUTPUT_MAP_STREAM_CODEC = ByteBufCodecs.map(
            HashMap::new,
            ByteBufCodecs.STRING_UTF8,
            ItemStack.STREAM_CODEC,
            5
    );
}
