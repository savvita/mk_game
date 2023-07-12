package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class SupermarketCard extends GreenCompanyCard {
    public SupermarketCard() {
        super("Универсам", "Возьмите две монеты из банка, если у вас не более одной достопримечательности. В свой ход", "2-3.png", 0);
        type = Cards.SUPERMARKET;
        minDiceValue = 2;
        maxDiceValue = 2;
        value = 2;
        activityType = ActivityTypes.SHOP;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return 1;
    }
    @Override
    protected boolean isApplied(Game game, Player player) {
        return super.isApplied(game, player) && player.getBuiltCitiesCount() <= 1;
    }
}
