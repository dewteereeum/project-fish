package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.block.custom.Fishtank;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.dewteereeum.aquaticaspirations.recipe.FishtankRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.ItemStack;
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.COPPER_INGOT, 1)
                .requires(ModItems.COPPER_NUGGET.get(), 9)
                .unlockedBy("has_copper_nugget", has(ModItems.COPPER_NUGGET.get()))
                .save(pRecipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.COPPER_NUGGET.get(), 9)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(pRecipeOutput);

        SimpleCookingRecipeBuilder.smelting(
                Ingredient.of(ModItems.IRON_SCALE.get()),
                RecipeCategory.MISC,
                Items.IRON_NUGGET,
                0.1f,
                100
        )
                .unlockedBy("has_iron_scale", has(ModItems.IRON_SCALE))
                .save(pRecipeOutput, "iron_scale_smelting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.COPPER_SCALE.get()),
                RecipeCategory.MISC,
                ModItems.COPPER_NUGGET.get(),
                0.1f,
                100
        )
                .unlockedBy("has_copper_scale", has(ModItems.COPPER_SCALE))
                .save(pRecipeOutput, "copper_scale_smelting");

        new FishtankRecipeBuilder(Ingredient.of(ModItems.SAND_FISH))
                .unlockedBy("has_sand_fish", has(ModItems.SAND_FISH))
                .Natural(Items.SAND, 1)
                .Altered(Items.SAND, 4)
                .Enhanced(Items.SAND, 16)
                .Enchanted(Items.SAND, 32)
                .Transcendent(Items.SAND, 64)
                .save(pRecipeOutput, "sand_fish_in_fishtank");
        new FishtankRecipeBuilder(Ingredient.of(ModItems.SKELETAL_FISH))
                .unlockedBy("has_skeletal_fish", has(ModItems.SKELETAL_FISH))
                .Natural(Items.BONE_MEAL, 1)
                .Altered(Items.BONE, 1)
                .Enhanced(Items.BONE, 4)
                .Enchanted(Items.BONE, 16)
                .Transcendent(Items.BONE, 32)
                .save(pRecipeOutput, "skeletal_fish_in_fishtank");
        new FishtankRecipeBuilder(Ingredient.of(ModItems.IRON_FISH))
                .unlockedBy("has_iron_fish", has(ModItems.IRON_FISH))
                .Natural(ModItems.IRON_SCALE.get(), 1)
                .Altered(ModItems.IRON_SCALE.get(), 9)
                .Enhanced(Items.IRON_INGOT, 4)
                .Enchanted(Items.IRON_INGOT, 8)
                .Transcendent(Items.IRON_INGOT, 32)
                .save(pRecipeOutput, "iron_fish_in_fishtank");
        new FishtankRecipeBuilder(Ingredient.of(ModItems.COPPER_FISH))
                .unlockedBy("has_copper_fish", has(ModItems.COPPER_FISH))
                .Natural(ModItems.COPPER_SCALE.get(), 1)
                .Altered(ModItems.COPPER_SCALE.get(), 9)
                .Enhanced(Items.COPPER_INGOT, 4)
                .Enchanted(Items.COPPER_INGOT, 8)
                .Transcendent(Items.COPPER_INGOT, 32)
                .save(pRecipeOutput, "copper_fish_in_fishtank");

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
