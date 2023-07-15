package com.savita.machi_koro;

import com.savita.machi_koro.controllers.MKController;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.cards.company.MineCard;
import com.savita.machi_koro.models.cards.company.PrestigiousRestaurantCard;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MK extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MK.class.getResource("mk.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1320, 740);
        stage.setTitle("MK");
        stage.setScene(scene);

        MKController controller = fxmlLoader.getController();
        Game game = new Game();
        Player player = new Player("Me");
        player.getAccount().setAmount(200);
        player.getPossibilities().setCanBuild(true);
        player.buildCity(Cards.PORT);
        player.getPossibilities().setCanBuild(true);
        player.buildCity(Cards.AIRPORT);
        player.getPossibilities().setCanBuild(true);
        player.buildCity(Cards.RADIO_TOWER);
        player.getPossibilities().setCanBuild(true);
        player.buildCity(Cards.RAILWAY_STATION);

        Player enemy = new Player("Enemy #1");
        enemy.getAccount().setAmount(20);
        enemy.getPossibilities().setCanBuild(true);
        enemy.buildCity(Cards.PORT);
        enemy.getPossibilities().setCanBuild(true);
        enemy.buildCity(Cards.RESTAURANT);

        Player enemy1 = new Player("Enemy #2");
        enemy1.getAccount().setAmount(250);
        enemy1.getPossibilities().setCanBuild(true);
        enemy1.buildCity(Cards.PORT);
        enemy1.getPossibilities().setCanBuild(true);
        enemy1.buildCompany(new PrestigiousRestaurantCard());

        Player enemy2 = new Player("Enemy #3");
        enemy2.getAccount().setAmount(120);
        enemy2.getPossibilities().setCanBuild(true);
        enemy2.buildCity(Cards.PORT);
        enemy2.getPossibilities().setCanBuild(true);
        enemy2.buildCompany(new MineCard());
        enemy2.getPossibilities().setCanBuild(true);
        enemy2.buildCompany(new MineCard());


        game.addPlayer(player);
        game.addPlayer(enemy);
        game.addPlayer(enemy1);
        game.addPlayer(enemy2);


        controller.initData(game, player);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
