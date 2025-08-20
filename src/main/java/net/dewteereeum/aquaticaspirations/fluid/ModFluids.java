package net.dewteereeum.aquaticaspirations.fluid;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(BuiltInRegistries.FLUID, AquaticAspirationsMod.MOD_ID);

    public static final Supplier<FlowingFluid> SOURCE_TANK_FLUID = FLUIDS.register("source_tank_fluid",
            () -> new BaseFlowingFluid.Source(ModFluids.TANK_FLUID_PROPERITES));

    public static final Supplier<FlowingFluid> FLOWING_TANK_FLUID = FLUIDS.register("flowing_tank_fluid",
            () -> new BaseFlowingFluid.Flowing(ModFluids.TANK_FLUID_PROPERITES));

    public static final DeferredBlock<LiquidBlock> TANK_FLUID_BLOCK = ModBlocks.BLOCKS.register("tank_fluid_block",
            () -> new LiquidBlock(ModFluids.SOURCE_TANK_FLUID.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER).noLootTable().noOcclusion()));
    public static final DeferredItem<Item> TANK_FLUID_BUCKET = ModItems.ITEMS.registerItem("tank_fluid_bucket",
            properties -> new BucketItem(ModFluids.SOURCE_TANK_FLUID.get(), properties.craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final BaseFlowingFluid.Properties TANK_FLUID_PROPERITES = new BaseFlowingFluid.Properties(
            ModFluidTypes.TANK_FLUID_TYPE, SOURCE_TANK_FLUID, FLOWING_TANK_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(1).block(ModFluids.TANK_FLUID_BLOCK).bucket(ModFluids.TANK_FLUID_BUCKET);



    public static void register(IEventBus eventBus) {
    FLUIDS.register(eventBus);
    }
}
