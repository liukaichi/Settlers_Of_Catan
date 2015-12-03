package shared.model.bank;

import junit.framework.TestCase;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resource;


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

        test.addResource(19);
        assertEquals(test.getAmount(), 19);
    }

    public void testSubResource() throws Exception
    {
        Resource test = setResourceWithAmount(19);

        test.subResource(2);
        assertEquals(test.getAmount(), 17);
    }
}