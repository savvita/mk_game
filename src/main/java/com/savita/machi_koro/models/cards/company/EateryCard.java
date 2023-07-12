package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class EateryCard extends RedCompanyCard {
    public EateryCard() {
        super("Закусочная", "Возьмите одну монету у игрока, бросившего кубики. В ход другого игрока", "8-2.png", 1);
        type = Cards.EATERY;
        minDiceValue = 8;
        maxDiceValue = 8;
        value = 1;
        activityType = ActivityTypes.RESTAURANT;
    }
}
