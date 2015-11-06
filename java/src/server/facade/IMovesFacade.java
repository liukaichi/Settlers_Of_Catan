package server.facade;

import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IMovesFacade
{
    /**
     * Add a message to the ClientModel with the sender's id
     * @return the updated ClientModel
     * @param sender
     * @param content
     */
    ClientModel sendChat(PlayerIndex sender, String content);
    /**
     * Distribute resources to players based on the number rolled
     * @return the updated ClientModel
     * @param playerIndex
     * @param number
     */
    ClientModel rollNumber(PlayerIndex playerIndex, int number);
    /**
     * Move the robber to the new given location.
     * Increase the robbed resource client player's PlayerBank.
     * Decrease the robbed resource from the robbed player's PlayerBank.
     * @return
     * @param playerIndex
     * @param victim
     * @param location
     */
    ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location);
    /**
     * Calls finish turn from the model to progress to next state.
     * @return the updated ClientModel
     * @param playerIndex
     */
    ClientModel finishTurn(PlayerIndex playerIndex);
    /**
     * Add a random Development Card to the client player's PlayerBank
     * Pop said Development Card from the DevCardDeck
     * Display bought Development Card message.
     * @return the updated ClientModel
     * @param playerIndex
     */
    ClientModel buyDevCard(PlayerIndex playerIndex);
    /**
     * Increase up to two resources to the client player's PlayerBank
     * Decrease the appropriate resources from the game bank.
     * Display resources received message.
     * @return the updated ClientModel
     * @param playerIndex
     * @param resource2
     * @param resource2
     */
    ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2);
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
    ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2);
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
    ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location);
    /**
     * Updates the client player's PlayerBank with the resources gained.
     * Updates all other players' PlayerBank with the resources lost.
     * @return the updated ClientModel
     * @param playerIndex
     * @param resource
     */
    ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource);
   
    /**
     * Increase the client player's Victory Points by 1
     * @return the updated ClientModel
     * @param playerIndex
     */
    ClientModel monument(PlayerIndex playerIndex);
    /**
     * Increase the client player's road of AmountType.BUILT by 1.
     * Decrease the client player's road of AmountType.AVAILABLE by 1.
     * Update the map with a new road.
     * @return the updated ClientModel
     * @param playerIndex
     * @param roadLocation
     * @param free
     */
    ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free);
    /**
     * Increase the client player's settlement of AmountType.BUILT by 1.
     * Decrease the client player's settlement of AmountType.AVAILABLE by 1.
     * Update the map with a new settlement
     * @return the updated ClientModel
     * @param playerIndex
     * @param vertexLocation
     * @param free
     */
    ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free);
    /**
     * Increase the client player's city of AmountType.BUILT by 1.
     * Decrease the client player's city of AmountType.AVAILABLE by 1.
     * Update the map with a new city
     * @return the updated ClientModel
     * @param playerIndex
     * @param vertexLocation
     */
    ClientModel buildCity(PlayerIndex playerIndex, VertexLocation vertexLocation);
    /**
     * Update model with a new TradeOffer object
     * Player to be traded is asked to trade.
     * @return the updated ClientModel
     * @param playerIndex
     * @param offer
     * @param receiver
     */
    ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver);
    /**
     * Update model and remove the TradeOffer
     * Update both players' PlayerBank depending on whether
     * the TradeOffer is accepted or not.
     * @return the updated ClientModel
     * @param playerIndex
     * @param willAccept
     */
    ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept);
    /**
     * The client player trades with the game bank based on the
     * trade ratio that is available to the player
     * @return the updated ClientModel
     * @param playerIndex
     * @param ratio
     * @param inputResource
     * @param outputResource
     */
    ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource);
    /**
     * The client player discards a number of each resource.
     * The resources are returned to the game bank
     * @return the updated CLientModel
     * @param playerIndex
     * @param discardedCards
     */
    ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards);
}
