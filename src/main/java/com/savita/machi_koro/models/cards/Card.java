package com.savita.machi_koro.models.cards;

public abstract class Card {
    private final String title;
    private final String description;
    private final String image;
    private final int price;
    protected Cards type;
    public Card(String title, String description, String image, int price) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.price = price;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }
    public int getPrice() {
        return price;
    }
    public Cards getType() {
        return type;
    }
}
