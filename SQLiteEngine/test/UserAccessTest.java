
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import server.manager.User;
import shared.communication.Credentials;

import java.util.Map;

/**
 * Created by cstaheli on 12/4/2015.
 */
public class UserAccessTest extends BaseTest
{
    UserAccess dao;
    @Before
    public void init() throws Exception
    {
        dao = new UserAccess(engine);
    }

    @Test
    public void testGetNullUser() throws Exception
    {
        engine.startTransaction();
        User user = dao.getUser(1);
        User user2 = dao.getUser(new Credentials("Chubby", "Bunny"));
        Map<Integer, Credentials> list = dao.getAllUsers();
        engine.endTransaction(false);

        assertNull(user);
        assertNull(user2);
        assertTrue(list.isEmpty());
    };

    @Test public void testGetUser1() throws Exception
    {
        Credentials cred = new Credentials("Chubby", "Bunny");

        engine.startTransaction();
        dao.registerUser(cred);
        dao.registerUser(new Credentials("Lazy", "Bunny"));
        dao.registerUser(new Credentials("Fat", "Bunny"));
        engine.endTransaction(true);

        engine.startTransaction();
        User user = dao.getUser(2);
        User user2 = dao.getUser(cred);
        engine.endTransaction(false);

        assertEquals("Lazy", user.getUserName());
        assertEquals("Chubby", user2.getUserName());
    }

    @Test public void testGetAllUsers() throws Exception
    {
        engine.startTransaction();
        dao.registerUser(new Credentials("Chubby", "Bunny"));
        dao.registerUser(new Credentials("Lazy", "Bunny"));
        dao.registerUser(new Credentials("Fat", "Bunny"));
        engine.endTransaction(true);

        engine.startTransaction();
        Map<Integer, Credentials> list = dao.getAllUsers();
        engine.endTransaction(false);

        assertEquals(3, list.size());
        // the key is 1 because it's the UserID
        assertEquals("Chubby", list.get(1).getUsername());
    }
}