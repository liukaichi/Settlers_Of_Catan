import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.manager.User;
import shared.communication.Credentials;

import static org.junit.Assert.assertEquals;

/**
 * Created by longl on 12/7/2015.
 */
public class UserAccessTest {

    private static UserAccess userAccess;

    @BeforeClass
    public static void setUp() throws Exception {
        userAccess = new UserAccess();
    }

    @Before
    public void initialize() throws Exception {
        //Database.getInstance().initialize(); //clear database
    }

    @Test
    public void testGetUserById() throws Exception {
        userAccess.registerUser(new Credentials("Sheila","Parker"));
        userAccess.registerUser(new Credentials("Sheila2","Parker2"));
        userAccess.registerUser(new Credentials("Sheila3","Parker3"));
        userAccess.registerUser(new Credentials("Sheila4","Parker4"));
        assertEquals(new User(new Credentials("Sheila", "Parker"), 1), userAccess.getUser(1));
        assertEquals(new User(new Credentials("Sheila4", "Parker4"), 4), userAccess.getUser(4));
        assertEquals(new User(new Credentials("Sheila2", "Parker2"), 2), userAccess.getUser(2));
        assertEquals(new User(new Credentials("Sheila3", "Parker3"), 3), userAccess.getUser(3));
    }

    @Test
    public void testGetUserByCredential() throws Exception {
        userAccess.registerUser(new Credentials("Sheila","Parker"));
        assertEquals(new User(new Credentials("Sheila", "Parker"), 1), userAccess.getUser(new Credentials("Sheila", "Parker")));
    }

    @Test
    public void testRegisterUser() throws Exception {
        assertEquals(1, userAccess.registerUser(new Credentials("Sheila", "Parker")));//design by contract so it will always allow registering of the same user
        assertEquals(2, userAccess.registerUser(new Credentials("Sheila", "Parker")));//design by contract so it will always allow registering of the same user
        assertEquals(3, userAccess.registerUser(new Credentials("Sheilas", "Parkers")));
        assertEquals(new User(new Credentials("Sheilas", "Parkers"), 3), userAccess.getUser(3));
    }
}