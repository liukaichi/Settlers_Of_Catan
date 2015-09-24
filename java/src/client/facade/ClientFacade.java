package client.facade;

import client.data.*;
import server.proxy.*;
import shared.communication.AIType;
import shared.definitions.*;
import shared.locations.*;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.*;

/**
<<<<<<< HEAD
 * The Facade for the Controllers/Views interacting with the Model classes. The
 * only class to directly touch the Model classes will be the Facade. Everything
 * else will call the Facade to do actions. This class contains all available
 * communications with the model.
=======
 * The facade, containing all communication with the model
 * @author dtaylor
 *
>>>>>>> branch 'phase1' of https://bitbucket.org/thesettlers/the-settlers-of-catan.git
 */
public class ClientFacade
{
    private static ClientFacade _instance = null;
    private ClientModel model;
    private IProxy proxy;

    private ClientFacade()
    {
        model = new ClientModel();
        proxy = new ServerProxy();
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

    }

    /*
     * Game History Controller
     */
    /**
     * Initializes the game history from a model.
     */
    public void initHistoryFromModel()
    {

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
        return false;
    }

    /**
     * Purchases a Development Card. This will take the card from the bank, and
     * adds it to the player' hand.
     */
    public void buyDevCard()
    {

    }

    /**
     * Plays a Monopoly Card.
     * 
     * @param resource
     *        the type of resource the player is getting the monopoly on.
     */
    public void playMonopolyCard(ResourceType resource)
    {

    }

    /**
     * Plays a Year of Plenty Card.
     * 
     * @param resource1
     *        The first resource.
     * @param resource2
     *        The second resource.
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
    public void sendTradeOffer(TradeOffer offer)
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
    /**
     * Creates a new game.
     */
    public void createNewGame(GameInfo gameInfo)
    {

    }

    /**
     * Joins an already existent game.
     */
    public void joinGame(GameInfo gameInfo)
    {

    }

    /*
     * Player Waiting Controller methods
     */
    /**
     * Adds an AI to the game.
     * 
     * @param type
     *        the type of the AI (LARGEST_ARMY is the only supported value.)
     */
    public void addAI(AIType type)
    {

    }

    /*
     * Login Controller methods
     */
    /**
     * Signs in the player with the given credentials.
     * 
     * @param credentials
     *        the player's credentials
     */
    public void signInUser(Credentials credentials)
    {

    }

    /**
     * Registers the user with the given credentials.
     * 
     * @param credentials
     *        the credentials of the user registering.
     */
    public void registerUser(Credentials credentials)
    {

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
        return false;
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
     * Player purchases and places a road
     * 
     * @pre player clicks on a location to place road
     * @post player met conditions and road is on map
     * @param edgeLoc
     *        the location of the road
     */
    public void placeRoad(EdgeLocation edgeLoc)
    {

    }

    /**
     * Player purchases and places a settlement
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
     * Player purchases and places a city at a location specified
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
    public Dice rollDice()
    {
        // roll dice
        return null;
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
}
