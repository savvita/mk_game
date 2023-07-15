package com.savita.machi_koro.controllers;

import com.savita.machi_koro.events.Event;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.cities.CityCard;
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

public class CitiesController {
    private static final double MAX_CARD_WIDTH = 90;
    private static final double MAX_CARD_HEIGHT = 170;
    private static final int H_GAP = 5;

    @FXML private FlowPane citiesPane;
    @FXML private Text previewTitle;
    @FXML private Text previewPrice;
    @FXML private Text previewDescription;
    @FXML private Button destroyBtn;
    @FXML private VBox previewPane;
    private Collection<CityCard> cities;
    private Card selectedCard;
    public Event<Card> onDestroyed = new Event();
    public void initData(Collection<CityCard> cities) {
        previewPane.setVisible(false);
        this.cities = cities;
        drawCities();
        destroyBtn.setOnAction(x -> {
            if(selectedCard != null) onDestroyed.invoke(selectedCard);
        });
    }

    private void drawCities() {
        citiesPane.getChildren().clear();

        double prefWidth = MAX_CARD_WIDTH + H_GAP;
        double prefHeight = MAX_CARD_HEIGHT;

        for(CityCard city : cities) {
            Pane cityPane = getCityPane(city, prefWidth, prefHeight);

            cityPane.setOnMouseClicked(mouseEvent -> {
                drawPreviewPane(city);
                previewPane.setVisible(true);
                selectedCard = city;
            });

            citiesPane.getChildren().add(cityPane);
        }
    }

    private void drawPreviewPane(Card card) {
        previewTitle.setText(card.getTitle());
        previewPrice.setText("Стоимость : " + card.getPrice());
        previewDescription.setText(card.getDescription());
    }

    private Pane getCityPane(CityCard city, double prefWidth, double prefHeight) {
        FlowPane pane = new FlowPane();
        pane.setPrefSize(prefWidth, prefHeight);
        Canvas canvas = new Canvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            drawCity(gc, city);
        });


        pane.getChildren().add(canvas);
        return pane;
    }

    private void drawCity(GraphicsContext gc, CityCard card) {
        Path path = Paths.get("images", card.getImage());
        Image original = new Image("file:" + path);
        double width = original.getWidth();
        double height = original.getHeight();
        double k = MAX_CARD_WIDTH / width;
        gc.drawImage(original, 0, 0, width * k, height * k);
    }
}
