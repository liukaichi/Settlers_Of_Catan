package server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import server.manager.User;
import shared.communication.Credentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /user/*
 */
public class UserHandler implements HttpHandler
{
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    private AbstractServerFacade facade = AbstractServerFacade.getInstance();

    /**
     * Parses the HTTP Context for the command and executes it.
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
            String commandString = uri.getPath().split("/")[2];

            // instantiate CookieManager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar = manager.getCookieStore();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder builder = new StringBuilder();
            String json;
            while ((json = reader.readLine()) != null)
            {
                builder.append(json);
            }
            String request = builder.toString();
            System.out.print(request);
            Credentials creds = new Credentials(request);

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
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Set-cookie", cookie.getValue() + ";Path=/");
            respHeaders.set("Content-Type", "application/json");

            // send response
            String result = user.toString();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, result.length());
            OutputStream os = httpExchange.getResponseBody();

            os.write(result.getBytes());
            os.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (httpExchange != null)
            {
                httpExchange.close();
            }
            LOGGER.exiting(this.getClass().getCanonicalName(), "handle");
        }
    }
}