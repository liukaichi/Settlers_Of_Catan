package server.proxy;


import client.data.GameInfo;
import client.facade.ClientFacade;
import client.utils.BufferedReaderParser;
import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.AIType;
import shared.definitions.exceptions.GameQueryException;
import shared.model.ClientModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;

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
 *
 */
public class MockProxy implements IProxy
{
    String jsonFileDir = "sample" + File.separator + "mockproxy" + File.separator;
    ClientModel serverModel;
    public MockProxy(){
        serverModel = new ClientModel();
        serverModel.setVersion(1);
        serverModel.setGameInfo(new GameInfo(1, "gameTitle"));
        ClientFacade.getInstance().setModel(serverModel);

    }

    public ClientModel getServerModel(){
        return serverModel;
    }

    @Override
    public void userLogin(Credentials credentials)
    {
    }

    @Override
    public void userRegister(Credentials credentials)
    {
    }

    @Override
    public void changeLogLevel(Level level)
    {

    }

    @Override
    public ListGamesResponse listGames()
    {
        String json = "";

        File file = new File(jsonFileDir + "listGamesResponse.json");
        try
        {
            json = BufferedReaderParser.parse(new BufferedReader(new FileReader(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        ListGamesResponse listGamesResponse = new ListGamesResponse(json);


        return listGamesResponse;
    }

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest)
    {
        String json = "";
        File file = new File(jsonFileDir + "createGameResponse.json");
        try
        {
            json = BufferedReaderParser.parse(new BufferedReader(new FileReader(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        CreateGameResponse createGameResponse = new CreateGameResponse(json);
        return createGameResponse;
    }

    @Override
    public void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException
    {

    }

    @Override
    public void saveGame(SaveGameRequest saveGameRequest) throws GameQueryException
    {
    }

    @Override
    public void loadGame(LoadGameRequest loadGameRequest) throws GameQueryException
    {
    }

    @Override
    public ClientModel getGameState(int versionNumber)
    {
        if (versionNumber == serverModel.getVersion()){
            return null;
        }
        return serverModel;
    }

    @Override
    public ClientModel resetGame()
    {
        String json = "";
        File file = new File(jsonFileDir + "resetGame.json");
        try
        {
            json = BufferedReaderParser.parse(new BufferedReader(new FileReader(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        ClientModel clientModel = new ClientModel(json);
        return clientModel;
    }

    @Override
    public GetCommandsResponse getCommands()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel postCommands(PostCommandsRequest commands)
    {
        //shouldn't need to be done. Used only when debugging the the server running.
        return null;
    }

    @Override
    public ListAIResponse listAI()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addAI(AIType aiType)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public ClientModel sendChat(SendChatCommand sendChat)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel rollNumber(RollNumberCommand rollNumber)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel acceptTrade(AcceptTradeCommand acceptTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel discardCards(DiscardCardsCommand discardCards)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buildRoad(BuildRoadCommand buildRoad)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buildSettlement(BuildSettlementCommand buildSettlement)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buildCity(BuildCityCommand buildCity)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel offerTrade(OfferTradeCommand offerTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel maritimeTrade(MaritimeTradeCommand maritimeTrade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel robPlayer(RobPlayerCommand robPlayer)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel finishTurn(FinishTurnCommand finishTurn)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel buyDevCard(BuyDevCardCommand buyDevCard)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel soldier(SoldierCommand soldier)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel yearOfPlenty(YearOfPlentyCommand yearOfPlenty)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel roadBuilding(RoadBuildingCommand roadBuilding)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel monopoly(MonopolyCommand monopoly)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel monument(MonumentCommand monument)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
