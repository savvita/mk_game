package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class TrawlerCard extends BlueCompanyCard {
    public TrawlerCard() {
        super("Траулер", "Активный игрок бросает два кубика. Если у вас есть \"Порт\", возьмите из банка столько монет, сколько выпало на кубиках. В ход любого игрока","12_14-1.png", 5);
        type = Cards.TRAWLER;
        minDiceValue = 12;
        maxDiceValue = 14;
        value = 1;
        activityType = ActivityTypes.FISHING;
    }

    @Override
    protected boolean isApplied(Game game, Player player) {
        return super.isApplied(game, player) && player.hasCityCard(Cards.PORT);
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            if(isClosed) {
                if(game.isActive(player)) {
                    isClosed = false;
                    return new CardResult(this, CardResultTypes.COMPANY_OPENED);
                }
                return new CardResult(this, CardResultTypes.NONE);
            }

            int result = value * game.getTrawlerAmount() + player.getAdditionalValue(activityType);
            player.getAccount().increase(result);
            return new CardResult(this, CardResultTypes.MONEY, result, null, player);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
