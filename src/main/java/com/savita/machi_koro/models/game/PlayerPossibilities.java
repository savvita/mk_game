package com.savita.machi_koro.models.game;

import com.savita.machi_koro.models.cards.Cards;

public class PlayerPossibilities {
    private Player player;
    private boolean canAddToDice;
    private boolean hasExtraMove;
    private boolean canSteal;
    private boolean hasExtraMoney;
    private boolean canExchangeCompany;
    private boolean canBuild;
    private boolean canCloseCompany;
    private boolean canThrowAgain;
    private int extraMoneyAmount;
    private int getAwayCompanyBonus;
    private int destroyCityBonus;
    private int stealAmount;
    private boolean canGetAwayCompany;
    private boolean canDestroyCity;
    public PlayerPossibilities(Player player) {
        this.player = player;
    }

    public PlayerPossibilities(){}

    public boolean isCanAddToDice() {
        return canAddToDice;
    }

    public void setCanAddToDice(boolean canAddToDice) {
        this.canAddToDice = canAddToDice;
    }

    public boolean isHasExtraMove() {
        return hasExtraMove;
    }

    public void setHasExtraMove(boolean hasExtraMove) {
        this.hasExtraMove = hasExtraMove;
    }

    public boolean isCanSteal() {
        return canSteal;
    }

    public void setCanSteal(boolean canSteal) {
        this.canSteal = canSteal;
    }

    public boolean isHasExtraMoney() {
        return hasExtraMoney;
    }

    public void setHasExtraMoney(boolean hasExtraMoney) {
        this.hasExtraMoney = hasExtraMoney;
    }

    public boolean isCanExchangeCompany() {
        return canExchangeCompany;
    }

    public void setCanExchangeCompany(boolean canExchangeCompany) {
        this.canExchangeCompany = canExchangeCompany;
    }

    public boolean isCanBuild() {
        return canBuild;
    }

    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }

    public boolean isCanCloseCompany() {
        return canCloseCompany;
    }

    public void setCanCloseCompany(boolean canCloseCompany) {
        this.canCloseCompany = canCloseCompany;
    }

    public boolean isCanThrowAgain() {
        return canThrowAgain;
    }

    public void setCanThrowAgain(boolean canThrowAgain) {
        this.canThrowAgain = canThrowAgain;
    }

    public boolean isCanDeposit() {
        return player.getCompanies().stream().anyMatch(x -> x.getType() == Cards.VENTURE_FUND);
    }

    public int getExtraMoneyAmount() {
        return extraMoneyAmount;
    }

    public void setExtraMoneyAmount(int extraMoneyAmount) {
        this.extraMoneyAmount = extraMoneyAmount;
    }

    public boolean isCanGetAwayCompany() {
        return canGetAwayCompany;
    }

    public void setCanGetAwayCompany(boolean canGetAwayCompany) {
        this.canGetAwayCompany = canGetAwayCompany;
    }

    public int getGetAwayCompanyBonus() {
        return getAwayCompanyBonus;
    }

    public void setGetAwayCompanyBonus(int getAwayCompanyBonus) {
        this.getAwayCompanyBonus = getAwayCompanyBonus;
    }

    public boolean isCanDestroyCity() {
        return canDestroyCity;
    }

    public void setCanDestroyCity(boolean canDestroyCity) {
        this.canDestroyCity = canDestroyCity;
    }

    public int getDestroyCityBonus() {
        return destroyCityBonus;
    }

    public void setDestroyCityBonus(int destroyCityBonus) {
        this.destroyCityBonus = destroyCityBonus;
    }

    public int getStealAmount() {
        return stealAmount;
    }

    public void setStealAmount(int stealAmount) {
        this.stealAmount = stealAmount;
    }
}
