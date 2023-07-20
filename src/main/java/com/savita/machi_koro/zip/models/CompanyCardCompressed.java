package com.savita.machi_koro.zip.models;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.company.CompanyCard;

public class CompanyCardCompressed {
    private Cards type;
    private boolean isClosed;
    public CompanyCardCompressed(Cards type, boolean isClosed) {
        this.type = type;
        this.isClosed = isClosed;
    }

    public CompanyCardCompressed(CompanyCard card) {
        this.type = card.getType();
        this.isClosed = card.isClosed();
    }

    public Cards getType() {
        return type;
    }

    public boolean isClosed() {
        return isClosed;
    }
}