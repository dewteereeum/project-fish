package net.dewteereeum.functionalfish.block.entity;

import net.dewteereeum.functionalfish.FunctionalFishMod;
import net.dewteereeum.functionalfish.block.ModBlocks;
import net.dewteereeum.functionalfish.block.entity.custom.FishbowlBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, FunctionalFishMod.MOD_ID);

   public static final Supplier<BlockEntityType<FishbowlBlockEntity>> FISHBOWL_BE =
           BLOCK_ENTITIES.register("fishbowl_be", () -> BlockEntityType.Builder.of(
                   FishbowlBlockEntity::new, ModBlocks.FISHBOWL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
