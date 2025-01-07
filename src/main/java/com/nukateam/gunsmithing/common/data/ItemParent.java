package com.nukateam.gunsmithing.common.data;

public enum ItemParent {
    GENERATED("item/generated"),
    HANDHELD("item/handheld"),
    ENTITY("builtin/entity"),
    SPAWN_EGG("item/template_spawn_egg"),
    ;

    private final String path;

    ItemParent(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
