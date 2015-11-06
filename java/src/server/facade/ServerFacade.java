package server.facade;

import client.data.GameInfo;
import shared.communication.Credentials;
import shared.definitions.exceptions.SignInException;
import shared.model.ClientModel;

import java.util.List;

/**
 * Created by cstaheli on 11/4/2015.
 */
public class ServerFacade extends AbstractServerFacade
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

    /**
     * Calls finish turn from the model to progress to next state.
     * @return the updated ClientModel
     */
    @Override public ClientModel finishTurn()
    {
    	model.finishTurn(); 
        return null;
    }

    /**
     * Add a random Development Card to the client player's PlayerBank
     * Pop said Development Card from the DevCardDeck
     * Display bought Development Card message.
     * @return the updated ClientModel
     */
    @Override public ClientModel buyDevCard()
    {
        model.buyDevCard();
        return null;
    }

    /**
     * Increase up to two resources to the client player's PlayerBank
     * Decrease the appropriate resources from the game bank.
     * Display resources received message.
     * @return the updated ClientModel
     */
    @Override public ClientModel yearOfPlenty()
    {
        return null;
    }

    /**
     * Increase the client player's roads of AmountType.BUILT by 2
     * Decrease the client player's roads of AmountType.AVAILABLE by 2
     * Update the map with the 2 new roads
     * Display 2 roads built message
     * @return the updated ClientModel
     */
    @Override public ClientModel roadBuilding()
    {
        return null;
    }

    /**
     * Update the map with the new Robber location.
     * Increase the client player's PlayerBank with the robbed resource.
     * Decrease the robber player's PlayerBank with the robber resource.
     * Display robbed message.
     * @return the updated ClientModel
     */
    @Override public ClientModel soldier()
    {
        return null;
    }

    /**
     * Updates the client player's PlayerBank with the resources gained.
     * Updates all other players' PlayerBank with the resources lost.
     * @return the updated ClientModel
     */
    @Override public ClientModel monopoly()
    {
        return null;
    }

    /**
     * Increase the client player's Victory Points by 1
     * @return the updated ClientModel
     */
    @Override public ClientModel monument()
    {
        return null;
    }

    /**
     * Increase the client player's road of AmountType.BUILT by 1.
     * Decrease the client player's road of AmountType.AVAILABLE by 1.
     * Update the map with a new road.
     * @return the updated ClientModel
     */
    @Override public ClientModel buildRoad()
    {
        model.buildRoad(null, null);
        return null;
    }

    /**
     * Increase the client player's settlement of AmountType.BUILT by 1.
     * Decrease the client player's settlement of AmountType.AVAILABLE by 1.
     * Update the map with a new settlement
     * @return the updated ClientModel
     */
    @Override public ClientModel buildSettlement()
    {
        model.buildSettlement(null, null);
        return null;
    }

    /**
     * Increase the client player's city of AmountType.BUILT by 1.
     * Decrease the client player's city of AmountType.AVAILABLE by 1.
     * Update the map with a new city
     * @return the updated ClientModel
     */
    @Override public ClientModel buildCity()
    {
        model.buildCity(null, null);
        return null;
    }

    /**
     * Update model with a new TradeOffer object
     * Player to be traded is asked to trade.
     * @return the updated ClientModel
     */
    @Override public ClientModel offerTrade()
    {
        return null;
    }

    /**
     * Update model and remove the TradeOffer
     * Update both players' PlayerBank depending on whether
     * the TradeOffer is accepted or not.
     * @return the updated ClientModel
     */
    @Override public ClientModel acceptTrade()
    {
        return null;
    }

    /**
     * The client player trades with the game bank based on the
     * trade ratio that is available to the player
     * @return the updated ClientModel
     */
    @Override public ClientModel maritimeTrade()
    {
        return null;
    }

    /**
     * The client player discards a number of each resource.
     * The resources are returned to the game bank
     * @return the updated CLientModel
     */
    @Override public ClientModel discardCards()
    {
        return null;
    }

    /**
     * Signs in the player with the given credentials
     * @param credentials
     * @throws SignInException
     */
    @Override public void signInUser(Credentials credentials) throws SignInException
    {

    }

    /**
     * Registers a new player with the given credentials
     * @param credentials
     * @throws SignInException
     */
    @Override public void registerUser(Credentials credentials) throws SignInException
    {

    }
}
