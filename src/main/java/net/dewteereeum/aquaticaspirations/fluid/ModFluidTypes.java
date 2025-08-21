package net.dewteereeum.aquaticaspirations.fluid;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.model.DynamicFluidContainerModel;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class ModFluidTypes {

    //PLACEHOLDER TEXTURES
    public static final ResourceLocation WATER_STILL_RL = ResourceLocation.parse("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = ResourceLocation.parse("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = ResourceLocation.parse("block/water_overlay");


    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, AquaticAspirationsMod.MOD_ID);

    public static final Supplier<FluidType> TANK_FLUID_TYPE = registerFluidType("tank_fluid",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA1343E69,
                    new Vector3f(108 / 255f, 168 / 255f, 212 / 255f),
                    FluidType.Properties.create()));
    public static final Supplier<FluidType> ABYSSAL_WATER_TYPE = registerFluidType("abyssal_water",
            new BaseFluidType(WATER_STILL_RL, WATER_FLOWING_RL, WATER_OVERLAY_RL, 0xA10c0554,
                    new Vector3f(108 / 255f, 168 / 255f, 212 / 255f),
                    FluidType.Properties.create()));


    private static Supplier<FluidType> registerFluidType(String name, FluidType fluidType){
        return FLUID_TYPES.register(name, () -> fluidType);
    }

    public static void register(IEventBus eventBus){
        FLUID_TYPES.register(eventBus);
    }
}
