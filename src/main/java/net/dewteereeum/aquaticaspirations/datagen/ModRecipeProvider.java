package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput){

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get())
                .pattern("AA")
                .pattern("AA")
                .define('A', ModItems.IMPROVED_SUBSTRATE.get())
                .unlockedBy("has_improved_substrate_block", has(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get()))
                .save(pRecipeOutput, "aquaticaspirations:improved_substrate_block_from_substrate");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get())
                .pattern("GNG")
                .pattern("NGN")
                .pattern("GNG")
                .define('G', Items.GRAVEL)
                .define('N', Items.IRON_NUGGET)
                .unlockedBy("has_improved_substrate_block", has(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get()))
                .save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.IMPROVED_SUBSTRATE.get(), 4)
                .requires(ModBlocks.IMPROVED_SUBSTRATE_BLOCK.get())
                .unlockedBy("has_improved_substrate", has(ModItems.IMPROVED_SUBSTRATE.get()))
                .save(pRecipeOutput);

    }

    protected static void oreSmelting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pRecipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput pRecipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, AquaticAspirationsMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

}
