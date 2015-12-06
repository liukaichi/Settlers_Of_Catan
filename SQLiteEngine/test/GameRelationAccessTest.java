import org.junit.Before;
import org.junit.Test;
import shared.definitions.exceptions.CatanException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by cstaheli on 12/4/2015.
 */
public class GameRelationAccessTest
{
    GameRelationAccess dao;
    SQLiteEngine engine;

    @Before
    public void setUp() {
        engine = new SQLiteEngine(10);
        dao = new GameRelationAccess(engine);
        try {
            SQLiteEngine.initialize();
            engine.startTransaction();
            engine.initializeTables();
            engine.endTransaction(true);
        } catch (CatanException e) {
            e.printStackTrace();
        }

    }

    private void createData(){
        engine.startTransaction();
        // Game 1 : Users 1 3 5 7
        // Game 2 : Users 1 2 3 4
        // Game 3 : Users 2 5 6 7
        try {
            dao.addUserToGame(1, 1);
            dao.addUserToGame(3, 1);
            dao.addUserToGame(5, 1);
            dao.addUserToGame(7, 1);

            dao.addUserToGame(1, 2);
            dao.addUserToGame(2, 2);
            dao.addUserToGame(3, 2);
            dao.addUserToGame(4, 2);

            dao.addUserToGame(2, 3);
            dao.addUserToGame(5, 3);
            dao.addUserToGame(6, 3);
            dao.addUserToGame(7, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        engine.endTransaction(true);

    }

    @Test
    public void testAddUserToGame() throws Exception
    {
        int userId = 1;
        int gameId = 2;
        engine.startTransaction();
        dao.addUserToGame(userId, gameId);
        engine.endTransaction(true);

        engine.startTransaction();
        List<Integer> userList = dao.listPlayersInGame(gameId);
        engine.endTransaction(true);

        assertTrue(userList.contains(userId));
    }

    @Test
    public void testListPlayersInGame() throws Exception
    {
        createData();
        engine.startTransaction();
        List<List<Integer>> gameLists = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            gameLists.add(dao.listPlayersInGame(i));
        }
        engine.endTransaction(true);
        // Game 1 : Users 1 3 5 7
        // Game 2 : Users 1 2 3 4
        // Game 3 : Users 2 5 6 7
        assertArrayEquals(new Integer[]{1, 3, 5, 7}, gameLists.get(0).toArray(new Integer[4]));
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, gameLists.get(1).toArray(new Integer[4]));
        assertArrayEquals(new Integer[]{2, 5, 6, 7}, gameLists.get(2).toArray(new Integer[4]));
    }
}