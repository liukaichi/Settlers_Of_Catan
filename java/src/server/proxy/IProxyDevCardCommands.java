package server.proxy;

import shared.communication.moveCommands.*;
import shared.model.ClientModel;

/**
 * Contains all of the commands that are called on the server relating to
 * playing cards.<br>
 * <br>
 * This interface is extended by {@link IProxyMoveAPI}.
 * 
 * @author Cache Staheli
 *
 */
public interface IProxyDevCardCommands
{

    /**
     * Calls the server to have a player play a Soldier card.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model status is 'Playing.'
     *      <li>You have the specific card you want to play in your old
     *      Development Card hand
     *      <li>You have not yet played a non­monument Development Card this
     *      turn
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>The robber is not being kept in the same location.
     *      <li>If a player is being robbed (i.e., victimIndex != ­1), the
     *      player being robbed has resource cards.
     *      </ul>
     * @post
     *       <ul>
     *       <li>The robber is in the new location.
     *       <li>The player being robbed (if any) gave you one of his resource
     *       cards (randomly selected.
     *       <li>If applicable, “largest army” has been awarded to the player
     *       who has played the most soldier cards.
     *       <li>You are not allowed to play other development cards during this
     *       turn (except for monument cards, which may still be played).
     *       </ul>
     * 
     * @param soldier
     *        Soldier command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel soldier(SoldierCommand soldier);

    /**
     * Calls the server to have a player play a Year of Plenty card.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model status is 'Playing.'
     *      <li>You have the specific card you want to play in your old
     *      Development Card hand
     *      <li>You have not yet played a non­monument Development Card this
     *      turn
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>The two specified resources are in the bank.
     *      </ul>
     * @post You gained the two specified resources.
     * @param yearOfPlenty
     *        Year_of_Plenty command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel yearOfPlenty(YearOfPlentyCommand yearOfPlenty);

    /**
     * Calls the server to have a player play a Road Building card.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model status is 'Playing.'
     *      <li>You have the specific card you want to play in your old
     *      Development Card hand.
     *      <li>You have not yet played a non­monument Development Card this
     *      turn.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>The first road location (spot1) is connected to one of your
     *      roads.
     *      <li>The second road location (spot2) is connected to one of your
     *      roads or to the first road location (spot1).
     *      <li>Neither road location is on water.
     *      <li>You have at least two unused roads.
     *      </ul>
     * @post
     *       <ul>
     *       <li>You have two fewer unused roads.
     *       <li>Two new roads appear on the map at the specified locations.
     *       <li>If applicable, “longest road” has been awarded to the player
     *       with the longest road.
     *       </ul>
     * @param roadBuilding
     *        Road_Building command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel roadBuilding(RoadBuildingCommand roadBuilding);

    /**
     * Calls the server to have a player play a Monopoly card.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model status is 'Playing.'
     *      <li>You have the specific card you want to play in your old
     *      Development Card hand.
     *      <li>You have not yet played a non­monument Development Card this
     *      turn.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>None
     *      </ul>
     * @post All of the other players have given you all of their resource cards
     *       of the specified type.
     * 
     * @param monopoly
     *        Monopoly command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel monopoly(MonopolyCommand monopoly);

    /**
     * Calls the server to have a player play a Monument card.
     * 
     * @pre General Preconditions:
     *      <ul>
     *      <li>It is your turn.
     *      <li>The client model status is 'Playing.'
     *      <li>You have the specific card you want to play in your old
     *      Development Card hand.
     *      <li>You have not yet played a non­monument Development Card this
     *      turn.
     *      </ul>
     *      Others:
     *      <ul>
     *      <li>You have enough monument cards to win the game (i.e., reach 10
     *      victory points).
     *      </ul>
     * @post You gained a victory point.
     * 
     * @param monument
     *        Monument command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel monument(MonumentCommand monument);
}
