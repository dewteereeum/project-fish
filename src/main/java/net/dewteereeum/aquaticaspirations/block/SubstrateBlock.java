package net.dewteereeum.aquaticaspirations.block;

import net.dewteereeum.aquaticaspirations.component.SubstrateTier;
import net.dewteereeum.aquaticaspirations.component.SubstrateType;
import net.minecraft.world.level.block.Block;

public class SubstrateBlock extends Block {
    protected SubstrateTier subTier;
    protected SubstrateType subType;
    public SubstrateBlock(Properties properties, SubstrateTier tier, SubstrateType type) {
        super(properties);
        this.subTier = tier;
        this.subType = type;
    }
}
