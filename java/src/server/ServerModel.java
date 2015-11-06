package server;

import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.player.TradeOffer;

/**
 * Created by cstaheli on 11/5/2015.
 */
public class ServerModel extends ClientModel
{
    public ClientModel sendChat(PlayerIndex playerIndex, String content)
    {
        return null;
    }
    public ClientModel rollNumber(PlayerIndex playerIndex, int number)
    {
        return null;
    }
    public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location)
    {
        return null;
    }
    public ClientModel finishTurn(PlayerIndex playerIndex)
    {
        return null;
    }
    public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        return null;
    }
    public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        return null;
    }
    public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        return null;
    }
    public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        return null;
    }
    public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource)
    {
        return null;
    }
    public ClientModel monument(PlayerIndex playerIndex)
    {
        return null;
    }
    public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        return null;
    }
    /**
     * Builds a settlement for the given player at the given location.
     * @param player the player who is building the settlement.
     * @param location the location where the settlement is being built.
     */
    public void buildSettlement(PlayerIndex player, VertexLocation location, boolean isFree)
    {

    }
    /**
     * Builds a city for the given player at the given location.
     * @param player the player who is building the city.
     * @param location the location where the city is being built.
     */
    public void buildCity(PlayerIndex player, VertexLocation location)
    {
        getMap().buildCity(player, location);
    }
    public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return null;
    }
    public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }
    public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource)
    {
        return null;
    }
    public ClientModel discardCards()
    {
        return null;
    }
}
