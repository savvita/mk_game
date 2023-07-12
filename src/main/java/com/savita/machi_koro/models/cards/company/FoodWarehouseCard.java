package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class FoodWarehouseCard extends GreenCompanyCard {
    public FoodWarehouseCard() {
        super("Склад продовольствия", "Возьмите две монеты из банка за каждое ваше заведение питания. В свой ход", "12_13-3.png", 2);
        type = Cards.FOOD_WAREHOUSE;
        minDiceValue = 12;
        maxDiceValue = 13;
        value = 2;
        activityType = ActivityTypes.FACTORY;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return player.getCompaniesCount(ActivityTypes.RESTAURANT);
    }
}
