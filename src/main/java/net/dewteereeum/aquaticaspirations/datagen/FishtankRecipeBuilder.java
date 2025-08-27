package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.recipe.FishtankRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FishtankRecipeBuilder implements RecipeBuilder {
    protected Map<String, ItemStack> results;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    private final Ingredient inputFish;

    public FishtankRecipeBuilder(Ingredient inputFish){
        this.inputFish = inputFish;
        this.results = new HashMap<>();
    }
    public FishtankRecipeBuilder Natural(Item result, int count){
        results.put("natural", new ItemStack(result, count));
        return this;
    }
    public FishtankRecipeBuilder Altered(Item result, int count){
        results.put("altered", new ItemStack(result, count));
        return this;
    }
    public FishtankRecipeBuilder Enhanced(Item result, int count){
        results.put("enhanced", new ItemStack(result, count));
        return this;
    }
    public FishtankRecipeBuilder Enchanted(Item result, int count){
        results.put("enchanted", new ItemStack(result, count));
        return this;
    }
    public FishtankRecipeBuilder Transcendent(Item result, int count){
        results.put("transcendent", new ItemStack(result, count));
        return this;
    }

    @Override
    public FishtankRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public FishtankRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public Item getResult() {
        return results.get("natural").getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        FishtankRecipe recipe = new FishtankRecipe(this.inputFish, results);
        recipeOutput.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
