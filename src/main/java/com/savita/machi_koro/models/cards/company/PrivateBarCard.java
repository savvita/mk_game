package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class PrivateBarCard extends RedCompanyCard {
    public PrivateBarCard() {
        super("Частный бар", "Возьмите все монеты у игрока, бросившего кубики, если у него три или более достопримечательности. В ход другого игрока", "12_14-2.png", 4);
        type = Cards.PRIVATE_BAR;
        minDiceValue = 12;
        maxDiceValue = 14;
        value = -1;
        activityType = ActivityTypes.RESTAURANT;
        isConditionPassed = x -> x.getBuiltCitiesCount() >= 3;
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
