package net.dewteereeum.functionalfish.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record SubstrateType(int type) {

    //'type' is an indicator of which substrate family to use

    public static final Codec<SubstrateType> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("type").forGetter(SubstrateType :: type))
                    .apply(instance, SubstrateType::new));


    static final String[] typeNames = {
            "Earthly",
            "Hellish",
            "Enderic",
            "COSMIC"
    };

    public String getTypeString(){
        return "tooltip.functionalfish.tooltip." + typeNames[type].toLowerCase();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        else {
            return
                    obj instanceof SubstrateType fd

                            && this.type == fd.type;


        }
    }
}
