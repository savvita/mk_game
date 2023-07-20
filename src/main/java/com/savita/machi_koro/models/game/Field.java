package com.savita.machi_koro.models.game;

import com.savita.machi_koro.models.cards.company.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Field {
    public static List<CompanyCard> getCompanies() {
        List<CompanyCard> companies = new ArrayList<>(100);
        for(int i = 0; i < GameConstants.COMPANY_COUNT; i++) {
            companies.add(new WheatFieldCard());
            companies.add(new FarmCard());
            companies.add(new ForestCard());
            companies.add(new MineCard());
            companies.add(new AppleGardenCard());
            companies.add(new CornFieldCard());
            companies.add(new VineyardCard());
            companies.add(new FlowerGardenCard());
            companies.add(new FishingLaunchCard());
            companies.add(new TrawlerCard());

            companies.add(new BakeryCard());
            companies.add(new ShopCard());
            companies.add(new CreameryCard());
            companies.add(new FurnitureFactoryCard());
            companies.add(new FruitMarketCard());
            companies.add(new SupermarketCard());
            companies.add(new FoodWarehouseCard());
            companies.add(new TransportCompanyCard());
            companies.add(new DemolitionCompanyCard());
            companies.add(new CreditBankCard());
            companies.add(new WineryCard());
            companies.add(new BeverageFactoryCard());
            companies.add(new FlowerShopCard());

            companies.add(new SushiCard());
            companies.add(new CafeCard());
            companies.add(new PrestigiousRestaurantCard());
            companies.add(new PizzeriaCard());
            companies.add(new EateryCard());
            companies.add(new RestaurantCard());
            companies.add(new PrivateBarCard());

            companies.add(new DownTownCard());
            companies.add(new TelevisionCenterCard());
            companies.add(new StadiumCard());
            companies.add(new PublishingHouseCard());
            companies.add(new TaxOfficeCard());
            companies.add(new CleaningCompanyCard());
            companies.add(new VentureFundCard());
            companies.add(new ParcCard());
        }
        return companies;
    }
}
