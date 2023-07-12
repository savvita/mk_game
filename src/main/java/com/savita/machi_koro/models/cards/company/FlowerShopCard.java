package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class FlowerShopCard extends GreenCompanyCard {
    public FlowerShopCard() {
        super("Цветочный магазин", "Возьмите одну монету из банка за каждый ваш \"Цветник\". В свой ход", "6-3.png", 1);
        type = Cards.FLOWER_SHOP;
        minDiceValue = 6;
        maxDiceValue = 6;
        value = 1;
        activityType = ActivityTypes.SHOP;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return (int)player.getCompanies().stream().filter(x -> x.getType() == Cards.FLOWER_GARDEN).count();
    }
}
