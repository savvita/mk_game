package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class ParcCard  extends VioletCompanyCard {
    public ParcCard()  {
        super("Парк", "Соберите монеты всех игроков и распределите их поровну между всеми игроками. Если поровну распределить не получается, возьмите недостающее количество из банка, после чего распределите монеты. В свой ход","11_13-4.png", 3);
        type = Cards.PARC;
        minDiceValue = 11;
        maxDiceValue = 13;
        value = 0;
        activityType = ActivityTypes.LARGE_COMPANY;
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            var players = game.getPlayers();
            int sum = 0;
            for(Player pl : players) {
                sum += pl.getAccount().getAmount();
            }
            if(sum % players.size() != 0) {
                sum += (players.size() - sum % players.size());
            }
            int amount = sum / players.size();
            for(Player pl : players) {
                pl.getAccount().setAmount(amount);
            }
            return new CardResult(this, CardResultTypes.MONEY, amount, null, player);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
