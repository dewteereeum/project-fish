package net.dewteereeum.aquaticaspirations.event;


import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.component.FishQualities;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.dewteereeum.aquaticaspirations.item.custom.FunctionalFishItem;
import net.dewteereeum.aquaticaspirations.recipe.FishbaitRecipe;
import net.dewteereeum.aquaticaspirations.recipe.FishbaitRecipeInput;
import net.dewteereeum.aquaticaspirations.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemFishedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@EventBusSubscriber(modid = AquaticAspirationsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {



//    @SubscribeEvent
//    public static void fishBaitingEvent(EntityTickEvent.Post event){
//        Entity entity = event.getEntity();
//        Level level = entity.level();
//
//        if(!(entity instanceof ItemEntity itemEntity)) return;
//        if(!itemEntity.getItem().is(Items.APPLE)) return;
//
//        if(itemEntity.isInFluidType(Fluids.WATER.getFluidType())){
//            level.addParticle(ParticleTypes.BUBBLE, itemEntity.getX(), itemEntity.getY() + 0.5f, itemEntity.getZ(), 0, 0.2f, 0);
//            level.playLocalSound(entity.getOnPos(), SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 1.0f, 1.0f, true);
//            if(itemEntity.getAge() > 40) {
//                int roll = entity.level().getRandom().nextInt(0, FishBaitEvent.fishPool.size());
//                ItemStack dropStack = new ItemStack(FishBaitEvent.fishPool.get(roll), 1);
//                dropStack.set(ModDataComponentTypes.FISH_QUALITY.get(), FishQualities.NATURAL);
//
//
//                itemEntity.spawnAtLocation(dropStack);
//                itemEntity.level().playLocalSound(entity.getOnPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f, true);
//                itemEntity.getItem().shrink(1);
//            }
//        }
//    }

//    @SubscribeEvent
//    public static void baitedFishingEvent(PlayerInteractEvent.RightClickItem event){
//        if(event.getItemStack().is(Items.FISHING_ROD)){
//            if(event.getEntity().fishing != null){
//                var hook = event.getEntity().fishing;
//                if(!hook.isInWater()) return;
//                Level level = hook.level();
//                if(hook.getHookedIn() == null) return;
//                float radius = 1.0f;
//                var region = new AABB(hook.getX() - radius, hook.getY() - radius, hook.getZ() - radius,
//                        hook.getX() + radius, hook.getY() + radius, hook.getZ() + radius);
//                List<Entity> entities = level.getEntities(hook, region).stream().filter(e -> e instanceof ItemEntity ie && ie.getItem().is(Items.APPLE)).toList();
//
//                if(!entities.isEmpty()){
//                    int roll = level.getRandom().nextInt(0, FishBaitEvent.fishPool.size());
//                    ItemStack dropStack = new ItemStack(FishBaitEvent.fishPool.get(roll), 1);
//                    dropStack.set(ModDataComponentTypes.FISH_QUALITY.get(), FishQualities.NATURAL);
//
//                    ItemEntity ie = (ItemEntity) entities.getFirst();
//                    ie.spawnAtLocation(dropStack);
//                    ie.getItem().shrink(1);
//
//                }
//            }
//        }
//    }

    private static Block getFishingFluidBlock(FishingHook hook){
        if(!hook.isInFluidType()) return null;
        var out = hook.getBlockStateOn().getBlock();

        System.out.println(out.toString());
        return hook.getBlockStateOn().getBlock();
    }

    @SubscribeEvent
    public static void baitedHookEvent(ItemFishedEvent event){
        var hook = event.getHookEntity();
        Level level = hook.level();
        float radius = 1.0f;
        AABB region = new AABB(hook.getX() - radius, hook.getY() - radius, hook.getZ() - radius,
                hook.getX() + radius, hook.getY() + radius, hook.getZ() + radius);
        List<Entity> entities = level.getEntities(hook, region).stream()
                .filter(e -> e instanceof ItemEntity ie && getCurrentRecipe(ie.getItem(), getFishingFluidBlock(hook), hook.level()).isPresent()).toList();
        if(!entities.isEmpty()){
            ItemEntity ie = (ItemEntity) entities.getFirst();
            Optional<RecipeHolder<FishbaitRecipe>> recipe = getCurrentRecipe(ie.getItem(), getFishingFluidBlock(hook), level);
            ItemStack dropStack = recipe.get().value().output();
            if(dropStack.getItem() instanceof FunctionalFishItem) {
                dropStack.set(ModDataComponentTypes.FISH_QUALITY.get(), FishQualities.NATURAL);
            }
            ie.spawnAtLocation(dropStack);
            ie.getItem().shrink(1);

            event.setCanceled(true);

        } else {
            System.out.println("No matching recipe.");
        }
    }

    private static Optional<RecipeHolder<FishbaitRecipe>> getCurrentRecipe(ItemStack stack, Block block, Level level){
        System.out.println("Passing in:" + stack + ", " + block + ", " + level);
        System.out.println(level.getRecipeManager().getRecipeFor(ModRecipes.FISHBAIT_TYPE.get(),
                new FishbaitRecipeInput(stack, block), level));
        return level.getRecipeManager().getRecipeFor(ModRecipes.FISHBAIT_TYPE.get(),
                new FishbaitRecipeInput(stack, block), level);
    }




}
