package com.savita.machi_koro.zip.zippers;

import com.savita.machi_koro.models.cards.cities.CityCard;
import com.savita.machi_koro.zip.models.CityCardCompressed;

public class CityCardZipper {
    public static CityCardCompressed zip(CityCard card) {
        return new CityCardCompressed(card);
    }

    public CityCard unzip(CityCardCompressed compressed, CityCard card) {
        card.setBuilt(compressed.isBuilt());
        return card;
    }
}
