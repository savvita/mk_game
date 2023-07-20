package com.savita.machi_koro.zip.models;

import com.savita.machi_koro.models.cards.company.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerCompressed {
    private String name;
    private int diceCount;
    private int bankAmount;
    private int depositAmount;
    private final List<CompanyCardCompressed> companies = new ArrayList<>();
    private final List<CityCardCompressed> cities = new ArrayList<>();
    private HashMap<ActivityTypes, Integer> additionValues = new HashMap<>();

    public int getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(int diceCount) {
        this.diceCount = diceCount;
    }

    public int getBankAmount() {
        return bankAmount;
    }

    public void setBankAmount(int bankAmount) {
        this.bankAmount = bankAmount;
    }

    public int getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(int depositAmount) {
        this.depositAmount = depositAmount;
    }

    public List<CompanyCardCompressed> getCompanies() {
        return companies;
    }

    public List<CityCardCompressed> getCities() {
        return cities;
    }

    public HashMap<ActivityTypes, Integer> getAdditionValues() {
        return additionValues;
    }

    public void setAdditionValues(HashMap<ActivityTypes, Integer> additionValues) {
        this.additionValues = additionValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
