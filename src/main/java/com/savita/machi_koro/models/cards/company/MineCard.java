package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;

public class MineCard extends BlueCompanyCard{
    public MineCard() {
        super("Шахта", "Возьмите пять монет из банка. В ход любого игрока", "9-1.png", 6);
        type = Cards.MINE;
        minDiceValue = 9;
        maxDiceValue = 9;
        value = 5;
        activityType = ActivityTypes.WORKER;
    }
}
