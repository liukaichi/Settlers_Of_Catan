package client.facade;

import client.base.ObserverController;
import client.data.*;
import server.proxy.IProxy;
import server.proxy.ServerProxy;
import shared.communication.*;
import shared.communication.moveCommands.BuildRoadCommand;
import shared.communication.moveCommands.BuyDevCardCommand;
import shared.communication.moveCommands.RollNumberCommand;
import shared.communication.moveCommands.SendChatCommand;
import shared.definitions.*;
import shared.definitions.exceptions.AddAIException;
import shared.definitions.exceptions.GameQueryException;
import shared.definitions.exceptions.SignInException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Used to fill in the clientPlayer's info from the list of PlayerInfos.
     * @param players the info of each of the players. Typically, this is supplied by GameInfo#getPlayerInfos().
     * @pre The clientPlayer has already been initialized with a name and an ID.
     */
    private void buildClientPlayerFromGameInfo(List<PlayerInfo> players)
    {
        for(PlayerInfo playerInfo : players)
        {
            if (playerInfo.getId() == clientPlayer.getId())
            {
                clientPlayer = playerInfo;
            }

        }
    }

    public Player getPlayerByName(String name)
    {
        for (Player player : ClientFacade.getInstance().getPlayers())
        {
            if (player.getName().matches(name))
            {
                return player;
            }
        }
        LOGGER.log(Level.WARNING, "Player's name could not be found in the ClientFacade.");
        return null;
    }

    public Player getPlayer()
    {
        return getPlayers().get(clientPlayer.getPlayerIndex().getIndex());
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
     * @param message the message to send.
     */

    public void sendMessage(String message)
    {
        // Call the proxy and model to send a chat
        model.updateModel(proxy.sendChat(new SendChatCommand(clientPlayer.getPlayerIndex(), message)));
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
     * @param resource the type of resource the player is getting the monopoly on.
     */

    public void playMonopolyCard(ResourceType resource)
    {
        // proxy.monopoly(new MonopolyCommand(player, resource))
    }

    /**
     * Plays a Year of Plenty Card.
     *
     * @param resource1 The first resource.
     * @param resource2 The second resource.
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
     * @param discardedResources the list of resources to discard.
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
     * @param willAccept Whether or not the player will accept the trade.
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
        CreateGameResponse response = proxy.createGame(request);
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
        if (clientPlayer.getNormalizedPlayerIndex() == -1)
        {
            List<PlayerInfo> players = model.getGameInfo().getPlayerInfos();
            buildClientPlayerFromGameInfo(players);
        }
        this.model.updateModel(model);
        return model;
    }

    /**
     * Adds an AI to the game.
     *
     * @param type the type of the AI (LARGEST_ARMY is the only supported value.)
     */

    public void addAI(AIType type)
    {
        try
        {
            proxy.addAI(type);
        } catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        } catch (GameQueryException e)
        {
            e.printStackTrace();
        } catch (AddAIException e)
        {
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
     * @param credentials the player's credentials
     * @throws SignInException
     */

    public void signInUser(Credentials credentials) throws SignInException
    {
        try
        {
            setClientPlayer(proxy.userLogin(credentials));
        } catch (SignInException e)
        {
            LOGGER.log(Level.SEVERE, "Failed to Login", e);
            throw e;
        }
    }

    /**
     * Registers the user with the given credentials.
     *
     * @param credentials the credentials of the user registering.
     * @throws SignInException
     */

    public void registerUser(Credentials credentials) throws SignInException
    {
        try
        {
            setClientPlayer(proxy.userRegister(credentials));
        } catch (SignInException e)
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
     * @param edgeLoc the location of the Road
     * @return boolean - true if the player has the required resources and the
     * location is vacant and the player owns a settlement or city at a
     * neighboring vertex location
     * @pre place road is called
     * @post place road continues
     */

    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return model.canPlaceRoad(clientPlayer.getPlayerIndex(), edgeLoc);
    }

    /**
     * Checks to see if the player meets the condition to place a settlement
     *
     * @param vertLoc the location of the Vertex
     * @return boolean - true if player has the required resources and the
     * location is 2 edges or more from another settlement
     * @pre place settlement is called
     * @post place settlement continues
     */

    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }

    /**
     * Checks to see if the player meets the condition to place a city
     *
     * @param vertLoc the location of the Vertex
     * @return boolean - true if player has the required resources and the
     * player owns the settlement at that location
     * @pre place city is called
     * @post place city continues
     */

    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }

    /**
     * Checks to see if player meets the condition to move the robber
     *
     * @param hexLoc the location of the hex
     * @return true if a seven has been rolled and the location is viable
     * @pre place robber is called
     * @post place city continues
     */

    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }

    /**
     * player purchases and places a road
     *
     * @param edgeLoc the location of the road
     * @pre player clicks on a location to place road
     * @post player met conditions and road is on map
     */

    public void placeRoad(EdgeLocation edgeLoc, boolean isFree)
    {
        proxy.buildRoad(new BuildRoadCommand(clientPlayer.getPlayerIndex(), edgeLoc, isFree));
    }

    /**
     * player purchases and places a settlement
     *
     * @param vertLoc the location of the Settlement
     * @pre player clicks on a location to place a settlement
     * @post player met conditions and settlement is now on map
     */

    public void placeSettlement(VertexLocation vertLoc)
    {

    }

    /**
     * player purchases and places a city at a location specified
     *
     * @param vertLoc the location of the City
     * @post a city is now owned by the player
     * @pre player clicks to build on a location
     */

    public void placeCity(VertexLocation vertLoc)
    {

    }

    /**
     * Changes the Robbers HexLocation
     *
     * @param hexLoc the location of the Robber
     * @post player robs player
     * @pre player rolls a 7
     */

    public void placeRobber(HexLocation hexLoc)
    {

    }

    /**
     * Robs a player, player receives one resource from the player being robbed
     *
     * @param victim the victim of the brutal armed robbery
     * @pre robber is placed
     * @post player has an extra resource
     */

    public void robPlayer(RobPlayerInfo victim)
    {

    }

    /*
     * Martitime Trade Controller methods
     */

    /**
     * Completes a maritime trade
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
     * @param
     */
    public void setProxy(String host, String port)
    {
        proxy = new ServerProxy(host, port);

    }

    public void addObserver(ObserverController observerController)
    {
        model.addObserver(observerController);
    }

    public List<Player> getPlayers()
    {
        return model.getGameInfo().getPlayers();
    }
}
