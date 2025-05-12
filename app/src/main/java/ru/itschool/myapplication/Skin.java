package ru.itschool.myapplication;

public class Skin {
    private final int drawableId;
    private final String name;
    private int price;

    public Skin(int drawableId, String name, int price) {
        this.drawableId = drawableId;
        this.name = name;
        this.price = price;
    }
    public Skin(int drawableId, String name) {
        this.drawableId = drawableId;
        this.name = name;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}