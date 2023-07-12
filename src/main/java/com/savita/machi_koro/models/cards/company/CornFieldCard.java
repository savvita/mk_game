package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class CornFieldCard extends BlueCompanyCard {
    public CornFieldCard() {
        super("Кукурузное поле", "Возьмите одну монету из банка, если у вас не более одной достопримечательности. В ход любого игрока","3_4-1.png", 2);
        type = Cards.CORN_FIELD;
        minDiceValue = 3;
        maxDiceValue = 4;
        value = 1;
        activityType = ActivityTypes.AGRICULTURE;
    }

    @Override
    protected boolean isApplied(Game game, Player player) {
        return super.isApplied(game, player) && player.getBuiltCitiesCount() <= 1;
    }
}
