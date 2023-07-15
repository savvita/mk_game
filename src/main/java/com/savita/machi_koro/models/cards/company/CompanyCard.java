package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public abstract class CompanyCard extends Card {
    protected int minDiceValue;
    protected int maxDiceValue;
    protected int value;
    protected ActivityTypes activityType;
    protected boolean isClosed;
    protected CompanyColors color;
    public CompanyCard(String title, String description, String image, int price) {
        super(title, description, image, price);
    }
    public int getMinDiceValue() {
        return minDiceValue;
    }
    public int getMaxDiceValue() {
        return maxDiceValue;
    }
    public int getValue() {
        return value;
    }
    public ActivityTypes getActivityType() {
        return activityType;
    }
    public boolean isClosed() {
        return isClosed;
    }
    public abstract CardResult apply(Game game, Player player);

    public void close() {
        isClosed = true;
    }
    public void build(Player player) {}

    public CompanyColors getColor() {
        return color;
    }
}