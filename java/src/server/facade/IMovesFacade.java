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
     *
     * @param gameID  the id of the game to execute the command on.
     * @param sender  the person sending the chat.
     * @param content the content of the chat.
     * @return the updated ClientModel
     */
    ClientModel sendChat(int gameID, PlayerIndex sender, String content);

    /**
     * Distribute resources to players based on the number rolled
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the player rolling the number.
     * @param number      the number rolled.
     * @return the updated ClientModel
     */
    ClientModel rollNumber(int gameID, PlayerIndex playerIndex, int number);

    /**
     * Move the robber to the new given location.
     * Increase the robbed resource client player's PlayerBank.
     * Decrease the robbed resource from the robbed player's PlayerBank.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the playing doing the robbing.
     * @param victim      the index of the player being robbed.
     * @param location    the new location of the robber.
     * @return the updated ClientModel
     */
    ClientModel robPlayer(int gameID, PlayerIndex playerIndex, PlayerIndex victim, HexLocation location);

    /**
     * Calls finish turn from the model to progress to next state.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player finishing the turn.
     * @return the updated ClientModel
     */
    ClientModel finishTurn(int gameID, PlayerIndex playerIndex);

    /**
     * Add a random Development Card to the client player's PlayerBank
     * Pop said Development Card from the DevCardDeck
     * Display bought Development Card message.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player buying a card.
     * @return the updated ClientModel
     */
    ClientModel buyDevCard(int gameID, PlayerIndex playerIndex);

    /**
     * Increase up to two resources to the client player's PlayerBank
     * Decrease the appropriate resources from the game bank.
     * Display resources received message.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player playing the card.
     * @param resource1   the first resource.
     * @param resource2   the second resource.
     * @return the updated ClientModel
     */
    ClientModel yearOfPlenty(int gameID, PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2);

    /**
     * Increase the client player's roads of AmountType.BUILT by 2
     * Decrease the client player's roads of AmountType.AVAILABLE by 2
     * Update the map with the 2 new roads
     * Display 2 roads built message
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player playing the card.
     * @param spot1       the first location to build a road.
     * @param spot2       the second location to build a road.
     * @return the updated ClientModel
     */
    ClientModel roadBuilding(int gameID, PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2);

    /**
     * Update the map with the new Robber location.
     * Increase the client player's PlayerBank with the robbed resource.
     * Decrease the robber player's PlayerBank with the robber resource.
     * Display robbed message.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player playing the card.
     * @param victimIndex the index of the player being robbed.
     * @param location    the new location of the robber.
     * @return the updated ClientModel
     */
    ClientModel soldier(int gameID, PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location);

    /**
     * Updates the client player's PlayerBank with the resources gained.
     * Updates all other players' PlayerBank with the resources lost.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player playing the card.
     * @param resource    the resource the card is being played for.
     * @return the updated ClientModel
     */
    ClientModel monopoly(int gameID, PlayerIndex playerIndex, ResourceType resource);

    /**
     * Increase the client player's Victory Points by 1
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player playing the card.
     * @return the updated ClientModel
     */
    ClientModel monument(int gameID, PlayerIndex playerIndex);

    /**
     * Increase the client player's road of AmountType.BUILT by 1.
     * Decrease the client player's road of AmountType.AVAILABLE by 1.
     * Update the map with a new road.
     *
     * @param gameID       the id of the game to execute the command on.
     * @param playerIndex  the index of the player building the road.
     * @param roadLocation the location where the road is being built.
     * @param isFree       if the road is free (only in setup state).
     * @return the updated ClientModel
     */
    ClientModel buildRoad(int gameID, PlayerIndex playerIndex, EdgeLocation roadLocation, boolean isFree);

    /**
     * Increase the client player's settlement of AmountType.BUILT by 1.
     * Decrease the client player's settlement of AmountType.AVAILABLE by 1.
     * Update the map with a new settlement
     *
     * @param gameID         the id of the game to execute the command on.
     * @param playerIndex    the index of the player building the settlement.
     * @param vertexLocation the location of the settlement.
     * @param isFree         if the settlement is free (only in setup state).
     * @return the updated ClientModel
     */
    ClientModel buildSettlement(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation, boolean isFree);

    /**
     * Increase the client player's city of AmountType.BUILT by 1.
     * Decrease the client player's city of AmountType.AVAILABLE by 1.
     * Update the map with a new city
     *
     * @param gameID         the id of the game to execute the command on.
     * @param playerIndex    the index of the player building the city.
     * @param vertexLocation the location of the city.
     * @return the updated ClientModel
     */
    ClientModel buildCity(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation);

    /**
     * Update model with a new TradeOffer object
     * Player to be traded is asked to trade.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player offering the trade.
     * @param offer       the offer being sent.
     * @param receiver    the intended receiver of the trade.
     * @return the updated ClientModel
     */
    ClientModel offerTrade(int gameID, PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver);

    /**
     * Update model and remove the TradeOffer
     * Update both players' PlayerBank depending on whether
     * the TradeOffer is accepted or not.
     *
     * @param gameID      the id of the game to execute the command on.
     * @param playerIndex the index of the player sending the command.
     * @param willAccept  whether or not the player will accept the trade offered them.
     * @return the updated ClientModel
     */
    ClientModel acceptTrade(int gameID, PlayerIndex playerIndex, boolean willAccept);

    /**
     * The client player trades with the game bank based on the
     * trade ratio that is available to the player
     *
     * @param gameID         the id of the game to execute the command on.
     * @param playerIndex    the index of the player doing the trade.
     * @param ratio          the ratio at which to trade.
     * @param inputResource  the resource the player is trading away.
     * @param outputResource the resource the player is trading for.
     * @return the updated ClientModel
     */
    ClientModel maritimeTrade(int gameID, PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource);

    /**
     * The client player discards a number of each resource.
     * The resources are returned to the game bank
     *
     * @param gameID         the id of the game to execute the command on.
     * @param playerIndex    the index of the player discarding.
     * @param discardedCards the cards discarded.
     * @return the updated CLientModel
     */
    ClientModel discardCards(int gameID, PlayerIndex playerIndex, Resources discardedCards);
}
