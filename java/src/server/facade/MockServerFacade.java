package server.facade;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.utils.BufferedReaderParser;
import shared.communication.CreateGameResponse;
import shared.definitions.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.bank.resource.Resources;
import shared.model.player.TradeOffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Uses dependency injection to allow this object to be used as the facade for testing purposes.
 */
public class MockServerFacade extends AbstractServerFacade
{
    private final static String modelFilePath = "sample/mockServerJsons/";

    private List<PlayerInfo> aiPlayers, validUsers;

    private List<GameInfo> games;

    private GameInfo joinedGame;

    public MockServerFacade()
    {
        games = new ArrayList<>();

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
        } catch (FileNotFoundException e)
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
        int joinedGameSize = joinedGame.getPlayers().size();
        if (joinedGameSize < 4)
        {
            this.joinedGame.addPlayer(aiPlayers.get(joinedGameSize - 1));
        }
    }


    @Override public void joinGame(PlayerInfo player, int gameID, CatanColor color)
    {
        for (GameInfo game : games)
        {
            if (game.getId() == gameID)
            {
                if (game.getPlayers().size() < 4)
                {
                    game.addPlayer(player);
                    this.joinedGame = game;
                }
            }
        }
    }

    @Override public CreateGameResponse createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts,
            String name)
    {
        int newGameID = games.size() + 1;
        games.add(new GameInfo(newGameID, name));
        return new CreateGameResponse(newGameID, name);
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

    @Override
    public ClientModel soldier(int gameID, PlayerIndex playerIndex, PlayerIndex victimIndex, HexLocation location)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override
    public ClientModel monopoly(int gameID, PlayerIndex playerIndex, ResourceType resource)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override
    public ClientModel monument(int gameID, PlayerIndex playerIndex)
    {
        return getModelFromFile("playersHaveCards");
    }

    @Override
    public ClientModel buildRoad(int gameID, PlayerIndex playerIndex, EdgeLocation roadLocation, boolean free)
    {
        return getModelFromFile("advancedGame");
    }

    @Override
    public ClientModel buildSettlement(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation, boolean free)
    {
        return getModelFromFile("advancedGame");
    }

    @Override
    public ClientModel buildCity(int gameID, PlayerIndex playerIndex, VertexLocation vertexLocation)
    {
        return getModelFromFile("advancedGame");
    }

    @Override
    public ClientModel offerTrade(int gameID, PlayerIndex playerIndex, TradeOffer offer, PlayerIndex receiver)
    {
        return getModelFromFile("tradeAvailable");
    }

    @Override
    public ClientModel acceptTrade(int gameID, PlayerIndex playerIndex, boolean willAccept)
    {
        return getModelFromFile("tradeAvailable");
    }

    @Override
    public ClientModel maritimeTrade(int gameID, PlayerIndex playerIndex, TradeRatio ratio, ResourceType inputResource,
                                     ResourceType outputResource)
    {
        return getModelFromFile("tradeAvailable");
    }

    @Override
    public ClientModel discardCards(int gameID, PlayerIndex playerIndex, Resources discardedCards)
    {
        return getModelFromFile("basicGameTurn");
    }
}
