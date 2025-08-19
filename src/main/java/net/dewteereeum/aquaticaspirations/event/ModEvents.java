package net.dewteereeum.aquaticaspirations.event;


import net.dewteereeum.aquaticaspirations.AquaticAspirationsMod;
import net.dewteereeum.aquaticaspirations.fluid.TankFluid;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.event.entity.item.ItemEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

//@EventBusSubscriber(modid = AquaticAspirationsMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

//    @SubscribeEvent
//    public static void registerTankFluids(FMLLoadCompleteEvent loadCompleteEvent) throws IOException {
//        List<Fluid> registry = BuiltInRegistries.FLUID.stream().toList();
//        File tankFluidFile = new File("resources/data/tank_fluids");
//        FileWriter writer = new FileWriter(tankFluidFile);
//
//        for(Fluid f : registry){
//            try {
//                TankFluid entry = new TankFluid(f);
//                if(!entry.findColour()){
//                    entry.giveColor();
//                }
//                writer.append(entry.toString());
//                writer.append("%n".formatted());
//            } catch (IOException e){
//                System.out.println("Unable to write TankFluid entry for " + f.toString());
//            }
//
//        }
//
//        writer.close();
//    }

    //public static void replaceWithAbyssalWater(){}

}
