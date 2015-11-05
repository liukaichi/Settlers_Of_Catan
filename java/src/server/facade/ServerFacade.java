package server.facade;

import client.data.GameInfo;
import shared.communication.Credentials;
import shared.definitions.exceptions.SignInException;
import shared.model.ClientModel;

import java.util.List;

/**
 * Created by cstaheli on 11/4/2015.
 */
public class ServerFacade implements IServerFacade
{
    private ClientModel model;

    @Override public ClientModel getGameState()
    {
        return null;
    }

    @Override public void addAI()
    {

    }

    @Override public void listAI()
    {

    }

    @Override public List<GameInfo> listGames()
    {
        return null;
    }

    @Override public void joinGame()
    {

    }

    @Override public void createGame()
    {

    }

    @Override public ClientModel sendChat()
    {
        return null;
    }

    @Override public ClientModel rollNumber()
    {
        return null;
    }

    @Override public ClientModel robPlayer()
    {
        return null;
    }

    @Override public ClientModel finishTurn()
    {
        return null;
    }

    @Override public ClientModel buyDevCard()
    {
        model.buyDevCard();
        return null;
    }

    @Override public ClientModel yearOfPlenty()
    {
        return null;
    }

    @Override public ClientModel roadBuilding()
    {
        return null;
    }

    @Override public ClientModel soldier()
    {
        return null;
    }

    @Override public ClientModel monopoly()
    {
        return null;
    }

    @Override public ClientModel monument()
    {
        return null;
    }

    @Override public ClientModel buildRoad()
    {
        model.buildRoad(null, null);
        return null;
    }

    @Override public ClientModel buildSettlement()
    {
        model.buildSettlement(null, null);
        return null;
    }

    @Override public ClientModel buildCity()
    {
        model.buildCity(null, null);
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
