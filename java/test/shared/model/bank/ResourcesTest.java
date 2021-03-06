package shared.model.bank;

import org.junit.Test;
import shared.definitions.ResourceType;
import shared.model.bank.resource.Resources;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 9/28/2015.
 */
public class ResourcesTest
{

    Resources list;

    public void setUpNonGame()
    {
        list = new Resources(false);
    }

    public void setUpGame()
    {
        list = new Resources(true);
    }

    @Test public void GameBankShouldInitializeWithZeroOfEach() throws Exception
    {
        setUpNonGame();
        assertEquals(0, list.getResource(ResourceType.BRICK).getAmount());
        assertEquals(0, list.getResource(ResourceType.SHEEP).getAmount());
        assertEquals(0, list.getResource(ResourceType.ORE).getAmount());
        assertEquals(0, list.getResource(ResourceType.WHEAT).getAmount());
        assertEquals(0, list.getResource(ResourceType.WOOD).getAmount());
    }

    @Test public void GameBankShouldInitializeWith19OfEach() throws Exception
    {
        setUpGame();
        assertEquals(19, list.getResource(ResourceType.BRICK).getAmount());
        assertEquals(19, list.getResource(ResourceType.SHEEP).getAmount());
        assertEquals(19, list.getResource(ResourceType.ORE).getAmount());
        assertEquals(19, list.getResource(ResourceType.WHEAT).getAmount());
        assertEquals(19, list.getResource(ResourceType.WOOD).getAmount());
    }

    @Test public void testSerialization()
    {
        setUpGame();
        String json = list.toString();
        assertEquals("{\"brick\":19,\"ore\":19,\"sheep\":19,\"wheat\":19,\"wood\":19}", json);

        Resources resources = new Resources(list.toString());
        assertEquals(19, resources.getResource(ResourceType.BRICK).getAmount());
        assertEquals(19, resources.getResource(ResourceType.SHEEP).getAmount());
        assertEquals(19, resources.getResource(ResourceType.ORE).getAmount());
        assertEquals(19, resources.getResource(ResourceType.WHEAT).getAmount());
        assertEquals(19, resources.getResource(ResourceType.WOOD).getAmount());
    }

}