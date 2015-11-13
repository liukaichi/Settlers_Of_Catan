package server.facade;

import client.data.PlayerInfo;
import server.ServerModel;
import server.manager.User;
import shared.communication.CreateGameResponse;
import shared.communication.Credentials;
import shared.communication.ListAIResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.*;
import shared.definitions.exceptions.SignInException;
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

    @Override public void addAI(AIType aiType, int gameID)
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

    @Override
    public void joinGame(PlayerInfo player, int gameID, CatanColor color)
    {

    }

    @Override
    public CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        return null;
    }

    @Override public ClientModel sendChat(PlayerIndex sender, String content)
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
    	model.finishTurn(); 
        return null;
    }

    @Override public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        //model.buyDevCard();
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

    @Override public User signInUser(Credentials credentials) throws SignInException
    {
        throw new SignInException("Failed to login - bad username or password.");
    }

    @Override public User registerUser(Credentials credentials) throws SignInException
    {
        throw new SignInException("Failed to register - someone already has that username.");
    }
}
