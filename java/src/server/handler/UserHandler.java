package server.handler;

import com.google.gson.JsonStreamParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import shared.communication.CatanCommand;
import shared.communication.Credentials;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /user/*
 */
public class UserHandler implements HttpHandler
{
    AbstractServerFacade facade = new MockServerFacade();
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    /**
     * Parses the HTTP Context for the command and executes it.
     * @param httpExchange the httpExchange to parse.
     * @throws IOException
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try {
            //Handling cookie
            String cookie =  httpExchange.getRequestHeaders().getFirst("Cookie");
            //Handling input Request
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder builder = new StringBuilder();
            String json;
            while ((json = reader.readLine()) != null)
            {
                builder.append(json);
            }
            String request = builder.toString();

            String commandString = httpExchange.getRequestURI().getPath().split("/")[2]; //TODO get the class name from the context
            Credentials creds;
            if(commandString.equalsIgnoreCase("login"))
            {
                creds = new Credentials(request);
                facade.signInUser(creds);
            }
            else if(commandString.equalsIgnoreCase("register"))
            {
                creds = new Credentials(request);
                facade.registerUser(creds);
                HashMap<String, List<String>> cookieHeaders = new HashMap<String, List<String>>();
                ArrayList<String> cookieLines = new ArrayList<>();
                cookieLines.add("catan.user="+creds.getUsername()); //TODO we need to get userId
                cookieHeaders.put("Set-cookie",cookieLines);
                CookieManager cMan = new CookieManager();
                cMan.put(httpExchange.getRequestURI(),cookieHeaders);
                httpExchange.getResponseHeaders().set("Set-cookie",cMan.getCookieStore().getCookies().get(0).toString());
            }
            /*
            to help know how to build cookie
    private void parseCookie(String cookieHeader)
    {
        HttpCookie httpCookie = HttpCookie.parse(cookieHeader).get(0);
        String cookie = httpCookie.toString();
        if (cookie.contains("catan.user"))
        {
            this.catanUserCookie = cookie.substring(cookie.indexOf("=") + 1);
        }
        else if (cookie.contains("catan.game"))
        {
            this.catanGameCookie = cookie.substring(cookie.indexOf("=") + 1);
        }
    }

             */
            //Set cookie
            httpExchange.getResponseHeaders().set("Set-cookie", cookie);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            //Handling response to request
            String result = command.execute(-1);
            OutputStream responseBody = httpExchange.getResponseBody();
            ObjectOutput out = new ObjectOutputStream(responseBody);
            out.writeObject(result);
            out.close();
            responseBody.close();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (httpExchange != null) {
                httpExchange.close();
            }
            LOGGER.exiting(this.getClass().getCanonicalName(), "handle");
        }
    }
}
