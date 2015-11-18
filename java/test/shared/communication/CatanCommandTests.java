package shared.communication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.ServerModel;
import server.facade.AbstractServerFacade;
import server.facade.MockServerFacade;
import server.facade.ServerFacade;
import shared.communication.moveCommands.AcceptTradeCommand;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.model.player.TradeOffer;

import static org.junit.Assert.*;

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
        assertNotNull(model);
        TradeOffer offer = model.getTradeOffer();
        assertNotNull(offer);
        TradeOffer testOffer = new TradeOffer(PlayerIndex.PLAYER_0, PlayerIndex.PLAYER_1, 0, 0, -1, 1, 0);
        assertEquals(testOffer, offer);
        testOffer.addToOffer(ResourceType.BRICK, 1);
        assertNotEquals(testOffer, offer);

        AbstractServerFacade.setFacade(new ServerFacade());
        command = new AcceptTradeCommand(PlayerIndex.PLAYER_0, false);
        model = new ServerModel(command.execute(1));
        //        assertNotNull(model);
        //        offer = model.getTradeOffer();
        //        assertNotNull(offer);

    }
}