package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class WineryCard extends GreenCompanyCard {
    public WineryCard() {
        super("Винный завод", "Возьмите шесть монет из банка за каждый ваш \"Виноградник\", после чего \"Винный завод\" временно закрывается. В свой ход", "9-3.png", 3);
        type = Cards.WINERY;
        minDiceValue = 9;
        maxDiceValue = 9;
        value = 6;
        activityType = ActivityTypes.FACTORY;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return (int)player.getCompanies().stream().filter(x -> x.getType() == Cards.VINEYARD).count();
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            if(isClosed) {
                isClosed = false;
                return new CardResult(this, CardResultTypes.COMPANY_OPENED);
            }

            int amount = getAmount(game, player);
            int result = amount * (value + player.getAdditionalValue(activityType));
            player.getAccount().increase(result);
            isClosed = true;
            return new CardResult(this, CardResultTypes.MONEY, result, null, player);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
