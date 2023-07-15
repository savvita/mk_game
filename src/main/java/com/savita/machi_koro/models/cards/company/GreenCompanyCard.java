package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

abstract class GreenCompanyCard extends CompanyCard {
    public GreenCompanyCard(String title, String description, String image, int price) {
        super(title, description, image, price);
        color = CompanyColors.GREEN;
    }

    protected boolean isApplied(Game game, Player player) {
        var dice = game.getDiceSum();
        return dice >= minDiceValue && dice <= maxDiceValue && game.isActive(player);
    }

    protected abstract int getAmount(Game game, Player player);

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
            return new CardResult(this, CardResultTypes.MONEY, result, null, player);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
