package server.facade;

import client.data.GameInfo;
import shared.communication.*;
import shared.definitions.*;
import shared.definitions.exceptions.SignInException;
import shared.locations.*;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

/**
 * Created by cstaheli on 11/4/2015.
 */
public class ServerFacade extends AbstractServerFacade
{
    private ClientModel model;

    /**
     * Retrieves the current GameplayState
     * @return the current GameplayState
     * @param version
     */
    @Override public ClientModel getGameState(int version)
    {
        return null;
    }

    /**
     * Add an AI player to the game
     * @param aiType
     */
    @Override public void addAI(AIType aiType)
    {

    }

    /**
     * List the types of AI available
     */
    @Override public ListAIResponse listAI()
    {
        return null;
    }

    /**
     * List the games available
     * @return the list of all games
     */
    @Override public ListGamesResponse listGames()
    {
        return null;
    }

    /**
     * Joins game of the given id with the specified color
     */
    @Override
    public void joinGame(PlayerIndex player, int gameID, CatanColor color)
    {

    }

    /**
     * Create a new game with the given id and specified name
     */
    @Override
    public CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        return null;
    }

    /**
     * Add a message to the ClientModel with the sender's id
     * @return the updated ClientModel
     * @param sender
     * @param content
     */
    @Override public ClientModel sendChat(PlayerIndex sender, String content)
    {
        return null;
    }

    /**
     * Distribute resources to players based on the number rolled
     * @return the updated ClientModel
     * @param playerIndex
     * @param number
     */
    @Override public ClientModel rollNumber(PlayerIndex playerIndex, int number)
    {
        return null;
    }

    /**
     * Move the robber to the new given location.
     * Increase the robbed resource client player's PlayerBank.
     * Decrease the robbed resource from the robbed player's PlayerBank.
     * @return
     * @param playerIndex
     * @param victim
     * @param location
     */
    @Override public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location)
    {
        return null;
    }

    /**
     * Calls finish turn from the model to progress to next state.
     * @return the updated ClientModel
     * @param playerIndex
     */
    @Override public ClientModel finishTurn(PlayerIndex playerIndex)
    {
    	model.finishTurn(); 
        return null;
    }

    /**
     * Add a random Development Card to the client player's PlayerBank
     * Pop said Development Card from the DevCardDeck
     * Display bought Development Card message.
     * @return the updated ClientModel
     * @param playerIndex
     */
    @Override public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        //model.buyDevCard();
        return null;
    }

    /**
     * Increase up to two resources to the client player's PlayerBank
     * Decrease the appropriate resources from the game bank.
     * Display resources received message.
     * @return the updated ClientModel
     * @param playerIndex
     * @param resource2
     * @param resource2
     */
    @Override public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        return null;
    }

    /**
     * Increase the client player's roads of AmountType.BUILT by 2
     * Decrease the client player's roads of AmountType.AVAILABLE by 2
     * Update the map with the 2 new roads
     * Display 2 roads built message
     * @return the updated ClientModel
     * @param playerIndex
     * @param spot1
     * @param spot2
     */
    @Override public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        return null;
    }

    /**
     * Update the map with the new Robber location.
     * Increase the client player's PlayerBank with the robbed resource.
     * Decrease the robber player's PlayerBank with the robber resource.
     * Display robbed message.
     * @return the updated ClientModel
     * @param playerIndex
     * @param victimIndex
     * @param location
     */
    @Override public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        return null;
    }

    /**
     * Updates the client player's PlayerBank with the resources gained.
     * Updates all other players' PlayerBank with the resources lost.
     * @return the updated ClientModel
     * @param playerIndex
     * @param resource
     */
    @Override public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource)
    {
        return null;
    }

    /**
     * Increase the client player's Victory Points by 1
     * @return the updated ClientModel
     * @param playerIndex
     */
    @Override public ClientModel monument(PlayerIndex playerIndex)
    {
        return null;
    }

    /**
     * Increase the client player's road of AmountType.BUILT by 1.
     * Decrease the client player's road of AmountType.AVAILABLE by 1.
     * Update the map with a new road.
     * @return the updated ClientModel
     * @param playerIndex
     * @param roadLocation
     * @param free
     */
    @Override public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        model.buildRoad(null, null);
        return null;
    }

    /**
     * Increase the client player's settlement of AmountType.BUILT by 1.
     * Decrease the client player's settlement of AmountType.AVAILABLE by 1.
     * Update the map with a new settlement
     * @return the updated ClientModel
     * @param playerIndex
     * @param vertexLocation
     * @param free
     */
    @Override public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free)
    {
        //model.buildSettlement(null, null);
        return null;
    }

    /**
     * Increase the client player's city of AmountType.BUILT by 1.
     * Decrease the client player's city of AmountType.AVAILABLE by 1.
     * Update the map with a new city
     * @return the updated ClientModel
     * @param playerIndex
     * @param vertexLocation
     */
    @Override public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation vertexLocation)
    {
        //model.buildCity(null, null);
        return null;
    }

    /**
     * Update model with a new TradeOffer object
     * Player to be traded is asked to trade.
     * @return the updated ClientModel
     * @param playerIndex
     * @param offer
     * @param receiver
     */
    @Override public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return null;
    }

    /**
     * Update model and remove the TradeOffer
     * Update both players' PlayerBank depending on whether
     * the TradeOffer is accepted or not.
     * @return the updated ClientModel
     * @param playerIndex
     * @param willAccept
     */
    @Override public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }

    /**
     * The client player trades with the game bank based on the
     * trade ratio that is available to the player
     * @return the updated ClientModel
     * @param playerIndex
     * @param ratio
     * @param inputResource
     * @param outputResource
     */
    @Override public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource)
    {
        return null;
    }

    /**
     * The client player discards a number of each resource.
     * The resources are returned to the game bank
     * @return the updated CLientModel
     * @param playerIndex
     * @param discardedCards
     */
    @Override public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        return null;
    }

    /**
     * Signs in the player with the given credentials
     * @param credentials
     * @throws SignInException
     */
    @Override public void signInUser(Credentials credentials) throws SignInException
    {

    }

    /**
     * Registers a new player with the given credentials
     * @param credentials
     * @throws SignInException
     */
    @Override public void registerUser(Credentials credentials) throws SignInException
    {

    }
}
