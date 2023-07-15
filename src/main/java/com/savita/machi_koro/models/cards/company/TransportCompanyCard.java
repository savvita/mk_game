package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class TransportCompanyCard extends GreenCompanyCard {
    public TransportCompanyCard() {
        super("Транспортная компания", "Передайте одно ваше предприятие (кроме крупных предприятий) другому игроку. Сделав это, возьмите четыре монеты из банка. В свой ход", "9_10-3.png", 2);
        type = Cards.TRANSPORT_COMPANY;
        minDiceValue = 9;
        maxDiceValue = 10;
        value = 4;
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

            player.getPossibilities().setCanGetAwayCompany(true);
            player.getPossibilities().setGetAwayCompanyBonus(value);

            return new CardResult(this, CardResultTypes.CAN_GET_AWAY_COMPANY);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
