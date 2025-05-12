package net.dewteereeum.functionalfish.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record FishQuality(int quality) {

    //'quality' refers to the upgrade-level of the fish. Think of it as a production multiplier. It should start at 0 by default.


    public static final Codec<FishQuality> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("quality").forGetter(FishQuality:: quality))
                    .apply(instance, FishQuality::new));



    static final String[] qualityNames = {
            "Natural",
            "Altered",
            "Enhanced",
            "Enchanted",
            "Transcendent"
    };


    public String getQualityString(){
        return "tooltip.functionalfish.tooltip." + qualityNames[quality].toLowerCase();
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.quality);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        else {
            return
                    obj instanceof FishQuality fd
                    //&& this.type == fd.type
                    //&& this.tier == fd.tier
                    && this.quality == fd.quality;

            }
    }
}
