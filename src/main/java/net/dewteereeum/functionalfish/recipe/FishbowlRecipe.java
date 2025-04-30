package net.dewteereeum.functionalfish.recipe;

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

public record FishbowlRecipe(Ingredient inputItem, ItemStack output) implements Recipe<FishbowlRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(FishbowlRecipeInput pInput, Level level) {
        if(level.isClientSide()) {
            return false;
        }

        return inputItem.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(FishbowlRecipeInput pInput, HolderLookup.Provider provider) {
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
        return ModRecipes.FISHBOWL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FISHBOWL_TYPE.get();
    }
    public static class Serializer implements RecipeSerializer<FishbowlRecipe> {

        public static final MapCodec<FishbowlRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(FishbowlRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(FishbowlRecipe::output)
        ).apply(inst, FishbowlRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FishbowlRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, FishbowlRecipe::inputItem,
                        ItemStack.STREAM_CODEC, FishbowlRecipe::output,
                        FishbowlRecipe::new);
        @Override
        public MapCodec<FishbowlRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FishbowlRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
