package server.proxy;

import java.util.List;
import java.util.logging.Level;

import client.data.GameInfo;
import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.model.ClientModel;
import shared.model.player.Credentials;

/**
 * ServerProxy is used in Dependency injection, along with MockProxy, to return
 * specific results from the server. <br>
 * <br>
 * the ServerProxy actually communicates with the server and gets the actual
 * server responses back, which it sends on to the Poller, or the Facade as
 * needed.
 * 
 * @author cstaheli
 *
 */
public class ServerProxy implements IProxy
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
    public List<GameInfo> listGames()
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
    public void joinGame(JoinGameRequest joinGameRequest)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void saveGame(SaveGameRequest saveGameRequest)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void loadGame(LoadGameRequest loadGameRequest)
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
    public List<Command> getCommands()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClientModel postCommands(List<Command> commands)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<AIType> listAI()
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

    /**
     * Takes an object and turns it into JSON.
     * 
     * @param object
     *        the object to serialize
     * @return The Formatted JSON from the object.
     */
    private String serializeClass(Object object)
    {
        return null;
    }

    /**
     * Takes JSON and turns it into an object
     * 
     * @param json
     *        the JSON to deserialize.
     * @return the Object from the JSON.
     */
    private Object deserializeJSON(String json)
    {
        return null;
    }

}
