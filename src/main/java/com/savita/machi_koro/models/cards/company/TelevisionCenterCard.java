package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class TelevisionCenterCard extends VioletCompanyCard {
    public TelevisionCenterCard() {
        super("Телецентр", "Возьмите пять монет у одного любого игрока. В свой ход ","6-4-2.png", 7);
        type = Cards.TELEVISION_CENTER;
        minDiceValue = 6;
        maxDiceValue = 6;
        value = 5;
        activityType = ActivityTypes.LARGE_COMPANY;
    }


    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            player.getPossibilities().setCanSteal(true);
            return new CardResult(type, CardResultTypes.CAN_STEAL);
        }

        player.getPossibilities().setCanSteal(false);
        return new CardResult(type, CardResultTypes.NONE);
    }
}
