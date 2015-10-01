package server.proxy;

import java.util.List;
import java.util.logging.Level;

import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.AIType;
import shared.definitions.exceptions.GameQueryException;
import shared.model.ClientModel;

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

    @Override
    public void userLogin(Credentials credentials)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void userRegister(Credentials credentials)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeLogLevel(Level level)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public ListGamesResponse listGames()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void joinGame(JoinGameRequest joinGameRequest) throws GameQueryException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveGame(SaveGameRequest saveGameRequest) throws GameQueryException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadGame(LoadGameRequest loadGameRequest) throws GameQueryException
    {
        // TODO Auto-generated method stub

    }

    @Override
    public ClientModel getGameState(int versionNumber)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel resetGame()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MoveCommand> getCommands()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel postCommands(List<MoveCommand> commands)
    {
        // TODO Auto-generated method stub
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
