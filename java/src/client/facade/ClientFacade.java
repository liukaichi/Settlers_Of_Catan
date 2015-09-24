package client.facade;

import client.data.RobPlayerInfo;
import server.proxy.*;
import shared.communication.AIType;
import shared.definitions.*;
import shared.definitions.exceptions.*;
import shared.locations.*;
import shared.model.ClientModel;
import shared.model.resource.ResourceList;

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
     * @throws CatanException
     *         if anything goes wrong.
     */
    public void sendMessage(String message) throws CatanException
    {

    }

    /*
     * Game History Controller
     */
    /**
     * Initializes the game history from a model.
     * 
     * @throws CatanException
     *         if the model is invalid, or if anything goes wrong in
     *         initialization.
     */
    public void initHistoryFromModel() throws CatanException
    {

    }

    /*
     * Dev Card Controller Methods
     */
    /**
     * Purchases a Development Card. This will take the card from the bank, and
     * adds it to the player' hand.
     * 
     * @throws CardException
     *         if a card cannot be purchased.
     */
    public void buyDevCard() throws CardException
    {

    }

    /**
     * Plays a Monopoly Card.
     * 
     * @param resource
     *        the type of resource the player is getting the monopoly on.
     * @throws CardException
     *         if the Player does not have a Monopoly card.
     */
    public void playMonopolyCard(ResourceType resource) throws CardException
    {

    }

    /**
     * Plays a Monument Card.
     * 
     * @throws CardException
     *         if the Player does not have a Monument card.
     */
    public void playMonumentCard() throws CardException
    {

    }

    /**
     * Plays a Road Building Card.
     * 
     * @throws CardException
     *         if the Player does not have a Road Building card.
     */
    public void playRoadBuildCard() throws CardException
    {

    }

    /**
     * Plays a Soldier Card.
     * 
     * @throws CardException
     *         if the Player does not have a Soldier card.
     */
    public void playSoldierCard() throws CardException
    {

    }

    /**
     * Plays a Year of Plenty Card.
     * 
     * @param resource1
     *        The first resource.
     * @param resource2
     *        The second resource.
     * @throws CardException
     *         if the Player does not have a Year of Plenty card.
     */
    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws CardException
    {

    }

    /*
     * Discard Controller methods
     */
    /**
     * Increases the amount of resources to be discarded of a certain type.
     * 
     * @param resource
     *        the resource to increase the amount of.
     * @throws DiscardException
     *         if the operation fails.
     */
    public void increaseResourceAmountDiscarded(ResourceType resource) throws DiscardException
    {

    }

    /**
     * Decreases the amount of resources to be discarded of a certain type.
     * 
     * @param resource
     *        the resource to decrease the amount of.
     * @throws DiscardException
     *         if the operation fails.
     */
    public void decreaseResourceAmountDiscarded(ResourceType resource) throws DiscardException
    {

    }

    /**
     * Discards the amount of resources set with the increase/decrease methods
     * 
     * @param discardedResources
     *        the list of resources to discard.
     * @throws DiscardException
     *         if the operation fails.
     */
    public void discardResources(ResourceList discardedResources) throws DiscardException
    {

    }

    /*
     * Domestic trade controller methods
     */
    /**
     * Decreases the amount of a type of resource to trade.
     * 
     * @param resource
     *        The type of resource to decrease the amount of.
     * @throws TradingException
     *         if the operation fails.
     */
    public void decreaseTradeResourceAmount(ResourceType resource) throws TradingException
    {

    }

    /**
     * Increases the amount of a type of resource to trade.
     * 
     * @param resource
     *        The type of resource to increase the amount of.
     * @throws TradingException
     *         if the operation fails.
     */
    public void increaseTradeResourceAmount(ResourceType resource) throws TradingException
    {

    }

    /**
     * Sends a trade offer to a player.
     * 
     * @throws TradingException
     *         if the operation fails.
     */
    public void sendTradeOffer() throws TradingException
    {
    }

    /**
     * Sets the player to trade with.
     * 
     * @param playerIndex
     *        the index of the player to trade with.
     * @throws TradingException
     *         if the operation fails.
     */
    public void setPlayerToTradeWith(int playerIndex) throws TradingException
    {

    }

    /**
     * Sets the desired resource for the trade.
     * 
     * @param resource
     *        the resource to receive.
     * @throws TradingException
     *         if the operation fails.
     */
    public void setResourceToReceive(ResourceType resource) throws TradingException
    {

    }

    /**
     * Sets the resource to send for the trade.
     * 
     * @param resource
     *        the resource to send.
     * @throws TradingException
     *         if the operation fails.
     */
    public void setResourceToSend(ResourceType resource) throws TradingException
    {

    }

    /**
     * Removes the specified resource from the trade.
     * 
     * @param resource
     *        the resource to remove.
     * @throws TradingException
     *         if the operation fails.
     */
    public void unsetTradeResource(ResourceType resource) throws TradingException
    {

    }

    /**
     * Cancels the trade.
     * 
     * @throws TradingException
     *         if the operation fails.
     */
    public void cancelTrade() throws TradingException
    {

    }

    /**
     * Used for accepting/rejecting a trade.
     * 
     * @param willAccept
     *        Whether or not the player will accept the trade.
     * @throws TradingException
     *         if the operation fails.
     */
    public void acceptTrade(boolean willAccept) throws TradingException
    {

    }

    /*
     * Join Game Controller methods
     */
    /**
     * Creates a new game.
     * 
     * @throws CatanException
     *         if the game fails to be created.
     */
    public void createNewGame() throws CatanException
    {

    }

    /**
     * Joins an already existent game.
     * 
     * @throws CatanException
     *         if there are not games to join, or if the requesting player is
     *         already in a game.
     */
    public void joinGame() throws CatanException
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
     * @throws CatanException
     *         if the type is invalid, or if anything goes wrong.
     */
    public void addAI(AIType type) throws CatanException
    {

    }

    /*
     * Login Controller methods
     */

    public void signInUser() throws SignInException
    {

    }

    public void registerUser() throws SignInException
    {

    }

    /*
     * Map controller methods
     */
    /**
     * Checks to see if the player meets the conditions to place a road
     * @pre place road is called
     * @post place road continuese
     * @param edgeLoc
     * @return boolean - true if the player has the required resources and the location is vacant and the player owns a settlement or city at a neighboring vertex location
     */
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return false;
    }
