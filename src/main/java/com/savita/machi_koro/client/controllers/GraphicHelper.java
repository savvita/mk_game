package com.savita.machi_koro.client.controllers;

import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.cities.CityCard;
import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.models.game.Player;
import javafx.beans.binding.Binding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GraphicHelper {
    public static final double MAX_CARD_WIDTH = 65;
    public static final double MAX_CARD_HEIGHT = 130;
    public static final int STACK_OFFSET = 7;
    public static final int H_GAP = 5;
    public static final int V_GAP = 5;
    public static final double BLACKOUT = -0.6;
    public static final HashMap<Cards, Image> images = new HashMap<>();
    public static Image getImage(Card card) {
        Image original;
        if(images.containsKey(card.getType())) {
            original = images.get(card.getType());
        } else {
            Path path = Paths.get("images", card.getImage());
            original = new Image("file:" + path);
            images.put(card.getType(), original);
        }
        return original;
    }

    public static void setBlackout(GraphicsContext gc) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(BLACKOUT);
        gc.setEffect(colorAdjust);
    }

    public static void clearBlackout(GraphicsContext gc) {
        gc.setEffect(null);
    }


    public static <T> void  bindProperty(ObjectProperty<T> objectProperty, Function<T, String> selector, Label node) {
        Binding<String> binding = new ObjectBinding<>() {
            { bind(objectProperty); }
            @Override
            protected String computeValue() {
                return objectProperty.get() == null ? "" : selector.apply(objectProperty.get());
            }
        };

        node.textProperty().bind(binding);
    }

    public static void drawCity(GraphicsContext gc, CityCard card, double x, double y) {
        Image original = GraphicHelper.getImage(card);
        double width = original.getWidth();
        double height = original.getHeight();
        double k = MAX_CARD_WIDTH / width;
        if(!card.isBuilt()) {
            setBlackout(gc);
        } else {
            clearBlackout(gc);
        }
        gc.drawImage(original, x, y, width * k, height * k);
    }

    public static void drawCompany(GraphicsContext gc, CompanyCard card, double x, double y) {
        Image original = GraphicHelper.getImage(card);
        double width = original.getWidth();
        double height = original.getHeight();
        double k = MAX_CARD_WIDTH / width;
        if(card.isClosed()) {
            GraphicHelper.setBlackout(gc);
        } else {
            GraphicHelper.clearBlackout(gc);
        }
        gc.drawImage(original, x, y, width * k, height * k);
    }

    public static void drawCompaniesStack(GraphicsContext gc, List<CompanyCard> companies) {
        int size = companies.size();
        for(int i = 0; i < size; i++) {
            CompanyCard card = companies.get(i);
            drawCompany(gc, card, 0, i * STACK_OFFSET);
        }
    }

    public static Pane getCompaniesStackPane(List<CompanyCard> companies, double prefWidth, double prefHeight) {
        FlowPane pane = new FlowPane();
        pane.setPrefSize(prefWidth, prefHeight);
        Canvas canvas = new Canvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            GraphicHelper.drawCompaniesStack(gc, companies);
        });

        pane.getChildren().add(canvas);
        return pane;
    }

    public static Pane getCityPane(CityCard city, double prefWidth, double prefHeight) {
        FlowPane pane = new FlowPane();
        pane.setPrefSize(prefWidth, prefHeight);
        Canvas canvas = new Canvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            drawCity(gc, city, 0, 0);
        });

        pane.getChildren().add(canvas);
        return pane;
    }

    public static void drawField(FlowPane pane, Collection<CompanyCard> cards, Consumer<CompanyCard> onClick) {
        var groupedCompanies = cards
                .stream()
                .collect(Collectors.groupingBy(CompanyCard::getType))
                .values()
                .stream()
                .sorted(Comparator.comparingInt(x -> x.get(0).getMinDiceValue()))
                .toList();

        pane.getChildren().clear();

        pane.setBackground(Background.fill(Color.ALICEBLUE));
        pane.setAlignment(Pos.CENTER);

        double prefWidth = MAX_CARD_WIDTH + 2 * H_GAP;
        double prefHeight = MAX_CARD_HEIGHT + 2 * V_GAP;

        for (List<CompanyCard> companies : groupedCompanies) {
            Pane companiesPane = getCompaniesStackPane(companies, prefWidth, prefHeight);
            if(onClick != null) {
                companiesPane.setOnMouseClicked((event) -> onClick.accept(companies.get(0)));
            }
            pane.getChildren().add(companiesPane);
        }
    }

    public static void drawCities(Pane pane, Collection<CityCard> cities, Consumer<CityCard> onClick) {
        pane.getChildren().clear();

        double prefWidth = MAX_CARD_WIDTH + H_GAP;
        double prefHeight = MAX_CARD_HEIGHT;

        for(CityCard city : cities) {
            Pane cityPane = GraphicHelper.getCityPane(city, prefWidth, prefHeight);
            if(onClick != null) {
                cityPane.setOnMouseClicked(mouseEvent -> onClick.accept(city));
            }
            pane.getChildren().add(cityPane);
        }
    }

    public static void drawCompanies(FlowPane pane, List<CompanyCard> companies, Consumer<CompanyCard> onClick) {
        pane.getChildren().clear();

        double prefWidth = MAX_CARD_WIDTH + H_GAP;
        double prefHeight = MAX_CARD_HEIGHT;

        for(CompanyCard company : companies) {
            Pane companyPane = getCompanyPane(company, prefWidth, prefHeight);
            if(onClick != null) {
                companyPane.setOnMouseClicked(event -> onClick.accept(company));
            }
            pane.getChildren().add(companyPane);
        }
    }

    public static Pane getCompanyPane(CompanyCard company, double prefWidth, double prefHeight) {
        FlowPane pane = new FlowPane();
        pane.setPrefSize(prefWidth, prefHeight);
        Canvas canvas = new Canvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();

        pane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
            canvas.setWidth(newValue.getWidth());
            canvas.setHeight(newValue.getHeight());
            drawCompany(gc, company, 0, 0);
        });


        pane.getChildren().add(canvas);
        return pane;
    }

    public static FlowPane getEnemyListPane(Collection<Player> players, Consumer<Player> onClick) {
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        for(Player player : players) {
            VBox box = new VBox();
            Label name = new Label(player.getName());
            name.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
            box.getChildren().add(name);
            Label bankAmount = new Label("Банк : " + player.getAccount().getAmount());
            bankAmount.setStyle("-fx-font-size: 13px;");
            box.getChildren().add(bankAmount);
            box.setStyle("-fx-border-width: 1px; -fx-border-style: solid; fx-border-color: black;");


            box.setPadding(new Insets(20, 20, 20, 20));

            if(onClick != null) {
                box.setOnMouseClicked((event) -> onClick.accept(player));
            }
            pane.getChildren().add(box);
        }
        return pane;
    }
}
