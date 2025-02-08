package com.nukateam.cgs.common.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataGen {
    ItemParent parent() default ItemParent.GENERATED;
    ItemType type() default ItemType.ITEM;
    String path() default "";
    boolean ownDir() default false;
}
