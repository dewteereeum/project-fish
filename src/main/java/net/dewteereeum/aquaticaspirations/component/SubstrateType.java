package net.dewteereeum.aquaticaspirations.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Objects;

public record SubstrateType(int value, String name) {



    //'type' is an indicator of which substrate family to use

    public static final Codec<SubstrateType> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("type").forGetter(SubstrateType :: value),
                            Codec.STRING.fieldOf("name").forGetter(SubstrateType::name))
                    .apply(instance, SubstrateType::new));


    public String getTypeString(){
        return "tooltip.aquaticaspirations.tooltip." + name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.value, this.name);

    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        else {
            return
                    obj instanceof SubstrateType fd

                            && this.value == fd.value
                    && this.name.equals(fd.name);


        }
    }
}
