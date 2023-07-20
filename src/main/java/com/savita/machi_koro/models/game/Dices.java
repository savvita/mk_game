package com.savita.machi_koro.models.game;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Arrays;

public class Dices {
    public final int maxDiceCount = 2;
    private int[] dices = new int[maxDiceCount];
    public int[] getDices() {
        return dices;
    }
    private int count;
    private transient StringProperty value = new SimpleStringProperty();
    public int getSum() {
        return Arrays.stream(dices).sum();
    }
    public boolean isDouble() {
        if(dices.length == 2) {
            return dices[0] == dices[1];
        }

        return false;
    }
    public void throwDices(int count) {
        if(count > maxDiceCount) {
            this.count = 0;
            return;
        }
        for(int i = 0; i < count; i++) {
            this.dices[i] = 1 + (int) (Math.random() * 6);
        }
        for(int i = count; i < maxDiceCount; i++) {
            this.dices[i] = 0;
        }

        this.count = count;
        setValue(this.toString());
    }

    public String getValue() {
        return value.get();
    }

    public StringProperty valueProperty() {
        return value;
    }

    public void setValue(String value) {
        this.value.set(value);
    }

    public void setDices(int[] values) {
        for(int i = 0; i < this.dices.length && i < values.length; i++) {
            this.dices[i] = values[i];
        }
        for(int i = values.length; i < this.dices.length; i++) {
            this.dices[i] = 0;
        }
        count = Math.min(this.dices.length, (int)Arrays.stream(values).filter(x -> x != 0).count());
        setValue(this.toString());
    }

    @Override
    public String toString() {
        if(count == 1) return String.valueOf(dices[0]);
        if(count == 2) return String.format("%d - %d", dices[0], dices[1]);
        return "";
    }
}
