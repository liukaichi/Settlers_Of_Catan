/**
 * 
 */
package client.state;

import client.ICatanGameMethods;
import client.data.GameInfo;
import client.data.RobPlayerInfo;
import shared.communication.Credentials;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

/**
 * @author cstaheli
 *
 */

public abstract class GameplayState implements ICatanGameMethods
{

    /*
     * Chat Controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#sendMessage(shared.definitions.PlayerIndex, java.lang.String)
     */
    @Override
    public void sendMessage(PlayerIndex player, String message)
    {

    }

    /*
     * Game History Controller
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#initHistoryFromModel(shared.model.ClientModel)
     */
    @Override
    public void initHistoryFromModel(ClientModel model)
    {

    }

    /*
     * Dev Card Controller Methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#canBuyDevCard(shared.definitions.PlayerIndex)
     */
    @Override
    public boolean canBuyDevCard(PlayerIndex player)
    {
        // model.canBuyDevCard();
        return false;
    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#buyDevCard(shared.definitions.PlayerIndex)
     */
    @Override
    public void buyDevCard(PlayerIndex player)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#playMonopolyCard(shared.definitions.PlayerIndex, shared.definitions.ResourceType)
     */
    @Override
    public void playMonopolyCard(PlayerIndex player, ResourceType resource)
    {
        // proxy.monopoly(new MonopolyCommand(player, resource))
    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#playYearOfPlentyCard(shared.definitions.PlayerIndex, shared.definitions.ResourceType, shared.definitions.ResourceType)
     */
    @Override
    public void playYearOfPlentyCard(PlayerIndex player, ResourceType resource1, ResourceType resource2)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#playOtherDevCard(shared.definitions.PlayerIndex, shared.definitions.DevCardType)
     */
    @Override
    public void playOtherDevCard(PlayerIndex player, DevCardType type)
    {

    }

    /*
     * Discard Controller methods
     */

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#discardResources(shared.definitions.PlayerIndex, shared.model.bank.resource.Resources)
     */
    @Override
    public void discardResources(PlayerIndex player, Resources discardedResources)
    {

    }

    /*
     * Domestic trade controller methods
     */

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#sendTradeOffer(shared.definitions.PlayerIndex)
     */
    @Override
    public void sendTradeOffer(PlayerIndex player)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#acceptTrade(shared.definitions.PlayerIndex, boolean)
     */
    @Override
    public void acceptTrade(PlayerIndex player, boolean willAccept)
    {

    }

    /*
     * Join Game Controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#createNewGame(client.data.GameInfo)
     */
    @Override
    public void createNewGame(GameInfo gameInfo)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#joinGame(client.data.GameInfo)
     */
    @Override
    public void joinGame(GameInfo gameInfo)
    {

    }

    /*
     * player Waiting Controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#addAI(shared.definitions.AIType)
     */
    @Override
    public void addAI(AIType type)
    {

    }

    /*
     * Login Controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#signInUser(shared.communication.Credentials)
     */
    @Override
    public void signInUser(Credentials credentials)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#registerUser(shared.communication.Credentials)
     */
    @Override
    public void registerUser(Credentials credentials)
    {

    }

    /*
     * Map controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#canPlaceRoad(shared.definitions.PlayerIndex, shared.locations.EdgeLocation)
     */
    @Override
    public boolean canPlaceRoad(PlayerIndex player, EdgeLocation edgeLoc)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#canPlaceSettlement(shared.locations.VertexLocation)
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#canPlaceCity(shared.locations.VertexLocation)
     */
    @Override
    public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#canPlaceRobber(shared.locations.HexLocation)
     */
    @Override
    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        return false;
    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#placeRoad(shared.definitions.PlayerIndex, shared.locations.EdgeLocation, boolean)
     */
    @Override
    public void placeRoad(PlayerIndex player, EdgeLocation edgeLoc, boolean isFree)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#placeSettlement(shared.locations.VertexLocation)
     */
    @Override
    public void placeSettlement(VertexLocation vertLoc)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#placeCity(shared.locations.VertexLocation)
     */
    @Override
    public void placeCity(VertexLocation vertLoc)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#placeRobber(shared.locations.HexLocation)
     */
    @Override
    public void placeRobber(HexLocation hexLoc)
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#robPlayer(client.data.RobPlayerInfo)
     */
    @Override
    public void robPlayer(RobPlayerInfo victim)
    {

    }

    /*
     * Martitime Trade Controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#makeMaritimeTrade(shared.model.player.TradeOffer)
     */
    @Override
    public void makeMaritimeTrade(TradeOffer offer)
    {

    }

    /*
     * Points Controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#initPointsFromModel()
     */
    @Override
    public void initPointsFromModel()
    {

    }

    /*
     * Roll Dice Controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#rollDice()
     */
    @Override
    public Dice rollDice()
    {
        // roll dice
        return null;
    }

    /*
     * Turn tracker controller methods
     */
    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#endTurn()
     */
    @Override
    public void endTurn()
    {

    }

    /* (non-Javadoc)
     * @see client.state.ICatanGameMethods#initTurnFromModel()
     */
    @Override
    public void initTurnFromModel()
    {

    }

}
