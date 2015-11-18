package server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import shared.model.ClientModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The Handler for all requests of the format game/*
 */
public class GameHandler implements HttpHandler
{
    private static Logger LOGGER = Logger.getLogger(GameHandler.class.getName());
    AbstractServerFacade facade = AbstractServerFacade.getInstance();
    private String response;

    /**
     * returns the url parameters in a map
     *
     * @param query
     * @return map
     */
    public static Map<String, String> parseQuery(String query)
    {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&"))
        {
            String pair[] = param.split("=");
            if (pair.length > 1)
            {
                result.put(pair[0], pair[1]);
            } else
            {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    /**
     * Parses the HTTP Context for the command and executes it
     *
     * @param httpExchange the httpExchange to parse.
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        try
        {
            URI uri = httpExchange.getRequestURI();
            String commandString = uri.getPath().split("/")[2];
            String cookie = httpExchange.getRequestHeaders().getFirst("Cookie");
            String[] cookies = cookie.split(";");

            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");

            if(cookies.length < 2){
                throw new Exception("Game cookie not set. Login and join before calling this method.");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder builder = new StringBuilder();
            String json;
            while ((json = reader.readLine()) != null)
            {
                builder.append(json);
            }

            String request = builder.toString();

            if (commandString.toLowerCase().equals("model"))
            {
                Map<String, String> params = parseQuery(uri.getQuery());
                int version = Integer.parseInt(params.get("version"));
                ClientModel model;

                if (params.isEmpty())
                {
                    model = facade.getGameState(-1);
                } else
                {
                    model = facade.getGameState(version);
                }

                if (model == null)
                {
                    response = "true";
                } else
                {
                    response = model.toString();
                }
            }

            // set cookie
//            respHeaders.set("Set-cookie", cookie + ";Path=/");

            // send response
//            response = "Success";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
        } catch (Exception e)
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
