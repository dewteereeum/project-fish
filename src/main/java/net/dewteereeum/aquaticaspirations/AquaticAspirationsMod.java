package net.dewteereeum.aquaticaspirations;

import net.dewteereeum.aquaticaspirations.block.ModBlocks;
import net.dewteereeum.aquaticaspirations.block.entity.ModBlockEntities;
import net.dewteereeum.aquaticaspirations.block.entity.renderer.FishtankBlockEntityRenderer;
import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.dewteereeum.aquaticaspirations.event.ModBusEvents;
import net.dewteereeum.aquaticaspirations.fluid.BaseFluidType;
import net.dewteereeum.aquaticaspirations.fluid.ModFluidTypes;
import net.dewteereeum.aquaticaspirations.fluid.ModFluids;
import net.dewteereeum.aquaticaspirations.item.ModCreativeModeTabs;
import net.dewteereeum.aquaticaspirations.item.ModItems;
import net.dewteereeum.aquaticaspirations.recipe.ModRecipes;
import net.dewteereeum.aquaticaspirations.screen.ModMenuTypes;
import net.dewteereeum.aquaticaspirations.screen.custom.FishtankScreen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The tier here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AquaticAspirationsMod.MOD_ID)
public class AquaticAspirationsMod {
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "aquaticaspirations";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public AquaticAspirationsMod(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        ModDataComponentTypes.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
           event.accept(ModItems.DIAMOND_FISH);
           event.accept(ModItems.GOLD_FISH);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_TANK_FLUID.get(), RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_TANK_FLUID.get(), RenderType.translucent());
            });

        }

        @SubscribeEvent
        public static void onClientExtensions(RegisterClientExtensionsEvent event) {
            event.registerFluidType(((BaseFluidType) ModFluidTypes.TANK_FLUID_TYPE.get()).getClientFluidTypeExtensions(),
                    ModFluidTypes.TANK_FLUID_TYPE.get());
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            if(Config.RENDER_TANKS.getAsBoolean()) {
                event.registerBlockEntityRenderer(ModBlockEntities.FISHTANK_BE.get(), FishtankBlockEntityRenderer::new);
            }
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.FISHBOWL_MENU.get(), FishtankScreen::new);
        }
    }
}
