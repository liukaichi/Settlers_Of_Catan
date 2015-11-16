package server.facade;

import server.manager.User;
import server.util.FileUtils;
import shared.communication.Credentials;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.definitions.exceptions.ExistingRegistrationException;
import shared.definitions.exceptions.InvalidCredentialsException;
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

    @Override public ClientModel getGameState(int version)
    {
        if (version == -1 || version < 50)
            return FileUtils.getModelFromFile("sample/mockServerJson", "basicGameTurn", ".json");
        else if (version >= 50 && version < 100)
            return FileUtils.getModelFromFile("sample/mockServerJson", "playersHaveCards", ".json");
        else if (version >= 100)
            return FileUtils.getModelFromFile("sample/mockServerJson", "advancedGame", ".json");
        else
            return null;

    }

    @Override public ClientModel sendChat(int gameID, PlayerIndex playerIndex, String content)
    {

        return FileUtils.getModelFromFile("sample/mockServerJson", "sendChat", ".json");
    }

    @Override public ClientModel rollNumber(int gameID, PlayerIndex playerIndex, int number)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "basicGame", ".json");
    }

    @Override public ClientModel robPlayer(int gameID, PlayerIndex playerIndex, PlayerIndex victim,
            HexLocation location)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "basicGame", ".json");

    }

    @Override public ClientModel finishTurn(int gameID, PlayerIndex playerIndex)
    {
        ClientModel model = new ClientModel();
        switch (playerIndex)
        {
        case PLAYER_0:
            model = FileUtils.getModelFromFile("sample/mockServerJson", "player1Turn", ".json");
            break;
        case PLAYER_1:
            model = FileUtils.getModelFromFile("sample/mockServerJson", "player2Turn", ".json");
            break;
        case PLAYER_2:
            model = FileUtils.getModelFromFile("sample/mockServerJson", "player3Turn", ".json");
            break;
        case PLAYER_3:
            model = FileUtils.getModelFromFile("sample/mockServerJson", "player0Turn", ".json");
            break;
        }
        return model;
    }

    @Override public ClientModel buyDevCard(int gameID, PlayerIndex playerIndex)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "playersHaveCards", ".json");
    }

    @Override public ClientModel yearOfPlenty(int gameID, PlayerIndex playerIndex, ResourceType resource1,
            ResourceType resource2)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "playersHaveCards", ".json");
    }

    @Override public ClientModel roadBuilding(int gameID, PlayerIndex playerIndex, EdgeLocation spot1,
            EdgeLocation spot2)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "playersHaveCards", ".json");
    }

    @Override public ClientModel soldier(int gameID, PlayerIndex playerIndex, PlayerIndex victimIndex,
            HexLocation location)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "playersHaveCards", ".json");
    }

    @Override public ClientModel monopoly(int gameID, PlayerIndex playerIndex, ResourceType resource)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "playersHaveCards", ".json");
    }

    @Override public ClientModel monument(int gameID, PlayerIndex playerIndex)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "playersHaveCards", ".json");
    }

    @Override public ClientModel buildRoad(int gameID, PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "advancedGame", ".json");
    }

    @Override public ClientModel buildSettlement(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation,
            boolean free)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "advancedGame", ".json");
    }

    @Override public ClientModel buildCity(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "advancedGame", ".json");
    }

    @Override public ClientModel offerTrade(int gameID, PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "tradeAvailable", ".json");
    }

    @Override public ClientModel acceptTrade(int gameID, PlayerIndex playerIndex, boolean willAccept)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "tradeAvailable", ".json");
    }

    @Override public ClientModel maritimeTrade(int gameID, PlayerIndex playerIndex, TradeRatio ratio,
            ResourceType inputResource, ResourceType outputResource)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "tradeAvailable", ".json");
    }

    @Override public ClientModel discardCards(int gameID, PlayerIndex playerIndex, Resources discardedCards)
    {
        return FileUtils.getModelFromFile("sample/mockServerJson", "basicGameTurn", ".json");
    }

    @Override public User signInUser(Credentials credentials) throws InvalidCredentialsException
    {
        throw new InvalidCredentialsException("Failed to login - bad username or password.");
    }

    @Override public User registerUser(Credentials credentials)
            throws InvalidCredentialsException, ExistingRegistrationException
    {
        throw new ExistingRegistrationException("Failed to register - someone already has that username.");
    }
}
