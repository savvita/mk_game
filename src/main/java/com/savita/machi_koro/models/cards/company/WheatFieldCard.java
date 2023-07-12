package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class WheatFieldCard extends BlueCompanyCard {
    public WheatFieldCard() {
        super("Пшеничное поле", "Возьмите одну монету из банка. В ход любого игрока","1-1.png", 1);
        type = Cards.WHEAT_FIELD;
        minDiceValue = 1;
        maxDiceValue = 1;
        value = 1;
        activityType = ActivityTypes.AGRICULTURE;
    }
}
