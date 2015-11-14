package server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import server.manager.User;
import shared.communication.CatanCommand;
import shared.communication.Credentials;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /games/*
 */
public class GamesHandler implements HttpHandler
{
    AbstractServerFacade facade = AbstractServerFacade.getInstance();
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    private String response;


    /**
     * Parses the HTTP Context for the command and executes it
     * @param httpExchange the httpExchange to parse.
     * @throws IOException
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try {
            URI uri = httpExchange.getRequestURI();

            // instantiate CookieManager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar = manager.getCookieStore();

            //Handling cookie
            cookieJar.add(uri, new HttpCookie("catan.user", httpExchange.getRequestHeaders().getFirst("Cookie")));

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
            String className = "shared.communication."
                    + Character.toUpperCase(commandString.charAt(0))
                    + commandString.substring(1)
                    + "GameRequest";
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand newCommand = (CatanCommand)c.newInstance(json);

            // set response
            if(commandString.equalsIgnoreCase("join")){
                String gameId = newCommand.execute(-1);
                // testing
                gameId = "1";
                cookieJar.add(uri, new HttpCookie("catan.game", gameId));
                response = "Success";
            }
            else if(commandString.equalsIgnoreCase("list")){
                response = newCommand.execute(-1);
            }

            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");

            // create cookie


            String cookie = "";
            for(HttpCookie co : cookieJar.getCookies()){
                cookie += co.getValue() + ";";
            }
            respHeaders.set("Set-cookie", cookie);

            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response = e.getLocalizedMessage();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, response.length());

        }
        finally {
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

            if (httpExchange != null) {
                httpExchange.close();
            }
            LOGGER.exiting(this.getClass().getCanonicalName(), "handle");
        }
    }
}
