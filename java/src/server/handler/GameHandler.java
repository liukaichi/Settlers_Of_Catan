package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import shared.communication.CatanCommand;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 11/4/2015.
 */
public class GameHandler implements HttpHandler
{
    AbstractServerFacade facade = new MockServerFacade();
    private static Logger LOGGER = Logger.getLogger(GameHandler.class.getName());
    /**
     * Parses the HTTP Context for the command and executes it
     * @param httpExchange
     * @throws IOException
     */
    @Override public void handle(HttpExchange httpExchange)
    {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try {
            //Handling cookie
            String cookie =  httpExchange.getRequestHeaders().getFirst("Cookie");
            //Handling input Request
            InputStream requestBody = httpExchange.getRequestBody();
            ObjectInput in = new ObjectInputStream(requestBody);
            String className = httpExchange.getRequestURI().getPath().split("/")[1]; //TODO get the class name from the context
            Constructor c = Class.forName(className).getConstructor(String.class, AbstractServerFacade.class);
            CatanCommand request = (CatanCommand)c.newInstance(in.readObject(), facade);
            in.close();
            requestBody.close();

            //Handling response to request
            httpExchange.getResponseHeaders().set("Set-cookie", cookie);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String result = request.execute();
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
