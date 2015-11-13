package client.proxy;

import client.data.PlayerInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.AIType;
import shared.definitions.exceptions.AddAIException;
import shared.definitions.exceptions.GameQueryException;
import shared.definitions.exceptions.InvalidCredentialsException;
import shared.model.ClientModel;

import java.io.*;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerProxy is used in Dependency injection, along with MockProxy, to return
 * specific results from the server. <br>
 * <br>
 * the ServerProxy actually communicates with the server and gets the actual
 * server responses back, which it sends on to the Poller, or the Facade as
 * needed.
 * 
 * @author cstaheli
 *
 */
public class ServerProxy implements IProxy
{
    /**
     * Contexts
     * 
     */
    /* user : Operations about users */
    // POST
    private static final String USER_LOGIN = "user/login";
    private static final String USER_REGISTER = "user/register";

    /* games : Game queries/actions (pre-joining) */
    // GET
    private static final String LIST_GAMES = "games/list";
    // POST
    private static final String CREATE_GAME = "games/create";
    private static final String JOIN_GAME = "games/join";
    private static final String SAVE_GAME = "games/save";
    private static final String LOAD_GAME = "games/load";

    /* game : Operations for the game you're in. (requires cookie) */
    // GET
    private static final String GET_GAME_STATE = "game/model";
    private static final String LIST_AI = "game/listAI";
    // POST
    private static final String RESET_GAME = "game/reset";
    private static final String ADD_AI = "game/addAI";
    // GET/POST
    private static final String COMMANDS = "game/commands";

    /* moves : Actions you can take mid game. (requires cookie) */
    // POST
    private static final String SEND_CHAT = "moves/sendChat";
    private static final String ROLL_NUMBER = "moves/rollNumber";
    private static final String ROB_PLAYER = "moves/robPlayer";
    private static final String FINISH_TURN = "moves/finishTurn";
    private static final String BUY_DEV_CARD = "moves/buyDevCard";
    private static final String YEAR_OF_PLENTY = "moves/Year_of_Plenty";
    private static final String ROAD_BUILDING = "moves/Road_Building";
    private static final String SOLDIER = "moves/Soldier";
    private static final String MONOPOLY = "moves/Monopoly";
    private static final String MONUMENT = "moves/Monument";
    private static final String BUILD_ROAD = "moves/buildRoad";
    private static final String BUILD_SETTLEMENT = "moves/buildSettlement";
    private static final String BUILD_CITY = "moves/buildCity";
    private static final String OFFER_TRADE = "moves/offerTrade";
    private static final String ACCEPT_TRADE = "moves/acceptTrade";
    private static final String MARITIME_TRADE = "moves/maritimeTrade";
    private static final String DISCARD_CARDS = "moves/discardCards";

    /* util : Change how the server runs */
    // POST
    private static final String CHANGE_LOG_LEVEL = "util/changeLogLevel";

    /* HTML request type */
    private static final String HTTP_POST = "POST";
    private static final String HTTP_GET = "GET";
    private String URLPrefix, host, port;

    /* Logger */
    private final static Logger LOGGER = Logger.getLogger(ServerProxy.class.getName());

    /* Cookies */
    private String catanUserCookie, catanGameCookie;

    public ServerProxy()
    {
        LOGGER.setLevel(Level.ALL);
        host = "localhost";
        port = "8081";
        setURLPrefix(host, port);
    }

    public void setURLPrefix(String host, String port)
    {
        URLPrefix = "http://" + host + ":" + port + "/";
    }


    public ServerProxy(String host, String port)
    {
        this();
        setURLPrefix(host, port);
    }

    /**
     * Parses the Catan User Cookie to set the current player.
     * 
     * @return The info of the current player.
     * @throws UnsupportedEncodingException
     */
    private PlayerInfo buildPlayerInfoFromCookie() throws UnsupportedEncodingException
    {
        String decodedPlayerCookie = URLDecoder.decode(catanUserCookie, "UTF-8");
        LOGGER.info("Decoded Player Cookie" + decodedPlayerCookie);
        return new PlayerInfo(decodedPlayerCookie);
    }

