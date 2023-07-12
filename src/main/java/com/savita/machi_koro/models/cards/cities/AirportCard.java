package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class AirportCard extends CityCard {
    public AirportCard() {
        super("Аэропорт", "Если вы ничего не построили в этот ход, возьмите десять монет из банка. В свой ход", "c30.png", 30);
        type = Cards.AIRPORT;
        value = 10;
    }

    @Override
    public void build(Player player) {
        super.build(player);
        player.getPossibilities().setExtraMoneyAmount(value);
    }
    @Override
    public void destroy(Player player) {
        super.destroy(player);
        player.getPossibilities().setExtraMoneyAmount(0);
    }
    @Override
    protected CardResult applyCard(Game game, Player player) {
        player.getPossibilities().setHasExtraMoney(true);
        return new CardResult(type, CardResultTypes.NONE);
    }
}
