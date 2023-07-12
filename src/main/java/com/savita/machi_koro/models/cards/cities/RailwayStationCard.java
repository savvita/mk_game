package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Player;

public class RailwayStationCard extends CityCard {
    public RailwayStationCard() {
        super("Вокзал", "Можете бросать два кубика вместо одного. В свой ход", "c4.png", 4);
        type = Cards.RAILWAY_STATION;
        value = 2;
    }

    @Override
    public void build(Player player) {
        super.build(player);
        player.setDiceCount(value);
    }
    @Override
    public void destroy(Player player) {
        super.destroy(player);
        player.setDiceCount(1);
    }
}
