import org.junit.Before;
import org.junit.Test;
import server.Server;
import server.ServerModel;
import server.manager.GameManager;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Adrian on 12/5/2015.
 */
public class GameAccessTest extends BaseTest {

    GameAccess dao;
    ServerModel game, game2;

    @Before
    public void init() throws IOException {
        dao = new GameAccess(engine);
        game = new ServerModel(new String(Files.readAllBytes(Paths.get("sample/complexJSONModel.json"))));
        game2 = new ServerModel(new String(Files.readAllBytes(Paths.get("sample/realJSONSampleFoReal.json"))));

    }

    public void populateGames() throws Exception {

        engine.startTransaction();
        dao.addGame(game, "Game 1");
        dao.addGame(game2, "Game 2");
        dao.addGame(game, "Game 3");
        dao.addGame(game2, "Game 4");
        dao.addGame(game, "Game 5");
        dao.addGame(game2, "Game 6");
        engine.endTransaction(true);
    }

    @Test
    public void testUpdateModel() throws Exception {
        engine.startTransaction();
        int gameId = dao.addGame(game, "test");
        engine.endTransaction(true);

        engine.startTransaction();
        dao.updateModel(gameId, game2);
        engine.endTransaction(true);

        engine.startTransaction();
        ServerModel savedGame = dao.getGame(gameId);
        engine.endTransaction(true);

        assertEquals(game2, savedGame);
    }

    @Test
    public void testAddOneGame() throws Exception {
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

        populateGames();

        engine.startTransaction();
        ServerModel savedGame1 = dao.getGame(1);
        ServerModel savedGame2 = dao.getGame(2);
        ServerModel savedGame3 = dao.getGame(3);
        engine.endTransaction(true);

        assertEquals(game, savedGame1);
        assertEquals(game, savedGame3);
        assertEquals(game2, savedGame2);
    }

    @Test
    public void testGetAllGames() throws Exception {
        populateGames();

        engine.startTransaction();
        List<ServerModel> list = dao.getAllGames();
        engine.endTransaction(true);
        assertTrue(list.size() == 6);

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0){
                assertEquals(game, list.get(i));
            }
            else {
                assertEquals(game2, list.get(i));
            }
        }
    }
}