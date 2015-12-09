import database.Database;
import org.junit.*;
import server.ServerModel;
import server.util.FileUtils;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by longl on 12/7/2015.
 */
public class GameAccessTest {

    private static GameAccess gameAccess;
    private static ServerModel model;
    @BeforeClass
    public void setUp() throws Exception {
        gameAccess = new GameAccess();
        model = new ServerModel();
        model.updateModel(FileUtils.getModelFromFile("sample/serverDefaults/", "game-2"));
    }

    @Before
    public void initialize() throws Exception {
        Database.getInstance().initialize(); //clear database
    }

    @Test
    public void testUpdateModel() throws Exception {
        gameAccess.updateModel(0,model);
    }

    @Test
    public void testAddGame() throws Exception {
        assertEquals(0,gameAccess.addGame(model, "TestGame"));
    }

    @Test
    public void testGetGame() throws Exception {
        gameAccess.getGame(0);
    }

    @Test
    public void testGetAllGames() throws Exception {
        gameAccess.getAllGames();
    }

    @Test
    public void testGetNextGameID() throws Exception {
        gameAccess.getNextGameID();
    }
}