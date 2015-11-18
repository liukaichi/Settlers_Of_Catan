package server.facade;

import server.util.FileUtils;
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
 * Uses dependency injection to allow this object to be used as the facade for testing purposes.
 */
public class MockServerFacade extends AbstractServerFacade
{
    public MockServerFacade()
    {

    }

    @Override public ClientModel getGameState(int gameID, int version)
    {
        if (version == -1 || version < 50)
            return FileUtils.getModelFromFile(null, "basicGame");
        else if (version >= 50 && version < 100)
            return FileUtils.getModelFromFile(null, "playersHaveCards");
        else if (version >= 100)
            return FileUtils.getModelFromFile(null, "advancedGame");
        else
            return null;

    }

    @Override public ClientModel sendChat(int gameID, PlayerIndex playerIndex, String content) throws CatanException
    {

        return FileUtils.getModelFromFile(null, "sendChat");
    }

    @Override public ClientModel rollNumber(int gameID, PlayerIndex playerIndex, int number) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "basicGame");
    }

    @Override public ClientModel robPlayer(int gameID, PlayerIndex playerIndex, PlayerIndex victim,
            HexLocation location) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "basicGame");

    }

    @Override public ClientModel finishTurn(int gameID, PlayerIndex playerIndex) throws CatanException
    {
        ClientModel model = new ClientModel();
        switch (playerIndex)
        {
        case PLAYER_0:
            model = FileUtils.getModelFromFile(null, "player1Turn");
            break;
        case PLAYER_1:
            model = FileUtils.getModelFromFile(null, "player2Turn");
            break;
        case PLAYER_2:
            model = FileUtils.getModelFromFile(null, "player3Turn");
            break;
        case PLAYER_3:
            model = FileUtils.getModelFromFile(null, "player0Turn");
            break;
        }
        return model;
    }

    @Override public ClientModel buyDevCard(int gameID, PlayerIndex playerIndex) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "playersHaveCards");
    }

    @Override public ClientModel yearOfPlenty(int gameID, PlayerIndex playerIndex, ResourceType resource1,
            ResourceType resource2) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "playersHaveCards");
    }

    @Override public ClientModel roadBuilding(int gameID, PlayerIndex playerIndex, EdgeLocation spot1,
            EdgeLocation spot2) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "playersHaveCards");
    }

    @Override public ClientModel soldier(int gameID, PlayerIndex playerIndex, PlayerIndex victimIndex,
            HexLocation location) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "playersHaveCards");
    }

    @Override public ClientModel monopoly(int gameID, PlayerIndex playerIndex, ResourceType resource)
            throws CatanException
    {
        return FileUtils.getModelFromFile(null, "playersHaveCards");
    }

    @Override public ClientModel monument(int gameID, PlayerIndex playerIndex) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "playersHaveCards");
    }

    @Override public ClientModel buildRoad(int gameID, PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
            throws CatanException
    {
        return FileUtils.getModelFromFile(null, "advancedGame");
    }

    @Override public ClientModel buildSettlement(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation,
            boolean free) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "advancedGame");
    }

    @Override public ClientModel buildCity(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation)
            throws CatanException
    {
        return FileUtils.getModelFromFile(null, "advancedGame");
    }

    @Override public ClientModel offerTrade(int gameID, PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
            throws CatanException
    {
        return FileUtils.getModelFromFile(null, "tradeAvailable");
    }

    @Override public ClientModel acceptTrade(int gameID, PlayerIndex playerIndex, boolean willAccept)
            throws CatanException
    {
        return FileUtils.getModelFromFile(null, "tradeAvailable");
    }

    @Override public ClientModel maritimeTrade(int gameID, PlayerIndex playerIndex, TradeRatio ratio,
            ResourceType inputResource, ResourceType outputResource) throws CatanException
    {
        return FileUtils.getModelFromFile(null, "tradeAvailable");
    }

    @Override public ClientModel discardCards(int gameID, PlayerIndex playerIndex, Resources discardedCards)
            throws CatanException
    {
        return FileUtils.getModelFromFile(null, "basicGame");
    }
}
