package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class StadiumCard extends VioletCompanyCard {
    public StadiumCard() {
        super("Стадион", "Возьмите две монеты у каждого игрока. В свой ход ","6-4-3.png", 6);
        type = Cards.STADIUM;
        minDiceValue = 6;
        maxDiceValue = 6;
        value = 2;
        activityType = ActivityTypes.LARGE_COMPANY;
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            int sum = 0;
            Player activePlayer = game.getActivePlayer();
            var players = game.getPlayers().stream().filter(x -> x != activePlayer).toList();
            for(Player pl : players) {
                int amount = Math.min(pl.getAccount().getAmount(), value);
                activePlayer.getAccount().increase(amount);
                pl.getAccount().decrease(amount);
                sum += amount;
            }
            return new CardResult(this, CardResultTypes.MONEY, sum, null, activePlayer);
        }
        return new CardResult(this, CardResultTypes.NONE);
    }
}
