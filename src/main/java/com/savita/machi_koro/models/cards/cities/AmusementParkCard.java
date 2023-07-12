package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class AmusementParkCard extends CityCard {
    public AmusementParkCard() {
        super("Парк развлечений", "Если на кубиках выпал дубль, сделайте еще один ход. В свой ход", "c16.png", 16);
        type = Cards.AMUSEMENT_PARK;
    }

    @Override
    protected CardResult applyCard(Game game, Player player) {
        if(game.getDices().isDouble()) {
            player.getPossibilities().setHasExtraMove(true);
            return new CardResult(type, CardResultTypes.CAN_THROW_AGAIN);
        } else {
            player.getPossibilities().setHasExtraMove(false);
            return new CardResult(type, CardResultTypes.NONE);
        }
    }
}
