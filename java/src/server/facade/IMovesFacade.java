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
 * An API for the moves. that can happen during game play.
 */
public interface IMovesFacade
{
    /**
     * Add a message to the ClientModel with the sender's id
     * @return the updated ClientModel
     * @param sender the person sending the chat.
     * @param content the content of the chat.
     */
    ClientModel sendChat(PlayerIndex sender, String content);
    /**
     * Distribute resources to players based on the number rolled
     * @return the updated ClientModel
     * @param playerIndex the player rolling the number.
     * @param number the number rolled.
     */
    ClientModel rollNumber(PlayerIndex playerIndex, int number);
    /**
     * Move the robber to the new given location.
     * Increase the robbed resource client player's PlayerBank.
     * Decrease the robbed resource from the robbed player's PlayerBank.
     * @return the updated ClientModel
     * @param playerIndex the index of the playing doing the robbing.
     * @param victim the index of the player being robbed.
     * @param location the new location of the robber.
     */
    ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location);
    /**
     * Calls finish turn from the model to progress to next state.
     * @return the updated ClientModel
     * @param playerIndex the index of the player finishing the turn.
     */
    ClientModel finishTurn(PlayerIndex playerIndex);
    /**
     * Add a random Development Card to the client player's PlayerBank
     * Pop said Development Card from the DevCardDeck
     * Display bought Development Card message.
     * @return the updated ClientModel
     * @param playerIndex the index of the player buying a card.
     */
    ClientModel buyDevCard(PlayerIndex playerIndex);
    /**
     * Increase up to two resources to the client player's PlayerBank
     * Decrease the appropriate resources from the game bank.
     * Display resources received message.
     * @return the updated ClientModel
     * @param playerIndex the index of the player playing the card.
     * @param resource1 the first resource.
     * @param resource2 the second resource.
     */
    ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2);
    /**
     * Increase the client player's roads of AmountType.BUILT by 2
     * Decrease the client player's roads of AmountType.AVAILABLE by 2
     * Update the map with the 2 new roads
     * Display 2 roads built message
     * @return the updated ClientModel
     * @param playerIndex the index of the player playing the card.
     * @param spot1 the first location to build a road.
     * @param spot2 the second location to build a road.
     */
    ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2);
    /**
     * Update the map with the new Robber location.
     * Increase the client player's PlayerBank with the robbed resource.
     * Decrease the robber player's PlayerBank with the robber resource.
     * Display robbed message.
     * @return the updated ClientModel
     * @param playerIndex the index of the player playing the card.
     * @param victimIndex the index of the player being robbed.
     * @param location the new location of the robber.
     */
    ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location);
    /**
     * Updates the client player's PlayerBank with the resources gained.
     * Updates all other players' PlayerBank with the resources lost.
     * @return the updated ClientModel
     * @param playerIndex the index of the player playing the card.
     * @param resource the resource the card is being played for.
     */
    ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource);
   
    /**
     * Increase the client player's Victory Points by 1
     * @return the updated ClientModel
     * @param playerIndex the index of the player playing the card.
     */
    ClientModel monument(PlayerIndex playerIndex);
    /**
     * Increase the client player's road of AmountType.BUILT by 1.
     * Decrease the client player's road of AmountType.AVAILABLE by 1.
     * Update the map with a new road.
     * @return the updated ClientModel
     * @param playerIndex the index of the player building the road.
     * @param roadLocation the location where the road is being built.
     * @param free if the road is free (only in setup state).
     */
    ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free);
    /**
     * Increase the client player's settlement of AmountType.BUILT by 1.
     * Decrease the client player's settlement of AmountType.AVAILABLE by 1.
     * Update the map with a new settlement
     * @return the updated ClientModel
     * @param playerIndex the index of the player building the settlement.
     * @param vertexLocation the location of the settlement.
     * @param free if the settlement is free (only in setup state).
     */
    ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free);
    /**
     * Increase the client player's city of AmountType.BUILT by 1.
     * Decrease the client player's city of AmountType.AVAILABLE by 1.
     * Update the map with a new city
     * @return the updated ClientModel
     * @param playerIndex the index of the player building the city.
     * @param vertexLocation the location of the city.
     */
    ClientModel buildCity(PlayerIndex playerIndex, VertexLocation vertexLocation);
    /**
     * Update model with a new TradeOffer object
     * Player to be traded is asked to trade.
     * @return the updated ClientModel
     * @param playerIndex the index of the player offering the trade.
     * @param offer the offer being sent.
     * @param receiver the intended receiver of the trade.
     */
    ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver);
    /**
     * Update model and remove the TradeOffer
     * Update both players' PlayerBank depending on whether
     * the TradeOffer is accepted or not.
     * @return the updated ClientModel
     * @param playerIndex the index of the player sending the command.
     * @param willAccept whether or not the player will accept the trade offered them.
     */
    ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept);
    /**
     * The client player trades with the game bank based on the
     * trade ratio that is available to the player
     * @return the updated ClientModel
     * @param playerIndex the index of the player doing the trade.
     * @param ratio the ratio at which to trade.
     * @param inputResource the resource the player is trading away.
     * @param outputResource the resource the player is trading for.
     */
    ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource);
    /**
     * The client player discards a number of each resource.
     * The resources are returned to the game bank
     * @return the updated CLientModel
     * @param playerIndex the index of the player discarding.
     * @param discardedCards the cards discarded.
     */
    ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards);
}