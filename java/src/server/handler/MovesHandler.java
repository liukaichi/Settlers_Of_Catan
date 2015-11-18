package server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import shared.communication.CatanCommand;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.rmi.ServerException;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /moves/*
 */
public class MovesHandler implements HttpHandler {
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    AbstractServerFacade facade = AbstractServerFacade.getInstance();
    private String response;

    /**
     * Parses the HTTP Context for the command and executes it
     *
     * @param httpExchange the httpExchange to parse.
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try {
            String cookie = httpExchange.getRequestHeaders().getFirst("Cookie");
            String[] cookies = cookie.split(";");

            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");

            if (cookies.length < 2) {
                throw new Exception("Game cookie not set. Login and join before calling this method");
            }
            else {
                // create cookie
                respHeaders.set("Set-cookie", cookie);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder jsonBuilder = new StringBuilder();
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                jsonBuilder.append(nextLine);
            }
            String json = jsonBuilder.toString();

            //Handling input Request
            URI uri = httpExchange.getRequestURI();
            String commandString = uri.getPath().split("/")[2];
            InputStream requestBody = httpExchange.getRequestBody();
            //TODO get the class name from the context
            String className = "shared.communication.moveCommands."
                    + Character.toUpperCase(commandString.charAt(0))
                    + commandString.substring(1)
                    + "Command";
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand newCommand = (CatanCommand) c.newInstance(json);

            // send response
            response = newCommand.execute(-1);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            requestBody.close();


        }
        catch (Exception e) {
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
