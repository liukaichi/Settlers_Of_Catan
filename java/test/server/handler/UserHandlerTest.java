package server.handler;

import client.data.PlayerInfo;
import client.proxy.ServerProxy;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Server;
import shared.communication.Credentials;
import shared.definitions.exceptions.SignInException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by liukaichi on 11/9/2015.
 */
public class UserHandlerTest
{
    ServerProxy proxy = new ServerProxy();

    @BeforeClass public static void setupServer()
    {
        String args[] = {};
        Server.main(args);
    }

    @Test public void loginTest()
    {

        invalidUsernameLoginTest();
        validUsernameLoginTest();
    }

    public void invalidUsernameLoginTest()
    {
        try
        {
            proxy.userLogin(new Credentials("fail", "super fail"));
        } catch (SignInException e)
        {
            assertTrue(true);
        }
    }

    public void validUsernameLoginTest()
    {
        try
        {
            proxy.userLogin(new Credentials("Justin", "justin"));
        } catch (SignInException e)
        {
            fail();
        }
        assertTrue(true);
    }
}