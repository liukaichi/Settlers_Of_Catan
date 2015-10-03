package facades;

import client.data.GameInfo;
import client.data.PlayerInfo;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.proxy.MockProxy;
import shared.communication.CreateGameRequest;
import shared.communication.CreateGameResponse;
import shared.communication.ListGamesResponse;
import shared.model.player.Player;

import java.util.List;

/**
 * MockProxy Tester.
 *
 * @author <liukaichi>
 * @version 1.0
 * @since <pre>Oct 1, 2015</pre>
 */
public class MockProxyTester extends TestCase
{
    MockProxy proxy = new MockProxy();

    @Before
    public void before() throws Exception
    {

    }

    @After
    public void after() throws Exception
    {
    }

    /**
     * Method: getServerModel()
     */
    @Test
    public void testGetServerModel() throws Exception
    {
        //TODO: Test goes here...

    }

    /**
     * Method: userLogin(Credentials credentials)
     */
    @Test
    public void testUserLogin() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: userRegister(Credentials credentials)
     */
    @Test
    public void testUserRegister() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: changeLogLevel(Level level)
     */
    @Test
    public void testChangeLogLevel() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: listGames()
     */
    @Test
    public void testListGames() throws Exception
    {
        ListGamesResponse listGamesResponse = proxy.listGames();
        List<GameInfo> games = listGamesResponse.getGames();
        for (GameInfo info : games){
            String title = info.getTitle();
            assertTrue(title != null && !title.equals(""));
            List<Player> players = info.getPlayers();
            assertTrue(players != null);
        }

    }

    /**
     * Method: createGame(CreateGameRequest createGameRequest)
     */
    @Test
    public void testCreateGame() throws Exception
    {
        CreateGameRequest gameRequest = new CreateGameRequest(true, true, true, "mockGame");
        CreateGameResponse gameResponse = proxy.createGame(gameRequest);
        assertEquals(3, gameResponse.getGameID());
        assertEquals("mockGame",gameResponse.getGameInfo().getTitle());
    }

    /**
     * Method: joinGame(JoinGameRequest joinGameRequest)
     */
    @Test
    public void testJoinGame() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: saveGame(SaveGameRequest saveGameRequest)
     */
    @Test
    public void testSaveGame() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: loadGame(LoadGameRequest loadGameRequest)
     */
    @Test
    public void testLoadGame() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: getGameState(int versionNumber)
     */
    @Test
    public void testGetGameState() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: resetGame()
     */
    @Test
    public void testResetGame() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: getCommands()
     */
    @Test
    public void testGetCommands() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: postCommands(List<MoveCommand> commands)
     */
    @Test
    public void testPostCommands() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: listAI()
     */
    @Test
    public void testListAI() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: addAI(AIType aiType)
     */
    @Test
    public void testAddAI() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: sendChat(SendChatCommand sendChat)
     */
    @Test
    public void testSendChat() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: rollNumber(RollNumberCommand rollNumber)
     */
    @Test
    public void testRollNumber() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: acceptTrade(AcceptTradeCommand acceptTrade)
     */
    @Test
    public void testAcceptTrade() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: discardCards(DiscardCardsCommand discardCards)
     */
    @Test
    public void testDiscardCards() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: buildRoad(BuildRoadCommand buildRoad)
     */
    @Test
    public void testBuildRoad() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: buildSettlement(BuildSettlementCommand buildSettlement)
     */
    @Test
    public void testBuildSettlement() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: buildCity(BuildCityCommand buildCity)
     */
    @Test
    public void testBuildCity() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: offerTrade(OfferTradeCommand offerTrade)
     */
    @Test
    public void testOfferTrade() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: maritimeTrade(MaritimeTradeCommand maritimeTrade)
     */
    @Test
    public void testMaritimeTrade() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: robPlayer(RobPlayerCommand robPlayer)
     */
    @Test
    public void testRobPlayer() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: finishTurn(FinishTurnCommand finishTurn)
     */
    @Test
    public void testFinishTurn() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: buyDevCard(BuyDevCardCommand buyDevCard)
     */
    @Test
    public void testBuyDevCard() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: soldier(SoldierCommand soldier)
     */
    @Test
    public void testSoldier() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: yearOfPlenty(YearOfPlentyCommand yearOfPlenty)
     */
    @Test
    public void testYearOfPlenty() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: roadBuilding(RoadBuildingCommand roadBuilding)
     */
    @Test
    public void testRoadBuilding() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: monopoly(MonopolyCommand monopoly)
     */
    @Test
    public void testMonopoly() throws Exception
    {
        //TODO: Test goes here...
    }

    /**
     * Method: monument(MonumentCommand monument)
     */
    @Test
    public void testMonument() throws Exception
    {
        //TODO: Test goes here...
    }

} 
