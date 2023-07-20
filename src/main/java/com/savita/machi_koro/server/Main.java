package com.savita.machi_koro.server;

import com.savita.machi_koro.db.repositories.AuthRepository;
import com.savita.machi_koro.db.repositories.IAuthRepository;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("net.properties"))) {
            props.load(in);
        } catch (Exception ex) {
            System.out.println(ex);
        }


        Properties dbProps = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("db.properties"))) {
            dbProps.load(in);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        IAuthRepository repo = new AuthRepository(dbProps.getProperty("url"), dbProps.getProperty("username"), dbProps.getProperty("password"));

        Server server = new Server(props.getProperty("host"), Integer.parseInt(props.getProperty("port")), repo);
        server.start();
        server.close();
    }
}
