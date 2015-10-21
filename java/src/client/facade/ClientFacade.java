package client.facade;

import java.util.*;
import java.util.logging.*;

import client.data.*;
import server.proxy.*;
import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.*;
import shared.definitions.exceptions.*;
import shared.locations.*;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.*;

/**
 * The Facade for the Controllers/Views interacting with the Model classes. The
 * only class to directly touch the Model classes will be the Facade. Everything
 * else will call the Facade to do actions. This class contains all available
 * communications with the model.
 */
public class ClientFacade
{
    private static ClientFacade _instance = null;
    private ClientModel model;
    private IProxy proxy;
    private List<Player> players;
    private PlayerInfo clientPlayer;
    private final static Logger LOGGER = Logger.getLogger(ServerProxy.class.getName());

    private ClientFacade()
    {
        model = new ClientModel();
        proxy = new ServerProxy();
    }

    private void setClientPlayer(PlayerInfo clientPlayer)
    {
        this.clientPlayer = clientPlayer;
    }

    public PlayerInfo getClientPlayer()
    {
        return clientPlayer;
    }

    private void setupPlayersFromGame() throws CatanException
    {
        List<Player> playerInfos = model.getGameInfo().getPlayers();
        players = new ArrayList<Player>();
        for (int i = 0; i < 4; ++i)
        {
            Player player = playerInfos.get(i);
            players.add(player);
        }
    }

    public Player getPlayer(){
        return players.get(clientPlayer.getPlayerIndex().getIndex());
    }

    /**
     * Singleton Pattern to have a single instance of the Facade, since it
     * contains the models.
     * 
     * @return the static instance of the Client Facade.
     */
    public static ClientFacade getInstance()
    {
        if (_instance == null)
        {
            _instance = new ClientFacade();
        }
        return _instance;
    }

    /*
     * Chat Controller methods
     */
    /**
     * Sends a message to another player.
     *
     * @param message
     *        the message to send.
     */

    public void sendMessage(String message)
    {
        // Call the proxy and model to send a chat
        proxy.sendChat(new SendChatCommand(clientPlayer.getPlayerIndex(), message));
    }

    /*
     * Game History Controller
     */
    /**
     * Initializes the game history from a model.
     */

    public void initHistoryFromModel(ClientModel model)
    {
        this.model = model;
    }

    /*
     * Dev Card Controller Methods
     */
    /**
     * Determines if the player can buy a dev card.
     * 
     * @return whether or not the player can buy a dev card.
     */

    public boolean canBuyDevCard()
    {
        // model.canBuyDevCard();
        return false;
    }

    /**
     * Purchases a Development Card. This will take the card from the bank, and
     * adds it to the player' hand.
     */

    public void buyDevCard()
    {
        proxy.buyDevCard(new BuyDevCardCommand(clientPlayer.getPlayerIndex()));
    }

    /**
     * Plays a Monopoly Card.
     *
     * @param resource
     *        the type of resource the player is getting the monopoly on.
     */

    public void playMonopolyCard(ResourceType resource)
    {
        // proxy.monopoly(new MonopolyCommand(player, resource))
    }

