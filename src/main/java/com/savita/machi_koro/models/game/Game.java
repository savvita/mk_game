package com.savita.machi_koro.models.game;

import com.savita.machi_koro.events.Event;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.cities.CityCard;
import com.savita.machi_koro.models.cards.company.*;

import java.util.ArrayList;
import java.util.List;

public class Game {
    /* FIELDS */
    private final List<Player> players = new ArrayList<>(4);
    private Player activePlayer;
    private Dices dices = new Dices();
    private int diceSum;
    private final int companyCount = 6;
    private final List<CompanyCard> companies = new ArrayList<>();
    private int trawlerAmount = 0;
    private int activePlayerIdx = 0;

    /* END FIELDS */

    public Event<String> onMessaging = new Event<>();

    /* GETTERS */

    public Dices getDices() {
        return dices;
    }

    public int getDiceSum() {
        return diceSum;
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

    /* END GETTERS */

    public Game() {
        initializeField();
    }

    //TODO override this
    public void start() {
        activePlayer = players.get(0);
    }

    public boolean isActive(Player player) {
        return activePlayer == player;
    }


    private void initializeField() {
        for(int i = 0; i < companyCount; i++) {
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
        // TODO override this
        activePlayer.getPossibilities().setCanThrowAgain(false);
        activePlayer.getPossibilities().setCanBuild(true);
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
            System.out.println(String.format("%s : %s (%d)", res.getCard().getType(), res.getType(), res.getValue()));
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

        //TODO override this
        //TODO handle double
        activePlayer = players.get(0);
    }

    private void handleRedCompanies() {
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

    public boolean buyCompany(CompanyCard card) {
        List<CompanyCard> cards = companies.stream().filter(x -> x.getType().equals(card.getType())).toList();
        if(cards.size() == 0) {
            return false;
        }

        boolean result = activePlayer.buildCompany(card);
        if(result) {
            companies.remove(companies.stream().filter(x -> x.getType().equals(card.getType())).findFirst().get());
            onMessaging.invoke(String.format("Игрок %s купил %s", activePlayer.getName(), card.getTitle()));
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
        if(!isActive(player)) return false;
        if(!player.getPossibilities().isCanBuild()) return false;
        if(card instanceof CompanyCard && companies.stream().filter(x -> x.getType() == card.getType()).count() == 0) return false;
        if(card instanceof CityCard && player.hasCityCard(card.getType())) return false;

        // TODO add if game started
        return true;
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
