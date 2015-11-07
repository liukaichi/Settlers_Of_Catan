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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 11/4/2015.
 */
public class MovesHandler implements HttpHandler
{
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());

    /**
     * Parses the HTTP Context for the command and executes it
     *
     * @param httpExchange
     * @throws IOException
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
        try
        {
            String clientAddress = httpExchange.getLocalAddress().getHostString();
            InputStream requestBody = httpExchange.getRequestBody();
            ObjectInput in = new ObjectInputStream(requestBody);
            String className = httpExchange.getHttpContext().toString(); //TODO get the class name from the context
            AbstractServerFacade.useRealServerFacade(false);
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand request = (CatanCommand) c.newInstance(in.readObject());
            in.close();
            requestBody.close();

            //Handling response to request
            httpExchange.getResponseHeaders().set("Set-cookie", "cookie");
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String result = request.execute(-1); //TODO use cookie value
            OutputStream responseBody = httpExchange.getResponseBody();
            ObjectOutput out = new ObjectOutputStream(responseBody);
            out.writeObject(result);
            out.close();
            responseBody.close();

        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE, "Error in server.MovesHandler.handle():", e);
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
