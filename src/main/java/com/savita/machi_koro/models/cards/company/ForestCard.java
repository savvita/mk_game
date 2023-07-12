package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class ForestCard extends BlueCompanyCard{
    public ForestCard() {
        super("Лес", "Возьмите одну монету из банка. В ход любого игрока", "5-1.png", 3);
        type = Cards.FOREST;
        minDiceValue = 5;
        maxDiceValue = 5;
        value = 1;
        activityType = ActivityTypes.WORKER;
    }
}
