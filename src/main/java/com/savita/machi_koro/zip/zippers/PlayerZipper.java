package com.savita.machi_koro.zip.zippers;

import com.savita.machi_koro.models.cards.CardFactory;
import com.savita.machi_koro.models.cards.cities.CityCard;
import com.savita.machi_koro.models.cards.company.CompanyCard;
import com.savita.machi_koro.models.game.Player;
import com.savita.machi_koro.zip.models.PlayerCompressed;

public class PlayerZipper {

    public static PlayerCompressed zip(Player player) {
        PlayerCompressed compressed = new PlayerCompressed();
        compressed.setDiceCount(player.getDiceCount());
        compressed.setBankAmount(player.getAccount().getAmount());
        compressed.setDepositAmount(player.getAccount().getDepositCount());
        compressed.getCompanies().addAll(player.getCompanies().stream().map(CompanyCardZipper::zip).toList());
        compressed.getCities().addAll(player.getCities().stream().map(CityCardZipper::zip).toList());
        compressed.setAdditionValues(player.getAdditionValues());
        compressed.setName(player.getName());

        return compressed;
    }

    public static Player unzip(PlayerCompressed compressed, Player player) {
        player.setDiceCount(compressed.getDiceCount());
        player.getAccount().setAmount(compressed.getBankAmount());
        player.getAccount().setDepositCount(compressed.getDepositAmount());
        player.getAdditionValues().clear();
        player.getAdditionValues().putAll(compressed.getAdditionValues());

        unzipCompanies(compressed, player);
        unzipCities(compressed, player);

        return player;
    }

    private static void unzipCompanies(PlayerCompressed compressed, Player player) {
        player.getCompanies().clear();

        player.getCompanies().addAll(compressed.getCompanies().stream().map(x -> {
            try {
                CompanyCard card = CardFactory.createCompany(x.getType());
                if(x.isClosed()) {
                    card.close();
                }
                return card;
            } catch(Exception ex) {
                return null;
            }
        }).toList());
    }

    private static void unzipCities(PlayerCompressed compressed, Player player) {
        for(CityCard city : player.getCities()) {
            var optional = compressed.getCities().stream().filter(x -> x.getType() == city.getType()).findFirst();
            if(optional.isPresent()) {
                if(optional.get().isBuilt()) {
                    city.build(player);
                } else {
                    city.destroy(player);
                }
            }
        }
    }
}
