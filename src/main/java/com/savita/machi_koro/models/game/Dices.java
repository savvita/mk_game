package com.savita.machi_koro.models.game;

import java.util.Arrays;

public class Dices {
    public final int maxDiceCount = 2;
    private int[] dices = new int[maxDiceCount];
    public int[] getDices() {
        return dices;
    }
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
        if(count > maxDiceCount) return;
        for(int i = 0; i < count; i++) {
            this.dices[i] = 1 + (int) (Math.random() * 6);
        }
        for(int i = count; i < maxDiceCount; i++) {
            this.dices[i] = 0;
        }
    }
}
