package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.IServerFacade;
import server.facade.MockServerFacade;
import server.facade.ServerFacade;
import shared.communication.CatanCommand;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.rmi.ServerException;
import java.util.logging.Logger;

/**
 * Created by dtaylor on 11/4/2015.
 */
public class MovesHandler implements HttpHandler
{
    IServerFacade facade = new MockServerFacade();
    private static Logger LOGGER = Logger.getLogger(MovesHandler.class.getName());
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        LOGGER.entering("MovesHandler", "handle");
        try {
            String clientAddress = httpExchange.getLocalAddress().getHostString();
            InputStream requestBody = httpExchange.getRequestBody();
            ObjectInput in = new ObjectInputStream(requestBody);
            String className = httpExchange.getHttpContext().toString(); //TODO get the class name from the context
            Constructor c = Class.forName(className).getConstructor(String.class);
            CatanCommand request = (CatanCommand)c.newInstance(in.readObject());
            in.close();
            requestBody.close();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            String result = request.execute(facade);
            OutputStream responseBody = httpExchange.getResponseBody();
            ObjectOutput out = new ObjectOutputStream(responseBody);
            out.writeObject(result);
            out.close();
            responseBody.close();

        } catch (ClassNotFoundException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            LOGGER.severe("Error in server.SubmitBatchHandler.handle(): "
                    + e.getMessage());
            e.printStackTrace();
        } catch (ServerException e) {
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR,
                    0);
            LOGGER.severe("Error in server.SubmitBatchHandler.handle(): "
                    + e.getMessage());
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        } finally {
            if (httpExchange != null)
                httpExchange.close();
            LOGGER.exiting("server.server.SubmitBatchHandler", "handle");
        }
    }
}
