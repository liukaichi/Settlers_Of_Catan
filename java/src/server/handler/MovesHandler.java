package server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import shared.communication.CatanCommand;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /moves/*
 */
public class MovesHandler implements HttpHandler
{
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
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
            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");
            //Handling cookie
            String receivedCookie = URLDecoder.decode(httpExchange.getRequestHeaders().getFirst("Cookie"), "UTF-8");

            if (receivedCookie == null)
            {
                throw new Exception("No cookie found");
            }

            String[] cookies = receivedCookie.split(";");
            if (cookies.length < 2)
            {
                throw new Exception("Game cookie not set");
            }

            //set gameID
            int gameID = Integer.parseInt(cookies[1].substring(cookies[1].indexOf('=') + 1));

            //resend the cookie
            respHeaders.set("Set-cookie", receivedCookie + ";Path=/;");

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
            String className =
                    "shared.communication.moveCommands." + Character.toUpperCase(commandString.charAt(0)) + commandString.substring(1) + "Command";
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand newCommand = (CatanCommand) c.newInstance(json);

            // send response
            response = newCommand.execute(gameID);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            requestBody.close();

        } catch (Exception e)
        {
            //e.printStackTrace();
            response = e.getLocalizedMessage();
            LOGGER.log(Level.SEVERE, "Bad Request: " + HttpURLConnection.HTTP_BAD_REQUEST, e);
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
