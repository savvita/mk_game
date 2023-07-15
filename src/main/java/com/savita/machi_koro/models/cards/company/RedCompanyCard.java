package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

import java.util.function.Predicate;

abstract class RedCompanyCard extends CompanyCard {
    protected Predicate<Player> isConditionPassed;
    public RedCompanyCard(String title, String description, String image, int price) {
        super(title, description, image, price);
        color = CompanyColors.RED;
    }

    protected boolean isApplied(Game game, Player player) {
        var dice = game.getDiceSum();
        if(dice < minDiceValue || dice > maxDiceValue) return false;
        if(isConditionPassed != null && !isConditionPassed.test(player)) {
            return false;
        }
        return !game.isActive(player);
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            if(isClosed) {
                if(game.isActive(player)) {
                    isClosed = false;
                    return new CardResult(this, CardResultTypes.COMPANY_OPENED);
                }
                return new CardResult(this, CardResultTypes.NONE);
            }

            Player activePlayer = game.getActivePlayer();
            int amount;
            if(getValue() == -1) {
                amount = activePlayer.getAccount().getAmount();
            } else {
                amount = Math.min(activePlayer.getAccount().getAmount(), getValue() + player.getAdditionalValue(getActivityType()));
            }

            activePlayer.getAccount().decrease(amount);
            player.getAccount().increase(amount);

            return new CardResult(this, CardResultTypes.MONEY, amount, activePlayer, player);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
