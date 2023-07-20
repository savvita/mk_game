package com.savita.machi_koro.models.game;

import com.savita.machi_koro.events.Event;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.cities.CityCard;
import com.savita.machi_koro.models.cards.company.*;
import com.savita.machi_koro.net.Messaging;
import com.savita.machi_koro.zip.models.GameCompressed;
import com.savita.machi_koro.zip.zippers.GameZipper;
import javafx.application.Platform;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Game {
    /* FIELDS */
    private final List<Player> players = new ArrayList<>(4);
    private Player activePlayer;
    private final Dices dices = new Dices();
    private int diceSum;

    private Player winner;
    private final List<CompanyCard> companies = new ArrayList<>();
    private int trawlerAmount = 0;
    private transient Socket socket;
    private final Collection<String> news = new ArrayList<>();

    private boolean isStarted;

    /* END FIELDS */

    public transient Event<String> onMessaging = new Event<>();

    public transient Event<Object> onUpdated = new Event<>();

    /* GETTERS */
    public Dices getDices() {
        return dices;
    }

    public int getDiceSum() {
        return diceSum;
    }

    public Player getWinner() {
        return winner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<CompanyCard> getCompanies() {
        return companies;
    }

    public int getTrawlerAmount() {
        return trawlerAmount;
    }

    public Collection<String> getNews() {
        return news;
    }

    public boolean isStarted() {
        return isStarted;
    }

    /* END GETTERS */

    /* SETTERS */
    public void setDices(Dices dices) {
        this.dices.setDices(dices.getDices());
    }

    public void setDiceSum(int diceSum) {
        this.diceSum = diceSum;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
    /* END SETTERS */

    public Game(Socket socket) {
        this();

        this.socket = socket;

        Thread thread = new Thread(this::listen);
        thread.setDaemon(true);
        thread.start();
    }

    public Game() {
        companies.addAll(Field.getCompanies());
        onMessaging.add(news::add);
    }
    private void listen() {
        while(true) {
            try {
                GameCompressed compressed = Messaging.getObj(socket, GameCompressed.class);

                Platform.runLater(() -> {
                    GameZipper.unzip(compressed, this);
                    onUpdated.invoke(null);
                });
            } catch(IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void start() {
        if(players.size() > 0) {
            activePlayer = players.get(0);
            isStarted = true;
        }
    }

    public boolean isActive(Player player) {
        return activePlayer == player;
    }

    public void sendChanges() {
        try {
            Messaging.sendObj(socket, GameZipper.zip(this));
            news.clear();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean destroyCity(Card card) {
        var res = activePlayer.destroyCity(card.getType());
        if(res) {
            onMessaging.invoke(String.format("Игрок %s разрушает %s", activePlayer.getName(), card.getTitle()));
        }
        return res;
    }

    public void throwDices(int count) {
        activePlayer.getPossibilities().setCanAddToDice(false);
        activePlayer.getPossibilities().setCanBuild(true);
        int actualCount = Math.min(Math.min(count, activePlayer.getDiceCount()), dices.maxDiceCount);
        dices.throwDices(actualCount);
        diceSum = dices.getSum();

        onMessaging.invoke("Выпало : " + dices.getValue());
        handleCities();
    }

    private boolean needThrowToTrawler() {
        if(diceSum < 12) return false;
        var pl = players.stream().filter(x -> x.hasCityCard(Cards.PORT)).toList();
        for(Player player : pl) {
            var contains = player.getCompanies().stream().anyMatch(x -> x.getType() == Cards.TRAWLER && !x.isClosed());
            if(contains) return true;
        }

        return false;
    }

    private void throwToTrawler() {
        dices.throwDices(2);
        trawlerAmount = dices.getSum();
        onMessaging.invoke("Выпало на траулер : " + trawlerAmount);
        System.out.println("Trawler : " + trawlerAmount);
    }

    public void goNext() {
        if(!activePlayer.getPossibilities().isHasExtraMove()) {
            int idx = getNextPlayerIndex();
            activePlayer = players.get(idx);
        }

        activePlayer.getPossibilities().setCanThrowAgain(false);
        activePlayer.getPossibilities().setCanBuild(true);
    }

    private int getNextPlayerIndex() {
        int idx = players.indexOf(activePlayer);
        return idx < players.size() - 1 ? idx + 1 : 0;
    }

    public boolean getAwayCompany(Player player, CompanyCard card) {
        if(player == activePlayer) return false;
        if(card.getActivityType() == ActivityTypes.LARGE_COMPANY) return false;
        if(!activePlayer.getCompanies().contains(card)) return false;

        activePlayer.removeCompany(card);
        player.addCompany(card);
        activePlayer.getAccount().increase(activePlayer.getPossibilities().getGetAwayCompanyBonus());
        activePlayer.getPossibilities().setCanGetAwayCompany(false);
        onMessaging.invoke(String.format("Игрок %s передал игроку %s предприятие %s", activePlayer.getName(), player.getName(), card.getTitle()));
        return true;
    }

    private void handleCities() {
        var cities = activePlayer.getCities();
        for(CityCard city : cities) {
            var res = city.apply(this, activePlayer);
            onMessaging.invoke(res.toString());
            System.out.printf("%s : %s (%d)%n", res.getCard().getType(), res.getType(), res.getValue());
        }
    }

    public void addToDice() {
        if(activePlayer.getPossibilities().isCanAddToDice()) {
            diceSum += 2;
            onMessaging.invoke(String.format("Игрок %s добавил два к кубикам", activePlayer.getName()));
        }
        activePlayer.getPossibilities().setCanAddToDice(false);
    }

    public void handleDice() {
        if(needThrowToTrawler()) {
            throwToTrawler();
        }

        handleRedCompanies();
        handleBlueCompanies();
        handleGreenCompanies();
        handleVioletCompanies();
    }

    private void handleRedCompanies() {
        int activePlayerIdx = players.indexOf(activePlayer);
        for(int i = activePlayerIdx - 1; i >= 0; i--) {
            handleCompanies(players.get(i), CompanyColors.RED);
        }

        for(int i = players.size() - 1; i > activePlayerIdx; i--) {
            handleCompanies(players.get(i), CompanyColors.RED);
        }
    }

    private void handleCompanies(Player player, CompanyColors color) {
        var companies = player.getCompanies().stream().filter(x -> x.getColor() == color).toList();
        for(CompanyCard card : companies) {
            var result = card.apply(this, player);
            onMessaging.invoke(result.toString());
            System.out.printf("%s: %s - %d\n", result.getCard().getType(), result.getType(), result.getValue());
        }
    }

    private void handleBlueCompanies() {
        for(Player player : players) {
            handleCompanies(player, CompanyColors.BLUE);
        }
    }

    private void handleGreenCompanies() {
        handleCompanies(activePlayer, CompanyColors.GREEN);
    }

    private void handleVioletCompanies() {
        handleCompanies(activePlayer, CompanyColors.VIOLET);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public boolean buildCity(Cards type) {
        if(activePlayer.buildCity(type)) {
            if(activePlayer.getCities().stream().allMatch(CityCard::isBuilt)) {
                isStarted = false;
                winner = activePlayer;
                onMessaging.invoke(String.format("Игра окончена. Игрок %s выиграл", winner.getName()));
            }
            return true;
        }

        return false;
    }


    public boolean buyCompany(CompanyCard card) {
        List<CompanyCard> cards = companies.stream().filter(x -> x.getType().equals(card.getType())).toList();
        if(cards.size() == 0) {
            return false;
        }

        boolean result = activePlayer.buildCompany(card);
        if(result) {
            var optional = companies.stream().filter(x -> x.getType().equals(card.getType())).findFirst();
            if(optional.isPresent()) {
                companies.remove(optional.get());
                onMessaging.invoke(String.format("Игрок %s купил %s", activePlayer.getName(), card.getTitle()));
            }
        }
        return result;
    }

    public boolean closeCompany(CompanyCard card) {
        if(card.getActivityType() == ActivityTypes.LARGE_COMPANY) return false;
        if(activePlayer.getPossibilities().isCanCloseCompany()) {
            int sum = 0;
            for(Player player : players) {
                var companies = player.getCompanies().stream().filter(x -> x.getType() == card.getType() && !x.isClosed()).toList();
                for(CompanyCard company : companies) {
                    company.close();
                    sum++;
                }
            }

            activePlayer.getAccount().increase(sum);

            onMessaging.invoke(String.format("Игрок %s закрыл предприятие %s и получил %d монет(ы)", activePlayer.getName(), card.getTitle(), sum));

            return true;
        }

        return false;
    }

    public boolean deposit() {
        if(activePlayer.getPossibilities().isCanDeposit()) {
            boolean result = activePlayer.deposit();

            if(result) {
                onMessaging.invoke(String.format("Игрок %s сделал депозит", activePlayer.getName()));
                return true;
            }
        }

        return false;
    }

    public boolean takeExtraMoney() {
        boolean result = activePlayer.takeExtraMoney();
        if(result) {
            onMessaging.invoke(String.format("Игрок %s взял дополнительные монеты", activePlayer.getName()));
        }
        return result;
    }

    public boolean exchangeCompanies(Player player, CompanyCard outgo, CompanyCard income) {
        boolean result = activePlayer.exchangeCompany(player, outgo, income);
        if(result) {
            onMessaging.invoke(String.format("Игрок %s обменял предприятие %s с предприятием %s игрока %s",
                    activePlayer.getName(), outgo.getTitle(), income.getTitle(), player.getName()));
        }

        return result;
    }

    public boolean isCanBuilt(Player player, Card card) {
        if(!isActive(player) || !isStarted) return false;
        if(!player.getPossibilities().isCanBuild()) return false;
        if(card instanceof CompanyCard && companies.stream().noneMatch(x -> x.getType() == card.getType())) return false;
        return !(card instanceof CityCard) || !player.hasCityCard(card.getType());
    }

    public boolean steal(Player player) {
        if(player == activePlayer) return false;

        if(activePlayer.getPossibilities().isCanSteal()) {
            int result = activePlayer.steal(player, activePlayer.getPossibilities().getStealAmount());
            onMessaging.invoke(String.format("Игрок %s взял у игрока %s %d монет(ы)", activePlayer.getName(), player.getName(), result));

            return true;
        }

        return false;
    }

}
