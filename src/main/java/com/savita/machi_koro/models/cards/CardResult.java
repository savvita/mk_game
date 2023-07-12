package com.savita.machi_koro.models.cards;

import com.savita.machi_koro.models.game.Player;

public class CardResult {
    private CardResultTypes type = CardResultTypes.NONE;
    private Cards card;
    private int value = 0;
    private Player from = null;
    private Player to = null;
    public CardResult(Cards card, CardResultTypes type) {
        this.card = card;
        this.type = type;
    }
    public CardResult(Cards card, CardResultTypes type, int value) {
        this(card, type);
        this.value = value;
    }

    public CardResult(Cards card, CardResultTypes type, int value, Player from, Player to) {
        this(card, type, value);
        this.from = from;
        this.to = to;
    }

    public CardResultTypes getType() {
        return type;
    }

    public Cards getCard() {
        return card;
    }

    public int getValue() {
        return value;
    }

    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }
}
