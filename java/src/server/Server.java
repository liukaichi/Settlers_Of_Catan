package server;

import com.sun.net.httpserver.HttpServer;
import server.handler.GameHandler;
import server.handler.GamesHandler;
import server.handler.MovesHandler;
import server.handler.UserHandler;

import java.net.InetSocketAddress;

/**
 * Created by dtaylor on 11/6/2015.
 */
public class Server
{
    public Server()
    {
        try
        {
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/game/", new GameHandler());
            server.createContext("/games/", new GamesHandler());
            server.createContext("/moves/", new MovesHandler());
            server.createContext("/user/", new UserHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
