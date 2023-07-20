package com.savita.machi_koro.server;

import com.savita.machi_koro.db.models.Auth;
import com.savita.machi_koro.db.models.Register;
import com.savita.machi_koro.db.models.User;
import com.savita.machi_koro.db.repositories.IAuthRepository;
import com.savita.machi_koro.log.Log;
import com.savita.machi_koro.models.game.Game;
import com.savita.machi_koro.models.game.GameConstants;
import com.savita.machi_koro.net.Messaging;
import com.savita.machi_koro.zip.models.GameCompressed;
import com.savita.machi_koro.models.game.Player;
import com.savita.machi_koro.zip.zippers.GameZipper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private final int port;
    private final String host;
    private ServerSocket server;
    private final IAuthRepository repository;
    private Game game;
    private final List<Client> clients = new ArrayList<>();
    private final Log log = new Log(System.out::println);

    public Server(String host, int port, IAuthRepository repository) {
        this.host = host;
        this.port = port;
        this.repository = repository;
    }

    public void start() {
        game = new Game();
        try {
            server = new ServerSocket(port);
            log.info("Server started");

            do {
                if(clients.size() > GameConstants.PLAYER_COUNT) {
                    log.warning("Maximum players reached");
                    continue;
                };
                Socket socket = server.accept();
                Client client = new Client(socket);
                User user = authorize(socket);
                if(user == null) {
                    continue;
                }

                client.setUsername(user.getUsername());
                clients.add(client);
                log.info(String.format("Client %s accepted", client.getUsername()));
                handleClient(client);
                if(clients.size() == GameConstants.PLAYER_COUNT) {
                    for(Client cl : clients) {
                        Thread thread = new Thread(() -> listen(cl));
                        thread.setDaemon(true);
                        thread.start();
                    }
                }

            } while(true);
        } catch(Exception ex) {
            log.error("Error starting server");
            log.error(ex.getMessage());
        }
    }

    private User authorize(Socket socket) {
        User user = null;
        try {
            RequestCodes code = Messaging.getObj(socket, RequestCodes.class);
            Messaging.sendObj(socket, ResponseCodes.OK);
            switch (code) {
                case SIGN_IN -> user = signIn(socket);
                case SIGN_UP -> user = signUp(socket);
            }
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
        return user;
    }

    private User signIn(Socket socket) {
        try {
            Auth auth = Messaging.getObj(socket, Auth.class);
            User user = repository.signIn(auth);

            if(user != null) {
                if(clients.stream().anyMatch(x -> x.getUsername().equals(user.getUsername()))) {
                    Messaging.sendObj(socket, new AuthResponse(ResponseCodes.ALREADY_SIGNED_IN));
                    Messaging.getString(socket);
                    log.info("User is already signed in");
                    socket.close();
                } else {
                    Messaging.sendObj(socket, new AuthResponse(ResponseCodes.OK, user));
                    Messaging.getString(socket);
                    log.info(String.format("User %s is signed in", auth.getUsername()));
                }
                return user;
            }
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }

        try {
            Messaging.sendObj(socket, new AuthResponse(ResponseCodes.INVALID_CREDENTIALS));
            Messaging.getString(socket);
            socket.close();
            log.warning("Attempting sign in with invalid credentials");
        } catch (Exception ex) {

        }
        return null;
    }

    private User signUp(Socket socket) {
        try {
            Register auth = Messaging.getObj(socket, Register.class);
            if(!repository.checkUsername(auth.getUsername())) {
                Messaging.sendObj(socket, new AuthResponse(ResponseCodes.LOGIN_IS_USED));
                Messaging.getString(socket);
                socket.close();
                log.warning("Attempting sign up with username in use");
                return null;
            }
            User user = repository.signUp(auth);
            if(user != null) {
                Messaging.sendObj(socket, new AuthResponse(ResponseCodes.OK, user));
                Messaging.getString(socket);
                log.info("Registered new user " + auth.getUsername());
                return user;
            }
        } catch(Exception ex) {
            log.error(ex.getMessage());
        }
        try {
            Messaging.sendObj(socket, new AuthResponse(ResponseCodes.FAILED));
            Messaging.getString(socket);
            socket.close();
            log.error("Registration of user is failed");
        } catch (Exception ex) {

        }

        return null;
    }

    private void handleClient(Client client) {
        game.addPlayer(new Player(client.getUsername()));
        if(game.getPlayers().size() == GameConstants.PLAYER_COUNT) {
            game.start();
        }

        try{
            broadcast(clients, GameZipper.zip(game));
        } catch(Exception ex){
            log.error("Cannot send a message to client " + client.getUsername());
            log.error(ex.getMessage());
            clients.remove(client);
            client.close();
        }
    }
    private void listen(Client client){
        while(true) {
            try {
                GameCompressed msg = Messaging.getObj(client.getSocket(), GameCompressed.class);
                log.info("Receive message from " + client.getUsername());
                if(msg != null) {
                    broadcast(clients.stream().filter(x -> x != client).toList(), msg);
                }
            } catch(IOException ex) {
                log.error("Cannot send message to client " + client.getUsername());
                log.error(ex.getMessage());
                clients.remove(client);
                client.close();
                break;
            }
        }
    }

    private void broadcast(Collection<Client> clients, GameCompressed msg) throws IOException {
        for(Client client : clients) {
            Messaging.<GameCompressed>sendObj(client.getSocket(), msg);
            log.info("Send message to " + client.getUsername());
        }
    }

    public void close() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception ex) {}
    }
}
