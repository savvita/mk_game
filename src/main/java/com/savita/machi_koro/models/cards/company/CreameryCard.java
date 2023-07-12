package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class CreameryCard extends GreenCompanyCard {
    public CreameryCard() {
        super("Сыроварня", "Возьмите три монеты из банка за каждое ваше животноводческое предприятие. В свой ход", "7-3.png", 5);
        type = Cards.CREAMERY;
        minDiceValue = 7;
        maxDiceValue = 7;
        value = 3;
        activityType = ActivityTypes.FACTORY;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return player.getCompaniesCount(ActivityTypes.ANIMAL_HUSBANDRY);
    }
}
