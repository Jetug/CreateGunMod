package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.AttachmentItems;
import com.nukateam.cgs.common.faundation.registry.ModBlocks;
import com.nukateam.cgs.common.faundation.registry.ModWeapons;
import com.nukateam.cgs.common.faundation.registry.ModItems;

public class DataGenConfig {
    public static Class[] dataGenClasses = new Class[]{
            ModWeapons.class,
            ModItems.class,
            AttachmentItems.class,
            ModBlocks.class
    };

    public static final String DATA_MOD_ID = Gunsmithing.MOD_ID;
}
