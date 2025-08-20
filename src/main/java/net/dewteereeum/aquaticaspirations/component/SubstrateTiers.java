package net.dewteereeum.aquaticaspirations.component;

import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Map;

public class SubstrateTiers {

    public static final SubstrateTier BASIC = new SubstrateTier(0, "basic");
    public static final SubstrateTier IMPROVED = new SubstrateTier(1, "improved");
    public static final SubstrateTier ADVANCED = new SubstrateTier(2, "advanced");
    public static final SubstrateTier SUPERIOR = new SubstrateTier(3, "superior");
    public static final SubstrateTier CATALYTIC = new SubstrateTier(4, "catalytic");

    public static final Map<TagKey<Item>, SubstrateTier> subTierMap = Map.of(
            ModTags.Items.BASIC, BASIC,
            ModTags.Items.IMPROVED, IMPROVED,
            ModTags.Items.ADVANCED, ADVANCED,
            ModTags.Items.SUPERIOR, SUPERIOR,
            ModTags.Items.CATALYTIC, CATALYTIC
    );
}
