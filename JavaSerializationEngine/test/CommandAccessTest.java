import database.Database;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import shared.communication.moveCommands.BuildRoadCommand;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

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
        EdgeLocation centerNorthEdge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.North);
        BuildRoadCommand buildRoad = new BuildRoadCommand(PlayerIndex.PLAYER_0, centerNorthEdge,true);
        commandAccess.saveCommand(1,buildRoad);
    }

    @Test public void testGetNumberOfCommandsInGame() throws Exception
    {
        commandAccess.getNumberOfCommandsInGame(1);
    }

    @Test public void testGetAllCommands() throws Exception
    {
        commandAccess.getAllCommands(1);
    }

    @Test public void testGetAllCommandsAfter() throws Exception
    {
        commandAccess.getAllCommandsAfter(1,0);
    }

    @Test public void testGetCommand() throws Exception
    {
        commandAccess.getCommand(1,0);
    }
}