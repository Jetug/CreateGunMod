package com.nukateam.cgs.common.datagen.annotations;

public enum ItemType {
    ITEM("item"),
    BLOCK("block");

    private final String path;

    ItemType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}