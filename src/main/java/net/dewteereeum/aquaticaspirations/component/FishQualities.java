package net.dewteereeum.aquaticaspirations.component;

import java.util.ArrayList;
import java.util.List;

public class FishQualities {

    //I understand this is basically an enum, I just couldn't figure out how to make a component-codec for it.
    public static final FishQuality NATURAL = new FishQuality(0, "natural");
    public static final FishQuality ALTERED = new FishQuality(1, "altered");
    public static final FishQuality ENHANCED = new FishQuality(2, "enhanced");
    public static final FishQuality ENCHANTED = new FishQuality(3, "enchanted");
    public static final FishQuality TRANSCENDENT = new FishQuality(4, "transcendent");

    private static final List<FishQuality> fishQualitiesList = new ArrayList<>(List.of(
            NATURAL,
            ALTERED,
            ENHANCED,
            ENCHANTED,
            TRANSCENDENT
    ));

    public static FishQuality getNextTier(FishQuality input){
        if(input.equals(TRANSCENDENT)) return TRANSCENDENT;
        return fishQualitiesList.get(fishQualitiesList.indexOf(input) + 1);
    }
    public static FishQuality getPreviousTier(FishQuality input){
        if(input.equals(NATURAL)) return NATURAL;
        return fishQualitiesList.get(fishQualitiesList.indexOf(input));
    }
}
