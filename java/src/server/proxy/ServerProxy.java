package server.proxy;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.logging.Level;

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

    @Override
    public void userLogin(Credentials credentials) throws SignInException
    {
        String response = doPost(USER_LOGIN, credentials.toString());
        if (response == null)
        {
            throw new SignInException(response);
        }
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
        String response = doPost(CHANGE_LOG_LEVEL, level.toString());
        if (response == null)
        {

        }

    }

    @Override
    public ListGamesResponse listGames()
    {
        String reponse = doGet(LIST_GAMES, "");
        return new ListGamesResponse(reponse);
    }

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest)
    {
        Gson gson = new GsonBuilder().create();
        gson.toJson(createGameRequest);
        String response = doPost(CREATE_GAME, gson.toJson(createGameRequest));
        return new CreateGameResponse(response);
    }

    @Override
    public void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException
    {
        String response = doPost(JOIN_GAME, serializeObject(joinGameRequest));
        if (response == null)
        {
            throw new GameQueryException();
        }
    }

    @Override
    public void saveGame(SaveGameRequest saveGameRequest) throws GameQueryException
    {
        String response = doPost(SAVE_GAME, serializeObject(saveGameRequest));
        if (response == null)
        {
            throw new GameQueryException();
        }
    }

    @Override
    public void loadGame(LoadGameRequest loadGameRequest) throws GameQueryException
    {
        String response = doPost(LOAD_GAME, serializeObject(loadGameRequest));
        if (response == null)
        {
            throw new GameQueryException();
        }

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
    public ListAIResponse listAI()
    {
        String response = doGet(LIST_AI, "");
        return new ListAIResponse(response);
    }

    @Override
    public void addAI(AIType aiType) throws IllegalArgumentException, GameQueryException, AddAIException
    {
        String response = doPost(ADD_AI, aiType.toString());
        if (response == null)
        {
            throw new AddAIException();
        }

    }

    @Override
    public ClientModel sendChat(SendChatCommand sendChat)
    {
        String response = doPost(SEND_CHAT, sendChat.toString());
        return null;
    }

    @Override
    public ClientModel rollNumber(RollNumberCommand rollNumber)
    {
        String response = doPost(ROLL_NUMBER, rollNumber.toString());
        return null;
    }

    @Override
    public ClientModel acceptTrade(AcceptTradeCommand acceptTrade)
    {
        String response = doPost(ACCEPT_TRADE, acceptTrade.toString());
        return null;
    }

    @Override
    public ClientModel discardCards(DiscardCardsCommand discardCards)
    {
        String response = doPost(DISCARD_CARDS, discardCards.toString());
        return null;
    }

    @Override
    public ClientModel buildRoad(BuildRoadCommand buildRoad)
    {
        String response = doPost(BUILD_ROAD, buildRoad.toString());
        return null;
    }

    @Override
    public ClientModel buildSettlement(BuildSettlementCommand buildSettlement)
    {
        String response = doPost(BUILD_SETTLEMENT,
                ""/* serializeObject(buildSettlement) */);
        return null;
    }

    @Override
    public ClientModel buildCity(BuildCityCommand buildCity)
    {
        String response = doPost(BUILD_CITY, buildCity.toString());
        return null;
    }

    @Override
    public ClientModel offerTrade(OfferTradeCommand offerTrade)
    {
        String response = doPost(OFFER_TRADE, offerTrade.toString());
        return null;
    }

    @Override
    public ClientModel maritimeTrade(MaritimeTradeCommand maritimeTrade)
    {
        String response = doPost(MARITIME_TRADE, maritimeTrade.toString());
        return null;
    }

    @Override
    public ClientModel robPlayer(RobPlayerCommand robPlayer)
    {
        String response = doPost(ROB_PLAYER, robPlayer.toString());
        return null;
    }

    @Override
    public ClientModel finishTurn(FinishTurnCommand finishTurn)
    {
        String response = doPost(FINISH_TURN, finishTurn.toString());
        return null;
    }

    @Override
    public ClientModel buyDevCard(BuyDevCardCommand buyDevCard)
    {
        String response = doPost(BUY_DEV_CARD, buyDevCard.toString());
        return null;
    }

    @Override
    public ClientModel soldier(SoldierCommand soldier)
    {
        String response = doPost(SOLDIER, soldier.toString());
        return null;
    }

    @Override
    public ClientModel yearOfPlenty(YearOfPlentyCommand yearOfPlenty)
    {
        String response = doPost(YEAR_OF_PLENTY, yearOfPlenty.toString());
        return null;
    }

    @Override
    public ClientModel roadBuilding(RoadBuildingCommand roadBuilding)
    {
        String response = doPost(ROAD_BUILDING, roadBuilding.toString());
        return null;
    }

    @Override
    public ClientModel monopoly(MonopolyCommand monopoly)
    {
        String response = doPost(MONOPOLY, monopoly.toString());
        return null;
    }

    @Override
    public ClientModel monument(MonumentCommand monument)
    {
        String response = doPost(MONUMENT, monument.toString());
        return null;
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
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String string;
                while ((string = reader.readLine()) != null)
                {
                    builder.append(string);
                }
                response = builder.toString();
            } else
            {
                System.out.println("Forget about it");
            }
            return response;
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
            }
            return response;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
