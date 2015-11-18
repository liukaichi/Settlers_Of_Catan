package shared.communication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.ServerModel;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import shared.communication.moveCommands.AcceptTradeCommand;
import shared.definitions.PlayerIndex;

/**
 * Test the CatanCommands
 */
public class CatanCommandTests
{

    @After public void tearDown() throws Exception
    {

    }

    @Before public void initializeFacade()
    {
        AbstractServerFacade.setFacade(new MockServerFacade());
    }

    @Test public void testAcceptTradeCommand()
    {
        AcceptTradeCommand command = new AcceptTradeCommand(PlayerIndex.PLAYER_0, false);
        ServerModel model = new ServerModel(command.execute(-1)); //id doesn't matter.

    }
}