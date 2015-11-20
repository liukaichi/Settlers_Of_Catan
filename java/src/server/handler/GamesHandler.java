package server.handler;

import client.data.PlayerInfo;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import shared.communication.CatanCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /games/*
 */
public class GamesHandler implements HttpHandler
{
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    AbstractServerFacade facade = AbstractServerFacade.getInstance();
    private String response;

    /**
     * Parses the HTTP Context for the command and executes it
     *
     * @param httpExchange the httpExchange to parse.
     * @throws IOException
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try
        {
            URI uri = httpExchange.getRequestURI();
            String cookie = URLDecoder.decode(httpExchange.getRequestHeaders().getFirst("Cookie"),"UTF-8");

            LOGGER.info("Received Cookie from uri "+uri.toString()+": "+cookie+"\n");

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder jsonBuilder = new StringBuilder();
            String nextLine;
            while ((nextLine = reader.readLine()) != null)
            {
                jsonBuilder.append(nextLine);
            }
            String json = jsonBuilder.toString();

            //Handling input Request
            String commandString = uri.getPath().split("/")[2];
            //TODO get the class name from the context
            String className = "shared.communication." + Character.toUpperCase(commandString.charAt(0)) + commandString
                    .substring(1) + "GameRequest";
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand newCommand = (CatanCommand) c.newInstance(json);

            // set response
            if (commandString.equalsIgnoreCase("join"))
            {
                String playerIDLabel = "\"playerID\": ";
                cookie = cookie.substring(cookie.indexOf("=") + 1);
                if (cookie.contains("catan.game"))
                {
                    cookie = cookie.substring(0,cookie.indexOf("; catan.game"));
                }
                PlayerInfo playerInfo = new PlayerInfo(cookie);
                int playerID = playerInfo.getId();
                String gameId = newCommand.execute(playerID);
                // testing
                cookie = "catan.game=" + gameId;
                response = "Success";
            } else
            {
                response = newCommand.execute(-1);
            }

            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text/html");
            // set cookie
            respHeaders.set("Set-cookie", cookie + ";Path=/;");
            LOGGER.info(commandString + "- Set Response Header: Set-cookie: "+respHeaders.get("Set-cookie")+"\n");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
        } catch (Exception e)
        {
            e.printStackTrace();
            response = e.getLocalizedMessage();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());

        } finally
        {
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            if (httpExchange != null)
            {
                httpExchange.close();
            }
            LOGGER.exiting(this.getClass().getCanonicalName(), "handle");
        }
    }
}
