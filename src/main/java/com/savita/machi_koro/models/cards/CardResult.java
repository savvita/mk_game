package com.savita.machi_koro.models.cards;

import com.savita.machi_koro.models.game.Player;

public class CardResult {
    private CardResultTypes type = CardResultTypes.NONE;
    private Card card;
    private int value = 0;
    private Player from = null;
    private Player to = null;
    public CardResult(Card card, CardResultTypes type) {
        this.card = card;
        this.type = type;
    }
    public CardResult(Card card, CardResultTypes type, int value) {
        this(card, type);
        this.value = value;
    }

    public CardResult(Card card, CardResultTypes type, int value, Player from, Player to) {
        this(card, type, value);
        this.from = from;
        this.to = to;
    }

    public CardResultTypes getType() {
        return type;
    }

    public Card getCard() {
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

    @Override
    public String toString() {
        String res = String.format("%s : ", card.getTitle());
        switch(type) {
            case NONE:
                res += type;
                break;
            case MONEY:
                res += getMoneyMessage();
                break;
            case CAN_THROW_AGAIN:
                res += "Дополнительный ход";
                break;
            case CAN_RETHROW:
                res += "Может перебросить кубики";
                break;
            case CAN_ADD_TO_DICE:
                res += "Может добавить два к значению на кубиках";
                break;
            case CAN_EXCHANGE_COMPANY:
                res += "Может обменяться предприятием с другим игроком";
                break;
            case CAN_STEAL:
                res += String.format("Может взять %d монет у другого игрока", value);
                break;
            case COMPANY_OPENED:
                res += "Предприятие открылось";
                break;
            case CAN_GET_AWAY_COMPANY:
                res += "Может передать предприятие другому игроку";
                break;
            case CAN_DESTROY_CITY:
                res += "Может разрушить достопримечательность";
                break;
        }
        return res;
    }

    private String getMoneyMessage() {
        String res = "Игрок ";

        if(from == null) {
            res += String.format("%s получает %d монет(ы)", to.getName(), value);
        } else {
            res += String.format("%s отдает %d монет(ы)", from.getName(), value);
            if(to != null) {
                res += " игроку " + to.getName();
            }
        }
        return res;
    }
}
