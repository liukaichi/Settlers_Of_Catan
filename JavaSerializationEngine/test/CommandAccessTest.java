import database.Database;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dtaylor on 12/8/2015.
 */
public class CommandAccessTest
{
    private static CommandAccess commandAccess;

    @BeforeClass
    public static void setUp() throws Exception {
        commandAccess = new CommandAccess();
    }

    @Before
    public void initialize() throws Exception {
        Database.getInstance().initialize(); //clear database
    }

    @Test public void testInitialize() throws Exception
    {

    }

    @Test public void testSaveCommand() throws Exception
    {

    }

    @Test public void testGetNumberOfCommandsInGame() throws Exception
    {

    }

    @Test public void testGetAllCommands() throws Exception
    {

    }

    @Test public void testGetAllCommandsAfter() throws Exception
    {

    }

    @Test public void testGetCommand() throws Exception
    {

    }
}