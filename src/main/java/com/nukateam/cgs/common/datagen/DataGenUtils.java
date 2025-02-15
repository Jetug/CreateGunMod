package com.nukateam.cgs.common.datagen;

import com.nukateam.cgs.Gunsmithing;

import java.lang.annotation.Annotation;
import java.util.function.BiConsumer;

public class DataGenUtils {
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
