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
    ClientModel monument(PlayerIndex playerIndex);
    ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free);
    ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free);
    ClientModel buildCity(PlayerIndex playerIndex, VertexLocation vertexLocation);
    ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver);
    ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept);
    ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource);
    ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards);
}
