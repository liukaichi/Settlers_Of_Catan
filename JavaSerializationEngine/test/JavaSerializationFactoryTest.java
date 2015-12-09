import database.Database;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dtaylor on 12/8/2015.
 */
public class JavaSerializationFactoryTest
{
    private static JavaSerializationFactory jsFactory;
    @BeforeClass
    public static void setUp() throws Exception {
        jsFactory = new JavaSerializationFactory();
    }

    @Before
    public void initialize() throws Exception {
        Database.getInstance().initialize(); //clear database
    }

    @Test public void testCreatePersistenceEngine() throws Exception
    {

    }
}