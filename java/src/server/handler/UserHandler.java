package server.handler;

import com.google.gson.JsonStreamParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import server.facade.ServerFacade;
import server.manager.User;
import shared.communication.CatanCommand;
import shared.communication.Credentials;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /user/*
 */
public class UserHandler implements HttpHandler
{
    private AbstractServerFacade facade = AbstractServerFacade.getInstance();
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    private String response;
    /**
     * Parses the HTTP Context for the command and executes it.
     * @param httpExchange the httpExchange to parse.
     * @throws IOException
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try {
            URI uri = httpExchange.getRequestURI();
            String commandString = uri.getPath().split("/")[2];

            // instantiate CookieManager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar = manager.getCookieStore();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder jsonBuilder = new StringBuilder();
            String nextLine;
            while ((nextLine = reader.readLine()) != null)
            {
                jsonBuilder.append(nextLine);
            }
            String json = jsonBuilder.toString();

            Credentials creds = new Credentials(json);

            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");

            User user = null;
            if (commandString.equalsIgnoreCase("login"))
            {
                user = facade.signInUser(creds);
            } else if (commandString.equalsIgnoreCase("register"))
            {
                user = facade.registerUser(creds);
            }

            // create cookie
            HttpCookie cookie = new HttpCookie("catan.user", user.toString()); //TODO this might cause a NullPointer.

            // add cookie to CookieStore
            cookieJar.add(uri, cookie);

            // set cookie
            respHeaders.set("Set-cookie", cookie.getValue() + ";Path=/");

            // send response
            response = "Success";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
        }
        catch(Exception e)
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