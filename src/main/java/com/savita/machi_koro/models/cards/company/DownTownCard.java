package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class DownTownCard extends VioletCompanyCard {
    public DownTownCard() {
        super("Деловой центр", "Можете обменяться одной картой предприятия с другим игроком. Нельзя меняться картами больших предприятий. В свой ход ","6-4-1.png", 8);
        type = Cards.DOWNTOWN;
        minDiceValue = 6;
        maxDiceValue = 6;
        value = 0;
        activityType = ActivityTypes.LARGE_COMPANY;
    }


    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            player.getPossibilities().setCanExchangeCompany(true);
            return new CardResult(type, CardResultTypes.CAN_EXCHANGE_COMPANY);
        }

        player.getPossibilities().setCanExchangeCompany(false);
        return new CardResult(type, CardResultTypes.NONE);
    }
}
