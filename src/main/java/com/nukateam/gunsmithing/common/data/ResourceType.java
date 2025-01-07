package com.nukateam.gunsmithing.common.data;

public enum ResourceType {
    ITEM("item"),
    BLOCK("block");

    private final String path;

    ResourceType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}