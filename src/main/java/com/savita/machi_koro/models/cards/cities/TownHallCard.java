package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class TownHallCard extends CityCard {
    public TownHallCard() {
        super("Ратуша", "Если у вас нет монет, возьмите одну монету из банка. В свой ход перед строительством", "c0.png", 0);
        type = Cards.TOWN_HALL;
        value = 1;
    }

    @Override
    protected CardResult applyCard(Game game, Player player) {
        if(player.getAccount().getAmount() == 0) {
            player.getAccount().setAmount(value);
            return new CardResult(this, CardResultTypes.MONEY, value, null, player);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
