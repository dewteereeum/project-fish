package net.dewteereeum.aquaticaspirations.event;

import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.block.entity.ModBlockEntities;
import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.dewteereeum.aquaticaspirations.component.*;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

@EventBusSubscriber(modid = AquaticAspirationsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModBusEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.FISHTANK_BE.get(), FishtankBlockEntity::getItemHandler);
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.FISHTANK_BE.get(), FishtankBlockEntity::getTank);
    }

    @SubscribeEvent
    public static void addSubstrateData(ModifyDefaultComponentsEvent event){
        event.modify(Items.SAND, builder ->
                builder.set(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                        .set(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY));
        event.modify(Items.GRAVEL, builder ->
                builder.set(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                        .set(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY));
        event.modify(Items.SOUL_SAND, builder ->
                builder.set(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                        .set(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.HELLISH));

    }

    @SubscribeEvent
    public static void addFishData(ModifyDefaultComponentsEvent event){
        event.modify(Items.TROPICAL_FISH, builder ->
                builder.set(ModDataComponentTypes.FISH_QUALITY.get(), FishQualities.NATURAL)
                        .set(ModDataComponentTypes.SUBSTRATE_TIER.get(), SubstrateTiers.BASIC)
                        .set(ModDataComponentTypes.SUBSTRATE_TYPE.get(), SubstrateTypes.EARTHLY)
                        .set(ModDataComponentTypes.DIRTINESS.get(), new Dirtiness(5, 30, false)));
    }
}
