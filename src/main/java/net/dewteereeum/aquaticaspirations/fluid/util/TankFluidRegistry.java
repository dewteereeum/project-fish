package net.dewteereeum.aquaticaspirations.fluid.util;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class TankFluidRegistry {
/*
    public static Collection<Fluid> fluidList;
    public static ResourceLocation taggedFluids = ModTags.Fluids.FISHTANK_FLUID.location();

    public static File fluidsWithColors;


    public static ArrayList<Fluid> getFluidList(){
        if (fluidsWithColors.exists()){


        } else return fluidList;
    }
    


    public static void createFluidList() {
        try{
            FileReader fileReader = new FileReader(taggedFluids.getPath());
            Type type = new TypeToken<ArrayList<Fluid>>(){}.getType();

            Gson gson = new Gson();

            fluidList = gson.fromJson(fileReader, type);
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error in finding tagged fluid list");
        } catch (IOException e) {
            System.err.println("Error in closing file.");
        }
    }
    */




}
