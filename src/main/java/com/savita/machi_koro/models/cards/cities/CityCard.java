package com.savita.machi_koro.models.cards.cities;

import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public abstract class CityCard extends Card {
    protected boolean isBuilt = false;
    protected int value;
    public CityCard(String title, String description, String image, int price) {
        super(title, description, image, price);
    }
    public int getValue() {
        return value;
    }
    public boolean isBuilt() {
        return isBuilt;
    }
    public void build(Player player) {
        isBuilt = true;
    }
    public CardResult apply(Game game, Player player) {
        if(game.isActive(player) && isBuilt) {
            return applyCard(game, player);
        }
        return new CardResult(this, CardResultTypes.NONE);
    }

    protected CardResult applyCard(Game game, Player player) {
        return new CardResult(this, CardResultTypes.NONE);
    }
    public void destroy(Player player) {
        isBuilt = false;
    }
}
