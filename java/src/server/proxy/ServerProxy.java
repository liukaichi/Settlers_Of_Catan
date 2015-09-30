package server.proxy;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.logging.Level;

import client.data.GameInfo;
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

    @Override
    public void userLogin(Credentials credentials) throws SignInException
    {
        doPost(USER_LOGIN, credentials.toString());
    }

    @Override
    public void userRegister(Credentials credentials) throws SignInException
    {
        String response = doPost(USER_REGISTER, credentials.toString());
        if (response == null)
        {
            throw new SignInException(response);
        }
    }

    @Override
    public void changeLogLevel(Level level)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public List<GameInfo> listGames()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveGame(SaveGameRequest saveGameRequest) throws GameQueryException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadGame(LoadGameRequest loadGameRequest) throws GameQueryException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public ClientModel getGameState(int versionNumber)
    {
        String query = String.format("version=%s", versionNumber);
        String responseJSON = doGet(GET_GAME_STATE, query);
        ClientModel responseModel = null;
        /* If the model has changed */
        if (!responseJSON.equals("\"true\""))
        {
            responseModel = (ClientModel) deserializeJSON(responseJSON);
        }
        return responseModel;
    }

    @Override
    public ClientModel resetGame()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MoveCommand> getCommands()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel postCommands(List<MoveCommand> commands)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AIType> listAI()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addAI(AIType aiType) throws IllegalArgumentException, GameQueryException, AddAIException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public ClientModel sendChat(SendChatCommand sendChat)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel rollNumber(RollNumberCommand rollNumber)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel acceptTrade(AcceptTradeCommand acceptTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel discardCards(DiscardCardsCommand discardCards)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buildRoad(BuildRoadCommand buildRoad)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buildSettlement(BuildSettlementCommand buildSettlement)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buildCity(BuildCityCommand buildCity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel offerTrade(OfferTradeCommand offerTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel maritimeTrade(MaritimeTradeCommand maritimeTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel robPlayer(RobPlayerCommand robPlayer)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel finishTurn(FinishTurnCommand finishTurn)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buyDevCard(BuyDevCardCommand buyDevCard)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel soldier(SoldierCommand soldier)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel yearOfPlenty(YearOfPlentyCommand yearOfPlenty)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel roadBuilding(RoadBuildingCommand roadBuilding)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel monopoly(MonopolyCommand monopoly)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel monument(MonumentCommand monument)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Takes an object and turns it into JSON.
     * 
     * @param object
     *        the object to serialize
     * @return The Formatted JSON from the object.
     */
    private String serializeClass(Object object)
    {
        return null;
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
        String result = null;
        try
        {
            URL url = new URL(URLPrefix + commandName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            // connection.setRequestProperty("Content-Type",
            // "application/json");
            connection.setRequestMethod(HTTP_POST);
            connection.setDoOutput(true);
            connection.connect();
            OutputStream stream = connection.getOutputStream();
            stream.write(postDataJson.getBytes());
            stream.flush();
            stream.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                result = connection.getInputStream().toString();
            }
            return result;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
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
            InputStream stream = connection.getInputStream();
            response = stream.toString();
            return response;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
