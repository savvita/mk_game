package com.savita.machi_koro.zip.models;

import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.cities.CityCard;

public class CityCardCompressed {
    private Cards type;
    private boolean isBuilt;
    public CityCardCompressed(Cards type, boolean isBuilt) {
        this.type = type;
        this.isBuilt = isBuilt;
    }

    public CityCardCompressed(CityCard card) {
        this.type = card.getType();
        this.isBuilt = card.isBuilt();
    }

    public Cards getType() {
        return type;
    }

    public boolean isBuilt() {
        return isBuilt;
    }
}
