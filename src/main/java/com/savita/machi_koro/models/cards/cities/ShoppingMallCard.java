package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.company.ActivityTypes;
import com.savita.machi_koro.models.game.Player;

public class ShoppingMallCard extends CityCard {
    public ShoppingMallCard() {
        super("Торговый центр", "Каждое ваше заведение питания и магазин приносит на одну монету больще", "c10.png", 10);
        type = Cards.SHOPPING_MALL;
        value = 1;
    }

    @Override
    public void build(Player player) {
        super.build(player);

        player.addAdditionalValue(ActivityTypes.SHOP, value);
        player.addAdditionalValue(ActivityTypes.RESTAURANT, value);
    }
    @Override
    public void destroy(Player player) {
        super.destroy(player);
        player.removeAdditionalValue(ActivityTypes.SHOP, value);
        player.removeAdditionalValue(ActivityTypes.RESTAURANT, value);
    }
}
