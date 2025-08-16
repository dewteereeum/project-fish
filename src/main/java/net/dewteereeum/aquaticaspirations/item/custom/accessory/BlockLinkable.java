package net.dewteereeum.aquaticaspirations.item.custom.accessory;

import net.dewteereeum.aquaticaspirations.block.entity.custom.FishtankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;


public interface BlockLinkable {
    BlockPos getLinkedBlock(FishtankBlockEntity fishtankBlockEntity);
    Direction getDirection(FishtankBlockEntity fishtankBlockEntity);
}
