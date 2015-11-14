package server.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import server.manager.User;
import shared.communication.CatanCommand;
import shared.communication.Credentials;
import shared.definitions.exceptions.CatanException;
import shared.model.ClientModel;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The Handler for all requests of the format game/*
 */
public class GameHandler implements HttpHandler
{
    AbstractServerFacade facade = AbstractServerFacade.getInstance();
    private static Logger LOGGER = Logger.getLogger(GameHandler.class.getName());
    private String response;

    /**
     * returns the url parameters in a map
     * @param query
     * @return map
     */
    public static Map<String, String> parseQuery(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    /**
     * Parses the HTTP Context for the command and executes it
     * @param httpExchange the httpExchange to parse.
     */
    @Override public void handle(HttpExchange httpExchange) throws IOException
    {
        try {
            URI uri = httpExchange.getRequestURI();
            String commandString = uri.getPath().split("/")[2];

            // instantiate CookieManager
            CookieManager manager = new CookieManager();
            CookieHandler.setDefault(manager);
            CookieStore cookieJar = manager.getCookieStore();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpExchange.getRequestBody()));
            StringBuilder builder = new StringBuilder();
            String json;
            while ((json = reader.readLine()) != null)
            {
                builder.append(json);
            }
            String request = builder.toString();
            String method = httpExchange.getRequestMethod();
            List<String> cookies = httpExchange.getRequestHeaders().get("Cookie");


//            Credentials creds = new Credentials(request);

//            String className = "shared.communication."
//                    + Character.toUpperCase(commandString.charAt(0))
//                    + commandString.substring(1);

            if(commandString.toLowerCase() == "model"){
                Map<String, String> params = parseQuery(uri.getQuery());
                int version = Integer.parseInt(params.get("version"));
                ClientModel model;

                if(params.isEmpty()){
                    model = facade.getGameState();
                }
                else{
                    model = facade.getGameState(version);
                }

                if(model == null){
                    response = "true";
                }
                else{
                    response = model.toString();
                }
            }

            // set initial headers
            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "text");

            // create cookie
            HttpCookie cookie = new HttpCookie("catan.user", cookies.get(0));

            // add cookie to CookieStore
            cookieJar.add(uri, cookie);

            // set cookie
            respHeaders.set("Set-cookie", cookie.getValue() + ";Path=/");

            // send response
            response = "Success";
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
        }
        catch(Exception e)
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
//        LOGGER.entering(this.getClass().getCanonicalName(), "handle");
//        try {
//            //Handling cookie
//            String cookie =  httpExchange.getRequestHeaders().getFirst("Cookie");
//            //Handling input Request
//            InputStream requestBody = httpExchange.getRequestBody();
//            ObjectInput in = new ObjectInputStream(requestBody);
//            String className = httpExchange.getRequestURI().getPath().split("/")[1]; //TODO get the class name from the context
//            Constructor c = Class.forName(className).getConstructor(String.class, AbstractServerFacade.class);
//            CatanCommand request = (CatanCommand)c.newInstance(in.readObject(), facade);
//            in.close();
//            requestBody.close();
//
//            //Handling response to request
//            httpExchange.getResponseHeaders().set("Set-cookie", cookie);
//            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
//            String result = request.execute(-1);
//            OutputStream responseBody = httpExchange.getResponseBody();
//            ObjectOutput out = new ObjectOutputStream(responseBody);
//            out.writeObject(result);
//            out.close();
//            responseBody.close();
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally {
//            if (httpExchange != null) {
//                httpExchange.close();
//            }
//            LOGGER.exiting(this.getClass().getCanonicalName(), "handle");
//        }
    }
}
