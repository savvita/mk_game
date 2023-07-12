package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class BeverageFactoryCard extends GreenCompanyCard {
    public BeverageFactoryCard() {
        super("Завод напитков", "Возьмите из банка столько монет, сколько заведений питания построено у всех игроков. В свой ход", "11-3.png", 5);
        type = Cards.BEVERAGE_FACTORY;
        minDiceValue = 11;
        maxDiceValue = 11;
        value = 1;
        activityType = ActivityTypes.FACTORY;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        int sum = 0;
        var players = game.getPlayers();
        for(Player pl : players) {
            sum += pl.getCompaniesCount(ActivityTypes.RESTAURANT);
        }
        return sum;
    }
}
