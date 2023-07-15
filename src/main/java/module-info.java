module com.savita.machi_koro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.savita.machi_koro to javafx.fxml;
    opens com.savita.machi_koro.controllers to javafx.fxml;

    exports com.savita.machi_koro;
    exports com.savita.machi_koro.controllers to javafx.fxml;
}