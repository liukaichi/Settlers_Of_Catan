package server.facade;

import server.ServerModel;
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
 * The actual Facade that interacts for real.
 */
public class ServerFacade extends AbstractServerFacade
{
    private ServerModel model;

    @Override public ClientModel getGameState(int version)
    {
        return null;
    }

    @Override public ClientModel sendChat(int gameID, PlayerIndex sender, String content)
    {
        return null;
    }

    @Override public ClientModel rollNumber(int gameID, PlayerIndex playerIndex, int number)
    {
        return null;
    }

    @Override public ClientModel robPlayer(int gameID, PlayerIndex playerIndex, PlayerIndex victim,
            HexLocation location)
    {
        return null;
    }

    @Override public ClientModel finishTurn(int gameID, PlayerIndex playerIndex)
    {
    	model.finishTurn(); 
        return null;
    }

    @Override public ClientModel buyDevCard(int gameID, PlayerIndex playerIndex)
    {
        //model.buyDevCard();
        return null;
    }

    @Override public ClientModel yearOfPlenty(int gameID, PlayerIndex playerIndex, ResourceType resource1,
            ResourceType resource2)
    {
        return null;
    }

    @Override public ClientModel roadBuilding(int gameID, PlayerIndex playerIndex, EdgeLocation spot1,
            EdgeLocation spot2)
    {
        return null;
    }

    @Override
    public ClientModel soldier(int gameID, PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        return null;
    }

    @Override
    public ClientModel monopoly(int gameID, PlayerIndex playerIndex, ResourceType resource)
    {
        return null;
    }

    @Override
    public ClientModel monument(int gameID, PlayerIndex playerIndex)
    {
        return null;
    }

    @Override
    public ClientModel buildRoad(int gameID, PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        model.buildRoad(null, null);
        return null;
    }

    @Override
    public ClientModel buildSettlement(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free)
    {
        //model.buildSettlement(null, null);
        return null;
    }

    @Override
    public ClientModel buildCity(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation)
    {
        //model.buildCity(null, null);
        return null;
    }

    @Override
    public ClientModel offerTrade(int gameID, PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return null;
    }

    @Override
    public ClientModel acceptTrade(int gameID, PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }

    @Override
    public ClientModel maritimeTrade(int gameID, PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
                                     ResourceType outputResource)
    {
        return null;
    }

    @Override
    public ClientModel discardCards(int gameID, PlayerIndex playerIndex, Resources discardedCards)
    {
        return null;
    }
}
