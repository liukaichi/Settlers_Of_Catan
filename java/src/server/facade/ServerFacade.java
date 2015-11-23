package server.facade;

import server.ServerModel;
import server.manager.GameManager;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.definitions.exceptions.CatanException;
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
    @Override public ClientModel getGameState(int gameID, int version)
    {
        ClientModel model = getGame(gameID);
        int modelVersion = model.getVersion();
        if (version == -1 || version < modelVersion)
        {
            return model;
        } else
        {
            return null;
        }
    }

    @Override public ClientModel sendChat(int gameID, PlayerIndex playerIndex, String content) throws CatanException
    {
        return getGame(gameID).sendChat(playerIndex, content);
    }

    @Override public ClientModel rollNumber(int gameID, PlayerIndex playerIndex, int number) throws CatanException
    {
        return getGame(gameID).rollNumber(playerIndex, number);
    }

    @Override public ClientModel robPlayer(int gameID, PlayerIndex playerIndex, PlayerIndex victim,
            HexLocation location) throws CatanException
    {
        return getGame(gameID).robPlayer(playerIndex, victim, location);
    }

    @Override public ClientModel finishTurn(int gameID, PlayerIndex playerIndex) throws CatanException
    {
       // return new ClientModel();
        return getGame(gameID).finishTurn(playerIndex);
    }

    @Override public ClientModel buyDevCard(int gameID, PlayerIndex playerIndex) throws CatanException
    {
        return getGame(gameID).buyDevCard(playerIndex);
    }

    @Override public ClientModel yearOfPlenty(int gameID, PlayerIndex playerIndex, ResourceType resource1,
            ResourceType resource2) throws CatanException
    {
        return getGame(gameID).yearOfPlenty(playerIndex, resource1, resource2);
    }

    @Override public ClientModel roadBuilding(int gameID, PlayerIndex playerIndex, EdgeLocation spot1,
            EdgeLocation spot2) throws CatanException
    {
        return getGame(gameID).roadBuilding(playerIndex, spot1, spot2);
    }

    @Override public ClientModel soldier(int gameID, PlayerIndex playerIndex, PlayerIndex victimIndex,
            HexLocation location) throws CatanException
    {
        return getGame(gameID).soldier(playerIndex, victimIndex, location);
    }

    @Override public ClientModel monopoly(int gameID, PlayerIndex playerIndex, ResourceType resource)
            throws CatanException
    {
        return getGame(gameID).monopoly(playerIndex, resource);
    }

    @Override public ClientModel monument(int gameID, PlayerIndex playerIndex) throws CatanException
    {
        return getGame(gameID).monument(playerIndex);
    }

    @Override public ClientModel buildRoad(int gameID, PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
            throws CatanException
    {
        //return new ClientModel(); //TODO fix this.
        return getGame(gameID).buildRoad(playerIndex,roadLocation,free);
    }

    @Override public ClientModel buildSettlement(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation,
            boolean free) throws CatanException
    {
        //return new ClientModel(); //TODO fix this.
        return getGame(gameID).buildSettlement(playerIndex,vertexLocation, free);
    }

    @Override public ClientModel buildCity(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation)
            throws CatanException
    {
        //return new ClientModel(); //TODO fix this.
        return getGame(gameID).buildCity(playerIndex,vertexLocation);
    }

    @Override public ClientModel offerTrade(int gameID, PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
            throws CatanException
    {
        return getGame(gameID).offerTrade(playerIndex, offer, receiver);
    }

    @Override public ClientModel acceptTrade(int gameID, PlayerIndex playerIndex, boolean willAccept)
            throws CatanException
    {
        return getGame(gameID).acceptTrade(playerIndex, willAccept);
    }

    @Override public ClientModel maritimeTrade(int gameID, PlayerIndex playerIndex, TradeRatio ratio,
            ResourceType inputResource, ResourceType outputResource) throws CatanException
    {
        return getGame(gameID).maritimeTrade(playerIndex, ratio, inputResource, outputResource);
    }

    @Override public ClientModel discardCards(int gameID, PlayerIndex playerIndex, Resources discardedCards)
            throws CatanException
    {
        return getGame(gameID).discardCards(playerIndex, discardedCards);
    }

    private ServerModel getGame(int gameID)
    {
        return GameManager.getInstance().getGame(gameID);
    }
}