    /**
     * Plays a Year of Plenty Card.
     * 
     * @param resource1
     *        The first resource.
     * @param resource2
     */

    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2)
    {

    }

    /**
     * Plays any other kind of Development Card.
     */

    public void playOtherDevCard(DevCardType type)
    {

    }

    /*
     * Discard Controller methods
     */

    /**
     * Discards the amount of resources set with the increase/decrease methods
     *
     * @param discardedResources
     *        the list of resources to discard.
     */

    public void discardResources(Resources discardedResources)
    {

    }

    /*
     * Domestic trade controller methods
     */

    /**
     * Sends a trade offer to a player.
     */

    public void sendTradeOffer()
    {

    }

    /**
     * Used for accepting/rejecting a trade.
     *
     * @param willAccept
     *        Whether or not the player will accept the trade.
     */

    public void acceptTrade(boolean willAccept)
    {

    }

    /*
     * Join Game Controller methods
     */

    public ListGamesResponse listGames()
    {
        return proxy.listGames();
    }

    /**
     * Creates a new game.
     * 
     * @param request
     */

    public void createNewGame(CreateGameRequest request)
    {
        proxy.createGame(request);
    }

    /**
     * Joins an already existent game.
     * 
     * @throws GameQueryException
     */

    public void joinGame(int id, CatanColor color) throws GameQueryException
    {
        proxy.joinGame(new JoinGameRequest(id, color));
    }

    /*
     * player Waiting Controller methods
     */

    public ClientModel getGameState(int version)
    {
        ClientModel model = proxy.getGameState(version);
        this.model.updateModel(model);
        return model;
    }

    /**
     * Adds an AI to the game.
     * 
     * @param type
     *        the type of the AI (LARGEST_ARMY is the only supported value.)
     */

    public void addAI(AIType type)
    {
        try
        {
            proxy.addAI(type);
        }
        catch (IllegalArgumentException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (GameQueryException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (AddAIException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<AIType> listAI()
    {
        ListAIResponse response = proxy.listAI();
        return response.getAITypes();
    }

    /*
     * Login Controller methods
     */
    /**
     * Signs in the player with the given credentials.
     * 
     * @param credentials
     *        the player's credentials
     * @throws SignInException
     */

    public void signInUser(Credentials credentials) throws SignInException
    {
        try
        {
            setClientPlayer(proxy.userLogin(credentials));
        }
        catch (SignInException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to Login", e);
            throw e;
        }
    }

    /**
     * Registers the user with the given credentials.
     * 
     * @param credentials
     *        the credentials of the user registering.
     * @throws SignInException
     */

    public void registerUser(Credentials credentials) throws SignInException
    {
        try
        {
            setClientPlayer(proxy.userRegister(credentials));
        }
        catch (SignInException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to Login", e);
            throw e;
        }
    }

    /*
     * Map controller methods
     */
    /**
     * Checks to see if the player meets the conditions to place a road
     * 
     * @pre place road is called
     * @post place road continues
     * @param edgeLoc
     *        the location of the Road
     * @return boolean - true if the player has the required resources and the
     *         location is vacant and the player owns a settlement or city at a
     *         neighboring vertex location
     */

    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return model.canPlaceRoad(clientPlayer.getPlayerIndex(), edgeLoc);
    }

    /**
     * Checks to see if the player meets the condition to place a settlement
     * 
     * @pre place settlement is called
     * @post place settlement continues
     * @param vertLoc
     *        the location of the Vertex
     * @return boolean - true if player has the required resources and the
     *         location is 2 edges or more from another settlement
     */

    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }

    /**
     * Checks to see if the player meets the condition to place a city
     * 
     * @pre place city is called
     * @post place city continues
     * @param vertLoc
     *        the location of the Vertex
     * @return boolean - true if player has the required resources and the
     *         player owns the settlement at that location
     */

    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }

    /**
     * Checks to see if player meets the condition to move the robber
     * 
     * @pre place robber is called
     * @post place city continues
     * @param hexLoc
     *        the location of the hex
     * @return true if a seven has been rolled and the location is viable
     */

    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }

    /**
     * player purchases and places a road
     * 
     * @pre player clicks on a location to place road
     * @post player met conditions and road is on map
     * @param edgeLoc
     *        the location of the road
     */

    public void placeRoad(EdgeLocation edgeLoc, boolean isFree)
    {
        proxy.buildRoad(new BuildRoadCommand(clientPlayer.getPlayerIndex(), edgeLoc, isFree));
    }

    /**
     * player purchases and places a settlement
     * 
     * @pre player clicks on a location to place a settlement
     * @post player met conditions and settlement is now on map
     * @param vertLoc
     *        the location of the Settlement
     */

    public void placeSettlement(VertexLocation vertLoc)
    {

    }

    /**
     * player purchases and places a city at a location specified
     * 
     * @post a city is now owned by the player
     * @pre player clicks to build on a location
     * @param vertLoc
     *        the location of the City
     */

    public void placeCity(VertexLocation vertLoc)
    {

    }

    /**
     * Changes the Robbers HexLocation
     * 
     * @post player robs player
     * @pre player rolls a 7
     * @param hexLoc
     *        the location of the Robber
     */

    public void placeRobber(HexLocation hexLoc)
    {

    }

    /**
     * Robs a player, player receives one resource from the player being robbed
     * 
     * @pre robber is placed
     * @post player has an extra resource
     * @param victim
     *        the victim of the brutal armed robbery
     */

    public void robPlayer(RobPlayerInfo victim)
    {

    }

    /*
     * Martitime Trade Controller methods
     */
    /**
     * Completes a maritime trade
     * 
     */

    public void makeMaritimeTrade(TradeOffer offer)
    {

    }

    /*
     * Points Controller methods
     */
    /**
     * Updates the players victory points, getting the values from the model
     * 
     * @post players points reflect the values from the model
     * @pre model changed
     */

    public void initPointsFromModel()
    {

    }

    /*
     * Roll Dice Controller methods
     */
    /**
     * Calls the roll method on the dice
     * 
     * @return Dice - Returns a dice object containing the values of two dice.
     * @post Value of dice is changed
     */

    public void rollDice(int diceRollResult)
    {
    			proxy.rollNumber(new RollNumberCommand(clientPlayer.getPlayerIndex(), diceRollResult));
    }

    /*
     * Turn tracker controller methods
     */
    /**
     * Ends the players turn
     *
     * @post The turn is ended
     */

    public void endTurn()
    {

    }

    /**
     * Initializes the turn tracker using the model
     * 
     * @post the turn is now initialized
     */

    public void initTurnFromModel()
    {

    }

    public void setModel(ClientModel newModel)
    {
        model = newModel;
    }

    public ClientModel getModel()
    {
        return model;
    }

    /**
     * @param string
     */
    public void setProxy(String host, String port)
    {
        proxy = new ServerProxy(host, port);

    }

}
