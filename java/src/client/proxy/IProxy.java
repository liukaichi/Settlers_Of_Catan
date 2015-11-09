package client.proxy;

/**
 * This interface defines the actions that can be taken with the server. <br>
 * <br>
 * There are two types of APIs which this IProxy uses: Non-Move and Move.
 * Non-Move operations include operations about users, queries and actions about
 * the game before joining, operations for a game that you are in, and utility
 * methods to change how the server runs. Move actions are all of the commands
 * that you can apply while a game is running that relate to direct play of the
 * game.<br>
 * <br>
 * Nothing much is actually defined in this interface, as it simply extends
 * others. See these interfaces for further documentation.
 * 
 * @author Cache Staheli
 * @see IProxyNonMoveAPI
 * @see IProxyMoveAPI
 *
 */
public interface IProxy extends IProxyNonMoveAPI, IProxyMoveAPI
{

}
