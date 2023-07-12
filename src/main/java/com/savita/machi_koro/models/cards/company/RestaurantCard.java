package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class RestaurantCard extends RedCompanyCard {
    public RestaurantCard() {
        super("Ресторан", "Возьмите две монеты у игрока, бросившего кубики. В ход другого игрока", "9_10-2.png", 3);
        type = Cards.RESTAURANT;
        minDiceValue = 9;
        maxDiceValue = 10;
        value = 2;
        activityType = ActivityTypes.RESTAURANT;
    }
}
