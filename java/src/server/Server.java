package server;

import com.sun.net.httpserver.HttpServer;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import server.facade.ServerFacade;
import server.handler.*;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 11/6/2015.
 */
public class Server
{

    private static final int MAX_WAITING_CONNECTIONS = 10;
    private static int DEFAULT_PORT_NUMBER = 8081;
    private static String DEFAULT_HOST_NAME = "localhost";
    private HttpServer server;
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Server(int server)
    {
        DEFAULT_PORT_NUMBER = server;
    }

    public Server(String address, int port)
    {
        try
        {
            //TODO: Change this to accept the port instead of the DEFAULT PORT NUMBER
            server = HttpServer.create(new InetSocketAddress(address, port), 0);
            server.createContext("/game/", new GameHandler());
            server.createContext("/games/", new GamesHandler());
            server.createContext("/moves/", new MovesHandler());
            server.createContext("/user/", new UserHandler());

            server.createContext("/docs/api/data", new SwaggerHandler.JSONAppender(""));
            server.createContext("/docs/api/view", new SwaggerHandler.BasicFile(""));

            server.setExecutor(null); // creates a default executor
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Server run()
    {
        return run(DEFAULT_PORT_NUMBER);
    }
    public static Server run(int port)
    {
        Server server = null;
            server = new Server(DEFAULT_HOST_NAME, port);
            //DEFAULT_HOST_NAME = InetAddress.getLocalHost().getHostName();
        server.start();
        return server;
    }
    public void start()
    {
        LOGGER.info("Starting Server on Host:"+DEFAULT_HOST_NAME +" Port:"+DEFAULT_PORT_NUMBER);
        server.start();
    }

    public void stop()
    {
        server.stop(0);
    }

    public static void main(String[] args) {

        switch(args.length) {
            case 0:
                //AbstractServerFacade.setFacade(new MockServerFacade());
                AbstractServerFacade.setFacade(new ServerFacade());
                Server.run();
                break;
            case 1:
                int port = Integer.parseInt(args[0]);
                //AbstractServerFacade.setFacade(new MockServerFacade());
                AbstractServerFacade.setFacade(new ServerFacade());
                Server.run(port);
                break;
        }
    }
}