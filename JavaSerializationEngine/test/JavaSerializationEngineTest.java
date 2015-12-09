import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by dtaylor on 12/8/2015.
 */
public class JavaSerializationEngineTest
{
    private static JavaSerializationEngine jsEngine;
    @BeforeClass
    public static void setUp() throws Exception {
        jsEngine = new JavaSerializationEngine(10);
    }

    @Before
    public void initialize() throws Exception {
        //Database.getInstance().initialize(); //clear database
    }

    @Test public void testSaveGame() throws Exception
    {

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

    @Test public void testAddGame() throws Exception
    {

    }

    @Test public void testGetNextGameID() throws Exception
    {

    }

    @Test public void testGetAllGames() throws Exception
    {

    }

    @Test public void testGetAllUsers() throws Exception
    {

    }

    @Test public void testUpdateColor() throws Exception
    {

    }

    @Test public void testGetCommandBatch() throws Exception
    {

    }
}