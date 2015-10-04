package server.proxy;

import shared.communication.moveCommands.*;
import shared.model.ClientModel;

/**
 * Contains the commands/actions that you can take for a game that your are
 * currently in. This requires a Cookie.<br>
 * <br>
 * This interface is used by {@link IProxy}.
 * 
 * @author Cache Staheli
 * 
 */
public interface IProxyMoveAPI extends IProxyPlayingCommands, IProxyDevCardCommands
{
    // Anytime Commands
    /**
     * Tells the server to send a chat.
     * 
     * @pre All /move/* methods also have a common pre­condition in that they
     *      assume that the caller has already logged in to the server and
     *      joined a game.
     * @post The chat contains your message at the end.
     * @param sendChat
     *        sendChat command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel sendChat(SendChatCommand sendChat);

    // Rolling Commands
    /**
     * Rolls the dice.
     * 
     * @pre
     *      <ul>
     *      <li>All /move/* methods also have a common pre­condition in that
     *      they assume that the caller has already logged in to the server and
     *      joined a game.
     *      <li>It is your turn.
     *      <li>The client model’s status is ‘Rolling’
     *      </ul>
     * @post The client model’s status is now in ‘Discarding’ or ‘Robbing’ or
     *       ‘Playing’
     * 
     * @param rollNumber
     *        rollNumber command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel rollNumber(RollNumberCommand rollNumber);

    // Miscellaneous Commands
    /**
     * Tells the server to accept/reject the trade offered.
     * 
     * @pre
     *      <ul>
     *      <li>You have been offered a domestic trade
     *      <li>To accept the offered trade, you have the required resources
     *      </ul>
     * @post
     *       <ul>
     *       <li>If you accepted, you and the player who offered swap the
     *       specified resources.
     *       <li>If you declined, no resources are exchanged.
     *       <li>The trade offer is removed.
     *       </ul>
     * @param acceptTrade
     *        acceptTrade command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel acceptTrade(AcceptTradeCommand acceptTrade);

    /**
     * @pre
     *      <ul>
     *      <li>The status of the client model is 'Discarding.'
     *      <li>You have over 7 cards.
     *      <li>You have the cards you're choosing to discard.
     * 
     *      </ul>
     * @post
     *       <ul>
     *       <li>You gave up the specified resources.
     *       <li>If you're the last one to discard, the client model status
     *       changes to 'Robbing.'
     *       </ul>
     * @param discardCards
     *        discardCards command object.
     * @return updated Game model after the command is executed.
     */
    ClientModel discardCards(DiscardCardsCommand discardCards);
}
