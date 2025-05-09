package net.dewteereeum.functionalfish.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record FishData(int type, int tier, int quality) {

    //'type' is an indicator of which substrate family to use
    //'tier' is the lowest level of substrate the fish requires. Higher-value fish (ex: diamond > dirt) require higher substrate tiers.
    //'quality' refers to the upgrade-level of the fish. Think of it as a production multiplier. It should start at 0 by default.


    public static final Codec<FishData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("type").forGetter(FishData :: type),
                    Codec.INT.fieldOf("tier").forGetter(FishData :: tier),
                    Codec.INT.fieldOf("quality").forGetter(FishData :: quality))

                    .apply(instance, FishData::new));


    static final String[] typeNames = {
            "Earthly",
            "Hellish",
            "Enderic",
            "COSMIC"
    };

    static final String[] qualityNames = {
            "Natural",
            "Altered",
            "Enhanced",
            "Enchanted",
            "Transcendent"
    };

    static final String[] tierNames = {
            "Basic",
            "Improved",
            "Advanced",
            "Superior",
            "Catalytic"
    };


    public String getTypeString(){
        return "tooltip.functionalfish.tooltip." + typeNames[type].toLowerCase();
    }

    public String getTierString(){
        return "tooltip.functionalfish.tooltip." + tierNames[tier];
    }

    public String getQualityString(){
        return "tooltip.functionalfish.tooltip." + qualityNames[quality];
    }


    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.quality, this.tier);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        else {
            return
                    obj instanceof FishData fd
                    && this.type == fd.type
                    && this.quality == fd.quality
                    && this.tier == fd.tier;
        }
    }
}
