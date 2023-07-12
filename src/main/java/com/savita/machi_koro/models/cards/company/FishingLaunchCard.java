package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class FishingLaunchCard extends BlueCompanyCard {
    public FishingLaunchCard() {
        super("Рыбацкий баркас", "Если у вас есть \"Порт\", возьмите три монеты из банка. В ход любого игрока","8-1.png", 2);
        type = Cards.FISHING_LAUNCH;
        minDiceValue = 8;
        maxDiceValue = 8;
        value = 3;
        activityType = ActivityTypes.FISHING;
    }

    @Override
    protected boolean isApplied(Game game, Player player) {
        return super.isApplied(game, player) && player.hasCityCard(Cards.PORT);
    }
}
