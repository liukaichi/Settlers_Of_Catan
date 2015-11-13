package server.facade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.utils.BufferedReaderParser;
import server.manager.User;
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
 * Uses dependency injection to allow this object to be used as the facade for testing purposes.
 */
public class MockServerFacade extends AbstractServerFacade
{
	private final static String modelFilePath = "sample/mockServerJsons/";

    public MockServerFacade()
    {

    }

	private ClientModel getModelFromFile(String fileName)
	{
		File file = new File(modelFilePath + fileName + "");
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
			e.printStackTrace();
		}
        
		return model;
	}
	
    @Override public ClientModel getGameState(int version)
    {
        if (version == -1 || version < 50)
            return getModelFromFile("basicGameTurn");
        else if (version >= 50 && version < 100)
            return getModelFromFile("playersHaveCards");
        else if (version >= 100)
            return getModelFromFile("advancedGame");
        else
            return null;

    }

    @Override public void addAI(AIType aiType, int gameID)
    {
        //TODO how on earth do we have AI's?
    }

    @Override public ListAIResponse listAI()
    {
        List<AIType> types = new ArrayList<>();
        types.add(AIType.LARGEST_ARMY);
        return new ListAIResponse(types);
    }

    @Override public ListGamesResponse listGames()
    {
        List<GameInfo> games = new ArrayList<>();
        for (int i = 1; i < 3; ++i)
        {
            GameInfo game = new GameInfo(i, "Game_" + i);
            game.addPlayer(new PlayerInfo(0,"Cache", CatanColor.YELLOW));
            game.addPlayer(new PlayerInfo(1,"Amanda", CatanColor.BLUE));
            game.addPlayer(new PlayerInfo(2,"Justin", CatanColor.ORANGE));
            game.addPlayer(new PlayerInfo(3,"David", CatanColor.BROWN));
            games.add(game);
        }
        return new ListGamesResponse(games);
    }

    @Override public void joinGame(int playerID, int gameID, CatanColor color)
    {

    }

    @Override public CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
    {
        return null;
    }

    @Override public ClientModel sendChat(PlayerIndex playerIndex, String content)
    {
    	
        return getModelFromFile("sendChat");
    }

    @Override public ClientModel rollNumber(PlayerIndex playerIndex, int number)
    {
        return getModelFromFile("basicGame");
    }

    @Override public ClientModel robPlayer(PlayerIndex playerIndex, PlayerIndex victim, HexLocation location)
    {
        return getModelFromFile("basicGame");
    }

    @Override public ClientModel finishTurn(PlayerIndex playerIndex)
    {
        ClientModel model = new ClientModel();
        switch (playerIndex)
        {
        case PLAYER_0:
            model = getModelFromFile("player1Turn");
            break;
        case PLAYER_1:
            model = getModelFromFile("player2Turn");
            break;
        case PLAYER_2:
            model = getModelFromFile("player3Turn");
            break;
        case PLAYER_3:
            model = getModelFromFile("player0Turn");
            break;
        }
        return model;
    }

    @Override public ClientModel buyDevCard(PlayerIndex playerIndex)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override public ClientModel yearOfPlenty(PlayerIndex playerIndex, ResourceType resource1, ResourceType resource2)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override public ClientModel roadBuilding(PlayerIndex playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override public ClientModel soldier(PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override public ClientModel monopoly(PlayerIndex playerIndex, ResourceType resource)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override public ClientModel monument(PlayerIndex playerIndex)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override public ClientModel buildRoad(PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        return getModelFromFile("advancedGame");
    }

    @Override public ClientModel buildSettlement(PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free)
    {
        return getModelFromFile("advancedGame");
    }

    @Override public ClientModel buildCity(PlayerIndex playerIndex, VertexLocation vertexLocation)
    {
        return getModelFromFile("advancedGame");
    }

    @Override public ClientModel offerTrade(PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return getModelFromFile("tradeAvailable");
    }

    @Override public ClientModel acceptTrade(PlayerIndex playerIndex, boolean willAccept)
    {
        return getModelFromFile("tradeAvailable");
    }

    @Override public ClientModel maritimeTrade(PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
            ResourceType outputResource)
    {
        return getModelFromFile("tradeAvailable");
    }

    @Override public ClientModel discardCards(PlayerIndex playerIndex, Resources discardedCards)
    {
        return getModelFromFile("basicGameTurn");
    }

    @Override public User signInUser(Credentials credentials) throws SignInException
    {

        throw new SignInException("Failed to login - bad username or password.");
    }

    @Override public User registerUser(Credentials credentials) throws SignInException
    {
        throw new SignInException( "Failed to register - someone already has that username.");
    }
}
