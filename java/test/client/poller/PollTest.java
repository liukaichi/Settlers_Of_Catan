package client.poller;

import client.facade.ClientFacade;
import junit.framework.TestCase;
import server.proxy.MockProxy;
import shared.model.ClientModel;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by liukaichi on 9/26/2015.
 */
public class PollTest extends TestCase
{
    ClientModel clientModel;
    /**
     * Tests the ability to update through a test before the Poller is able to roll and after.
     * The version number on the client is checked to verify correct changes are made.
     * @throws Exception
     */
    public void testPoll() throws Exception
    {
        clientModel = new ClientModel(new String(
            Files.readAllBytes(Paths.get("sample/complexJSONModel.json"))));
        clientModel.setVersion(0);
        ClientFacade.getInstance().setModel(clientModel);
        MockProxy proxy = new MockProxy();

        testTimedUpdate(proxy);
        testSameVersion(proxy);


    }

    /**
     * Tests the timer feature of the roll to update the model.
     * @param proxy
     * @throws Exception
     */
    private void testTimedUpdate(MockProxy proxy) throws Exception{
        proxy.getServerModel().setVersion(1);
        Poller poller = new Poller(proxy);
        assertFalse(clientModel.equals(proxy.getServerModel()));

        Thread.sleep(4000);

        assertTrue(ClientFacade.getInstance().getModel().equals(proxy.getServerModel()));
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