package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class PrestigiousRestaurantCard extends RedCompanyCard {
    public PrestigiousRestaurantCard() {
        super("Престижный ресторан", "Возьмите пять монет у игрока, бросившего кубики, если у него две или более достопримечательности. В ход другого игрока", "5-2.png", 3);
        type = Cards.PRESTIGIOUS_RESTAURANT;
        minDiceValue = 5;
        maxDiceValue = 5;
        value = 5;
        activityType = ActivityTypes.RESTAURANT;
        isConditionPassed = x -> x.getBuiltCitiesCount() >= 2;
    }

    @Override
    protected boolean isApplied(Game game, Player player) {
        var dice = game.getDiceSum();
        if(dice < minDiceValue || dice > maxDiceValue) return false;
        if(isConditionPassed != null && !isConditionPassed.test(game.getActivePlayer())) {
            return false;
        }
        return !game.isActive(player);
    }
}
