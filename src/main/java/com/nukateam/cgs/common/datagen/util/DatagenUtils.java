package com.nukateam.cgs.common.datagen.util;

import com.nukateam.cgs.Gunsmithing;
import com.nukateam.cgs.common.faundation.registry.ModBlocks;
import com.nukateam.cgs.common.faundation.registry.ModGuns;
import com.nukateam.cgs.common.faundation.registry.ModItems;

import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;

public class DatagenUtils {
    public static Class[] dataGenClasses = new Class[]{
            ModGuns.class,
            ModItems.class,
            ModBlocks.class
    };

    public static <T extends Annotation> void handleFields(Class<?> modItemsClass, Class<T> annotation, BiConsumer<Object, T> consumer) {
        var fields = modItemsClass.getFields();

        try {
            for (var field : fields) {
                var filedObject = field.get(null);

                if (field.isAnnotationPresent(annotation))
                    consumer.accept(filedObject, field.getAnnotation(annotation));
            }
        } catch (Exception e) {
            Gunsmithing.LOGGER.error(e.getMessage(), e);
        }
    }
}
