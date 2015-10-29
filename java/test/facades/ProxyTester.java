/**
 * 
 */
package facades;

import static org.junit.Assert.*;

import java.util.List;
import java.util.logging.Level;

import org.junit.*;

import client.data.*;
import server.proxy.*;
import shared.communication.*;
import shared.communication.moveCommands.*;
import shared.definitions.*;
import shared.definitions.exceptions.*;
import shared.locations.*;
import shared.model.ClientModel;
import shared.model.message.Chat;
import shared.model.message.MessageLine;

/**
 * @author cstaheli
 *
 */
public class ProxyTester
{
    private ClientModel expectedModel, testingModel;
    private IProxy proxy;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {

        // Class[] parameterTypes = new Class[1];
        // parameterTypes[1] = Credentials.class;
        // Method method = ServerProxy.class.getMethod("userLogin",
        // parameterTypes);
        // method.setAccessible(true);
        // Credentials creds = new Credentials();
        // ClientModel model = (ClientModel) method.invoke("ServerProxy",
        // creds);

    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        expectedModel = new ClientModel();
        testingModel = new ClientModel();
        proxy = new ServerProxy();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        expectedModel = null;
        testingModel = null;
        proxy = null;
    }

    // @Test
    // public void testSerialize()
    // {
    // String methodName = "serializeClass";
    // try
    // {
    // Method serializeClass = ServerProxy.class.getMethod(methodName,
    // Object.class);
    //
    // // Serializing credentials
    // Credentials creds = new Credentials("cache", "password");
    //
    // String returned = (String) serializeClass.invoke(proxy, creds);
    // String expected = "{ \"username\": \"cache\", \"password\": \"password\"
    // }";
    // assertEquals(returned, expected);
    //
    // // Serializing Level - unneeded
    // // Level level =
    //
    // // Serializing CreateGameRequest
    // CreateGameRequest request = new CreateGameRequest(false, false, false,
    // "game");
    // returned = (String) serializeClass.invoke(proxy, request);
    // expected = "{ \"randomTiles\": \"false\", \"randomNumbers\": \"false\",
    // \"randomPorts\": \"false\", \"name\": \"game\" }";
    // assertEquals(returned, expected);
    //
    // // Serializing JoinGameRequest
    //
    // // Serializing SaveGameRequest
    // } catch (Exception e)
    // {
    // fail(e.toString());
    // }
    // startGame("");
    // }
    //
    // @Test
    // public void testDeserialize()
    // {
    // startGame("");
    // }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#userLogin(shared.communication.Credentials)}
     * .
     */
    @Test
    public void testUserLogin()
    {
        Credentials credentials = null;
        try
        {
            credentials = new Credentials("login", "login");
        } catch (SignInException e)
        {
            e.printStackTrace();
        }
        try
        {
            proxy.userLogin(credentials);
            fail("Shouldn't have reached this.");
        }
        catch (SignInException e1)
        {
            assertTrue(true);
        }
        try
        {
            proxy.userRegister(credentials);
            assertTrue(true);
            proxy.userLogin(credentials);
            assertTrue(true);
        }
        catch (SignInException e)
        {
            fail("Login failed");
        }
        try
        {
            proxy.userLogin(credentials);
        }
        catch (SignInException e)
        {
            fail("Shouldn't have reached this.");
        }
    }

