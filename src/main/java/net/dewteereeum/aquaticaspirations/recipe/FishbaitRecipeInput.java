package net.dewteereeum.aquaticaspirations.recipe;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public record FishbaitRecipeInput(ItemStack inputStack, Block fluidBlock) implements RecipeInput {


    public ItemStack getItem(int i){
        System.out.println("Item retrieved: " + inputStack.toString());
        return inputStack;
    }

    public Block getFluid(){
        System.out.println("Fluid block retrieved: " + fluidBlock.toString());
        return fluidBlock;
    }


    @Override
    public int size() {
        return 1;
    }
}
