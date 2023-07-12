package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class CafeCard extends RedCompanyCard {
    public CafeCard() {
        super("Кафе", "Возьмите одну монету у игрока, бросившего кубики. В ход другого игрока", "3-2.png", 2);
        type = Cards.CAFE;
        minDiceValue = 3;
        maxDiceValue = 3;
        value = 1;
        activityType = ActivityTypes.RESTAURANT;
    }
}
