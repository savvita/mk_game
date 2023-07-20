package com.savita.machi_koro.zip.zippers;

import com.savita.machi_koro.models.cards.CardFactory;
import com.savita.machi_koro.models.cards.Cards;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.Player;
import com.savita.machi_koro.zip.models.CompanyCardCompressed;
import com.savita.machi_koro.zip.models.GameCompressed;
import com.savita.machi_koro.zip.models.PlayerCompressed;

import java.util.stream.Collectors;

public class GameZipper {
    public static GameCompressed zip(Game game) {
        GameCompressed compressed = new GameCompressed();
        compressed.setDiceSum(game.getDiceSum());
        compressed.setDices(game.getDices());
        compressed.setStarted(game.isStarted());
        if(game.getActivePlayer() != null) {
            compressed.setActivePlayer(PlayerZipper.zip(game.getActivePlayer()));
        }

        if(game.getWinner() != null) {
            compressed.setWinner(PlayerZipper.zip(game.getWinner()));
        }

        compressed.setNews(game.getNews());

        compressed.getPlayers().addAll(game.getPlayers().stream().map(PlayerZipper::zip).toList());
        compressed.getCompanies().addAll(game.getCompanies().stream().map(CompanyCardZipper::zip).toList());

        return compressed;
    }

    public static Game unzip(GameCompressed compressed, Game game) {
        unzipPlayers(compressed, game);
        var active = compressed.getActivePlayer();
        if(active != null) {
            game.setActivePlayer(unzipPlayer(active, game));
        }

        var winner = compressed.getWinner();
        if(winner != null) {
            game.setWinner(unzipPlayer(winner, game));
        }

        game.setDices(compressed.getDices());
        game.setDiceSum(compressed.getDiceSum());
        game.setStarted(compressed.isStarted());

        unzipField(compressed, game);

        game.getNews().clear();
        game.getNews().addAll(compressed.getNews());

        return game;
    }

    private static Player unzipPlayer(PlayerCompressed compressed, Game game) {
        var optional = game.getPlayers().stream().filter(x -> x.getName().equals(compressed.getName())).findFirst();
        return optional.orElse(null);
    }
    private static void unzipPlayers(GameCompressed compressed, Game game) {
        for(PlayerCompressed player : compressed.getPlayers()) {
            var optional = game.getPlayers().stream().filter(x -> x.getName().equals(player.getName())).findFirst();
            if(optional.isPresent()) {
                PlayerZipper.unzip(player, optional.get());
            } else {
                game.addPlayer(new Player(player.getName()));
            }
        }
    }

    private static void unzipField(GameCompressed compressed, Game game) {
        var groupedCompanies = compressed
                .getCompanies()
                .stream()
                .collect(Collectors.groupingBy(CompanyCardCompressed::getType));

        for (Cards type : groupedCompanies.keySet()) {
            int count = groupedCompanies.get(type).size();
            int currentCount = (int) game.getCompanies().stream().filter(x -> x.getType() == type).count();

            for (int i = currentCount; i < count; i++) {
                try {
                    game.getCompanies().add(CardFactory.createCompany(type));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            while (currentCount > count) {
                var optional = game.getCompanies().stream().filter(x -> x.getType() == type).findFirst();

                if (optional.isEmpty()) break;

                game.getCompanies().remove(optional.get());

                currentCount = (int) game.getCompanies().stream().filter(x -> x.getType() == type).count();
            }
        }
    }
}
