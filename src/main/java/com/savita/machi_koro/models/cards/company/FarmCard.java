package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class FarmCard extends BlueCompanyCard {
    public FarmCard() {
        super("Ферма", "Возьмите одну монету из банка. В ход любого игрока","2-1.png", 1);
        type = Cards.FARM;
        minDiceValue = 2;
        maxDiceValue = 2;
        value = 1;
        activityType = ActivityTypes.ANIMAL_HUSBANDRY;
    }
}
