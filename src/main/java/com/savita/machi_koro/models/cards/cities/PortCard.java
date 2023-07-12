package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class PortCard extends CityCard {
    public PortCard() {
        super("Порт", "Если на кубиках выпало \"10\" или больше, можете добавить \"2\" к результату броска. В свой ход", "c2.png", 2);
        type = Cards.PORT;
        value = 2;
    }

    @Override
    protected CardResult applyCard(Game game, Player player) {
        var dice = game.getDiceSum();
        if (dice >= 10 ) {
            player.getPossibilities().setCanAddToDice(true);
            return new CardResult(type, CardResultTypes.CAN_ADD_TO_DICE);
        } else {
            player.getPossibilities().setCanAddToDice(false);
            return new CardResult(type, CardResultTypes.NONE);
        }
    }
}
