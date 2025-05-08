package net.dewteereeum.functionalfish.item.custom;

public enum FishQuality {

    NATURAL(0),
    ALTERED(1),
    ENHANCED(2),
    ENCHANTED(3),
    TRANSCENDENT(4);

    private final int qualityTier;

    FishQuality(int tier){
        this.qualityTier = tier;

    }

    public int getQualityValue(){
        return this.qualityTier;
    }
}
