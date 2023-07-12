package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class FurnitureFactoryCard extends GreenCompanyCard {
    public FurnitureFactoryCard() {
        super("Мебельная фабрика", "Возьмите три монеты из банка за каждое ваше рабочее предприятие. В свой ход", "8-3.png", 3);
        type = Cards.FURNITURE_FACTORY;
        minDiceValue = 8;
        maxDiceValue = 8;
        value = 3;
        activityType = ActivityTypes.FACTORY;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return player.getCompaniesCount(ActivityTypes.WORKER);
    }
}
