package com.savita.machi_koro.models.game;

public class BankAccount {
    private int amount;
    private int depositCount;
    public void increase(int value) {
        amount += value;
    }

    public void decrease(int value) {
        amount = Math.max(amount - value, 0);
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public int getDepositCount() {
        return depositCount;
    }

    public void setDepositCount(int depositCount) {
        this.depositCount = depositCount;
    }

    public boolean deposit() {
        if(amount > 0) {
            depositCount++;
            amount--;
            return true;
        }

        return false;
    }
}
