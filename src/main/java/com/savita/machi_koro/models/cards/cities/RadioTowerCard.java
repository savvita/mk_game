package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class RadioTowerCard extends CityCard {
    public RadioTowerCard() {
        super("Радиовышка", "Один раз можете перебросить кубики. В свой ход", "c22.png", 22);
        type = Cards.RADIO_TOWER;
    }

    @Override
    protected CardResult applyCard(Game game, Player player) {
        // TODO override this
        player.getPossibilities().setCanThrowAgain(!player.getPossibilities().isCanThrowAgain());
        if(player.getPossibilities().isCanThrowAgain()) {
            return new CardResult(this, CardResultTypes.CAN_RETHROW);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
