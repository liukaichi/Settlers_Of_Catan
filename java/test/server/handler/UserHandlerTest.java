package server.handler;

import client.data.PlayerInfo;
import client.proxy.ServerProxy;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.Server;
import shared.communication.Credentials;
import shared.definitions.exceptions.SignInException;
import shared.model.player.Player;

import static org.junit.Assert.*;

/**
 * Created by liukaichi on 11/9/2015.
 */
public class UserHandlerTest {
    ServerProxy proxy = new ServerProxy();
    static Server server;

    @BeforeClass
    public static void setupServer() {
        String args[] = {};
        server.main(args);
    }

    @Test
    public void loginTest() {
        PlayerInfo playerInfo;
        invalidUsernameLoginTest();
        validUsernameLoginTest();
    }

    public void invalidUsernameLoginTest() {
        try {
            proxy.userLogin(new Credentials("fail", "super fail"));
        }
        catch (SignInException e) {
            assertTrue(true);
        }
    }

    public void validUsernameLoginTest() {
        try {
            proxy.userLogin(new Credentials("Sam", "sam"));
        }
        catch (SignInException e) {
            fail();
        }
        assertTrue(true);
    }
}