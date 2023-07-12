package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class VineyardCard extends BlueCompanyCard {
    public VineyardCard() {
        super("Виноградник", "Возьмите три монеты из банка. В ход любого игрока","7-1.png", 3);
        type = Cards.VINEYARD;
        minDiceValue = 7;
        maxDiceValue = 7;
        value = 3;
        activityType = ActivityTypes.AGRICULTURE;
    }
}
