package net.dewteereeum.aquaticaspirations.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record SubstrateTier(int tier, String name) {

    //'tier' is the lowest level of substrate the fish requires. Higher-tier fish (ex: diamond > dirt) require higher substrate tiers.

    public static final Codec<SubstrateTier> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("tier").forGetter(SubstrateTier::tier),
                            Codec.STRING.fieldOf("name").forGetter(SubstrateTier::name))
                    .apply(instance, SubstrateTier::new));

    public String getTierString() {
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
                    obj instanceof SubstrateTier fd

                            && this.tier == fd.tier
                    && this.name.equals(fd.name);


        }
    }




}
