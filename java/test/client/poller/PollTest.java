package client.poller;

import client.facade.ClientFacade;
import junit.framework.TestCase;
import server.proxy.MockProxy;
import shared.model.ClientModel;

import java.lang.reflect.Method;

/**
 * Created by liukaichi on 9/26/2015.
 */
public class PollTest extends TestCase
{

    /**
     * Tests the ability to update through a test before the Poller is able to poll and after.
     * The version number on the client is checked to verify correct changes are made.
     * @throws Exception
     */
    public void testPoll() throws Exception
    {
        ClientModel clientModel = new ClientModel();
        clientModel.setVersion(0);
        ClientFacade.getInstance().setModel(clientModel);
        MockProxy proxy = new MockProxy();

        testTimedUpdate(proxy);
        testSameVersion(proxy);


    }

    /**
     * Tests the timer feature of the poll to update the model.
     * @param proxy
     * @throws Exception
     */
    private void testTimedUpdate(MockProxy proxy) throws Exception{
        proxy.getServerModel().setVersion(1);
        Poller poller = new Poller(proxy);
        assertFalse(poller.getCurrentVersion() == 1);
        Thread.sleep(3100);
        assertTrue(poller.getCurrentVersion() == 1);
    }

    /**
     * Tests that the model is not updated if the version is the same.
     * @param proxy
     * @throws Exception
     */
    private void testSameVersion(MockProxy proxy)throws Exception{
        proxy.getServerModel().setVersion(1);
        Poller poller = new Poller(proxy);

        Method getPollTask = Poller.class.getDeclaredMethod("getPollTask", null);
        getPollTask.setAccessible(true);

        boolean updated = ((Poller.PollTask)getPollTask.invoke(poller, null)).poll();
        assertTrue(updated);

        updated = ((Poller.PollTask)getPollTask.invoke(poller, null)).poll();
        assertFalse(updated);

    }

}