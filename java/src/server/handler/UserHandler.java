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
import java.net.HttpURLConnection;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 11/4/2015.
 */
public class UserHandler implements HttpHandler
{
    AbstractServerFacade facade = new MockServerFacade();
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    /**
     * Parses the HTTP Context for the command and executes it
     * @param httpExchange
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
            CatanCommand command = null;
            if(commandString.equalsIgnoreCase("login"))
            {
                command = new Credentials(request, AbstractServerFacade.getInstance());
            }
            //Set cookie
            httpExchange.getResponseHeaders().set("Set-cookie", cookie);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            //Handling response to request
            String result = command.execute();
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
