package server;

import com.sun.net.httpserver.HttpServer;
import server.facade.AbstractServerFacade;
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

    public Server(String address, int port) {
        try {
            server = HttpServer.create(new InetSocketAddress(address, 8081), 0);
            server.createContext("/game/", new GameHandler());
            server.createContext("/games/", new GamesHandler());
            server.createContext("/moves/", new MovesHandler());
            server.createContext("/user/", new UserHandler());

            server.createContext("/docs/api/data", new SwaggerHandler.JSONAppender(""));
            server.createContext("/docs/api/view", new SwaggerHandler.BasicFile(""));

            server.setExecutor(null); // creates a default executor
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static Server run()
    {
        Server server = new Server("localhost",8081);
        server.start();
        return server;
    }
    public void start()
    {
        LOGGER.info("Starting Server...");
        server.start();
    }

    public void stop()
    {
        server.stop(0);
    }

    public static void main(String[] args) {

        switch(args.length) {
            case 0:
                AbstractServerFacade.useRealServerFacade(false);
                Server.run();
                break;
        }
    }
}