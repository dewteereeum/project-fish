package net.dewteereeum.functionalfish.component;

public enum SubstrateType {

    EARTHLY(0),
    HELLISH(1),
    VOID(2),
    COSMIC(3);

    private final int subType;

    SubstrateType(int type){
        this.subType=type;
    }

    int getSubTypeValue(){
        return this.subType;
    }

}
