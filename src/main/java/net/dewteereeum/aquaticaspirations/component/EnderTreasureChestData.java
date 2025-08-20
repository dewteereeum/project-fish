package net.dewteereeum.aquaticaspirations.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public record EnderTreasureChestData(BlockState block, BlockPos position, Direction direction) {
    public static final Codec<EnderTreasureChestData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(BlockState.CODEC.fieldOf("block").forGetter(EnderTreasureChestData::block),
                    BlockPos.CODEC.fieldOf("position").forGetter(EnderTreasureChestData::position),
                            Direction.CODEC.fieldOf("direction").forGetter(EnderTreasureChestData::direction))
                    .apply(instance, EnderTreasureChestData::new));

    public String getLinkString(){
        return "Linked to %s at [%d, %d, %d][%s]".formatted(
                BuiltInRegistries.BLOCK.getKey(block.getBlock()).toString(),
                position.getX(), position.getY(), position.getZ(),
                direction.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.block, this.position);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this){
            return true;
        } else {
            return obj instanceof EnderTreasureChestData ecd && this.block == ecd.block && this.position == ecd.position;
        }
    }
}
