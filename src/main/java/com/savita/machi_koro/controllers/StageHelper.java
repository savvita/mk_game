package com.savita.machi_koro.controllers;

import com.savita.machi_koro.MK;
import com.savita.machi_koro.models.cards.Card;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;

public class StageHelper {
    public static void showPlayersCitiesStage(Stage owner, Player player, Consumer<Card> onAction) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MK.class.getResource("cities.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), player.getCities().size() * (GraphicHelper.MAX_CARD_WIDTH + 2 * GraphicHelper.H_GAP), 350);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выберите достопримечательность");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);

        dialogStage.setScene(scene);

        CitiesController controller = fxmlLoader.getController();
        controller.initData(player.getCities().stream().filter(x -> x.getType() != Cards.TOWN_HALL && x.isBuilt()).toList());

        controller.onDestroyed.add(x -> {
            if(onAction != null) {
                onAction.accept(x);
            }

            dialogStage.hide();
        });

        dialogStage.setResizable(false);
        dialogStage.show();
    }

    public static void showFieldStage(Stage owner, Game game, Consumer<CompanyCard> onAction) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MK.class.getResource("field.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Выберите предприятие");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);

        dialogStage.setScene(scene);

        FieldController controller = fxmlLoader.getController();
        controller.initData(game);
        controller.onClosed.add(x -> {
            if(onAction != null) {
                onAction.accept(x);
            }
            dialogStage.hide();
        });
        dialogStage.setResizable(false);
        dialogStage.show();
    }

    public static void showEnemiesStage(Stage owner, Collection<Player> players, Consumer<Player> onAction) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MK.class.getResource("enemies.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Ограбить игрока");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);

        dialogStage.setScene(scene);

        EnemiesController controller = fxmlLoader.getController();
        controller.initData(players);

        controller.onSteal.add(x -> {
            if(onAction != null) {
                onAction.accept(x);
            }

            dialogStage.hide();
        });

        dialogStage.setResizable(false);
        dialogStage.show();
    }
    public static void showExchangeStage(Stage owner, Game game, Consumer onAction) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MK.class.getResource("exchange.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 900);

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Обменять предприятие");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);

        dialogStage.setScene(scene);

        ExchangeController controller = fxmlLoader.getController();
        controller.initData(game);

        controller.onExchanged.add(x -> {
            if(onAction != null) {
                onAction.accept(x);
            }

            dialogStage.hide();
        });

        dialogStage.setResizable(false);
        dialogStage.show();
    }

}
