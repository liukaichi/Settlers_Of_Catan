import org.junit.Before;
import shared.definitions.exceptions.CatanException;

/**
 * Created by Adrian on 12/5/2015.
 */
public class BaseTest {
    SQLiteEngine engine;

    @Before
    public void setUp(){
        engine = new SQLiteEngine(10);
        try {
            SQLiteEngine.initialize();
            engine.initializeDatabase();
        } catch (CatanException e) {
            e.printStackTrace();
        }
    }
}
