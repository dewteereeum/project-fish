package net.dewteereeum.aquaticaspirations.item.custom.accessory;

import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

public interface IFishTankAccessory {

    void accessoryFunction(FishtankBlockEntity fishtankBlockEntity);

    //to set where the accessory renders in the tank
    //starting position is in middle of tank, on top of substrate layer (8/16f, 4/16f, 8/16f)

    float[] getTranslationVector(); //must be of length 3 [x, y, z]

    //Where SOUTH is default orientation
    int[] getRotationVector(); //must be of length 3 [xRot, yRot, zRot]
}
