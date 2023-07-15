package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class DemolitionCompanyCard extends GreenCompanyCard {
    public DemolitionCompanyCard() {
        super("Демонтажная компания", "Переверните карту одной вашей достопримечательности лицевой стороной вниз. Сделав это, возьмите восемь монет из банка. В свой ход", "4-3-2.png", 2);
        type = Cards.DEMOLITION_COMPANY;
        minDiceValue = 4;
        maxDiceValue = 4;
        value = 8;
        activityType = ActivityTypes.BUSINESS;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return 1;
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            if(isClosed) {
                isClosed = false;
                return new CardResult(this, CardResultTypes.COMPANY_OPENED);
            }

            player.getPossibilities().setCanDestroyCity(true);
            player.getPossibilities().setDestroyCityBonus(value);

            return new CardResult(this, CardResultTypes.CAN_DESTROY_CITY);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
