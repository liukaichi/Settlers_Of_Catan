package shared.model.bank.resource;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;

/**
 * Created by Adrian on 9/28/2015.
 */
public class ResourcesTest extends TestCase {

    Resources list = new Resources();

    @BeforeClass
    public void setUp() throws CatanException {
        list.getResource(ResourceType.BRICK).addResource(22);
        list.getResource(ResourceType.SHEEP).addResource(13);
    }

    public void testGetResource() throws Exception {
        Resource test = list.getResource(ResourceType.BRICK);
        assertEquals(test.getType(), ResourceType.BRICK);
        assertEquals(test.getAmount(), 22);

        test = list.getResource(ResourceType.SHEEP);
        assertEquals(test.getType(), ResourceType.SHEEP);
        assertEquals(test.getAmount(), 13);
    }
}