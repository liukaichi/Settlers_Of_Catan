package shared.model.bank;

import junit.framework.TestCase;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resource;

/**
 * Created by Adrian on 9/28/2015.
 */
public class ResourceTest extends TestCase {

    public ResourceType setResourceWithoutAmount(){
        return new ResourceType(ResourceType.SHEEP);
    }

    public ResourceType setResourceWithAmount(int amount){
        ResourceType test = new ResourceType(ResourceType.BRICK);
        test.addResource(amount);
        return test;
    }

    public void testAddResource() throws Exception {
        ResourceType test = setResourceWithoutAmount();

        test.addResource(23);
        assertEquals(test.getAmount(), 23);
    }

    public void testSubResource() throws Exception {
        ResourceType test = setResourceWithAmount(54);

        test.subResource(24);
        assertEquals(test.getAmount(), 30);
    }
}