package com.savita.machi_koro.server;

import com.savita.machi_koro.log.Log;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    private Socket socket;
    private final Log log;
    private String username;
    public Client() {
        log = new Log((System.out::println));
    }
    public Client(Socket socket) {
        this();
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void close() {
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                log.error("Error closing socket");
            }
        }
    }
}
