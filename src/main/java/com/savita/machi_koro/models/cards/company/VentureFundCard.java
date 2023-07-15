package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class VentureFundCard extends VioletCompanyCard {
    public VentureFundCard()  {
        super("Венчурный фонд", "Возьмите у каждого игрока столько монет, сколько лежит на этой карте. В свой ход. В конце каждого своего хода можете положить одну свою монету на эту карту","10-4.png", 1);
        type = Cards.VENTURE_FUND;
        minDiceValue = 10;
        maxDiceValue = 10;
        value = 1;
        activityType = ActivityTypes.LARGE_COMPANY;
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            Player activePlayer = game.getActivePlayer();
            var players = game.getPlayers().stream().filter(x -> x != activePlayer).toList();
            int sum = 0;
            for(Player pl : players) {
                int amount = Math.min(pl.getAccount().getAmount(), value * activePlayer.getAccount().getDepositCount());
                activePlayer.getAccount().increase(amount);
                pl.getAccount().decrease(amount);
                sum += amount;
            }
            return new CardResult(this, CardResultTypes.MONEY, sum, null, activePlayer);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
