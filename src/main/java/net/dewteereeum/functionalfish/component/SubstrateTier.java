package net.dewteereeum.functionalfish.component;

public enum SubstrateTier {
    BASIC(0),
    IMPROVED(1),
    ADVANCED(2),
    SUPERIOR(3),
    CATALYTIC(4);

    private final int substrateTier;

    SubstrateTier(int tier){
        this.substrateTier=tier;

    }

    public int getSubstrateTier(){
        return this.substrateTier;
    }
}
