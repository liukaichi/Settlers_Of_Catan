package server;

import com.sun.net.httpserver.HttpServer;
import server.handler.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.rmi.ServerException;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 11/6/2015.
 */
public class Server {

    private static final int MAX_WAITING_CONNECTIONS = 10;
    private HttpServer server;
    private static int SERVER_PORT_NUMBER = 8081;
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Server(int server) {
        SERVER_PORT_NUMBER = server;
    }

    public Server() {

    }

    public static void main(String[] args) {
        if (args.length >= 1) {

            int serverPort = Integer.parseInt(args[0]);
            new Server(serverPort).run();
        }
        else {
            new Server().run();
        }
    }

    private void run() {
        try {
            LOGGER.info("Starting Server...");
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/game/", new GameHandler());
            server.createContext("/games/", new GamesHandler());
            server.createContext("/moves/", new MovesHandler());
            server.createContext("/user/", new UserHandler());

            server.createContext("/docs/api/data", new SwaggerHandler.JSONAppender(""));
            server.createContext("/docs/api/view", new SwaggerHandler.BasicFile(""));

            server.setExecutor(null); // creates a default executor
            server.start();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}