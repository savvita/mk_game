package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

abstract class VioletCompanyCard extends CompanyCard {
    public VioletCompanyCard(String title, String description, String image, int price) {
        super(title, description, image, price);
        color = CompanyColors.VIOLET;
    }

    protected boolean isApplied(Game game, Player player) {
        var dice = game.getDiceSum();
        return dice >= minDiceValue && dice <= maxDiceValue && game.isActive(player);
    }

    @Override
    public void close() {

    }
}
