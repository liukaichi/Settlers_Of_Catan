package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Created by dtaylor on 11/4/2015.
 */
public class UserHandler implements HttpHandler
{
    /**
     * Parses the HTTP Context for the command and executes it
     * @param httpExchange
     * @throws IOException
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {

    }
}
