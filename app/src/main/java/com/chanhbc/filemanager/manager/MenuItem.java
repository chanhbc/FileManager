package com.chanhbc.filemanager.manager;

public class MenuItem {
    private String name;
    private int image;

    public MenuItem(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }
}
