import org.junit.Before;
import org.junit.Test;
import server.ServerModel;
import static org.junit.Assert.*;

import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Created by Adrian on 12/5/2015.
 */
public class GameAccessTest extends BaseTest {

    GameAccess dao;

    @Before
    public void init(){
        dao = new GameAccess(engine);
    }

    public void populateGames() throws Exception {

    }

    @Test
    public void testUpdateModel() throws Exception {

    }

    @Test
    public void testAddOneGame() throws Exception {
        ServerModel game = new ServerModel(new String(Files.readAllBytes(Paths.get("../sample/complexJSONModel.json"))));
        int gameId;
        engine.startTransaction();
        gameId = dao.addGame(game, "Test");
        engine.endTransaction(true);

        engine.startTransaction();
        ServerModel savedGame = dao.getGame(gameId);
        engine.endTransaction(true);

        assertEquals(game, savedGame);
    }

    @Test
    public void testGetGame() throws Exception {

    }

    @Test
    public void testGetAllGames() throws Exception {

    }

    @Test
    public void testGetNumberOfCommands() throws Exception {

    }

    @Test
    public void testInitialize() throws Exception {

    }
}