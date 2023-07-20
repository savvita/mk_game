package com.savita.machi_koro.db.repositories;

import com.savita.machi_koro.db.hash.Hash;
import com.savita.machi_koro.db.models.Auth;
import com.savita.machi_koro.db.models.Register;
import com.savita.machi_koro.db.models.User;

import java.sql.*;

public class AuthRepository implements IAuthRepository {
    private final String url;
    private final String username;
    private final String password;

    public AuthRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public User signIn(Auth auth) {
        if(!auth.validate()) return null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT id, username FROM users WHERE username= ? AND password=? LIMIT 1";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, auth.getUsername());
                preparedStatement.setString(2, Hash.encode(auth.getPassword()));
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new User(resultSet.getInt("id"), resultSet.getString("username"));
                }
            }
        } catch(Exception ex) {
            return null;
        }

        return null;
    }

    public User signUp(Register auth) {
        if(!auth.validate()) return null;
        if(!checkUsername(auth.getUsername())) return null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                String query = "INSERT INTO users (username, password) VALUES (?, ?);";
                PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, auth.getUsername());
                preparedStatement.setString(2, Hash.encode(auth.getPassword()));
                preparedStatement.executeUpdate();
                ResultSet keys = preparedStatement.getGeneratedKeys();

                if (keys.next()) {
                    return new User(keys.getInt(1), auth.getUsername());
                }
            }
        } catch(Exception ex) {
            return null;
        }

        return null;
    }


    public boolean checkUsername(String username) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, this.username, this.password)){
                String query = "SELECT id FROM users WHERE username= ? LIMIT 1;";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();

                return !resultSet.next();
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }
}
