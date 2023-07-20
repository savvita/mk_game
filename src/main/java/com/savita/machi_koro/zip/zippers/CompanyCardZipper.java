package com.savita.machi_koro.zip.zippers;

import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.zip.models.CompanyCardCompressed;

public class CompanyCardZipper {

    public static CompanyCardCompressed zip(CompanyCard card) {
        return new CompanyCardCompressed(card);
    }

    public static CompanyCard unzip(CompanyCardCompressed compressed, CompanyCard card) {
        card.setClosed(compressed.isClosed());
        return card;
    }
}
