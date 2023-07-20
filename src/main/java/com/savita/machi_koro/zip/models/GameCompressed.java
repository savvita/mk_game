package com.savita.machi_koro.zip.models;

import com.savita.machi_koro.models.game.Dices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameCompressed {
    private final List<PlayerCompressed> players = new ArrayList<>(4);
    private PlayerCompressed activePlayer;
    private PlayerCompressed winner;
    private Dices dices = new Dices();
    private boolean isStarted;
    private int diceSum;
    private final List<CompanyCardCompressed> companies = new ArrayList<>();
    private final Collection<String> news = new ArrayList<>();

    public List<PlayerCompressed> getPlayers() {
        return players;
    }

    public PlayerCompressed getWinner() {
        return winner;
    }

    public void setWinner(PlayerCompressed winner) {
        this.winner = winner;
    }

    public PlayerCompressed getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(PlayerCompressed activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Dices getDices() {
        return dices;
    }

    public void setDices(Dices dices) {
        this.dices = dices;
    }

    public int getDiceSum() {
        return diceSum;
    }

    public void setDiceSum(int diceSum) {
        this.diceSum = diceSum;
    }

    public List<CompanyCardCompressed> getCompanies() {
        return companies;
    }

    public Collection<String> getNews() {
        return news;
    }

    public void setNews(Collection<String> news) {
        this.news.clear();
        this.news.addAll(news);
    }
    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
