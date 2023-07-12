package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class ShopCard extends GreenCompanyCard {
    public ShopCard() {
        super("Магазин", "Возьмите три монеты из банка. В свой ход", "4-3.png", 2);
        type = Cards.SHOP;
        minDiceValue = 4;
        maxDiceValue = 4;
        value = 3;
        activityType = ActivityTypes.SHOP;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return 1;
    }
}
