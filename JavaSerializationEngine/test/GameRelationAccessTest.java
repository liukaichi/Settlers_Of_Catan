import database.Database;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by longl on 12/7/2015.
 */
public class GameRelationAccessTest {

    static GameRelationAccess gameRelationAccess;

    @BeforeClass
    public static void setUp() throws Exception {
        gameRelationAccess = new GameRelationAccess();
    }

    @Before
    public void initialize() throws Exception {
        Database.getInstance().initialize(); //clear database
    }

    @Test
    public void testAddUserToGame() throws Exception {

    }

    @Test
    public void testListPlayersInGame() throws Exception {

    }
}