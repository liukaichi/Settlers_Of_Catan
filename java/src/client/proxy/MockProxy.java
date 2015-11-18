package client.proxy;

import client.data.PlayerInfo;
import client.facade.ClientFacade;
import client.utils.BufferedReaderParser;
import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.AIType;
import shared.definitions.exceptions.GameQueryException;
import shared.model.ClientModel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * MockProxy is used in Dependency injection, along with ServerProxy, to return
 * specific results from the server. <br>
 * <br>
 * the MockProxy does not actually communicate with the server. Instead, it is
 * hard-coded to return specific results for specific actions. Used mostly for
 * testing. This acts identically to the ServerProxy, but its server
 * interactions are not real.
 *
 * @author cstaheli
 */
public class MockProxy implements IProxy
{
    String jsonFileDir = "sample" + File.separator + "mockproxy" + File.separator;
    ClientModel serverModel;

    public MockProxy()
    {
        try
        {
            serverModel = new ClientModel(new String(Files.readAllBytes(Paths.get("sample/modelWithTradeOffer.json"))));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        serverModel.setVersion(1);
        ClientFacade.getInstance().setModel(serverModel);

    }

    public ClientModel getServerModel()
    {
        return serverModel;
    }

    @Override public PlayerInfo userLogin(Credentials credentials)
    {
        PlayerInfo player = new PlayerInfo();
        player.setId(3);
        player.setName(credentials.getUsername());
        return player;
    }

    @Override public PlayerInfo userRegister(Credentials credentials)
    {
        PlayerInfo player = new PlayerInfo();
        player.setId(3);
        player.setName(credentials.getUsername());
        return player;
    }

    @Override public ListGamesResponse listGames()
    {
        String json = "";

        File file = new File(jsonFileDir + "listGamesResponse.json");
        try
        {
            json = BufferedReaderParser.parse(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        ListGamesResponse listGamesResponse = new ListGamesResponse(json);

        return listGamesResponse;
    }

    @Override public CreateGameResponse createGame(CreateGameRequest createGameRequest)
    {
        String json = "";
        File file = new File(jsonFileDir + "createGameResponse.json");
        try
        {
            json = BufferedReaderParser.parse(new BufferedReader(new FileReader(file)));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        CreateGameResponse createGameResponse = new CreateGameResponse(json);
        return createGameResponse;
    }

    @Override public void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException
    {

    }

    @Override public ClientModel getGameState(int versionNumber)
    {
        if (versionNumber == serverModel.getVersion())
        {
            return null;
        }
        return serverModel;
    }

    @Override public ListAIResponse listAI()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void addAI(AIType aiType)
    {
        // TODO Auto-generated method stub

    }

    @Override public ClientModel sendChat(SendChatCommand sendChat)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel rollNumber(RollNumberCommand rollNumber)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel acceptTrade(AcceptTradeCommand acceptTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel discardCards(DiscardCardsCommand discardCards)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel buildRoad(BuildRoadCommand buildRoad)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel buildSettlement(BuildSettlementCommand buildSettlement)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel buildCity(BuildCityCommand buildCity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel offerTrade(OfferTradeCommand offerTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel maritimeTrade(MaritimeTradeCommand maritimeTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel robPlayer(RobPlayerCommand robPlayer)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel finishTurn(FinishTurnCommand finishTurn)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel buyDevCard(BuyDevCardCommand buyDevCard)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel soldier(SoldierCommand soldier)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel yearOfPlenty(Year_of_PlentyCommand yearOfPlenty)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel roadBuilding(Road_BuildingCommand roadBuilding)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel monopoly(MonopolyCommand monopoly)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public ClientModel monument(MonumentCommand monument)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
