package client.facade;

import client.data.RobPlayerInfo;
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

    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }

    public void placeRoad(EdgeLocation edgeLoc) throws PlacementException
    {

    }

    public void placeSettlement(VertexLocation vertLoc) throws PlacementException
    {

    }

    public void placeCity(VertexLocation vertLoc) throws PlacementException
    {

    }

    public void placeRobber(HexLocation hexLoc) throws PlacementException
    {

    }

    public void robPlayer(RobPlayerInfo victim) throws PlacementException
    {

    }

    /*
     * Martitime Trade Controller methods
     */
    public void makeTrade() throws TradingException
    {

    }

    /*
     * Points Controller methods
     */
    public void initPointsFromModel()
    {

    }

    /*
     * Roll Dice Controller methods
     */
    public void rollDice()
    {

    }

    /*
     * Turn tracker controller methods
     */
    public void endTurn()
    {

    }

    public void initTurnFromModel()
    {

    }
}
