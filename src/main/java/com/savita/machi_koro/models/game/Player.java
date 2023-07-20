package com.savita.machi_koro.models.game;

import com.savita.machi_koro.models.cards.CardFactory;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.cities.*;
import com.savita.machi_koro.models.cards.company.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Player {
    /* FIELDS */
    private int diceCount = 1;
    private final BankAccount account = new BankAccount();
    private final PlayerPossibilities possibilities;
    private final List<CompanyCard> companies = new ArrayList<>();
    private final List<CityCard> cities = new ArrayList<>();
    private final HashMap<ActivityTypes, Integer> additionValues = new HashMap<>();
    private final String name;

    /* END FIELDS */

    /* GETTERS */

    public BankAccount getAccount() {
        return account;
    }
    public PlayerPossibilities getPossibilities() {
        return possibilities;
    }
    public int getAdditionalValue(ActivityTypes type) {
        var res = additionValues.get(type);
        return res != null ? res : 0;
    }
    public List<CompanyCard> getCompanies() {
        return companies;
    }
    public List<CityCard> getCities() {
        return cities;
    }
    public int getDiceCount() {
        return diceCount;
    }

    public String getName() {
        return name;
    }

    public HashMap<ActivityTypes, Integer> getAdditionValues() {
        return additionValues;
    }

    /* END GETTERS */

    public Player(String name) {
        this.name = name;
        initializeCity();
        initializeCompanies();
        account.setAmount(GameConstants.BANK_AMOUNT);
        possibilities = new PlayerPossibilities(this);
    }

    private void initializeCity() {
        try {
            cities.add(CardFactory.createCity(Cards.TOWN_HALL));
            cities.add(CardFactory.createCity(Cards.PORT));
            cities.add(CardFactory.createCity(Cards.RAILWAY_STATION));
            cities.add(CardFactory.createCity(Cards.SHOPPING_MALL));
            cities.add(CardFactory.createCity(Cards.AMUSEMENT_PARK));
            cities.add(CardFactory.createCity(Cards.RADIO_TOWER));
            cities.add(CardFactory.createCity(Cards.AIRPORT));

            var optional = cities.stream().filter(x -> x.getType() == Cards.TOWN_HALL).findFirst();

            optional.ifPresent(cityCard -> cityCard.build(this));
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void initializeCompanies() {
        companies.add(new WheatFieldCard());
        companies.add(new BakeryCard());
    }

    public void addAdditionalValue(ActivityTypes type, int value) {
        if(additionValues.containsKey(type)) {
            additionValues.put(type, additionValues.get(type) + value);
        } else {
            additionValues.put(type, value);
        }
    }

    public void removeAdditionalValue(ActivityTypes type, int value) {
        if(additionValues.containsKey(type)) {
            additionValues.put(type, additionValues.get(type) - value);
        }
    }

    public boolean buildCompany(CompanyCard card) {
        if(!possibilities.isCanBuild()) return false;

        if(card.getActivityType() == ActivityTypes.LARGE_COMPANY && companies.stream().anyMatch(x -> x.getType() == card.getType()))
            return false;

        if(account.getAmount() < card.getPrice()) {
            return false;
        }

        companies.add(card);
        account.decrease(card.getPrice());
        card.build(this);

        possibilities.setCanBuild(false);
        return true;
    }
    public boolean buildCity(Cards type) {
        if(!possibilities.isCanBuild()) return false;

        Optional<CityCard> optionalCity = cities.stream().filter(x -> x.getType() == type).findFirst();
        if(optionalCity.isEmpty()) {
            return false;
        }
        var city = optionalCity.get();

        if(city.isBuilt()) return false;

        if(account.getAmount() < city.getPrice()) {
            return false;
        }

        city.build(this);
        account.decrease(city.getPrice());
        possibilities.setCanBuild(false);
        return true;
    }

    public boolean destroyCity(Cards type) {
        if(!possibilities.isCanDestroyCity()) return false;
        var optional = cities.stream().filter(x -> x.getType() == type).findFirst();
        if(optional.isEmpty()) {
            return false;
        }
        var city = optional.get();
        if(!city.isBuilt()) return false;

        city.destroy(this);
        account.increase(possibilities.getDestroyCityBonus());
        possibilities.setCanDestroyCity(false);
        return true;
    }

    public int getBuiltCitiesCount() {
        return (int)cities.stream().filter(CityCard::isBuilt).count();
    }
    public int getCompaniesCount(ActivityTypes type) {
        return (int)companies.stream().filter(x -> x.getActivityType() == type).count();
    }

    public boolean hasCityCard(Cards type) {
        var optional = cities.stream().filter(x -> x.getType() == type).findFirst();
        return optional.map(CityCard::isBuilt).orElse(false);
    }

    public void setDiceCount(int diceCount) {
        if(diceCount > 0) {
            this.diceCount = diceCount;
        }
    }

    public boolean takeExtraMoney() {
        if(!possibilities.isHasExtraMoney()) return false;
        account.increase(possibilities.getExtraMoneyAmount());
        possibilities.setCanBuild(false);
        possibilities.setHasExtraMoney(false);
        return true;
    }

    public void addCompany(CompanyCard card) {
        companies.add(card);
    }
    public boolean removeCompany(CompanyCard card) {
        return companies.remove(card);
    }

    public boolean exchangeCompany(Player player, CompanyCard outgo, CompanyCard income) {
        if(!possibilities.isCanExchangeCompany()) return false;

        if(outgo.getActivityType() == ActivityTypes.LARGE_COMPANY || income.getActivityType() == ActivityTypes.LARGE_COMPANY) {
            return false;
        }
        player.removeCompany(income);
        player.addCompany(outgo);

        addCompany(income);
        removeCompany(outgo);

        possibilities.setCanExchangeCompany(false);
        return true;
    }

    public int steal(Player player, int amount) {
        if(!possibilities.isCanSteal()) return 0;
        int actualValue = Math.min(amount, player.getAccount().getAmount());
        account.increase(actualValue);
        player.account.decrease(actualValue);
        possibilities.setCanSteal(false);
        return actualValue;
    }

    public boolean deposit() {
        if(possibilities.isCanDeposit()) {
            return account.deposit();
        }

        return false;
    }

}
