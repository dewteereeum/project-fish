package net.dewteereeum.aquaticaspirations.datagen;

import net.dewteereeum.aquaticaspirations.recipe.FishbaitRecipe;
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
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FishbaitRecipeBuilder implements RecipeBuilder {
    protected ItemStack result;
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    protected String group;

    private final Ingredient inputItem;
    private final Block fluid;

    public FishbaitRecipeBuilder(Ingredient inputItem, Block fluid, Item resultItem){
        this.inputItem = inputItem;
        this.fluid = fluid;
        this.result = new ItemStack(resultItem, 1);
    }


    @Override
    public FishbaitRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.criteria.put(name, criterion);
        return this;
    }

    @Override
    public FishbaitRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public Item getResult() {
        return result.getItem();
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        FishbaitRecipe recipe = new FishbaitRecipe(this.inputItem, this.fluid, this.result);
        recipeOutput.accept(id, recipe, advancement.build(id.withPrefix("recipes/")));
    }
}
