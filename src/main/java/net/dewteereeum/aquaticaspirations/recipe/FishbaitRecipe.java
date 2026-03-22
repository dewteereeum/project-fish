package net.dewteereeum.aquaticaspirations.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

public record FishbaitRecipe(Ingredient inputItem, Block fluid, ItemStack output) implements Recipe<FishbaitRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(FishbaitRecipeInput fishbaitRecipeInput, Level level) {
        if(level.isClientSide()) return false;
        System.out.println("Looking for: " + inputItem.toString());
        System.out.println("Got: " + fishbaitRecipeInput.inputStack().toString());
        if(!inputItem.test(fishbaitRecipeInput.getItem(0))) return false;
        System.out.println("Loooking for:" + fluid.toString());
        System.out.println("Got:" + fishbaitRecipeInput.fluidBlock().toString());
        if(!fluid.equals(fishbaitRecipeInput.fluidBlock()))return false;
        return true;
    }

    @Override
    public ItemStack assemble(FishbaitRecipeInput recipeInput, HolderLookup.Provider provider) {
        var fluid = recipeInput.getFluid();
        if(fluid == null){
            return ItemStack.EMPTY;
        } else {
            return output;
        }

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
        return ModRecipes.FISHBAIT_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FISHBAIT_TYPE.get();
    }
    public static class Serializer implements RecipeSerializer<FishbaitRecipe> {

        public static final MapCodec<FishbaitRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(FishbaitRecipe::inputItem),
                BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fluid").forGetter(FishbaitRecipe::fluid),
                ItemStack.CODEC.fieldOf("result").forGetter(FishbaitRecipe::output)
        ).apply(inst, FishbaitRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FishbaitRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, FishbaitRecipe::inputItem,
                        ByteBufCodecs.registry(Registries.BLOCK), FishbaitRecipe::fluid,
                        ItemStack.STREAM_CODEC, FishbaitRecipe::output,
                        FishbaitRecipe::new);
        @Override
        public MapCodec<FishbaitRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FishbaitRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }


//    private static final Codec<Map<Block, ItemStack>> FLUID_OUTPUT_MAP_CODEC = Codec.unboundedMap(Block.CODEC.codec(), ItemStack.CODEC);
//    private static final StreamCodec<RegistryFriendlyByteBuf, Map<Block, ItemStack>> FLUID_OUTPUT_MAP_STREAM_CODEC = ByteBufCodecs.map(
//            HashMap::new,
//            ByteBufCodecs.fromCodec(Block.CODEC.codec()),
//            ItemStack.STREAM_CODEC,
//            10
//            );


}
