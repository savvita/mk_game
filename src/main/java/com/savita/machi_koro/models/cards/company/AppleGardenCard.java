package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class AppleGardenCard extends BlueCompanyCard{
    public AppleGardenCard() {
        super("Яблоневый сад", "Возьмите три монеты из банка. В ход любого игрока","10-1.png", 3);
        type = Cards.APPLE_GARDEN;
        minDiceValue = 10;
        maxDiceValue = 10;
        value = 3;
        activityType = ActivityTypes.AGRICULTURE;
    }
}
