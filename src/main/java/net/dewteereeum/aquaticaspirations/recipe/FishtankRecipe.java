package net.dewteereeum.aquaticaspirations.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record  FishtankRecipe(Ingredient inputItem, ItemStack output) implements Recipe<FishtankRecipeInput> {

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
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
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
                ItemStack.CODEC.fieldOf("result").forGetter(FishtankRecipe::output)
        ).apply(inst, FishtankRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FishtankRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, FishtankRecipe::inputItem,
                        ItemStack.STREAM_CODEC, FishtankRecipe::output,
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
}
