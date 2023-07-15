package com.savita.machi_koro.models.cards.company;

import com.savita.machi_koro.models.cards.CardResult;
import com.savita.machi_koro.models.cards.CardResultTypes;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;

public class CreditBankCard extends GreenCompanyCard {
    private final int creditValue;
    public CreditBankCard() {
        super("Кредитный банк", "Отдайте две монеты в банк. В свой ход. Построив это предприятие, возьмите пять монет из банка", "5_6-3.png", 0);
        type = Cards.CREDIT_BANK;
        minDiceValue = 5;
        maxDiceValue = 6;
        value = 2;
        activityType = ActivityTypes.BUSINESS;
        creditValue = 5;
    }

    @Override
    protected int getAmount(Game game, Player player) {
        return 1;
    }

    @Override
    public void build(Player player) {
        player.getAccount().increase(creditValue);
    }

    @Override
    public CardResult apply(Game game, Player player) {
        if(isApplied(game, player)) {
            if(isClosed) {
                isClosed = false;
                return new CardResult(this, CardResultTypes.COMPANY_OPENED);
            }

            player.getAccount().decrease(value);

            return new CardResult(this, CardResultTypes.MONEY, value, player, null);
        }

        return new CardResult(this, CardResultTypes.NONE);
    }
}
