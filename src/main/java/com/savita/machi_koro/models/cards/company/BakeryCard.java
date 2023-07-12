package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class BakeryCard extends GreenCompanyCard {
    public BakeryCard() {
        super("Пекарня", "Возьмите одну монету из банка. В свой ход", "2_3-3.png", 1);
        type = Cards.BAKERY;
        minDiceValue = 2;
        maxDiceValue = 3;
        value = 1;
        activityType = ActivityTypes.SHOP;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return 1;
    }
}
