package com.nukateam.cgs.common.datagen.util;

import com.simibubi.create.AllTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TagsKeys {
    public static TagKey<Item> brassSheet() {
        return AllTags.forgeItemTag("plates/brass");
    }

    public static TagKey<Item> copperSheet() {
        return AllTags.forgeItemTag("plates/copper");
    }

    public static TagKey<Item> ironSheet() {
        return AllTags.forgeItemTag("plates/iron");
    }

    public static TagKey<Item> leadNugget() {
        return AllTags.forgeItemTag("nuggets/lead");
    }

    public static TagKey<Item> steelNugget() {
        return AllTags.forgeItemTag("nuggets/steel");
    }

    public static TagKey<Item> ironNugget() {
        return AllTags.forgeItemTag("nuggets/iron");
    }
}
