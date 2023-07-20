package com.savita.machi_koro.models.cards;

import com.savita.machi_koro.models.cards.cities.*;
import com.savita.machi_koro.models.cards.company.*;

public class CardFactory {
    public static CompanyCard createCompany(Cards type) throws Exception {
        CompanyCard card;
        switch(type) {
            case BAKERY -> card = new BakeryCard();
            case SHOP -> card = new ShopCard();
            case CREAMERY -> card = new CreameryCard();
            case FURNITURE_FACTORY -> card = new FurnitureFactoryCard();
            case FRUIT_MARKET -> card = new FruitMarketCard();
            case SUPERMARKET -> card = new SupermarketCard();
            case FOOD_WAREHOUSE -> card = new FoodWarehouseCard();
            case TRANSPORT_COMPANY -> card = new TransportCompanyCard();
            case DEMOLITION_COMPANY -> card = new DemolitionCompanyCard();
            case CREDIT_BANK -> card = new CreditBankCard();
            case WINERY -> card = new WineryCard();
            case BEVERAGE_FACTORY -> card = new BeverageFactoryCard();
            case FLOWER_SHOP -> card = new FlowerShopCard();

            case DOWNTOWN -> card = new DownTownCard();
            case TELEVISION_CENTER -> card = new TelevisionCenterCard();
            case STADIUM -> card = new StadiumCard();
            case PUBLISHING_HOUSE -> card = new PublishingHouseCard();
            case TAX_OFFICE -> card = new TaxOfficeCard();
            case CLEANING_COMPANY -> card = new CleaningCompanyCard();
            case VENTURE_FUND -> card = new VentureFundCard();
            case PARC -> card = new ParcCard();

            case SUSHI_BAR -> card = new SushiCard();
            case CAFE -> card = new CafeCard();
            case PRESTIGIOUS_RESTAURANT -> card = new PrestigiousRestaurantCard();
            case PIZZERIA -> card = new PizzeriaCard();
            case EATERY -> card = new EateryCard();
            case RESTAURANT -> card = new RestaurantCard();
            case PRIVATE_BAR -> card = new PrivateBarCard();

            case WHEAT_FIELD -> card = new WheatFieldCard();
            case FARM -> card = new FarmCard();
            case FOREST -> card = new ForestCard();
            case MINE -> card = new MineCard();
            case APPLE_GARDEN -> card = new AppleGardenCard();
            case CORN_FIELD -> card = new CornFieldCard();
            case VINEYARD -> card = new VineyardCard();
            case FLOWER_GARDEN -> card = new FlowerGardenCard();
            case FISHING_LAUNCH -> card = new FishingLaunchCard();
            case TRAWLER -> card = new TrawlerCard();

            default -> throw new Exception("Unknown card type");
        }

        return card;
    }

    public static CityCard createCity(Cards type) throws Exception {
        CityCard card;
        switch(type) {
            case TOWN_HALL -> card = new TownHallCard();
            case PORT -> card = new PortCard();
            case RAILWAY_STATION -> card = new RailwayStationCard();
            case SHOPPING_MALL -> card = new ShoppingMallCard();
            case AMUSEMENT_PARK -> card = new AmusementParkCard();
            case RADIO_TOWER -> card = new RadioTowerCard();
            case AIRPORT -> card = new AirportCard();
            default -> throw new Exception("Unknown card type");
        }

        return card;
    }
}
