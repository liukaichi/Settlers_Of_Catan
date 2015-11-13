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
            //Handling cookie
            String receivedCookie =  httpExchange.getRequestHeaders().getFirst("Cookie");



            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder jsonBuilder = new StringBuilder();
            String nextLine;
            while ((nextLine = reader.readLine()) != null)
            {
                jsonBuilder.append(nextLine);
            }
            String json = jsonBuilder.toString();

            //Handling input Request
            URI uri = httpExchange.getRequestURI();
            String commandString = uri.getPath().split("/")[2];
            InputStream requestBody = httpExchange.getRequestBody();
            //TODO get the class name from the context
            String className = Character.toUpperCase(commandString.charAt(0))
                    + commandString.substring(1)
                    + "GameRequest";
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand newCommand = (CatanCommand)c.newInstance(json);

            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");

            // create cookie
            //TODO change this empty string to be the real cookie!!!!
            HttpCookie cookie = new HttpCookie("catan.user", "");

            // send response
            response = newCommand.execute(-1);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            requestBody.close();


        }
        catch(Exception e)
        {
            e.printStackTrace();
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
