package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.items.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.CgsBlocks;
import com.nukateam.cgs.common.faundation.registry.items.CgsWeapons;
import com.nukateam.cgs.common.faundation.registry.items.CgsItems;

public class DataGenConfig {
    public static Class[] dataGenClasses = new Class[]{
            CgsWeapons.class,
            CgsItems.class,
            AttachmentItems.class,
            CgsBlocks.class
    };

    public static final String DATA_MOD_ID = Gunsmithing.MOD_ID;
}
