package net.dewteereeum.aquaticaspirations.component;

import net.dewteereeum.aquaticaspirations.util.ModTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Map;

public class SubstrateTypes {

    public static final SubstrateType EARTHLY = new SubstrateType(0, "earthly");
    public static final SubstrateType HELLISH = new SubstrateType(1, "hellish");
    public static final SubstrateType ENDERIC = new SubstrateType(2, "enderic");
    public static final SubstrateType COSMIC = new SubstrateType(3, "cosmic");

    public static final Map<TagKey<Item>, SubstrateType> subTypeMap = Map.of(
            ModTags.Items.EARTHLY, EARTHLY,
            ModTags.Items.HELLISH, HELLISH,
            ModTags.Items.ENDERIC, ENDERIC,
            ModTags.Items.COSMIC, COSMIC
    );
}
