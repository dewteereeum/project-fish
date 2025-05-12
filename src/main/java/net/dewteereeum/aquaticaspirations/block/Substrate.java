package net.dewteereeum.aquaticaspirations.block;

import net.minecraft.world.level.block.Block;

public class Substrate extends Block {

    int tier;
    int type;

    public Substrate(Properties properties, int type, int tier) {
        super(properties);
        this.type = type;
        this.tier = tier;
    }

    private String[] tierNames = {
            "BASIC",
            "IMPROVED",
            "ADVANCED",
            "SUPERIOR",
            "CATALYTIC"
    };

    private String[] typeNames = {
            "EARTHLY",
            "HELLISH",
            "VOID"
    };

    public String getTierName(){
        return tierNames[tier];
    }

    public String getTypeName(){
        return typeNames[type];
    }

}
