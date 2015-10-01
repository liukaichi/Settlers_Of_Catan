package client.poller;

import junit.framework.TestCase;
import server.proxy.MockProxy;

/**
 * Created by liukaichi on 9/26/2015.
 */
public class PollTest extends TestCase
{

    public void testPoll() throws Exception
    {

    }

    public void testUpdateModel() throws Exception
    {
        MockProxy proxy = new MockProxy();
        Poller poller = new Poller(proxy);
        proxy.getGameState(0);



    }

    public void testRun() throws Exception
    {

    }
}