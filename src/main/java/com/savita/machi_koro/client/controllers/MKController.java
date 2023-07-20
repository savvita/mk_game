package com.savita.machi_koro.client.controllers;

import com.savita.machi_koro.log.Log;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.cities.CityCard;
import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MKController {
    private Game game;
    private Player player;
    private List<Player> enemies;
    private final ObjectProperty<Card> selectedCard = new SimpleObjectProperty<>();
    private final Log log = new Log(System.out::println);

    @FXML private FlowPane fieldPane;

    @FXML private TabPane previewTab;
    @FXML private Label previewTitle;
    @FXML private Label previewPrice;
    @FXML private Label previewDescription;
    @FXML private Button previewBtn;

    @FXML private HBox playerCities;
    @FXML private HBox playerCompanies;
    @FXML private Label bankAmount;
    @FXML private Label playerName;
    @FXML private Label player1name;
    @FXML private Label player1bank;
    @FXML private FlowPane player1Cities;
    @FXML private FlowPane player1Companies;
    @FXML private Label player2name;
    @FXML private Label player2bank;
    @FXML private FlowPane player2Cities;
    @FXML private FlowPane player2Companies;
    @FXML private Label player3name;
    @FXML private Label player3bank;
    @FXML private FlowPane player3Cities;
    @FXML private FlowPane player3Companies;

    @FXML private ImageView dicesImage;
    @FXML private Label dicesValue;

    @FXML private Button throwDiceBtn;
    @FXML private Button throwTwoDicesBtn;
    @FXML private Button rethrowDicesBtn;
    @FXML private Button addTwoToDicesBtn;
    @FXML private Button keepDicesBtn;
    @FXML private Button finishMoveBtn;
    @FXML private Button destroyCityBtn;
    @FXML private Button exchangeCompanyBtn;
    @FXML private Button stealBtn;
    @FXML private Button closeCompanyBtn;
    @FXML private Button makeDepositsBtn;
    @FXML private Button takeMoneyBtn;

    @FXML private TextArea results;
    @FXML private FlowPane actions;

    @FXML
    private void initialize() {
        actions.getChildren().clear();
        bindPreview();
        initializeButtons();
    }

    private void initializeDices() {
        Path path = Paths.get("images", "dice.png");
        Image image = new Image("file:" + path);
        dicesImage.setImage(image);
        dicesValue.setTextFill(Color.LIGHTBLUE);
        InnerShadow is = new InnerShadow();
        is.setOffsetX(0.5f);
        is.setOffsetY(0.5f);
        dicesValue.setEffect(is);

        dicesValue.textProperty().bind(game.getDices().valueProperty());
    }

    private void bindPreview() {
        GraphicHelper.bindProperty(selectedCard, Card::getTitle, previewTitle);
        GraphicHelper.bindProperty(selectedCard, (card) -> String.format("Стоимость : %d", card.getPrice()), previewPrice);
        GraphicHelper.bindProperty(selectedCard, Card::getDescription, previewDescription);
    }

    public void initData(Game game, Player player) {
        this.game = game;
        this.player = player;

        initializeDices();
        setPreviewBtnVisibility();
        refresh();

        this.game.onMessaging.add(x -> results.appendText(x + "\n"));
        this.game.onUpdated.add((x) -> refresh());

        playerName.setText(player.getName());
    }

    private void handleDices() {
        game.handleDice();
        showActions();
        drawPlayersCompanies();
        drawBankAmount();
        drawPlayers();
    }

    private void refresh() {
        enemies = this.game.getPlayers().stream().filter(x -> x != player).toList();
        drawField();
        drawPlayersCities();
        drawPlayersCompanies();
        drawBankAmount();
        drawPlayers();
        game.getNews().forEach(x -> results.appendText(x + "\n"));

        if(game.getWinner() != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Игра окончена");
            alert.setContentText(String.format("Игрок %s выиграл", game.getWinner().getName()));

            alert.showAndWait();
        }

        if(game.isStarted() && game.isActive(player)) {
            move();
        }
    }

    private void buyCard() {
        if(selectedCard.get() instanceof CompanyCard company) {
            var res = game.buyCompany(company);
            if(res) {
                drawField();
                drawPlayersCompanies();
                drawBankAmount();
                setPreviewBtnVisibility();
                game.sendChanges();
            }
        } else if(selectedCard.get() instanceof CityCard city) {
            var res = game.buildCity(city.getType());
            if(res) {
                drawPlayersCities();
                drawBankAmount();
                setPreviewBtnVisibility();
                game.sendChanges();
            }
        }
    }

    private void makeDeposit() {
        var result = game.deposit();
        if(result) {
            showActions();
            game.sendChanges();
            drawBankAmount();
        }
    }

    private void initializeButtons() {
        previewBtn.setOnMouseClicked(x -> buyCard());

        throwDiceBtn.setOnMouseClicked(x -> throwDices(1));
        throwTwoDicesBtn.setOnMouseClicked(x -> throwDices(2));
        rethrowDicesBtn.setOnMouseClicked(x -> {
            move();
            game.sendChanges();
        });
        keepDicesBtn.setOnMouseClicked(x -> {
            handleDices();
            game.sendChanges();
        });

        destroyCityBtn.setOnAction(x -> {
            Stage stage = (Stage) destroyCityBtn.getScene().getWindow();
            showPlayersCitiesStage(stage, player);
        });

        makeDepositsBtn.setOnAction(x -> makeDeposit());

        closeCompanyBtn.setOnAction(x -> {
            Stage stage = (Stage) closeCompanyBtn.getScene().getWindow();
            showFieldStage(stage);
        });

        stealBtn.setOnAction(x -> {
            Stage stage = (Stage) stealBtn.getScene().getWindow();
            showEnemiesStage(stage);
        });

        exchangeCompanyBtn.setOnAction(x -> {
            Stage stage = (Stage) exchangeCompanyBtn.getScene().getWindow();
            showExchangeStage(stage);
        });

        takeMoneyBtn.setOnAction(x -> takeExtraMoney());

        finishMoveBtn.setOnAction(x -> {
            game.goNext();
            game.sendChanges();
            actions.getChildren().clear();
            refresh();
        });

        addTwoToDicesBtn.setOnAction((x -> {
            game.addToDice();
            game.sendChanges();
        }));

    }

    private void takeExtraMoney() {
        if(game.takeExtraMoney()) {
            drawBankAmount();
            showActions();
            setPreviewBtnVisibility();
            game.sendChanges();
        }
    }

    private void setPreviewBtnVisibility() {
        if(selectedCard.get() != null) {
            previewBtn.setVisible(game.isCanBuilt(player, selectedCard.getValue()));
        } else {
            previewBtn.setVisible(false);
        }
    }

    private void throwDices(int count) {
        game.throwDices(count);

        boolean additionalAction = showAdditionalDiceActions();

        if(additionalAction) {
            actions.getChildren().add(keepDicesBtn);
        } else {
            handleDices();
        }

        previewTab.getSelectionModel().select(1);
        game.sendChanges();
    }

    private boolean showAdditionalDiceActions() {
        actions.getChildren().clear();

        boolean additionalAction = false;
        if(player.getPossibilities().isCanAddToDice()) {
            actions.getChildren().add(addTwoToDicesBtn);
            additionalAction = true;
        }
        if(player.getPossibilities().isCanThrowAgain()) {
            actions.getChildren().add(rethrowDicesBtn);
            additionalAction = true;
        }

        return additionalAction;
    }

    private void showActions() {
        setPreviewBtnVisibility();
        actions.getChildren().clear();
        actions.getChildren().add(finishMoveBtn);

        if(player.getPossibilities().isCanDestroyCity()) {
            actions.getChildren().add(destroyCityBtn);
        }

        if(player.getPossibilities().isCanExchangeCompany()) {
            actions.getChildren().add(exchangeCompanyBtn);
        }

        if(player.getPossibilities().isCanSteal()) {
            actions.getChildren().add(stealBtn);
        }

        if(player.getPossibilities().isCanCloseCompany()) {
            actions.getChildren().add(closeCompanyBtn);
        }

        if(player.getAccount().getAmount() > 0 && player.getPossibilities().isCanDeposit()) {
            actions.getChildren().add(makeDepositsBtn);
        }

        if(player.getPossibilities().isHasExtraMoney()) {
            actions.getChildren().add(takeMoneyBtn);
        }
    }

    private void move() {
        selectedCard.set(null);
        actions.getChildren().clear();
        actions.getChildren().add(throwDiceBtn);
        if(player.getDiceCount() > 1) {
            actions.getChildren().add(throwTwoDicesBtn);
        }
    }

    /* ENEMIES */
    private void drawPlayers() {
        if(enemies.size() > 0) drawPlayer(enemies.get(0), player1name, player1bank, player1Cities, player1Companies);
        if(enemies.size() > 1) drawPlayer(enemies.get(1), player2name, player2bank, player2Cities, player2Companies);
        if(enemies.size() > 2) drawPlayer(enemies.get(2), player3name, player3bank, player3Cities, player3Companies);
    }
    private void drawPlayer(Player player, Label name, Label bank, FlowPane cities, FlowPane companies) {
        name.setText(player.getName());
        bank.setText("Банк : " + player.getAccount().getAmount());

        drawEnemyCities(player, cities);
        drawEnemyCompanies(player, companies);
    }
    private void drawEnemyCities(Player player, FlowPane pane) {
        pane.getChildren().clear();
        var cities = player.getCities().stream().filter(CityCard::isBuilt).toList();

        for (CityCard city : cities) {
            Label lbl = new Label(city.getTitle());
            lbl.setOnMouseClicked(mouseEvent -> {
                previewTab.getSelectionModel().select(0);
                selectedCard.set(city);
            });
            pane.getChildren().add(lbl);
        }
    }
    private Label getCompanyLabel(List<CompanyCard> cards) {
        var card = cards.get(0);
        String text = String.format("%s (всего %d", card.getTitle(), cards.size());
        if(cards.stream().anyMatch(CompanyCard::isClosed)) {
            int count = (int)cards.stream().filter(CompanyCard::isClosed).count();
            text += String.format(", закрыто %d)", count);
        } else {
            text += ")";
        }
        Label lbl = new Label(text);

        lbl.setWrapText(true);

        switch(card.getColor()) {
            case RED -> lbl.setTextFill(Color.RED);
            case BLUE -> lbl.setTextFill(Color.BLUE);
            case GREEN -> lbl.setTextFill(Color.GREEN);
            case VIOLET -> lbl.setTextFill(Color.VIOLET);
        }

        lbl.setOnMouseClicked(mouseEvent -> {
            previewTab.getSelectionModel().select(0);
            selectedCard.set(card);
        });

        return lbl;
    }
    private void drawEnemyCompanies(Player player, FlowPane pane) {
        pane.getChildren().clear();
        var companies = player.getCompanies();
        var groupedCompanies = companies
                .stream()
                .collect(Collectors.groupingBy(CompanyCard::getType))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(x -> x.get(0).getMinDiceValue()))
                .toList();

        for(List<CompanyCard> card : groupedCompanies) {
            pane.getChildren().add(getCompanyLabel(card));
        }
    }
    /* END ENEMIES */

    private void drawField() {
        Consumer<CompanyCard> onClick = (card) -> {
            previewTab.getSelectionModel().select(0);
            selectedCard.set(card);
            setPreviewBtnVisibility();
        };

        GraphicHelper.drawField(fieldPane, game.getCompanies(), onClick);
    }
    private void drawBankAmount() {
        //TODO binding
        bankAmount.setText("Банк : " + player.getAccount().getAmount());
    }

    private void drawPlayersCompanies() {
        playerCompanies.getChildren().clear();
        var groupedCompanies = player
                .getCompanies()
                .stream()
                .collect(Collectors.groupingBy(CompanyCard::getType))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(x -> x.get(0).getMinDiceValue()))
                .toList();

        double prefWidth = GraphicHelper.MAX_CARD_WIDTH + GraphicHelper.H_GAP;
        double prefHeight = GraphicHelper.MAX_CARD_HEIGHT;

        for(List<CompanyCard> companies : groupedCompanies) {
            Pane company = GraphicHelper.getCompaniesStackPane(companies, prefWidth, prefHeight);
            company.setOnMouseClicked(mouseEvent -> {
                previewTab.getSelectionModel().select(0);
                selectedCard.set(companies.get(0));
                setPreviewBtnVisibility();
            });
            playerCompanies.getChildren().add(company);
        }
    }

    private void drawPlayersCities() {
        Consumer<CityCard> onClick = city -> {
            previewTab.getSelectionModel().select(0);
            selectedCard.set(city);
            setPreviewBtnVisibility();
        };

        GraphicHelper.drawCities(playerCities, player.getCities(), onClick);
    }

    /* STAGES */

    private void showPlayersCitiesStage(Stage owner, Player player) {
        try {
            Consumer<Card> onDestroyed = (x) -> {
                var result = game.destroyCity(x);
                if(result) {
                    showActions();
                    drawPlayersCities();
                    drawBankAmount();
                    game.sendChanges();
                    showActions();
                }
            };
            StageHelper.showPlayersCitiesStage(owner, player, onDestroyed);
        } catch (IOException e) {
            log.error("Cannot load resource : " + e.getMessage());
        }
    }

    private void showFieldStage(Stage owner) {
        try {
            Consumer<CompanyCard> onClosed = card -> {
                var result = game.closeCompany(card);
                if(result) {
                    drawPlayersCompanies();
                    drawBankAmount();
                    drawPlayers();
                    showActions();
                    game.sendChanges();
                }
            };

            StageHelper.showFieldStage(owner, game, onClosed);
        } catch (IOException e) {
            log.error("Cannot load resource : " + e.getMessage());
        }
    }

    private void showEnemiesStage(Stage owner) {
        try {
            Consumer<Player> onSteal = player -> {
                var result = game.steal(player);
                if(result) {
                    drawBankAmount();
                    drawPlayers();
                    showActions();
                    game.sendChanges();
                }
            };

            StageHelper.showEnemiesStage(owner, enemies, onSteal);
        } catch (IOException e) {
            log.error("Cannot load resource : " + e.getMessage());
        }
    }

    private void showExchangeStage(Stage owner) {
        try {
            Consumer<Object> onExchange = obj -> {
                drawPlayersCompanies();
                drawPlayers();
                showActions();
                game.sendChanges();
            };

            StageHelper.showExchangeStage(owner, game, onExchange);
        } catch (IOException e) {
            log.error("Cannot load resource : " + e.getMessage());
        }
    }

    /* END STAGES */
}
