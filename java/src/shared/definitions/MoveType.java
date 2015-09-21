package shared.definitions;

import server.proxy.IProxyPlayingCommands;

/**
 * Defines the types of moves that can be called on the server.
 * 
 * @see IProxyPlayingCommands
 */
public enum MoveType
{
    SEND_CHAT, ROLL_NUMBER, ROB_PLAYER, FINISH_TURN, BUY_DEV_CARD, YEAR_OF_PLENTY, ROAD_BUILDING, SOLDIER, MONOPOLY,
    MONUMENT, BUILD_ROAD, BUILD_SETTLEMENT, BUILD_CITY, OFFER_TRADE, ACCEPT_TRADE, MARITIME_TRADE, DISCARD_CARDS;
}