/**
 * Checks to see if the player meets the condition to place a settlement
 * @pre place settlement is called
 * @post place settlement continues
 * @param vertLoc
 * @return boolean - true if player has the required resources and the location is 2 edges or more from another settlement
 */
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }
/**
 * Checks to see if the player meets the condition to place a city
 * @pre place city is called
 * @post place city continues
 * @param vertLoc
 * @return boolean - true if player has the required resources and the player owns the settlement at that location
 */
    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }
/**
 * Checks to see if player meets the condition to move the robber
 * @pre place robber is called
 * @post place city continues
 * @param hexLoc
 * @return true if a seven has been rolled and the location is viable
 */
    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }
/**
 * Player purchases and places a road
 * @pre player clicks on a location to place road
 * @post player met conditions and road is on map
 * @param edgeLoc
 * @throws PlacementException
 */
    public void placeRoad(EdgeLocation edgeLoc) throws PlacementException
    {

    }
/**
 * Player purchases and places a settlement
 * @pre player clicks on a location to place a settlement
 * @post player met conditions and settlment is now on map
 * @param vertLoc
 * @throws PlacementException
 */
    public void placeSettlement(VertexLocation vertLoc) throws PlacementException
    {

    }
/**
 * Player purchases and places a city at a location specified
 * @post a city is now owned by the player
 * @pre player clicks to build on a location
 * @param vertLoc
 * @throws PlacementException
 */
    public void placeCity(VertexLocation vertLoc) throws PlacementException
    {

    }
/**
 * Changes the Robbers HexLocation
 * @post player robs player
 * @pre player rolls a 7
 * @param hexLoc
 * @throws PlacementException
 */
    public void placeRobber(HexLocation hexLoc) throws PlacementException
    {

    }
/**
 * Robs a player, player receives one resource from the player being robbed
 * @pre robber is placed
 * @post player has an extra resource
 * @param victim
 * @throws PlacementException
 */
    public void robPlayer(RobPlayerInfo victim) throws PlacementException
    {

    }

    /*
     * Martitime Trade Controller methods
     */
    /**
     * Completes maritime trade
     * @post trade complete and model updated
     * @throws TradingException
     */
    public void makeTrade() throws TradingException
    {

    }

    /*
     * Points Controller methods
     */
    /**
     * Updates the players victory points, getting the values from the model
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
 * @post the turn is now initialized
 */
    public void initTurnFromModel()
    {

    }
}
