package shared.model.bank;

import junit.framework.TestCase;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resource;

/**
 * Created by Adrian on 9/28/2015.
 */
public class ResourceTest extends TestCase
{

    public Resource setResourceWithoutAmount()
    {
        return new Resource(ResourceType.SHEEP);
    }

    public Resource setResourceWithAmount(int amount)
    {
        Resource test = new Resource(ResourceType.BRICK);
        test.addResource(amount);
        return test;
    }

    public void testAddResource() throws Exception
    {
        Resource test = setResourceWithoutAmount();

        test.addResource(23);
        assertEquals(test.getAmount(), 23);
    }

    public void testSubResource() throws Exception
    {
        Resource test = setResourceWithAmount(54);

        test.subResource(24);
        assertEquals(test.getAmount(), 30);
    }
}