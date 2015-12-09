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
    static public void setUp() throws Exception {
        gameAccess = new GameAccess();
        model = new ServerModel();
        model.updateModel(FileUtils.getModelFromFile("../sample/serverDefaults/", "game-2"));
    }

    @Before
    public void initialize() throws Exception {
        //Database.getInstance().initialize(); //clear database
        assertEquals(1,gameAccess.addGame(model, "TestGame"));
    }

    @Test
    public void testUpdateModel() throws Exception {
        gameAccess.updateModel(1,model);
    }

    @Test
    public void testAddGame() throws Exception {
        assertEquals(2,gameAccess.addGame(model, "TestGame"));
        assertEquals(3,gameAccess.addGame(model, "TestGame2"));
    }

    @Test
    public void testGetGame() throws Exception {
        assertEquals(model,gameAccess.getGame(1));
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