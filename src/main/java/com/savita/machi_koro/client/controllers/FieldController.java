package com.savita.machi_koro.client.controllers;

import com.savita.machi_koro.events.Event;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.models.cards.company.CompanyColors;
import com.savita.machi_koro.models.game.Field;
import com.savita.machi_koro.models.game.Game;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class FieldController {
    private Game game;
    private final ObjectProperty<CompanyCard> selectedCard = new SimpleObjectProperty<>();
    public Event<CompanyCard> onClosed = new Event();

    @FXML private FlowPane fieldPane;
    @FXML private Label previewTitle;
    @FXML private Label previewCount;
    @FXML private Label previewDescription;
    @FXML private Button closeBtn;
    @FXML private VBox previewPane;

    public void initData(Game game) {
        this.game = game;
        drawField();
        bindPreview();
        previewPane.setVisible(false);
        closeBtn.setOnAction(x -> {
            if(selectedCard.get() != null) onClosed.invoke(selectedCard.get());
        });
    }

    private void bindPreview() {
        GraphicHelper.<CompanyCard>bindProperty(selectedCard, Card::getTitle, previewTitle);
        GraphicHelper.<CompanyCard>bindProperty(selectedCard, (card) -> String.format("Построено : %d", getCompanyCount(card)), previewCount);
        GraphicHelper.<CompanyCard>bindProperty(selectedCard, Card::getDescription, previewDescription);
    }

    private int getCompanyCount(CompanyCard card) {
        return (int) game
            .getPlayers()
            .stream()
            .mapToLong(x -> x
                    .getCompanies()
                    .stream()
                    .filter(y -> y.getType() == card.getType() && !y.isClosed())
                    .count())
            .sum();
    }

    private void drawField() {
        var cards = Field
            .getCompanies()
            .stream()
            .filter(x -> x.getColor() != CompanyColors.VIOLET)
            .collect(Collectors.groupingBy(CompanyCard::getType))
            .values()
            .stream()
            .sorted(Comparator.comparingInt(x -> x.get(0).getMinDiceValue()))
            .map(x -> x.get(0))
            .toList();

        Consumer<CompanyCard> onClick = card -> {
            selectedCard.set(card);
            previewPane.setVisible(true);
        };
        GraphicHelper.drawField(fieldPane, cards, onClick);
    }
}
