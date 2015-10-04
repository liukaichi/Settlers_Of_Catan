package shared.definitions;

import server.proxy.IProxyPlayingCommands;

/**
 * Defines the types of moves that can be called on the server. <br>
 * <br>
 * These values are also the same string that will be passed throught to the
 * server via Json.
 * 
 * @see IProxyPlayingCommands
 */
public enum MoveType
{
    sendChat, rollNumber, robPlayer, finishTurn, buyDevCard, Year_Of_Plenty, Road_Building, Soldier, Monopoly, Monument,
    buildRoad, buildSettlement, buildCity, offerTrade, acceptTrade, maritimeTrade, discardCards;
}
