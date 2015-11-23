package server.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
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
import java.net.URLDecoder;
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
            LOGGER.fine(httpExchange.getRequestMethod()+" "+uri.getPath()+" "+httpExchange.getResponseCode());
            String commandString = uri.getPath().split("/")[2];


            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");

            String cookie = URLDecoder.decode(httpExchange.getRequestHeaders().getFirst("Cookie"),"UTF-8");
            LOGGER.info("Received Cookie: "+cookie+"\n");
            if(cookie == null){
                throw new Exception("No cookie found");
            }
            String[] cookies = cookie.split(";");

            if(cookies.length < 2){
                throw new Exception("Game cookie not set. Login and join before calling this method.");
            }
            //set gameID
            int gameID = Integer.parseInt(cookies[1].substring(cookies[1].indexOf('=') + 1));

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
                int version = -1;
                Map<String, String> params = new HashMap<>();
                if(uri.getQuery() != null)
                {
                    params = parseQuery(uri.getQuery());
                    version = Integer.parseInt(params.get("version"));
                }
                ClientModel model;


                if (params.isEmpty())
                {
                    model = facade.getGameState(gameID, -1);//TODO these need to get the proper game
                } else
                {
                    model = facade.getGameState(gameID, version);
                }

                if (model == null)
                {
                    response = "true";
                } else
                {
                    response = model.toString();
                }
            }
            else if(commandString.toLowerCase().equals("listai"))
            {
                JsonArray array = new JsonArray();
                array.add(new JsonPrimitive("LARGEST_ARMY"));
                response = array.toString();
                respHeaders.set("Content-Type", "application/json");
            }
            else if(commandString.toLowerCase().equals("addai"))
            {
                response = "Not implemented for this phase";
                respHeaders.set("Set-cookie", cookie + ";Path=/;");
                LOGGER.info("Set Response Header: Set-cookie: "+respHeaders.get("Set-cookie")+"\n");
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, response.length());
                return;
            }

            // set cookie
            respHeaders.set("Set-cookie", cookie + ";Path=/;");
            LOGGER.info("Set Response Header: Set-cookie: "+respHeaders.get("Set-cookie")+"\n");

            // send response
//            response = "Success";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
        } catch (Exception e)
        {
            //e.printStackTrace();
            response = e.getLocalizedMessage();
            LOGGER.fine("Bad Request: "+HttpURLConnection.HTTP_BAD_REQUEST +" "+ e.getLocalizedMessage());
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
