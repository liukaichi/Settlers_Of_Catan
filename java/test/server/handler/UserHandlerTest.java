package server.handler;

import client.data.PlayerInfo;
import client.proxy.ServerProxy;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Server;
import shared.communication.Credentials;
import shared.definitions.exceptions.InvalidCredentialsException;
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
        String args[] = { "8080" };
        Server.main(args);
    }

    @Test public void loginTests()
    {

        invalidUsernameLoginTest();
        validUsernameLoginTest();
        validRegister();
        invalidRegister();
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

    public void validRegister()
    {
        try
        {
            PlayerInfo info = proxy.userRegister(new Credentials("ABC", "ABCDE"));
            assertTrue(info.getId() != -1);

            info = proxy.userRegister(new Credentials("ABC-EFG", "ABCDEFGH"));
            assertTrue(info.getId() != -1);

            info = proxy.userRegister(new Credentials("Justin_", "goodpassword"));
            assertTrue(info.getId() != -1);
        } catch (InvalidCredentialsException e)
        {
            fail();
            e.printStackTrace();
        }
    }

    public void invalidRegister()
    {
        PlayerInfo info = null;
        try
        { //too short of a username
            proxy.userRegister(new Credentials("AB", "goodpassword"));
            fail();
        } catch (InvalidCredentialsException e)
        {
            assertTrue(e.getMessage().matches("Username must be between 3 and 7 characters."));
        }

        try
        { //too long of a username
            proxy.userRegister(new Credentials("Abittoolong", "stuff"));
            fail();
        } catch (InvalidCredentialsException e)
        {
            assertTrue(e.getMessage().matches("Username must be between 3 and 7 characters."));
        }
        try
        { //too short of a password
            proxy.userRegister(new Credentials("David", "good"));
            fail();
        } catch (InvalidCredentialsException e)
        {
            assertTrue(e.getMessage().matches("Password must be at least 5 characters."));
        }
    }
}