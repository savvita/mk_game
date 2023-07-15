package com.savita.machi_koro.controllers;

import com.savita.machi_koro.events.Event;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.models.cards.company.CompanyColors;
import com.savita.machi_koro.models.game.Game;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

public class FieldController {
    private static final double MAX_CARD_WIDTH = 90;
    private static final double MAX_CARD_HEIGHT = 170;
    private static final int H_GAP = 5;
    private Game game;
    private CompanyCard selectedCard;
    public Event<CompanyCard> onClosed = new Event();

    @FXML private FlowPane fieldPane;
    @FXML private Text previewTitle;
    @FXML private Text previewCount;
    @FXML private Text previewDescription;
    @FXML private Button closeBtn;
    @FXML private VBox previewPane;
    public void initData(Game game) {
        this.game = game;
        drawCompanies();
        previewPane.setVisible(false);
        closeBtn.setOnAction(x -> {
            if(selectedCard != null) onClosed.invoke(selectedCard);
        });
    }

    private void drawCompanies() {
        var groupedCompanies = game
                .getCompanies()
                .stream()
                .filter(x -> x.getColor() != CompanyColors.VIOLET)
                .collect(Collectors.groupingBy(CompanyCard::getType))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(x -> x.get(0).getMinDiceValue()))
                .map(x -> x.get(0))
                .toList();

        fieldPane.getChildren().clear();

        double prefWidth = MAX_CARD_WIDTH + H_GAP;
        double prefHeight = MAX_CARD_HEIGHT;

        for(CompanyCard company : groupedCompanies) {
            Pane companyPane = getCompanyPane(company, prefWidth, prefHeight);

            companyPane.setOnMouseClicked(mouseEvent -> {
                drawPreviewPane(company);
                previewPane.setVisible(true);
                selectedCard = company;
            });

            fieldPane.getChildren().add(companyPane);
        }
    }

    private void drawPreviewPane(CompanyCard card) {
        int count = (int) game
                .getPlayers()
                .stream()
                .mapToLong(x -> x
                        .getCompanies()
                        .stream()
                        .filter(y -> y.getType() == card.getType() && !y.isClosed())
                        .count())
                .sum();
//        int count = (int) companies
//                .stream()
//                .filter(x -> x.getType() == card.getType() && !x.isClosed())
//                .count();

        previewTitle.setText(card.getTitle());
        previewCount.setText("Построено : " + count);
        previewDescription.setText(card.getDescription());
    }

    private Pane getCompanyPane(CompanyCard company, double prefWidth, double prefHeight) {
        FlowPane pane = new FlowPane();
        pane.setPrefSize(prefWidth, prefHeight);
        Canvas canvas = new Canvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            drawCompany(gc, company);
        });


        pane.getChildren().add(canvas);
        return pane;
    }

    private void drawCompany(GraphicsContext gc, CompanyCard company) {
        Path path = Paths.get("images", company.getImage());
        Image original = new Image("file:" + path);
        double width = original.getWidth();
        double height = original.getHeight();
        double k = MAX_CARD_WIDTH / width;
        gc.drawImage(original, 0, 0, width * k, height * k);
    }
}
