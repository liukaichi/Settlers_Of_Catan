package client.facade;

import client.data.RobPlayerInfo;
import shared.definitions.Dice;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.*;
import shared.locations.*;
import shared.model.ClientModel;
import shared.model.resource.ResourceList;

public class ClientFacade
{
    private static ClientFacade _instance = null;
    private ClientModel model;

    private ClientFacade()
    {
        model = new ClientModel();
    }

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
    public void sendMessage(String message) throws CatanException
    {

    }

    /*
     * Game History Controller
     */
    public void initHistoryFromModel() throws CatanException
    {

    }

    /*
     * Dev Card Controller Methods
     */

    public void buyDevCard() throws CardException
    {

    }

    public void playMonopolyCard(ResourceType resource) throws CardException
    {

    }

    public void playMonumentCard() throws CardException
    {

    }

    public void playRoadBuildCard() throws CardException
    {

    }

    public void playSoldierCard() throws CardException
    {

    }

    public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws CardException
    {

    }

    /*
     * Discard Controller methods
     */

    public void increaseResourceAmountDiscarded(ResourceType resource) throws DiscardException
    {

    }

    public void decreaseResourceAmountDiscarded(ResourceType resource) throws DiscardException
    {

    }

    public void discardResources(ResourceList discardedResources) throws DiscardException
    {

    }

    /*
     * Domestic trade controller methods
     */

    public void decreaseTradeResourceAmount(ResourceType resource) throws TradingException
    {

    }

    public void increaseTradeResourceAmount(ResourceType resource) throws TradingException
    {

    }

    public void sendTradeOffer() throws TradingException
    {
    }

    public void setPlayerToTradeWith(int playerIndex) throws TradingException
    {

    }

    public void setResourceToReceive(ResourceType resource) throws TradingException
    {

    }

    public void setResourceToSend(ResourceType resource) throws TradingException
    {

    }

    public void unsetTradeResource(ResourceType resource) throws TradingException
    {

    }

    public void cancelTrade() throws TradingException
    {

    }

    public void acceptTrade(boolean willAccept) throws TradingException
    {

    }

    /*
     * Join Game Controller methods
     */
    public void createNewGame() throws CatanException
    {

    }

    public void joinGame() throws CatanException
    {

    }

    /*
     * Player Waiting Controller methods
     */

    public void addAI() throws CatanException
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
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return false;
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }

    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }
/**
 * Checks to see if player meets the condition to move the robber
 * @pre place robber is called
 * @post boolean is returned
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
     * @return Dice - Returns a dice object containing the values of two dice.
     * @post Value of dice is changed
     */
    public Dice rollDice()
    {
    	//roll dice
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
