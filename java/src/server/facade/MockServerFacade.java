package server.facade;

import shared.communication.CreateGameResponse;
import shared.communication.Credentials;
import shared.communication.ListAIResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.SignInException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.model.ClientModel;

/**
 * Created by cstaheli on 11/4/2015.
 */
public class MockServerFacade extends AbstractServerFacade
{
    @Override public ClientModel getGameState(int version)
    {
        return null;
    }

    @Override public void addAI(AIType aiType)
    {

    }

    @Override public ListAIResponse listAI()
    {
        return null;
    }

    @Override public ListGamesResponse listGames()
    {
        return null;
    }

    @Override public void joinGame(PlayerIndex player, int gameID, CatanColor color)
    {

    }

    @Override public CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        return null;
    }

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

    @Override public ClientModel buildSettlement()
    {
        return null;
    }

    @Override public ClientModel buildCity()
    {
        return null;
    }

    @Override public ClientModel offerTrade()
    {
        return null;
    }

    @Override public ClientModel acceptTrade()
    {
        return null;
    }

    @Override public ClientModel maritimeTrade()
    {
        return null;
    }

    @Override public ClientModel discardCards()
    {
        return null;
    }

    @Override public void signInUser(Credentials credentials) throws SignInException
    {

    }

    @Override public void registerUser(Credentials credentials) throws SignInException
    {

    }
}
