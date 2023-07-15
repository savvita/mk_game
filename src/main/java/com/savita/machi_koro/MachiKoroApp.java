//package com.savita.machi_koro;
//
//import com.savita.machi_koro.controllers.MKController;
//import com.savita.machi_koro.models.cards.Cards;
//import com.savita.machi_koro.models.cards.cities.CityCard;
//import com.savita.machi_koro.models.cards.company.*;
//import com.savita.machi_koro.models.game.Game;
//import com.savita.machi_koro.models.game.Player;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Orientation;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.Button;
//import javafx.scene.effect.ColorAdjust;
//import javafx.scene.image.Image;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.*;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class MachiKoroApp extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
////        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("h"));
////        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
////        stage.setTitle("Hello!");
////        stage.setScene(scene);
////        stage.show();
//
//        player = new Player();
//        player.getAccount().setAmount(300);
//
//        game = new Game();
//        game.addPlayer(player);
//        game.start();
//
//        citiesPane = new FlowPane();
//        citiesPane.setPrefSize(110 * 12, 200);
//        citiesPane.setOrientation(Orientation.HORIZONTAL);
//
//        player.getPossibilities().setCanBuild(true);
//        player.buildCity(Cards.PORT);
//        player.getPossibilities().setCanBuild(true);
//        player.buildCity(Cards.RAILWAY_STATION);
////        player.buildCity(CityCardTypes.AMUSEMENT_PARK);
////        player.buildCity(CityCardTypes.SHOPPING_MALL);
////        player.buildCity(CityCardTypes.RADIO_TOWER);
////        player.getPossibilities().setCanBuild(true);
////        player.buildCompany(new CafeCard());
//        player.getPossibilities().setCanBuild(true);
//        player.buildCompany(new TrawlerCard());
////        player.getPossibilities().setCanBuild(true);
////        player.buildCompany(new DemolitionCompanyCard());
////        player.setCanBuild(true);
////        player.buildCompany(new DownTownCard());
////        player.getPossibilities().setCanBuild(true);
////        player.buildCompany(new BeverageFactoryCard());
//
////        player.buildCompany(new StadiumCard());
////        player.buildCompany(new TelevisionCenterCard());
//       // player.buildCity(CityCardTypes.AIRPORT);
//        playerPane = new FlowPane(0, 0);
//        fieldPane = new FlowPane();
//        MKController.drawField(game, player, fieldPane, playerPane);
//
//        Player enemy = new Player();
//        enemy.getAccount().setAmount(10);
//        enemy.getPossibilities().setCanBuild(true);
//        enemy.buildCompany(new TrawlerCard());
//
//        enemy.getPossibilities().setCanBuild(true);
//        enemy.buildCity(Cards.PORT);
//        game.addPlayer(enemy);
//
//        FlowPane[] panes = new FlowPane[3];
//        for(int i = 0; i < 3; i++) {
//            panes[i] = getPlayerCards(player);
//        }
//
//        FlowPane btnPane = new FlowPane(Orientation.VERTICAL);
//        Button throwBtn = new Button("Throw dice " + player.getDiceCount());
//        throwBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("Bank before throwing : " + player.getAccount().getAmount());
//                game.throwDices(2);
//                System.out.println("Throw : " + Arrays.toString(game.getDices().getDices()));
//
//                if(player.getPossibilities().isCanAddToDice()) {
//                    game.addToDice();
//                    System.out.println("Added 2 : " + game.getDiceSum());
//                }
//
//                game.handleDice();
//
//                MKController.drawPlayer(playerPane, player);
//                System.out.println("Enemy: " + enemy.getAccount().getAmount());
//                System.out.println("Enemy card count: " + enemy.getCompanies().size());
//
//                if(player.getPossibilities().isCanExchangeCompany()) {
//                    System.out.println("Can exchange");
//                    if(player.getCompanies().size() > 0 && enemy.getCompanies().size() > 0) {
//                        player.exchangeCompany(enemy, player.getCompanies().get(0), enemy.getCompanies().get(0));
//                    }
//                }
//                if(player.getPossibilities().isCanSteal()) {
//                    System.out.println("Can steal");
//                    player.steal(enemy, 5);
//                }
//                if(player.getPossibilities().isCanCloseCompany()) {
//                    System.out.println("Can close");
//                    game.closeCompany(new MineCard());
//                }
//                if(player.getPossibilities().isCanGetAwayCompany()) {
//                    System.out.println("Get away");
//                    game.getAwayCompany(enemy, player.getCompanies().get(0));
//                }
//                if(player.getPossibilities().isCanDestroyCity()) {
//                    System.out.println("Destroy");
//                    player.destroyCity(Cards.PORT);
//                }
//            }
//        });
//        btnPane.getChildren().add(throwBtn);
//
//
//
//
//        playerPane.getChildren().add(citiesPane);
//        MKController.drawPlayer(playerPane, player);
//        var root = new BorderPane();
//        root.setCenter(fieldPane);
//        root.setBottom(playerPane);
//        root.setLeft(btnPane);
//        panes[1].setOrientation(Orientation.HORIZONTAL);
//        //root.setTop(getPlayerCards(enemy));
//        root.setRight(getPlayerCards(enemy));
//
//       // root.setPrefSize(1300, 600);
//
//        Scene scene = new Scene(root);
//        stage.setMaximized(true);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//
//    private FlowPane citiesPane;
//
//    private FlowPane fieldPane;
//    private FlowPane playerPane;
//
//    private FlowPane getPlayerCards(Player player) {
//        var c = player.getCompanies();
//        Map<Cards, List<CompanyCard>> cc = c.stream().collect(Collectors.groupingBy(CompanyCard::getType));
//
//        FlowPane pane = new FlowPane(Orientation.VERTICAL);
//        for(List<CompanyCard> card : cc.values()) {
//            pane.getChildren().add(new Text(String.format("%s X%d", card.get(0).getTitle(), card.size())));
//        }
//        Text text = new Text("Bank : " + player.getAccount().getAmount());
//        pane.getChildren().add(text);
//        return pane;
//    }
//
//
//    //    private Pane getCardInfo() {
////        Pane pane = new VBox();
////        pane.getChildren().add(new Text("Title : " + cards.get(0).getTitle()));
////        pane.getChildren().add(new Text("Description : " + cards.get(0).getDescription()));
////        pane.getChildren().add(new Text("Price : " + cards.get(0).getPrice()));
////
////        return pane;
////    }
////
////    private List<CompanyCard> cards = new ArrayList<>();
//    private List<CompanyCard> playersCard = new ArrayList<>();
//    private Game game;
//
//    private Player player;
//    public static void main(String[] args) {
//        launch();
//    }
//}