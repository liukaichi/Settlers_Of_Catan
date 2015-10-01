/**
 * 
 */
package facades;

import static org.junit.Assert.*;

import java.util.*;
import java.util.logging.Level;

import org.junit.*;

import client.data.*;
import server.proxy.*;
import shared.communication.*;
import shared.definitions.*;
import shared.definitions.exceptions.*;
import shared.model.ClientModel;

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
    // fail("Not yet implemented");
    // }
    //
    // @Test
    // public void testDeserialize()
    // {
    // fail("Not yet implemented");
    // }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#userLogin(shared.communication.Credentials)}
     * .
     */
    @Test
    public void testUserLogin()
    {
        Credentials credentials = new Credentials("username", "password");
        try
        {
            proxy.userLogin(credentials);
            fail("Shouldn't have reached this.");
        } catch (SignInException e1)
        {
            assertTrue(true);
        }
        try
        {
            proxy.userRegister(credentials);
            assertTrue(true);
            proxy.userLogin(credentials);
            assertTrue(true);
        } catch (SignInException e)
        {
            fail("Login failed");
        }
        try
        {
            proxy.userLogin(credentials);
            fail("Shouldn't have reached this.");
        } catch (SignInException e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void run1()
    {
        try
        {
            proxy.userRegister(new Credentials("sheila", "parker"));
        } catch (SignInException e)
        {
            fail(e.getMessage());
        } catch (Exception e1)
        {
            assertTrue(true);
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
        Credentials credentials = new Credentials("username", "password");
        try
        {
            proxy.userRegister(credentials);
            assertTrue(true);
        } catch (SignInException e)
        {
            fail("Login failed");
        }
        try
        {
            proxy.userRegister(credentials);
            fail("Shouldn't have reached this.");
        } catch (SignInException e)
        {
            assertTrue(true);
        }
        credentials = new Credentials("user", "pass");
        try
        {
            proxy.userRegister(credentials);
            assertTrue(true);
            proxy.userRegister(new Credentials("mel", "blanc"));
            assertTrue(true);
        } catch (SignInException e)
        {
            fail("Registration failed");
        } finally
        {
            fail("");
        }
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#changeLogLevel(java.util.logging.Level)}.
     */
    @Test
    public void testChangeLogLevel()
    {
        proxy.changeLogLevel(Level.ALL);
    }

    /**
     * Test method for {@link server.proxy.ServerProxy#listGames()}.
     */
    @Test
    public void testListGames()
    {
        try
        {
            proxy.userRegister(new Credentials("list", "games"));
        } catch (SignInException e)
        {
            fail("Registration failed");
        }

        ArrayList<GameInfo> games = (ArrayList<GameInfo>) proxy.listGames().getGames();
        int size = games.size();
        assertTrue(games.size() > 0);
        proxy.createGame(new CreateGameRequest(true, true, true, "list1"));
        games = (ArrayList<GameInfo>) proxy.listGames().getGames();
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
        games = (ArrayList<GameInfo>) proxy.listGames().getGames();
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
        games = (ArrayList<GameInfo>) proxy.listGames().getGames();
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
        try
        {
            proxy.userRegister(new Credentials("create", "game"));
        } catch (SignInException e)
        {
            fail("Registration failed");
        }

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "create1"));
        GameInfo info = response.getGameInfo();
        List<PlayerInfo> players = info.getPlayers();
        // assertTrue(players.size() <= 4);
        assertTrue(info.getTitle().equals("create1"));
        int id = info.getId();
        List<GameInfo> games = proxy.listGames().getGames();
        boolean found = false;
        for (int i = 0; i < games.size(); ++i)
        {
            GameInfo gameInfo = games.get(i);
            if (id == info.getId())
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
        try
        {
            proxy.userRegister(new Credentials("join", "game"));
        } catch (SignInException e)
        {
            fail("Registration failed");
        }

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "join1"));
        int id = response.getGameID();
        try
        {
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
            proxy.joinGame(new JoinGameRequest(id, CatanColor.PUCE));
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));

        } catch (GameQueryException e)
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
        } catch (GameQueryException e)
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
        } catch (GameQueryException e)
        {
        }
    }

    /**
     * Test method for {@link server.proxy.ServerProxy#getGameState(int)}.
     */
    @Test
    public void testGetGameState()
    {
        fail("Not yet implemented");
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
     * {@link server.proxy.ServerProxy#postCommands(java.util.List)}.
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

        try
        {
            proxy.userRegister(new Credentials("join", "game"));
        } catch (SignInException e)
        {
            fail("Registration failed");
        }
        ArrayList<AIType> aiTypes = (ArrayList<AIType>) proxy.listAI().getAITypes();
        assertNull(aiTypes);

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "listAI"));
        int id = response.getGameID();
        try
        {
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
            aiTypes = (ArrayList<AIType>) proxy.listAI().getAITypes();
            assertNotNull(aiTypes);
            assertTrue(aiTypes.size() == 1);
            assertTrue(aiTypes.get(0).LARGEST_ARMY.toString().equals("[ \"LARGEST_ARMY\" ]"));
        } catch (GameQueryException e)
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
        try
        {
            proxy.userRegister(new Credentials("join", "game"));
        } catch (SignInException e)
        {
            fail("Registration failed");
        }

        try
        {
            proxy.addAI(AIType.LARGEST_ARMY);
            fail("Shouldn't have gotten here");
        } catch (GameQueryException e)
        {
            assertTrue("Properly couldn't add AI.", true);
        } catch (Exception e)
        {
            fail("Shouldn't have gotten here");
        }

        CreateGameResponse response = proxy.createGame(new CreateGameRequest(true, true, true, "addAI"));
        int id = response.getGameID();
        try
        {
            proxy.joinGame(new JoinGameRequest(id, CatanColor.YELLOW));
        } catch (GameQueryException e)
        {
            fail("Join Game Failed");
        }
        try
        {
            proxy.addAI(AIType.LARGEST_ARMY);
        } catch (AddAIException e)
        {
            fail("Couldn't validly add AI");
        } catch (GameQueryException | IllegalArgumentException e1)
        {
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
        } catch (AddAIException e)
        {
            assertTrue("validly couldn't add AI", true);
        } catch (Exception e)
        {
            fail("Shouldn't have gotten here");
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

    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#rollNumber(shared.communication.moveCommands.RollNumberCommand)}
     * .
     */
    @Test
    public void testRollNumber()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#acceptTrade(shared.communication.moveCommands.AcceptTradeCommand)}
     * .
     */
    @Test
    public void testAcceptTrade()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#discardCards(shared.communication.moveCommands.DiscardCardsCommand)}
     * .
     */
    @Test
    public void testDiscardCards()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buildRoad(shared.communication.moveCommands.BuildRoadCommand)}
     * .
     */
    @Test
    public void testBuildRoad()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buildSettlement(shared.communication.moveCommands.BuildSettlementCommand)}
     * .
     */
    @Test
    public void testBuildSettlement()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buildCity(shared.communication.moveCommands.BuildCityCommand)}
     * .
     */
    @Test
    public void testBuildCity()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#offerTrade(shared.communication.moveCommands.OfferTradeCommand)}
     * .
     */
    @Test
    public void testOfferTrade()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#maritimeTrade(shared.communication.moveCommands.MaritimeTradeCommand)}
     * .
     */
    @Test
    public void testMaritimeTrade()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#robPlayer(shared.communication.moveCommands.RobPlayerCommand)}
     * .
     */
    @Test
    public void testRobPlayer()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#finishTurn(shared.communication.moveCommands.FinishTurnCommand)}
     * .
     */
    @Test
    public void testFinishTurn()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#buyDevCard(shared.communication.moveCommands.BuyDevCardCommand)}
     * .
     */
    @Test
    public void testBuyDevCard()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#soldier(shared.communication.moveCommands.SoldierCommand)}
     * .
     */
    @Test
    public void testSoldier()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#yearOfPlenty(shared.communication.moveCommands.YearOfPlentyCommand)}
     * .
     */
    @Test
    public void testYearOfPlenty()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#roadBuilding(shared.communication.moveCommands.RoadBuildingCommand)}
     * .
     */
    @Test
    public void testRoadBuilding()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#monopoly(shared.communication.moveCommands.MonopolyCommand)}
     * .
     */
    @Test
    public void testMonopoly()
    {
        fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link server.proxy.ServerProxy#monument(shared.communication.moveCommands.MonumentCommand)}
     * .
     */
    @Test
    public void testMonument()
    {
        fail("Not yet implemented");
    }

}
