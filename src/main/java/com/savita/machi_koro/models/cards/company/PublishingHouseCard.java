package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class PublishingHouseCard extends VioletCompanyCard {
    public PublishingHouseCard() {
        super("Издательство", "Возьмите одну монету у каждого игрока за каждое его предприятие питания или магазин. В свой ход ","7-4.png", 5);
        type = Cards.PUBLISHING_HOUSE;
        minDiceValue = 7;
        maxDiceValue = 7;
        value = 1;
        activityType = ActivityTypes.LARGE_COMPANY;
    }


    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            int sum = 0;
            Player activePlayer = game.getActivePlayer();
            var players = game.getPlayers().stream().filter(x -> x != activePlayer).toList();
            for(Player pl : players) {
                int count = (int)pl.getCompanies()
                        .stream()
                        .filter(x -> x.getActivityType() == ActivityTypes.RESTAURANT || x.getActivityType() == ActivityTypes.SHOP)
                        .count();
                int amount = Math.min(pl.getAccount().getAmount(), value * count);
                activePlayer.getAccount().increase(amount);
                pl.getAccount().decrease(amount);
                sum += amount;
            }
            return new CardResult(this, CardResultTypes.MONEY, sum, null, activePlayer);
        }
        return new CardResult(this, CardResultTypes.NONE);
    }
}
