package net.dewteereeum.aquaticaspirations.event;


import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.component.FishQualities;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.dewteereeum.aquaticaspirations.component.SubstrateTiers;
import net.dewteereeum.aquaticaspirations.component.SubstrateTypes;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.dewteereeum.aquaticaspirations.item.custom.FunctionalFishItem;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.neoforged.neoforge.event.entity.item.ItemEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.List;


@EventBusSubscriber(modid = AquaticAspirationsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {



    @SubscribeEvent
    public static void fishBaitingEvent(EntityTickEvent.Post event){
        Entity entity = event.getEntity();
        Level level = entity.level();

        if(!(entity instanceof ItemEntity itemEntity)) return;
        if(!itemEntity.getItem().is(Items.APPLE)) return;

        if(itemEntity.isInFluidType(Fluids.WATER.getFluidType())){
            int roll = entity.level().getRandom().nextInt(0, FishBaitEvent.fishPool.size());
                ItemStack dropStack = new ItemStack(FishBaitEvent.fishPool.get(roll), 1);
                dropStack.set(ModDataComponentTypes.FISH_QUALITY.get(), FishQualities.NATURAL);

                itemEntity.spawnAtLocation(dropStack);
                itemEntity.level().playLocalSound(entity.getOnPos(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0f, 1.0f, true);
                itemEntity.getItem().shrink(1);
        }
    }



}
