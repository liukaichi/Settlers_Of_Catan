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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /user/*
 */
public class UserHandler implements HttpHandler
{
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    private AbstractServerFacade facade = AbstractServerFacade.getInstance();
    private String response;

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
            LOGGER.fine(httpExchange.getRequestMethod()+" "+uri.getPath()+" "+httpExchange.getResponseCode());

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
                LOGGER.info(String.format("executing login using %s", creds));
            } else if (commandString.equalsIgnoreCase("register"))
            {
                user = facade.registerUser(creds);

                LOGGER.info(String.format("executing register using %s", creds));
            } else
            {
                throw new Exception("commandString doesn't match any allowed commands.");
            }
            //passing this point means login/register was successful.

            // create cookie
            String cookie = "catan.user=" + URLEncoder.encode(user.toString().replaceAll("\\s",""), "UTF-8");

            // set cookie
            respHeaders.set("Set-cookie", cookie + ";Path=/;");
            LOGGER.fine("Set Response Header: Set-cookie: '" + respHeaders.get("Set-cookie") + "'\n");

            // send response
            response = "Success";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
        } catch (Exception e)
        {
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