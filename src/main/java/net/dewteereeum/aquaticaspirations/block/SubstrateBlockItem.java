package net.dewteereeum.aquaticaspirations.block;

import net.dewteereeum.aquaticaspirations.component.ModDataComponentTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class SubstrateBlockItem extends BlockItem {
    public SubstrateBlockItem(SubstrateBlock block, Properties properties) {
        super(block, properties
                .component(ModDataComponentTypes.SUBSTRATE_TIER.get(), block.subTier)
                .component(ModDataComponentTypes.SUBSTRATE_TYPE.get(), block.subType));
    }
}
