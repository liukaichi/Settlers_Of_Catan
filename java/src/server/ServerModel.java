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

    }
    ClientModel rollNumber()
    {

    }
    ClientModel robPlayer()
    {

    }
    ClientModel finishTurn()
    {

    }
    ClientModel buyDevCard()
    {

    }
    ClientModel yearOfPlenty()
    {

    }
    ClientModel roadBuilding()
    {

    }
    ClientModel soldier()
    {

    }
    ClientModel monopoly()
    {

    }
    ClientModel monument()
    {

    }
    ClientModel buildRoad()
    {

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

    }
    ClientModel acceptTrade()
    {

    }
    ClientModel maritimeTrade()
    {

    }
    ClientModel discardCards()
    {

    }
}