    public void logUserIn()
    {
        try
        {
            proxy.userLogin(new Credentials("Sam", "sam"));
        }
        catch (SignInException e)
        {
            fail(e.getMessage());
        }
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#userRegister(shared.communication.Credentials)}
     * .
     */
    @Test
    public void testUserRegister()
    {
        Credentials credentials = null;
        try
        {
            credentials = new Credentials("userReg", "register");
        } catch (SignInException e)
        {
            e.printStackTrace();
        }
        try
        {
            proxy.userRegister(credentials);
            assertTrue(true);
        }
        catch (SignInException e)
        {
            fail("Login failed");
        }
        try
        {
            proxy.userRegister(credentials);
            fail("Shouldn't have reached this.");
        }
        catch (SignInException e)
        {
            assertTrue(true);
        }
        try
        {
            credentials = new Credentials("user1", "pass");
        } catch (SignInException e)
        {
            e.printStackTrace();
        }
        try
        {
            proxy.userRegister(credentials);
            assertTrue(true);
            proxy.userRegister(new Credentials("mel", "blanc"));
            assertTrue(true);
        }
        catch (SignInException e)
        {
            fail("Registration failed");
        }
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#changeLogLevel(java.util.logging.Level)}.
     */
    @Test
    public void testChangeLogLevel()
    {
        startGame("logLevel");
        proxy.changeLogLevel(Level.ALL);
    }

    /**
     * Test method for {@link server.proxy.ServerProxy#listGames()}.
     */
    @Test
    public void testListGames()
    {
        logUserIn();

        List<GameInfo> games = proxy.listGames().getGames();
        int size = games.size();
        assertTrue(games.size() > 0);
        proxy.createGame(new CreateGameRequest(true, true, true, "list1"));
        games = proxy.listGames().getGames();
        assertTrue(games.size() > size);
        boolean found = false;
        for (int i = 0; i < games.size(); ++i)
        {
            GameInfo info = games.get(i);
            if (info.getTitle().equals("list1"))
            {
                found = true;
            }
        }
        assertTrue(found);

        proxy.createGame(new CreateGameRequest(true, true, true, "list1"));
        int count = 0;
        games = proxy.listGames().getGames();
        for (int i = 0; i < games.size(); ++i)
        {
            GameInfo info = games.get(i);
            if (info.getTitle().equals("list1"))
            {
                ++count;
            }
        }
        assertTrue(count == 2);

        found = false;
        proxy.createGame(new CreateGameRequest(true, true, true, "list2"));
        games = proxy.listGames().getGames();
        for (int i = 0; i < games.size(); ++i)
        {
            GameInfo info = games.get(i);
            if (info.getTitle().equals("list2"))
            {
                found = true;
            }
        }
        assertTrue(found);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#createGame(shared.communication.CreateGameRequest)}
     * .
     */
    @Test
    public void testCreateGame()
    {
        logUserIn();

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "create1"));
        GameInfo info = response.getGameInfo();
        // List<PlayerInfo> players = info.getPlayers();
        // assertTrue(players.size() <= 4);
        assertTrue(info.getTitle().equals("create1"));
        int id = info.getId();
        List<GameInfo> games = proxy.listGames().getGames();
        boolean found = false;
        for (int i = 0; i < games.size(); ++i)
        {
            GameInfo gameInfo = games.get(i);
            if (id == gameInfo.getId())
            {
                found = true;
            }
        }
        assertTrue(found);

    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#joinGame(shared.communication.JoinGameRequest)}
     * .
     */
    @Test
    public void testJoinGame()
    {
        logUserIn();

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "join1"));
        int id = response.getGameID();
        try
        {
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
            proxy.joinGame(new JoinGameRequest(id, CatanColor.PUCE));
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
        }
        catch (GameQueryException e)
        {
            fail("Join game failed");
        }

    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#saveGame(shared.communication.SaveGameRequest)}
     * .
     */
    @Test
    public void testSaveGame()
    {
        try
        {
            proxy.saveGame(null);
        }
        catch (GameQueryException e)
        {
        }
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#loadGame(shared.communication.LoadGameRequest)}
     * .
     */
    @Test
    public void testLoadGame()
    {
        try
        {
            proxy.loadGame(null);
        }
        catch (GameQueryException e)
        {
        }
    }

    /**
     * Test method for {@link server.proxy.ServerProxy#getGameState(int)}.
     */
    @Test
    public void testGetGameState()
    {
        startGame("gameState");
        testingModel = proxy.getGameState(-1);
    }

    /**
     * Test method for {@link server.proxy.ServerProxy#resetGame()}.
     */
    @Test
    public void testResetGame()
    {
        proxy.resetGame();
    }

    /**
     * Test method for {@link server.proxy.ServerProxy#getCommands()}.
     */
    @Test
    public void testGetCommands()
    {
        proxy.getCommands();
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#postCommands(PostCommandsRequest)}.
     */
    @Test
    public void testPostCommands()
    {
        proxy.postCommands(null);
    }

    /**
     * Test method for {@link server.proxy.ServerProxy#listAI()}.
     */
    @Test
    public void testListAI()
    {
        logUserIn();

        List<AIType> aiTypes = proxy.listAI().getAITypes();
        // Aparently you don't need to be in a game.

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "listAI"));
        int id = response.getGameID();
        try
        {
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
            aiTypes = proxy.listAI().getAITypes();
            assertNotNull(aiTypes);
            assertTrue(aiTypes.size() == 1);
            assertTrue(aiTypes.get(0).toString().equals("LARGEST_ARMY"));
        }
        catch (GameQueryException e)
        {
            fail("Join Game Failed");
        }

    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#addAI(shared.definitions.AIType)}.
     */
    @Test
    public void testAddAI()
    {
        logUserIn();

        try
        {
            proxy.addAI(AIType.LARGEST_ARMY);
            fail("Shouldn't have gotten here");
        }
        catch (AddAIException e)
        {
            assertTrue("Properly couldn't add AI.", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Shouldn't have gotten here");
        }

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "addAI"));
        int id = response.getGameID();
        try
        {
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
        }
        catch (GameQueryException e)
        {
            e.printStackTrace();
            fail("Join Game Failed");
        }
        try
        {
            proxy.addAI(AIType.LARGEST_ARMY);
        }
        catch (AddAIException e)
        {
            fail("Couldn't validly add AI");
        }
        catch (GameQueryException | IllegalArgumentException e1)
        {
            e1.printStackTrace();
            fail("Shouldn't have gotten here");
        }
        try
        {
            proxy.addAI(AIType.LARGEST_ARMY);
            assertTrue(true);
            proxy.addAI(AIType.LARGEST_ARMY);
            assertTrue(true);
            proxy.addAI(AIType.LARGEST_ARMY);
            fail("Shouldn't have added the last AI properly");
        }
        catch (AddAIException e)
        {
            assertTrue("validly couldn't add AI", true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail("Shouldn't have gotten here");
        }
    }

    /**
     * A method to create log a user in, create and join a game. Since these
     * processes will be called many, many times, this method is here to reduce
     * code duplication.
     * 
     * @param gameName
     *        the name of the game to create.
     */
    private void startGame(String gameName)
    {
        logUserIn();

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, gameName));
        int id = response.getGameID();
        try
        {
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
        }
        catch (GameQueryException e)
        {
            fail("shouldn't have failed");
        }
        try
        {
            for (int i = 0; i < 3; ++i)
            {
                proxy.addAI(AIType.LARGEST_ARMY);
            }
        }
        catch (AddAIException | IllegalArgumentException | GameQueryException e)
        {
            fail("shouldn't have failed to add AI players");
        }
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#sendChat(shared.communication.moveCommands.SendChatCommand)}
     * .
     */
    @Test
    public void testSendChat()
    {
        startGame("sendChat");
        testingModel = proxy.sendChat(new SendChatCommand(PlayerIndex.PLAYER_0, "Test"));
        Chat chat = testingModel.getChat();
        assertTrue(chat.getMessages().size() == 1);
        for (MessageLine messageLine : chat.getMessages()) {
            if (!messageLine.getMessage().equals("Test"))
            {
                fail("Should have been equal");
            }
        }
        proxy.sendChat(new SendChatCommand(PlayerIndex.PLAYER_0, "Test1"));
        proxy.sendChat(new SendChatCommand(PlayerIndex.PLAYER_0, "YoYoYo"));
        testingModel = proxy.sendChat(new SendChatCommand(PlayerIndex.PLAYER_3, "Different Player"));
        chat = testingModel.getChat();
        assertEquals(4,chat.getMessages().size());


    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#rollNumber(shared.communication.moveCommands.RollNumberCommand)}
     * .
     */
    @Test
    public void testRollNumber()
    {
        startGame("rollNumber");
        testingModel = proxy.rollNumber(new RollNumberCommand(PlayerIndex.PLAYER_0, 8));
        assertNotNull(testingModel);
        testingModel = proxy.rollNumber(new RollNumberCommand(PlayerIndex.PLAYER_0, 8));
        assertNotNull(testingModel);
        testingModel = proxy.rollNumber(new RollNumberCommand(PlayerIndex.PLAYER_1, 2));
        assertNotNull(testingModel);
        testingModel = proxy.rollNumber(new RollNumberCommand(PlayerIndex.PLAYER_2, 3));
        assertNotNull(testingModel);
        testingModel = proxy.rollNumber(new RollNumberCommand(PlayerIndex.PLAYER_3, 12));
        assertNotNull(testingModel);

    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#acceptTrade(shared.communication.moveCommands.AcceptTradeCommand)}
     * .
     */
    @Test
    public void testAcceptTrade()
    {
        startGame("acceptTrade");
        testingModel = proxy
                .offerTrade(new OfferTradeCommand(PlayerIndex.fromInt(0), PlayerIndex.fromInt(3), 1, 0, -1, 0, 0));
        assertNotNull(testingModel);
        testingModel = proxy.finishTurn(new FinishTurnCommand(PlayerIndex.PLAYER_0));
        assertNotNull(testingModel);
        testingModel = proxy.acceptTrade(new AcceptTradeCommand(PlayerIndex.PLAYER_3, false));
        assertNotNull(testingModel);

    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#discardCards(shared.communication.moveCommands.DiscardCardsCommand)}
     * .
     */
    @Test
    public void testDiscardCards()
    {
        startGame("DiscardCards");
        testingModel = proxy.discardCards(new DiscardCardsCommand(PlayerIndex.PLAYER_0, 0, 1, 0, 0, 0));
        assertNotNull(testingModel);

    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buildRoad(shared.communication.moveCommands.BuildRoadCommand)}
     * .
     */
    @Test
    public void testBuildRoad()
    {
        startGame("BuildRoad");
        testingModel = proxy.buildRoad(new BuildRoadCommand(PlayerIndex.PLAYER_0,
                new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest), true));
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buildSettlement(shared.communication.moveCommands.BuildSettlementCommand)}
     * .
     */
    @Test
    public void testBuildSettlement()
    {
        startGame("BuildSettlement");
        testingModel = proxy.buildSettlement(new BuildSettlementCommand(PlayerIndex.PLAYER_0,
                new VertexLocation(new HexLocation(1, 0), VertexDirection.NorthWest), true));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buildCity(shared.communication.moveCommands.BuildCityCommand)}
     * .
     */
    @Test
    public void testBuildCity()
    {
        startGame("BuildCity");
        testingModel = proxy.buildCity(new BuildCityCommand(PlayerIndex.PLAYER_0,
                new VertexLocation(new HexLocation(1, 0), VertexDirection.NorthWest)));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#offerTrade(shared.communication.moveCommands.OfferTradeCommand)}
     * .
     */
    @Test
    public void testOfferTrade()
    {
        startGame("OfferTrade");
        testingModel = proxy
                .offerTrade(new OfferTradeCommand(PlayerIndex.PLAYER_0, PlayerIndex.PLAYER_2, 1, 0, -1, 0, 1));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#maritimeTrade(shared.communication.moveCommands.MaritimeTradeCommand)}
     * .
     */
    @Test
    public void testMaritimeTrade()
    {
        startGame("MaritimeTrade");
        testingModel = proxy.maritimeTrade(
                new MaritimeTradeCommand(PlayerIndex.PLAYER_0, TradeRatio.THREE, ResourceType.BRICK, ResourceType.ORE));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#robPlayer(shared.communication.moveCommands.RobPlayerCommand)}
     * .
     */
    @Test
    public void testRobPlayer()
    {
        startGame("RobPlayer");
        testingModel = proxy.robPlayer(new RobPlayerCommand(PlayerIndex.PLAYER_0,
                new RobPlayerInfo(PlayerIndex.PLAYER_2, 1), new HexLocation(0, 1)));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#finishTurn(shared.communication.moveCommands.FinishTurnCommand)}
     * .
     */
    @Test
    public void testFinishTurn()
    {
        startGame("FinishTurn");
        testingModel = proxy.finishTurn(new FinishTurnCommand(PlayerIndex.PLAYER_0));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buyDevCard(shared.communication.moveCommands.BuyDevCardCommand)}
     * .
     */
    @Test
    public void testBuyDevCard()
    {
        startGame("BuyDevCard");
        testingModel = proxy.buyDevCard(new BuyDevCardCommand(PlayerIndex.PLAYER_0));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#soldier(shared.communication.moveCommands.SoldierCommand)}
     * .
     */
    @Test
    public void testSoldier()
    {
        startGame("Soldier");
        testingModel = proxy.soldier(new SoldierCommand(PlayerIndex.PLAYER_0,
                new RobPlayerInfo(PlayerIndex.PLAYER_2, 1), new HexLocation(0, 1)));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#yearOfPlenty(shared.communication.moveCommands.YearOfPlentyCommand)}
     * .
     */
    @Test
    public void testYearOfPlenty()
    {
        startGame("YearOfPlenty");
        testingModel = proxy
                .yearOfPlenty(new YearOfPlentyCommand(PlayerIndex.PLAYER_0, ResourceType.BRICK, ResourceType.ORE));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#roadBuilding(shared.communication.moveCommands.RoadBuildingCommand)}
     * .
     */
    @Test
    public void testRoadBuilding()
    {
        startGame("RoadBuilding");
        testingModel = proxy.roadBuilding(new RoadBuildingCommand(PlayerIndex.PLAYER_0,
                new EdgeLocation(new HexLocation(0, 1), EdgeDirection.NorthEast),
                new EdgeLocation(new HexLocation(-1, 2), EdgeDirection.South)));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#monopoly(shared.communication.moveCommands.MonopolyCommand)}
     * .
     */
    @Test
    public void testMonopoly()
    {
        startGame("Monopoly");
        testingModel = proxy.monopoly(new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.WHEAT));
        assertNotNull(testingModel);
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#monument(shared.communication.moveCommands.MonumentCommand)}
     * .
     */
    @Test
    public void testMonument()
    {
        startGame("Monument");
        testingModel = proxy.monument(new MonumentCommand(PlayerIndex.PLAYER_0));
        assertNotNull(testingModel);
    }

}
