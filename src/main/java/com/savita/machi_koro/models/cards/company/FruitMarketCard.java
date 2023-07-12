package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class FruitMarketCard extends GreenCompanyCard {
    public FruitMarketCard() {
        super("Фруктовый рынок", "Возьмите две монеты из банка за каждое ваше аграрное предприятие. В свой ход", "11_12-3.png", 2);
        type = Cards.FRUIT_MARKET;
        minDiceValue = 11;
        maxDiceValue = 12;
        value = 2;
        activityType = ActivityTypes.FRUITS;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return player.getCompaniesCount(ActivityTypes.AGRICULTURE);
    }
}
