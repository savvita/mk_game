package com.savita.machi_koro.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Messaging {
    public static String getString(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readUTF();
    }

    public static void sendString(Socket socket, String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(msg);
    }

    public static <T> boolean sendObj(Socket socket, T obj) throws IOException {
        String json = getJson(obj);

        if(json != null) {
            sendString(socket, json);
            return true;
        }
        return false;
    }

    public static <T> T getObj(Socket socket, Class<T> t) throws IOException {
        String response = getString(socket);
        return getObjFromJson(response, t);
    }

    private static <T> String getJson(T obj) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    private static <T> T getObjFromJson(String json, Class<T> t) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, t);
    }
}

