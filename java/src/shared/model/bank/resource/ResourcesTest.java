package shared.model.bank.resource;

import org.junit.Test;
import shared.definitions.ResourceType;

import static org.junit.Assert.*;

/**
 * Created by Adrian on 9/28/2015.
 */
public class ResourcesTest {

    Resources list;

    public void setUpNonGame() {
        list = new Resources(false);
    }

    public void setUpGame() {
        list = new Resources(false);
    }

    @Test
    public void GameBankShouldInitializeWithZeroOfEach() throws Exception {
        setUpNonGame();
        assertEquals(0, list.getResource(ResourceType.BRICK).getAmount());
        assertEquals(0, list.getResource(ResourceType.SHEEP).getAmount());
        assertEquals(0, list.getResource(ResourceType.ORE).getAmount());
        assertEquals(0, list.getResource(ResourceType.WHEAT).getAmount());
        assertEquals(0, list.getResource(ResourceType.WOOD).getAmount());
    }

    @Test
    public void GameBankShouldInitializeWith19OfEach() throws Exception {
        setUpGame();
        assertEquals(19, list.getResource(ResourceType.BRICK).getAmount());
        assertEquals(19, list.getResource(ResourceType.SHEEP).getAmount());
        assertEquals(19, list.getResource(ResourceType.ORE).getAmount());
        assertEquals(19, list.getResource(ResourceType.WHEAT).getAmount());
        assertEquals(19, list.getResource(ResourceType.WOOD).getAmount());

    }
}