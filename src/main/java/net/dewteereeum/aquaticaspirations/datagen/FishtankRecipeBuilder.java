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

public abstract class FishtankRecipeBuilder implements RecipeBuilder {
    protected Map<String, ItemStack> results;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    private final Ingredient inputFish;

    public FishtankRecipeBuilder(Ingredient inputFish){
        this.inputFish = inputFish;
        this.results = new HashMap<>();
    }
    public void Natural(ItemStack result){
        results.put("natural", result);
    }
    public void Altered(ItemStack result){
        results.put("altered", result);
    }
    public void Enhanced(ItemStack result){
        results.put("enhanced", result);
    }
    public void Enchanted(ItemStack result){
        results.put("enchanted", result);
    }
    public void Transcendent(ItemStack result){
        results.put("transcendent", result);
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
