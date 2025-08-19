package net.dewteereeum.aquaticaspirations.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record Dirtiness(int dirtProduction, int dirtThreshold, boolean likesDirtyWater) {

    public static final Codec<Dirtiness> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("dirt_production").forGetter(Dirtiness::dirtProduction),
                    Codec.INT.fieldOf("dirt_threshold").forGetter(Dirtiness::dirtThreshold),
                    Codec.BOOL.fieldOf("likes_dirty_water").forGetter(Dirtiness::likesDirtyWater))
                    .apply(instance, Dirtiness::new));


    @Override
    public int hashCode() {
        return Objects.hash(this.dirtProduction, this.dirtThreshold, this.likesDirtyWater);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        } else {
            return obj instanceof Dirtiness d
                    && this.dirtProduction == d.dirtProduction
                    && this.dirtThreshold == d.dirtThreshold
                    && this.likesDirtyWater == d.likesDirtyWater;
        }
    }
}
