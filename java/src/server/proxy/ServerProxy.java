package server.proxy;

import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.google.gson.*;

import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.AIType;
import shared.definitions.exceptions.*;
import shared.model.ClientModel;

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
    private static final String MONOPOLY = "moves/monopoly";
    private static final String MONUMENT = "moves/monument";
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
    private String URLPrefix = "http://localhost:8081/";

    /* Logger */
    private final static Logger LOGGER = Logger.getLogger(ServerProxy.class.getName());

    /* Cookies */
    private String catanUserCookie, catanGameCookie;

    public ServerProxy()
    {
        LOGGER.setLevel(Level.ALL);
    }

    @Override
    public void userLogin(Credentials credentials) throws SignInException
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Credentials.class, credentials).create();
        String request = gson.toJson(credentials);
        String response = doPost(USER_LOGIN, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        if (response == null)
        {
            throw new SignInException(response);
        }
    }

    @Override
    public void userRegister(Credentials credentials) throws SignInException
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Credentials.class, credentials).create();
        String request = gson.toJson(credentials);
        String response = doPost(USER_REGISTER, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        if (response == null)
        {
            throw new SignInException(response);
        }
    }

    @Override
    public void changeLogLevel(Level level)
    {
        String response = doPost(CHANGE_LOG_LEVEL, level.toString());
        LOGGER.setLevel(level);
        LOGGER.log(Level.INFO, "Response:" + response);
    }

    @Override
    public ListGamesResponse listGames()
    {
        String response = doGet(LIST_GAMES, "");
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ListGamesResponse(response);
    }

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest)
    {
        Gson gson = new GsonBuilder().create();
        String request = gson.toJson(createGameRequest);
        String response = doPost(CREATE_GAME, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new CreateGameResponse(response);
    }

    @Override
    public void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException
    {
        Gson gson = new GsonBuilder().create();
        String request = gson.toJson(joinGameRequest);
        String response = doPost(JOIN_GAME, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        if (response == null)
        {
            throw new GameQueryException();
        }
    }

    @Override
    public void saveGame(SaveGameRequest saveGameRequest) throws GameQueryException
    {
        Gson gson = new GsonBuilder().create();
        String request = gson.toJson(saveGameRequest);
        String response = doPost(SAVE_GAME, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        if (response == null)
        {
            throw new GameQueryException();
        }
    }

    @Override
    public void loadGame(LoadGameRequest loadGameRequest) throws GameQueryException
    {
        Gson gson = new GsonBuilder().create();
        String request = gson.toJson(loadGameRequest);
        String response = doPost(LOAD_GAME, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        if (response == null)
        {
            throw new GameQueryException();
        }

    }

    @Override
    public ClientModel getGameState(int versionNumber)
    {
        String query = String.format("version=%s", versionNumber);
        String response = doGet(GET_GAME_STATE, query);
        LOGGER.log(Level.INFO, "Response:" + response);
        ClientModel responseModel = null;
        /* If the model has changed */
        if (!response.equals("\"true\""))
        {
            responseModel = (ClientModel) deserializeJSON(response);
        }
        return responseModel;
    }

    @Override
    public ClientModel resetGame()
    {
        // Unecessary
        return null;
    }

    @Override
    public GetCommandsResponse getCommands()
    {
        // Unecessary
        return null;
    }

    @Override
    public ClientModel postCommands(PostCommandsRequest request)
    {
        // Unecessary
        return null;
    }

    @Override
    public ListAIResponse listAI()
    {
        String response = doGet(LIST_AI, "");
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ListAIResponse(response);
    }

    @Override
    public void addAI(AIType aiType) throws IllegalArgumentException, GameQueryException, AddAIException
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(AIType.class, aiType).create();
        String request = gson.toJson(aiType);
        String response = doPost(ADD_AI, request);
        LOGGER.log(Level.INFO, "Response:" + response);
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
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel rollNumber(RollNumberCommand rollNumber)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(RollNumberCommand.class, rollNumber).create();
        String request = gson.toJson(rollNumber);
        String response = doPost(ROLL_NUMBER, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel acceptTrade(AcceptTradeCommand acceptTrade)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(AcceptTradeCommand.class, acceptTrade).create();
        String request = gson.toJson(acceptTrade);
        String response = doPost(ACCEPT_TRADE, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel discardCards(DiscardCardsCommand discardCards)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(DiscardCardsCommand.class, discardCards).create();
        String request = gson.toJson(discardCards);
        String response = doPost(DISCARD_CARDS, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buildRoad(BuildRoadCommand buildRoad)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuildRoadCommand.class, buildRoad).create();
        String request = gson.toJson(buildRoad);
        String response = doPost(BUILD_ROAD, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buildSettlement(BuildSettlementCommand buildSettlement)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuildSettlementCommand.class, buildSettlement).create();
        String request = gson.toJson(buildSettlement);
        String response = doPost(BUILD_SETTLEMENT, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buildCity(BuildCityCommand buildCity)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuildCityCommand.class, buildCity).create();
        String request = gson.toJson(buildCity);
        String response = doPost(BUILD_CITY, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel offerTrade(OfferTradeCommand offerTrade)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(OfferTradeCommand.class, offerTrade).create();
        String request = gson.toJson(offerTrade);
        String response = doPost(OFFER_TRADE, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel maritimeTrade(MaritimeTradeCommand maritimeTrade)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(MaritimeTradeCommand.class, maritimeTrade).create();
        String request = gson.toJson(maritimeTrade);
        String response = doPost(MARITIME_TRADE, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel robPlayer(RobPlayerCommand robPlayer)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(RobPlayerCommand.class, robPlayer).create();
        String request = gson.toJson(robPlayer);
        String response = doPost(ROB_PLAYER, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel finishTurn(FinishTurnCommand finishTurn)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(FinishTurnCommand.class, finishTurn).create();
        String request = gson.toJson(finishTurn);
        String response = doPost(FINISH_TURN, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel buyDevCard(BuyDevCardCommand buyDevCard)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(BuyDevCardCommand.class, buyDevCard).create();
        String request = gson.toJson(buyDevCard);
        String response = doPost(BUY_DEV_CARD, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel soldier(SoldierCommand soldier)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(SoldierCommand.class, soldier).create();
        String request = gson.toJson(soldier);
        String response = doPost(SOLDIER, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel yearOfPlenty(YearOfPlentyCommand yearOfPlenty)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(YearOfPlentyCommand.class, yearOfPlenty).create();
        String request = gson.toJson(yearOfPlenty);
        String response = doPost(YEAR_OF_PLENTY, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel roadBuilding(RoadBuildingCommand roadBuilding)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(RoadBuildingCommand.class, roadBuilding).create();
        String request = gson.toJson(roadBuilding);
        String response = doPost(ROAD_BUILDING, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel monopoly(MonopolyCommand monopoly)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(MonopolyCommand.class, monopoly).create();
        String request = gson.toJson(monopoly);
        String response = doPost(MONOPOLY, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    @Override
    public ClientModel monument(MonumentCommand monument)
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(MonumentCommand.class, monument).create();
        String request = gson.toJson(monument);
        String response = doPost(MONUMENT, request);
        LOGGER.log(Level.INFO, "Response:" + response);
        return new ClientModel(response);
    }

    /**
     * Takes an object and turns it into JSON.
     * 
     * @param object
     *        the object to serialize
     * @return The Formatted JSON from the object.
     */
    private String serializeObject(Object object)
    {
        // Gson gson = new
        // GsonBuilder().setPrettyPrinting().registerTypeAdapter(object.class,
        // arg1).create();
        return "";
    }

    /**
     * Takes JSON and turns it into an object
     * 
     * @param json
     *        the JSON to deserialize.
     * @return the Object from the JSON.
     */
    private Object deserializeJSON(String json)
    {
        return null;
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
            // connection.setRequestProperty("Content-Type",
            // "application/json");
            connection.setRequestMethod(HTTP_POST);
            if (catanUserCookie != null)
            {
                String cookieRequest = "";
                if (catanGameCookie != null)
                {
                    cookieRequest = catanUserCookie + "; " + catanGameCookie;
                }
                else
                {
                    cookieRequest = catanUserCookie;
                }
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
        if (cookie.indexOf("catan.user") != -1)
        {
            this.catanUserCookie = cookie;
        }
        else if (cookie.indexOf("catan.game") != -1)
        {
            this.catanGameCookie = cookie;
        }
    }

    private String doGet(String commandName, String query)
    {
        String response = null;
        try
        {
            URL url = new URL(URLPrefix + commandName + "?" + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/json");
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
                LOGGER.log(Level.INFO, response);
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
