package com.savita.machi_koro.client.controllers;

import com.savita.machi_koro.events.Event;
import com.savita.machi_koro.models.game.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.util.Collection;

public class EnemiesController {
    private Collection<Player> players;
    private Player selectedPlayer;
    public Event<Player> onSteal = new Event<>();

    @FXML private FlowPane enemiesPane;
    @FXML private FlowPane citiesPane;
    @FXML private FlowPane companiesPane;
    @FXML private Button stealBtn;

    public void initData(Collection<Player> enemies) {
        this.players = enemies;
        enemiesPane.getChildren().add(GraphicHelper.getEnemyListPane(players, (player) -> {
            selectedPlayer = player;
            stealBtn.setText("Ограбить " + selectedPlayer.getName());
            stealBtn.setVisible(true);
            drawCards();
        }));
        stealBtn.setOnAction(x -> {
            if(selectedPlayer != null) onSteal.invoke(selectedPlayer);
        });
    }

    private void drawCards() {
        if(selectedPlayer == null) return;
        var companies = selectedPlayer.getCompanies();

        GraphicHelper.drawCities(citiesPane, selectedPlayer.getCities(), null);
        GraphicHelper.drawField(companiesPane, companies, null);
    }

}
