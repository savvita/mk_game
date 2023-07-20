package com.savita.machi_koro.client.controllers;

import com.savita.machi_koro.db.models.Auth;
import com.savita.machi_koro.db.models.Register;
import com.savita.machi_koro.log.Log;
import com.savita.machi_koro.net.Messaging;
import com.savita.machi_koro.server.AuthResponse;
import com.savita.machi_koro.server.RequestCodes;
import com.savita.machi_koro.server.ResponseCodes;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class AuthController {
    @FXML private ImageView icon;
    @FXML private TextField login;
    @FXML private PasswordField password;
    @FXML private PasswordField passwordConfirmation;
    @FXML private Label link;
    @FXML private Button authBtn;
    @FXML private Button cancelBtn;
    @FXML private Label passwordConfirmationLabel;

    private Socket socket;

    private final StringProperty loginProperty = new SimpleStringProperty();
    private final StringProperty passwordProperty = new SimpleStringProperty();
    private final StringProperty passwordConfirmProperty = new SimpleStringProperty();

    private Image bmw;
    private Image honda;

    private final Log log = new Log(System.out::println);

    @FXML public void initialize() {
        setSignIn();
        cancelBtn.setCancelButton(true);
        cancelBtn.setOnAction(x -> Platform.exit());

        login.textProperty().bindBidirectional(loginProperty);
        password.textProperty().bindBidirectional(passwordProperty);
        passwordConfirmation.textProperty().bindBidirectional(passwordConfirmProperty);
    }

    private boolean connect() {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("net.properties"))) {
            props.load(in);

            socket = new Socket(props.getProperty("host"), Integer.parseInt(props.getProperty("port")));
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    private void setSignIn() {
        if(bmw == null) {
            Path path = Paths.get("images", "bmw.png");
            bmw = new Image("file:" + path);
        }
        icon.setImage(bmw);
        link.setText("Нет аккаунта?");
        link.setOnMouseClicked(x -> setSignUp());
        passwordConfirmation.setVisible(false);
        passwordConfirmationLabel.setVisible(false);
        authBtn.setOnAction(x -> signIn());
        authBtn.setText("Войти");

        loginProperty.set("");
        passwordProperty.set("");
        passwordConfirmProperty.set("");
    }

    private void setSignUp() {
        if(honda == null) {
            Path path = Paths.get("images", "honda.png");
            honda = new Image("file:" + path);
        }
        icon.setImage(honda);
        link.setText("Уже есть аккаунт?");
        link.setOnMouseClicked(x -> setSignIn());
        passwordConfirmation.setVisible(true);
        passwordConfirmationLabel.setVisible(true);
        authBtn.setOnAction(x -> signUp());
        authBtn.setText("Зарегистрироваться");

        loginProperty.set("");
        passwordProperty.set("");
        passwordConfirmProperty.set("");
    }

    private void signIn() {
        if(!connect()) return;
        try {
            Messaging.sendObj(socket, RequestCodes.SIGN_IN);
            Messaging.getObj(socket, ResponseCodes.class);
            Auth auth = new Auth(loginProperty.get(), passwordProperty.get());
            Messaging.sendObj(socket, auth);
            AuthResponse result = Messaging.getObj(socket, AuthResponse.class);

            if(result.getCode() == ResponseCodes.INVALID_CREDENTIALS) {
                Messaging.sendString(socket, "Ok");
                showError("Логин и/или пароль неправильные");
            } else {
                StageHelper.showMK(socket, loginProperty.get());
                Stage stage = (Stage) cancelBtn.getScene().getWindow();
                stage.hide();
            }
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }
    private void signUp() {
        if(!connect()) return;
        try {
            Messaging.sendObj(socket, RequestCodes.SIGN_UP);
            Messaging.getObj(socket, ResponseCodes.class);
            Register auth = new Register(loginProperty.get(), passwordProperty.get(), passwordConfirmProperty.get());
            Messaging.sendObj(socket, auth);
            AuthResponse result = Messaging.getObj(socket, AuthResponse.class);

            if(result.getCode() == ResponseCodes.LOGIN_IS_USED) {
                Messaging.sendString(socket, "Ok");
                showError("Логин уже занят");
            } else if (result.getCode() == ResponseCodes.FAILED) {
                Messaging.sendString(socket, "Ok");
                showError("Не удалось зарегистрировать пользователя");
            } else {
                StageHelper.showMK(socket, loginProperty.get());
                Stage stage = (Stage) cancelBtn.getScene().getWindow();
                stage.hide();
            }
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Ошибка авторизации");
        alert.setContentText(msg);

        alert.showAndWait();
    }
}
