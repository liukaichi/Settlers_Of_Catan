package server.proxy;

import shared.communication.moveCommands.*;
import shared.model.ClientModel;

public interface IProxyPlayingCommands
{
    /**
     * Tells the server to build a road for the specified player.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model’s status is 'Playing'.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>The road location is open
     *      <li>The road location is connected to another road owned by the
     *      player
     *      <li>The road location is not on water
     *      <li>You have the required resources (1 wood, 1 brick; 1 road)
     *      <li><b>Setup round:</b> Must be placed by settlement owned by the
     *      player with no adjacent road
     *      </ul>
     * @post
     *       <ul>
     *       <li>You lost the resources required to build a road (1 wood, 1
     *       brick; 1 road).
     *       <li>The road is on the map at the specified location.
     *       <li>If applicable, “longest road” has been awarded to the player
     *       with the longest road.
     *       </ul>
     * @param buildRoad
     *        buildRoad command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel buildRoad(BuildRoadCommand buildRoad);

    /**
     * Tells the server to build a Settlement at a specified location for a
     * player.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model’s status is 'Playing'.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>The settlement location is open
     *      <li>The settlement location is not on water.
     *      <li>The settlement location is connected to one of your roads except
     *      during setup.
     *      <li>You have the required resources (1 wood, 1 brick, 1 wheat, 1
     *      sheep; 1 settlement).
     *      <li>The settlement cannot be placed adjacent to another settlement.
     *      </ul>
     * @post
     *       <ul>
     *       <li>You lost the resources required to build a settlement (1 wood,
     *       1 brick, 1 wheat, 1 sheep; 1 settlement)
     *       <li>The settlement is on the map at the specified location
     *       </ul>
     * @param buildSettlement
     *        buildSettlement command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel buildSettlement(BuildSettlementCommand buildSettlement);

    /**
     * Tells the server to build a city at the specified location for the
     * specified player.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model’s status is 'Playing'.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>The city location is where you currently have a settlement
     *      <li>You have the required resources (2 wheat, 3 ore; 1 city)
     *      </ul>
     * @post
     *       <ul>
     *       <li>You lost the resources required to build a city (2 wheat, 3
     *       ore; 1 city)
     *       <li>The city is on the map at the specified location
     *       <li>You got a settlement back
     * 
     *       </ul>
     * @param buildCity
     *        buildCity command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel buildCity(BuildCityCommand buildCity);

    /**
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model’s status is 'Playing'.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>You have the resources you are offering.
     *      </ul>
     * @post
     *       <ul>
     *       <li>The trade is offered to the other player (stored in the server
     *       model).
     *       </ul>
     * @param offerTrade
     *        offerTrade command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel offerTrade(OfferTradeCommand offerTrade);

    /**
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model’s status is 'Playing'.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>You have the resources you are giving
     *      <li>For ratios less than 4:1, you have the correct port for the
     *      trade
     *      </ul>
     * @post
     *       <ul>
     *       <li>The trade has been executed (the offered resources are in the
     *       bank, and the requested resource has been received)
     *       </ul>
     * @param maritimeTrade
     *        maritimeTrade command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel maritimeTrade(MaritimeTradeCommand maritimeTrade);

    /**
     * Calls the server to rob a player.
     * 
     * @pre
     *      <ul>
     *      <li>All /move/* methods also have a common pre­condition in that
     *      they assume that the caller has already logged in to the server and
     *      joined a game.
     *      <li>The robber is not being kept in the same location.
     *      <li>If a player is being robbed (i.e., victimIndex != ­1), the
     *      player being robbed has resource cards.
     *      </ul>
     * @post
     *       <ul>
     *       <li>The robber is in the new location.
     *       <li>The player being robbed (if any) gave you one of his resource
     *       cards (randomly selected).
     *       </ul>
     * @param robPlayer
     *        robPlayer command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel robPlayer(RobPlayerCommand robPlayer);

    /**
     * Calls the server to finish a player's turn.
     * 
     * @pre All /move/* methods also have a common pre­condition in that they
     *      assume that the caller has already logged in to the server and
     *      joined a game.
     * @post
     *       <ul>
     *       <li>The cards in your new dev card hand have been transferred to
     *       your old dev card hand.
     *       <li>It is the next player’s turn.
     *       </ul>
     * @param finishTurn
     *        finishTurn command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel finishTurn(FinishTurnCommand finishTurn);

    /**
     * Calls the server to have a player buy a Development Card.
     * 
     * @pre
     *      <ul>
     *      <li>All /move/* methods also have a common pre­condition in that
     *      they assume that the caller has already logged in to the server and
     *      joined a game.
     *      <li>You have the required resources (1 ore, 1 wheat, 1 sheep).
     *      <li>There are dev cards left in the deck.
     *      </ul>
     * @post You have a new card:
     *       <ul>
     *       <li>If it is a monument card, it has been added to your old devcard
     *       hand.
     *       <li>If it is a non­monument card, it has been added to your new
     *       devcard hand (unplayable this turn).
     *       </ul>
     * @param buyDevCard
     *        buyDevCard command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel buyDevCard(BuyDevCardCommand buyDevCard);
}
