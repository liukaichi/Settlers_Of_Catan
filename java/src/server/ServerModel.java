package server;

import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;
import shared.model.ClientModel;

/**
 * Created by cstaheli on 11/5/2015.
 */
public class ServerModel extends ClientModel
{
    ClientModel sendChat()
    {
        return null;
    }
    ClientModel rollNumber()
    {
        return null;
    }
    ClientModel robPlayer()
    {
        return null;
    }/*
    ClientModel finishTurn()
    {
        return null;
    }*/
    ClientModel buyDevCard()
    {
        return null;
    }
    ClientModel yearOfPlenty()
    {
        return null;
    }
    ClientModel roadBuilding()
    {
        return null;
    }
    ClientModel soldier()
    {
        return null;
    }
    ClientModel monopoly()
    {
        return null;
    }
    ClientModel monument()
    {
        return null;
    }
    ClientModel buildRoad()
    {
        return null;
    }
    /**
     * Builds a settlement for the given player at the given location.
     * @param player the player who is building the settlement.
     * @param location the location where the settlement is being built.
     */
    public void buildSettlement(PlayerIndex player, VertexLocation location)
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
    ClientModel offerTrade()
    {
        return null;
    }
    ClientModel acceptTrade()
    {
        return null;
    }
    ClientModel maritimeTrade()
    {
        return null;
    }
    ClientModel discardCards()
    {
        return null;
    }
}
