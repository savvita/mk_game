package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class CleaningCompanyCard extends VioletCompanyCard {
    public CleaningCompanyCard()  {
        super("Клининговая компания", "Выберите одно предприятие (кроме крупного предприятия). Все предприятия этого вида, построенные всеми игроками временно закрываются. Возьмите одну монету из банка за каждое предприятие, временно закрытое этим эффектом. В свой ход ","8-4.png", 4);
        type = Cards.CLEANING_COMPANY;
        minDiceValue = 8;
        maxDiceValue = 8;
        value = 1;
        activityType = ActivityTypes.LARGE_COMPANY;
    }


    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            player.getPossibilities().setCanCloseCompany(true);
            return new CardResult(this, CardResultTypes.CAN_EXCHANGE_COMPANY);
        }

        player.getPossibilities().setCanCloseCompany(false);
        return new CardResult(this, CardResultTypes.NONE);
    }
}
