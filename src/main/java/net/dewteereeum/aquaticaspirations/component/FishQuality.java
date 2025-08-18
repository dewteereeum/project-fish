package net.dewteereeum.aquaticaspirations.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record FishQuality(int tier, String name) {

    //'quality' refers to the upgrade-level of the fish. Think of it as a production multiplier. It should start at 0 by default.


    public static final Codec<FishQuality> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("tier").forGetter(FishQuality::tier),
                            Codec.STRING.fieldOf("name").forGetter(FishQuality::name))
                    .apply(instance, FishQuality::new));


    public String getQualityString(){
        return "tooltip.aquaticaspirations.tooltip." + name;
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.tier, this.name);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        else {
            return
                    obj instanceof FishQuality fd
                    && this.tier == fd.tier
                    && this.name.equals(fd.name);

            }
    }
}
