module com.savita.machi_koro {
    requires com.google.common;
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires java.sql;


    opens com.savita.machi_koro to javafx.fxml;
    opens com.savita.machi_koro.client.controllers to javafx.fxml;
    opens com.savita.machi_koro.models.game to gson;
    opens com.savita.machi_koro.models.cards.company to gson;
    opens com.savita.machi_koro.models.cards to gson;
    opens com.savita.machi_koro.models.cards.cities to gson;
    opens com.savita.machi_koro.events to gson;
    exports com.savita.machi_koro.events to gson;

    exports com.savita.machi_koro.client;
    exports com.savita.machi_koro.client.controllers;
    exports com.savita.machi_koro.models.cards;
    exports com.savita.machi_koro.models.cards.cities;
    exports com.savita.machi_koro.models.cards.company;
    opens com.savita.machi_koro.client to javafx.fxml;
    exports com.savita.machi_koro.models.game to gson;
    exports com.savita.machi_koro.zip.models to gson;
    opens com.savita.machi_koro.zip.models to gson;
    opens com.savita.machi_koro.db.models to gson;
    opens com.savita.machi_koro.server to gson;
}