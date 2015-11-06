package server.facade;

import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.ClientModel;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IMovesFacade
{
    ClientModel sendChat(PlayerIndex playerIndex, String content);
    ClientModel rollNumber(PlayerIndex playerIndex, int number);
    ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location);
    ClientModel finishTurn(PlayerIndex playerIndex);
    ClientModel buyDevCard(PlayerIndex playerIndex);
    ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2);
    ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2);
    ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location);
    ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource);
    ClientModel monument(PlayerIndex playerIndex);
    ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free);
    ClientModel buildSettlement();
    ClientModel buildCity();
    ClientModel offerTrade();
    ClientModel acceptTrade();
    ClientModel maritimeTrade();
    ClientModel discardCards();
}