    @Override
    public PlayerInfo userLogin(Credentials credentials) throws InvalidCredentialsException
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Credentials.class, credentials).create();
        String request = gson.toJson(credentials);
        String response = doPost(USER_LOGIN, request);
        LOGGER.log(Level.INFO, "User Login Response:" + response);
        if (response == null)
        {
            throw new InvalidCredentialsException("Login response returned null");
        }
        try
        {
            return buildPlayerInfoFromCookie();
        }
        catch (UnsupportedEncodingException e)
        {
            LOGGER.log(Level.SEVERE, "Can't set client player from cookie", e);
            throw new InvalidCredentialsException("Can't set client player from cookie", e);
        }
    }

    @Override
    public PlayerInfo userRegister(Credentials credentials) throws InvalidCredentialsException
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Credentials.class, credentials).create();
        String request = gson.toJson(credentials);
        String response = doPost(USER_REGISTER, request);
        LOGGER.log(Level.INFO, "User Register Response:" + response);
        if (response == null)
        {
            throw new InvalidCredentialsException("Register response returned null");
        }
        try
        {
            return buildPlayerInfoFromCookie();
        }
        catch (UnsupportedEncodingException e)
        {
            throw new InvalidCredentialsException("Can't set client player from cookie");
        }
    }

    @Override
    public ListGamesResponse listGames()
    {
        String response = doGet(LIST_GAMES, "");
        LOGGER.log(Level.INFO, " List Games Response:" + response);
        return new ListGamesResponse(response);
    }

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest)
    {
        Gson gson = new GsonBuilder().create();
        String request = gson.toJson(createGameRequest);
        String response = doPost(CREATE_GAME, request);
        LOGGER.log(Level.INFO, "Create Game Response:" + response);
        return new CreateGameResponse(response);
    }

    @Override
    public void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException
    {
        Gson gson = new GsonBuilder().create();
        String request = gson.toJson(joinGameRequest);
        String response = doPost(JOIN_GAME, request);
        LOGGER.log(Level.INFO, "Join Game Response:" + response);
        if (response == null)
        {
            throw new GameQueryException();
        }
    }

    @Override
    public ClientModel getGameState(int versionNumber)
    {
        String query = "";
        if (versionNumber != -1)
        {
            query = String.format("?version=%s", versionNumber);
        }
        String response = doGet(GET_GAME_STATE, query);
        ClientModel responseModel = null;
        /* If the model has changed */
        if (response != null)
        {
            if (!response.equals("\"true\""))
            {
                responseModel = new ClientModel(response);
                LOGGER.log(Level.INFO, "Get Game Response:" + response);
            }
        }

        return responseModel;
    }

    @Override
    public ListAIResponse listAI()
    {
        String response = doGet(LIST_AI, "");
        LOGGER.log(Level.INFO, "List AI Response:" + response);
        return new ListAIResponse(response);
    }

    @Override
    public void addAI(AIType aiType) throws AddAIException
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(AIType.class, aiType).create();
        String request = gson.toJson(aiType);
        String response = doPost(ADD_AI, request);
        LOGGER.log(Level.INFO, "Add AI Response:" + response);
        if (response == null)
        {
            throw new AddAIException();
        }

    }

    @Override
    public ClientModel sendChat(SendChatCommand sendChat)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(SendChatCommand.class, sendChat).create();
        String request = gson.toJson(sendChat);
        String response = doPost(SEND_CHAT, request);
        LOGGER.log(Level.INFO, "Send Chat Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel rollNumber(RollNumberCommand rollNumber)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(RollNumberCommand.class, rollNumber).create();
        String request = gson.toJson(rollNumber);
        String response = doPost(ROLL_NUMBER, request);
        LOGGER.log(Level.INFO, "Roll Number Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel acceptTrade(AcceptTradeCommand acceptTrade)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(AcceptTradeCommand.class, acceptTrade).create();
        String request = gson.toJson(acceptTrade);
        String response = doPost(ACCEPT_TRADE, request);
        LOGGER.log(Level.INFO, "Accept Trade Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel discardCards(DiscardCardsCommand discardCards)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(DiscardCardsCommand.class, discardCards).create();
        String request = gson.toJson(discardCards);
        String response = doPost(DISCARD_CARDS, request);
        LOGGER.log(Level.INFO, "Discard Cards Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buildRoad(BuildRoadCommand buildRoad)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuildRoadCommand.class, buildRoad).create();
        String request = gson.toJson(buildRoad);
        String response = doPost(BUILD_ROAD, request);
        LOGGER.log(Level.INFO, "Build Road Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buildSettlement(BuildSettlementCommand buildSettlement)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuildSettlementCommand.class, buildSettlement).create();
        String request = gson.toJson(buildSettlement);
        String response = doPost(BUILD_SETTLEMENT, request);
        LOGGER.log(Level.INFO, "Build Settlement Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buildCity(BuildCityCommand buildCity)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuildCityCommand.class, buildCity).create();
        String request = gson.toJson(buildCity);
        String response = doPost(BUILD_CITY, request);
        LOGGER.log(Level.INFO, "Build City Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel offerTrade(OfferTradeCommand offerTrade)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(OfferTradeCommand.class, offerTrade).create();
        String request = gson.toJson(offerTrade);
        String response = doPost(OFFER_TRADE, request);
        LOGGER.log(Level.INFO, "Offer Trade Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel maritimeTrade(MaritimeTradeCommand maritimeTrade)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(MaritimeTradeCommand.class, maritimeTrade).create();
        String request = gson.toJson(maritimeTrade);
        String response = doPost(MARITIME_TRADE, request);
        LOGGER.log(Level.INFO, "Maritime Trade Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel robPlayer(RobPlayerCommand robPlayer)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(RobPlayerCommand.class, robPlayer).create();
        String request = gson.toJson(robPlayer);
        String response = doPost(ROB_PLAYER, request);
        LOGGER.log(Level.INFO, "Rob Player Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel finishTurn(FinishTurnCommand finishTurn)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(FinishTurnCommand.class, finishTurn).create();
        String request = gson.toJson(finishTurn);
        String response = doPost(FINISH_TURN, request);
        LOGGER.log(Level.INFO, "Finish Turn Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buyDevCard(BuyDevCardCommand buyDevCard)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuyDevCardCommand.class, buyDevCard).create();
        String request = gson.toJson(buyDevCard);
        String response = doPost(BUY_DEV_CARD, request);
        LOGGER.log(Level.INFO, "Buy Dev Card Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel soldier(SoldierCommand soldier)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(SoldierCommand.class, soldier).create();
        String request = gson.toJson(soldier);
        String response = doPost(SOLDIER, request);
        LOGGER.log(Level.INFO, "Soldier Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel yearOfPlenty(YearOfPlentyCommand yearOfPlenty)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(YearOfPlentyCommand.class, yearOfPlenty).create();
        String request = gson.toJson(yearOfPlenty);
        String response = doPost(YEAR_OF_PLENTY, request);
        LOGGER.log(Level.INFO, "Year Of Plenty Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel roadBuilding(RoadBuildingCommand roadBuilding)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(RoadBuildingCommand.class, roadBuilding).create();
        String request = gson.toJson(roadBuilding);
        String response = doPost(ROAD_BUILDING, request);
        LOGGER.log(Level.INFO, "Road Building Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel monopoly(MonopolyCommand monopoly)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(MonopolyCommand.class, monopoly).create();
        String request = gson.toJson(monopoly);
        String response = doPost(MONOPOLY, request);
        LOGGER.log(Level.INFO, "Monopoly Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel monument(MonumentCommand monument)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(MonumentCommand.class, monument).create();
        String request = gson.toJson(monument);
        String response = doPost(MONUMENT, request);
        LOGGER.log(Level.INFO, "Monument Response:" + response);
        return new ClientModel(response);
    }

    private String doPost(String commandName, String postDataJson)
    {
        String response = null;
        LOGGER.log(Level.INFO, commandName + "/" + postDataJson);
        try
        {
            URL url = new URL(URLPrefix + commandName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod(HTTP_POST);
            if (catanUserCookie != null)
            {
                String cookieRequest = buildCookie();
                connection.setRequestProperty("Cookie", cookieRequest);
            }
            connection.setDoOutput(true);
            connection.connect();
            OutputStream stream = connection.getOutputStream();
            stream.write(postDataJson.getBytes());
            stream.flush();
            stream.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                String cookie = connection.getHeaderField("Set-cookie");
                if (cookie != null)
                {
                    parseCookie(cookie);
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String string;
                while ((string = reader.readLine()) != null)
                {
                    builder.append(string);
                }
                response = builder.toString();
                LOGGER.log(Level.INFO, response + ", Cookie: " + cookie);
            }
            else
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder builder = new StringBuilder();
                String string;
                while ((string = reader.readLine()) != null)
                {
                    builder.append(string);
                }
                LOGGER.log(Level.WARNING, builder.toString());
            }
            return response;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a cookie to set the ServerProxy's catan.user cookie, or the
     * catan.game cookie.
     * 
     * @param cookieHeader
     *        the cookie to be parsed
     */
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

    /**
     * Builds a cookie for a HttpURLConnection
     * @return the cookie received previously from the server.
     */
    private String buildCookie()
    {
        String cookieRequest;
        if (catanGameCookie != null)
        {
            cookieRequest = "catan.user=" + catanUserCookie + "; " + "catan.game=" + catanGameCookie;
        }
        else
        {
            cookieRequest = "catan.user=" + catanUserCookie;
        }
        return cookieRequest;
    }

    private String doGet(String commandName, String query)
    {
        String response = null;
        LOGGER.fine(commandName + query);
        try
        {
            URL url = new URL(URLPrefix + commandName + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
            if (catanUserCookie != null)
            {
                String cookieRequest = buildCookie();
                connection.setRequestProperty("Cookie", cookieRequest);
            }
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String string;
                while ((string = reader.readLine()) != null)
                {
                    builder.append(string);
                }
                response = builder.toString();
                LOGGER.fine(response);
            }
            else
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder builder = new StringBuilder();
                String string;
                while ((string = reader.readLine()) != null)
                {
                    builder.append(string);
                }
                LOGGER.log(Level.WARNING, builder.toString());
            }
            return response;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
