package server;

import com.sun.net.httpserver.HttpServer;
import server.facade.AbstractServerFacade;
import server.facade.ServerFacade;
import server.handler.*;
import server.manager.GameManager;
import server.manager.UserManager;
import server.plugin.IPersistenceFactory;
import server.plugin.PluginManager;

import java.io.FileNotFoundException;
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
    private int checkpoints = 10;
    private String persistenceType = "sqlite";
    private HttpServer server;
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public Server(int server)
    {
        DEFAULT_PORT_NUMBER = server;
        AbstractServerFacade.setFacade(new ServerFacade());
    }

    public Server(String address, int port)
    {
        try
        {
            setPersistence();
            AbstractServerFacade.setFacade(new ServerFacade());
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

    public void setPersistence()
    {
        try
        {
            IPersistenceFactory factory = PluginManager.createFactory(persistenceType);
            UserManager.getInstance().setUserPersistence(factory.createUserPersistenceEngine());
            GameManager.getInstance().setGamePersistence(factory.createGamePersistenceEngine(checkpoints));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Server(String s, int port, String persistenceType, int checkpoints)
    {
        this(s,port,persistenceType);
        this.checkpoints = checkpoints;
        setPersistence();
    }

    public Server(String s, int port, String persistenceType)
    {
        this(s,port);
        this.persistenceType = persistenceType;
        setPersistence();
    }

    public static Server run()
    {
        return run(DEFAULT_PORT_NUMBER);
    }
    public static Server run(int port)
    {
        Server server = null;
            server = new Server("0.0.0.0", port);
            //DEFAULT_HOST_NAME = InetAddress.getLocalHost().getHostName();
        server.start();
        return server;
    }
    public void start()
    {
        LOGGER.info("Starting Server on Host:"+DEFAULT_HOST_NAME +" Port:"+DEFAULT_PORT_NUMBER);
        try
        {
            LOGGER.info("Address: "+InetAddress.getLocalHost());
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        server.start();
    }

    public void stop()
    {
        server.stop(0);
    }

    public static void main(String[] args) {
        //AbstractServerFacade.setFacade(new MockServerFacade());
        AbstractServerFacade.setFacade(new ServerFacade());
        switch(args.length) {
            case 0:
                Server.run();
                break;
            case 1://port
                int port = Integer.parseInt(args[0]);
                Server.run(port);
                break;
            case 2://port,persistence-type
                port = Integer.parseInt(args[0]);
                String persistenceType = args[1];
                Server.run(port, persistenceType);
                break;
            case 3:
                port = Integer.parseInt(args[0]);
                persistenceType = args[1];
                int checkpoints = Integer.parseInt(args[2]);
                AbstractServerFacade.setFacade(new ServerFacade());
                Server.run(port, persistenceType, checkpoints);
                break;
        }
    }

    private static Server run(int port, String persistenceType, int checkpoints)
    {
        Server server = null;
        server = new Server("0.0.0.0", port, persistenceType, checkpoints);
        //DEFAULT_HOST_NAME = InetAddress.getLocalHost().getHostName();
        server.start();
        return server;
    }

    private static Server run(int port, String persistenceType)
    {
        Server server = null;
        server = new Server("0.0.0.0", port, persistenceType);
        //DEFAULT_HOST_NAME = InetAddress.getLocalHost().getHostName();
        server.start();
        return server;
    }
}