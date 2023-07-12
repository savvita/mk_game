package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class FlowerGardenCard extends BlueCompanyCard {
    public FlowerGardenCard() {
        super("Цветник", "Возьмите одну монету из банка. В ход любого игрока","4-1.png", 2);
        type = Cards.FLOWER_GARDEN;
        minDiceValue = 4;
        maxDiceValue = 4;
        value = 1;
        activityType = ActivityTypes.AGRICULTURE;
    }
}
