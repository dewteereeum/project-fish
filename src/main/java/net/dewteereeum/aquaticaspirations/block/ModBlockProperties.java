package net.dewteereeum.aquaticaspirations.block;

import net.dewteereeum.aquaticaspirations.fluid.ModFluids;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

public class ModBlockProperties {

    public static final EnumProperty<ContainedFluid> CONTAINED_FLUID = EnumProperty.create("fluid", ContainedFluid.class);

    public enum ContainedFluid implements StringRepresentable {

        EMPTY("empty", Fluids.EMPTY),
        WATER("water", Fluids.WATER),
        LAVA("lava", Fluids.LAVA),
        ENDER("ender", ModFluids.SOURCE_TANK_FLUID.get());

        private final String name;
        private final Fluid fluid;


        ContainedFluid(String name, Fluid fluid){
            this.name = name;
            this.fluid = fluid;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public Fluid getFluid(){
            return this.fluid;
        }






    }
}
