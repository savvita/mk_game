package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class TaxOfficeCard extends VioletCompanyCard {
    public TaxOfficeCard() {
        super("Налоговая инспекция", "Возьмите половину монет у каждого игрока с 10 монетами или больше (округляя вниз). В свой ход ","8_9-4.png", 4);
        type = Cards.TAX_OFFICE;
        minDiceValue = 8;
        maxDiceValue = 9;
        value = -1;
        activityType = ActivityTypes.LARGE_COMPANY;
    }


    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            int sum = 0;
            Player activePlayer = game.getActivePlayer();
            var players = game.getPlayers().stream().filter(x -> x != activePlayer).toList();
            for(Player pl : players) {
                if(pl.getAccount().getAmount() >= 10) {
                    int amount = pl.getAccount().getAmount() / 2;
                    activePlayer.getAccount().increase(amount);
                    pl.getAccount().decrease(amount);
                    sum += amount;
                }
            }
            return new CardResult(this, CardResultTypes.MONEY, sum, null, activePlayer);
        }
        return new CardResult(this, CardResultTypes.NONE);
    }
}
