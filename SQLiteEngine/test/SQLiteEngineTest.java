import client.data.PlayerInfo;
import org.junit.Before;
import org.junit.Test;
import server.ServerModel;
import shared.communication.Credentials;
import shared.communication.moveCommands.MonopolyCommand;
import shared.communication.moveCommands.MoveCommand;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by cstaheli on 12/4/2015.
 */
public class SQLiteEngineTest extends BaseTest
{
    ServerModel game, game2;
    int version;

    @Before
    public void init() throws Exception {
        game = new ServerModel(new String(Files.readAllBytes(Paths.get("sample/complexJSONModel.json"))));
        game2 = new ServerModel(new String(Files.readAllBytes(Paths.get("sample/realJSONSampleFoReal.json"))));
    }

    public void initGame() throws Exception {
        game = new ServerModel(new String(Files.readAllBytes(Paths.get("sample/complexJSONModel.json"))));
        game.setVersion(0);
        engine.addGame(game, "test");
    }

    @Test public void testSaveGameWithoutSavingModel() throws Exception
    {
        int numBetweenSaves = 10;
        engine = new SQLiteEngine(numBetweenSaves);

        initGame();
        int gameId = engine.getNextGameID() - 1;

        MoveCommand command = new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.WHEAT);

        for (int i = 1; i < numBetweenSaves; i++) {
            // set game version to mimic version updating after every command
            // game2 is used to mimic a changed model
            game2.setVersion(i);
            assertTrue(engine.saveGame(gameId, command, game2));
        }

        // model should remain as the previous one
        assertEquals(game, engine.loadGame(gameId));
    }

    @Test public void testSaveGameWithSavingModel() throws Exception
    {
        int numBetweenSaves = 11;
        engine = new SQLiteEngine(numBetweenSaves);

        initGame();
        int gameId = engine.getNextGameID() - 1;

        MoveCommand command = new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.WHEAT);

        for (int i = 1; i < numBetweenSaves; i++) {
            // set game version to mimic version updating after every command
            // game2 is used to mimic a changed model
            game2.setVersion(i);
            assertTrue(engine.saveGame(gameId, command, game2));
        }

        // model should remain as the previous one
        assertEquals(game, engine.loadGame(gameId));
    }

    @Test public void testLoadGame() throws Exception
    {

    }

    @Test public void testAddPlayerToGame() throws Exception
    {
        int userId = engine.registerUser(new Credentials("Chubby", "Bunny"));
        PlayerInfo player = new PlayerInfo(userId, "Chubby", CatanColor.BLUE);

        initGame();
        int gameId = engine.getNextGameID() - 1;

        assertEquals(game, engine.addPlayerToGame(player, gameId));
//        assertEquals(player, engine.);

    }

    @Test public void testRegisterUser() throws Exception
    {

    }

    @Test public void testGetUser() throws Exception
    {

    }

    @Test public void testGetUser1() throws Exception
    {

    }

    @Test public void testStartTransaction() throws Exception
    {

    }

    @Test public void testEndTransaction() throws Exception
    {

    }
}