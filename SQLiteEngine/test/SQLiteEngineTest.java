import org.junit.*;
import server.ServerModel;
import shared.communication.CatanCommand;
import shared.communication.moveCommands.MonopolyCommand;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;



/**
 * Created by cstaheli on 12/4/2015.
 */
public class SQLiteEngineTest
{
    SQLiteEngine engine;
    ServerModel game;

    @Before public void setup(){
        engine = new SQLiteEngine(10);
        try {
            game = new ServerModel(new String(Files.readAllBytes(Paths.get("C:\\Users\\Adrian\\code\\the-settlers-of-catan\\sample\\complexJSONModel.json"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void testSaveGame() throws Exception
    {
        CatanCommand command  = new MonopolyCommand(PlayerIndex.PLAYER_0, ResourceType.WHEAT);
        engine.startTransaction();
        engine.saveGame(2, command, game);
        assertEquals(true,true);
    }

    @Test public void testLoadGame() throws Exception
    {

    }

    @Test public void testAddPlayerToGame() throws Exception
    {

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