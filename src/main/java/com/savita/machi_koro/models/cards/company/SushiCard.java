package com.savita.machi_koro.models.cards.company;


import com.savita.machi_koro.models.cards.Cards;

public class SushiCard extends RedCompanyCard {
    public SushiCard() {
        super("Суси-бар", "Если у вас есть \"Порт\", возьмите три монеты у игрока, бросившего кубики. В ход другого игрока", "1-2.png", 2);
        type = Cards.SUSHI_BAR;
        minDiceValue = 1;
        maxDiceValue = 1;
        value = 3;
        activityType = ActivityTypes.RESTAURANT;
        isConditionPassed = x -> x.hasCityCard(Cards.PORT);
    }
}
