package server;

import server.facade.IMovesFacade;
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
 * Created by cstaheli on 11/5/2015.
 */
public class ServerModel extends ClientModel implements IMovesFacade
{
    @Override public ClientModel sendChat(PlayerIndex playerIndex, String content)
    {
        return null;
    }
    @Override public ClientModel rollNumber(PlayerIndex playerIndex, int number)
    {
        return null;
    }
    @Override public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location)
    {
        return null;
    }
    @Override public ClientModel finishTurn(PlayerIndex playerIndex)
    {
        return null;
    }
    @Override public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        return null;
    }
    @Override public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        return null;
    }
    @Override public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        return null;
    }
    @Override public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        return null;
    }
    @Override public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource)
    {
        return null;
    }
    @Override public ClientModel monument(PlayerIndex playerIndex)
    {
        return null;
    }
    @Override public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        return null;
    }
    /**
     * Builds a settlement for the given player at the given location.
     * @param player the player who is building the settlement.
     * @param location the location where the settlement is being built.
     */
    @Override public ClientModel buildSettlement(PlayerIndex player, VertexLocation location, boolean isFree)
    {
        return null;
    }
    /**
     * Builds a city for the given player at the given location.
     * @param player the player who is building the city.
     * @param location the location where the city is being built.
     */
    @Override public ClientModel buildCity(PlayerIndex player, VertexLocation location)
    {
        getMap().buildCity(player, location);
        return null;
    }
    @Override public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return null;
    }
    @Override public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }
    @Override public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource)
    {
        return null;
    }

    @Override public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        return null;
    }
}
