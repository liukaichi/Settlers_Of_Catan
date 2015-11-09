package server.facade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import client.utils.BufferedReaderParser;
import shared.communication.CreateGameResponse;
import shared.communication.Credentials;
import shared.communication.ListAIResponse;
import shared.communication.ListGamesResponse;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.TradeRatio;
import shared.definitions.exceptions.SignInException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

/**
 * Created by cstaheli on 11/4/2015.
 */
public class MockServerFacade extends AbstractServerFacade
{
	
	private ClientModel getModelFromFile(String fileName)
	{
		File file = new File(fileName);
   	 	BufferedReader reader;
   	 	ClientModel model = new ClientModel();
		try 
		{
			reader = new BufferedReader(new FileReader(file));
			String json = BufferedReaderParser.parse(reader);
	         model = new ClientModel(json);
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return model;
	}
	
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
    	
        return getModelFromFile("sample/mockServerJsons/sendChat.json");
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

    @Override public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free)
    {
        return null;
    }

    @Override public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation vertexLocation)
    {
        return null;
    }

    @Override public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return null;
    }

    @Override public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept)
    {
        return null;
    }

    @Override public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource)
    {
        return null;
    }

    @Override public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        return null;
    }

    @Override public void signInUser(Credentials credentials) throws SignInException
    {
        throw new SignInException("Failed to login - bad username or password.");
    }

    @Override public void registerUser(Credentials credentials) throws SignInException
    {
        throw new SignInException( "Failed to register - someone already has that username.");
    }
}
