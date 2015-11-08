package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.MockServerFacade;
import server.facade.*;
import shared.communication.CatanCommand;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.rmi.ServerException;
import java.util.logging.Logger;

/**
 * The handler for all contexts of the form /moves/*
 */
public class MovesHandler implements HttpHandler
{
    AbstractServerFacade facade = new MockServerFacade();
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());

    /**
     * Parses the HTTP Context for the command and executes it
     * @param httpExchange the httpExchange to parse.
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try {
            //Handling cookie
            String cookie =  httpExchange.getRequestHeaders().getFirst("Cookie");
            //Handling input Request
            InputStream requestBody = httpExchange.getRequestBody();
            ObjectInput in = new ObjectInputStream(requestBody);
            String className = httpExchange.getRequestURI().getPath().split("/")[1]; //TODO get the class name from the context
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand request = (CatanCommand)c.newInstance(in.readObject());
            in.close();
            requestBody.close();

            //Handling response to request
            httpExchange.getResponseHeaders().set("Set-cookie", cookie);
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String result = request.execute(-1);
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
