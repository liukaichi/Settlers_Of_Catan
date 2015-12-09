package server;

import com.sun.net.httpserver.HttpServer;
import org.newdawn.slick.Game;
import server.facade.AbstractServerFacade;
import server.facade.ServerFacade;
import server.handler.*;
import server.manager.GameManager;
import server.manager.UserManager;
import server.plugin.IPersistenceEngine;
import server.plugin.IPersistenceFactory;
import server.plugin.PluginManager;

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
    private static int PORT_NUMBER = 8081;
    private static String HOST_NAME = "0.0.0.0";
    private static int CHECKPOINTS = 10;
    private static String PERSISTENCE_TYPE = "java";
    private HttpServer server;
    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public void createContexts()
    {
            server.createContext("/game/", new GameHandler());
            server.createContext("/games/", new GamesHandler());
            server.createContext("/moves/", new MovesHandler());
            server.createContext("/user/", new UserHandler());
            server.createContext("/docs/api/data", new SwaggerHandler.JSONAppender(""));
            server.createContext("/docs/api/view", new SwaggerHandler.BasicFile(""));
    }

    public Server()
    {
        try
        {
            server = HttpServer.create(new InetSocketAddress(HOST_NAME, PORT_NUMBER), MAX_WAITING_CONNECTIONS);
            createContexts();
            server.setExecutor(null); // creates a default executor
            AbstractServerFacade.setFacade(new ServerFacade());
            PluginManager pluginManager = new PluginManager();
            IPersistenceFactory factory = pluginManager.createFactory(PERSISTENCE_TYPE);
            IPersistenceEngine persistenceEngine = factory.createPersistenceEngine(CHECKPOINTS);

            UserManager userManager = UserManager.getInstance();
            GameManager gameManager = GameManager.getInstance();

            userManager.setPersistenceEngine(persistenceEngine);
            gameManager.setPersistenceEngine(persistenceEngine);


            persistenceEngine.initializeDatabase();
            userManager.loadUsers();
            gameManager.loadPersistedGames();
            gameManager.loadPersistedCommands();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void start()
    {
        LOGGER.info("Starting Server on Host:"+ HOST_NAME +" Port:"+ PORT_NUMBER);
        try
        {
            LOGGER.info("Address: "+InetAddress.getLocalHost());
            LOGGER.info("Persistence-Type: "+PERSISTENCE_TYPE);
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

    private static Server run(int port, String persistenceType, int checkpoints)
    {
        CHECKPOINTS = checkpoints;
        return run(port, persistenceType);
    }

    private static Server run(int port, String persistenceType)
    {
        PERSISTENCE_TYPE = persistenceType;
        return run(port);
    }

    private static Server run(int port)
    {
        PORT_NUMBER = port;
        return run();
    }

    public static Server run()
    {
        Server server = new Server();
        server.start();
        return server;
    }

    public static void main(String[] args) {
        int port;
        String persistenceType;
        int checkpoints;
        //AbstractServerFacade.setFacade(new MockServerFacade());
        AbstractServerFacade.setFacade(new ServerFacade());
        switch(args.length) {
        case 0:
            Server.run();
            break;
        case 1://port
            port = Integer.parseInt(args[0]);
            Server.run(port);
            break;
        case 2://port,persistence-type
            port = Integer.parseInt(args[0]);
            persistenceType = args[1];
            Server.run(port, persistenceType);
            break;
        case 3:
            port = Integer.parseInt(args[0]);
            persistenceType = args[1];
            checkpoints = Integer.parseInt(args[2]);
            Server.run(port, persistenceType, checkpoints);
            break;
        }
    }
}