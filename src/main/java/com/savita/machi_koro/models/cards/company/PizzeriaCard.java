package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class PizzeriaCard extends RedCompanyCard {
    public PizzeriaCard() {
        super("Пиццерия", "Возьмите одну монету у игрока, бросившего кубики. В ход другого игрока", "7-2.png", 1);
        type = Cards.PIZZERIA;
        minDiceValue = 7;
        maxDiceValue = 7;
        value = 1;
        activityType = ActivityTypes.RESTAURANT;
    }
}
