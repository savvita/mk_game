package com.savita.machi_koro.client.controllers;

import com.savita.machi_koro.events.Event;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.models.cards.company.CompanyColors;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;
import javafx.beans.binding.Binding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ExchangeController {
    private Game game;
    private final ObjectProperty<CompanyCard> selectedMyCard = new SimpleObjectProperty<>();
    private final ObjectProperty<CompanyCard> selectedEnemyCard = new SimpleObjectProperty<>();
    private final ObjectProperty<Boolean> isExchangePossible = new SimpleObjectProperty<>();

    private Player selectedPlayer;
    @FXML private FlowPane myCompaniesPane;
    @FXML private FlowPane enemiesPane;
    @FXML private FlowPane enemyCompaniesPane;

    @FXML private Label enemyLabel;

    @FXML private Label myPreviewTitle;
    @FXML private Label myPreviewPrice;
    @FXML private Label myPreviewDescription;

    @FXML private Label enemyPreviewTitle;
    @FXML private Label enemyPreviewPrice;
    @FXML private Label enemyPreviewDescription;

    @FXML private Button exchangeBtn;

    public Event<Object> onExchanged = new Event<>();

    public void initData(Game game) {
        this.game = game;
        drawCompanies(game.getActivePlayer(), myCompaniesPane, x -> {
            selectedMyCard.set(x);
            isExchangePossible.set(selectedMyCard.get() != null && selectedEnemyCard.get() != null);
        });
        drawEnemies();
        bindPreview();
        bindButtonVisibility();
        exchangeBtn.setOnMouseClicked(event -> {
            if(selectedMyCard.get() != null && selectedEnemyCard.get() != null) {
                game.exchangeCompanies(selectedPlayer, selectedMyCard.get(), selectedEnemyCard.get());
                onExchanged.invoke(null);
            }
        });
        enemyCompaniesPane.setMinHeight(GraphicHelper.MAX_CARD_HEIGHT);
    }

    private void bindButtonVisibility() {
        Binding<Boolean> binding = new ObjectBinding<>() {
            { bind(isExchangePossible); }
            @Override
            protected Boolean computeValue() {
                return isExchangePossible.get() != null ? isExchangePossible.get() : false;
            }
        };

        exchangeBtn.visibleProperty().bind(binding);
    }

    private void bindPreview() {
        GraphicHelper.<CompanyCard>bindProperty(selectedMyCard, Card::getTitle, myPreviewTitle);
        GraphicHelper.<CompanyCard>bindProperty(selectedMyCard, x -> String.format("Стоимость : %d", x.getPrice()), myPreviewPrice);
        GraphicHelper.<CompanyCard>bindProperty(selectedMyCard, Card::getDescription, myPreviewDescription);

        GraphicHelper.<CompanyCard>bindProperty(selectedEnemyCard, Card::getTitle, enemyPreviewTitle);
        GraphicHelper.<CompanyCard>bindProperty(selectedEnemyCard, x -> String.format("Стоимость : %d", x.getPrice()), enemyPreviewPrice);
        GraphicHelper.<CompanyCard>bindProperty(selectedEnemyCard, Card::getDescription, enemyPreviewDescription);
    }

    private void drawEnemies() {
        var enemies = game.getPlayers().stream().filter(x -> x != game.getActivePlayer()).toList();
        Consumer<Player> onClick = player -> {
            drawCompanies(player, enemyCompaniesPane, x -> {
                selectedEnemyCard.set(x);
                isExchangePossible.set(selectedMyCard.get() != null && selectedEnemyCard.get() != null);
                selectedPlayer = player;
                enemyLabel.setText("Предприятие " + selectedPlayer.getName());
            });
        };
        var pane = GraphicHelper.getEnemyListPane(enemies, onClick);
        enemiesPane.getChildren().add(pane);
    }

    private void drawCompanies(Player player, FlowPane pane, Consumer<CompanyCard> onClick) {
        var companies = player
                .getCompanies()
                .stream()
                .filter(x -> x.getColor() != CompanyColors.VIOLET)
                .collect(Collectors.groupingBy(CompanyCard::getType))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(x -> x.get(0).getMinDiceValue()))
                .map(x -> x.get(0))
                .toList();

        GraphicHelper.drawCompanies(pane, companies, onClick);
    }
}
