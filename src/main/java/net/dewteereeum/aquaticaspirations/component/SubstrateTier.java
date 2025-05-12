package net.dewteereeum.aquaticaspirations.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record SubstrateTier(int tier) {

    //'tier' is the lowest level of substrate the fish requires. Higher-value fish (ex: diamond > dirt) require higher substrate tiers.

    public static final Codec<SubstrateTier> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("tier").forGetter(SubstrateTier :: tier))
                    .apply(instance, SubstrateTier::new));



    static final String[] tierNames = {
            "Basic",
            "Improved",
            "Advanced",
            "Superior",
            "Catalytic"
    };

    public String getTierString() {
        return "tooltip.aquaticaspirations.tooltip." + tierNames[tier].toLowerCase();
    }



    @Override
    public int hashCode() {
        return Objects.hash(this.tier);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        else {
            return
                    obj instanceof SubstrateTier fd

                            && this.tier == fd.tier;


        }
    }




}
