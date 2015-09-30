package shared.model.bank.card;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import shared.definitions.DevCardType;

/**
 * Created by Adrian on 9/28/2015.
 */
public class DevCardsTest extends TestCase {

    DevCards list = new DevCards();

    @BeforeClass
    public void setUp() throws Exception {
        list.getCard(DevCardType.MONOPOLY).addCard(DevCard.AmountType.PLAYABLE, 3);
        list.getCard(DevCardType.SOLDIER).addCard(DevCard.AmountType.UNPLAYABLE, 2);
        list.getCard(DevCardType.ROAD_BUILD).addCard(DevCard.AmountType.UNPLAYABLE, 4);
        list.getCard(DevCardType.ROAD_BUILD).addCard(DevCard.AmountType.PLAYED, 2);
        list.getCard(DevCardType.YEAR_OF_PLENTY).addCard(DevCard.AmountType.PLAYABLE, 5);
        list.getCard(DevCardType.MONUMENT).addCard(DevCard.AmountType.UNPLAYABLE, 3);
        list.getCard(DevCardType.MONUMENT).addCard(DevCard.AmountType.PLAYABLE,2);
    }

    public void testGetMonopoly() throws Exception {
        DevCard test = list.getCard(DevCardType.MONOPOLY);

        assertEquals(test.getType(), DevCardType.MONOPOLY);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE),3);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 0);
    }

    public void testGetRoadBuilding() throws Exception {
        DevCard test = list.getCard(DevCardType.ROAD_BUILD);

        assertEquals(test.getType(), DevCardType.ROAD_BUILD);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 0);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYED), 2);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 4);
    }

    public void testGetYearOfPlenty() throws Exception {
        DevCard test = list.getCard(DevCardType.YEAR_OF_PLENTY);

        assertEquals(test.getType(), DevCardType.YEAR_OF_PLENTY);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE),5);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 0);
    }

    public void testGetKnight() throws Exception {
        DevCard test = list.getCard(DevCardType.SOLDIER);

        assertEquals(test.getType(), DevCardType.SOLDIER);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE),0);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 2);
    }

    public void testGetMonument() throws Exception {
        DevCard test = list.getCard(DevCardType.MONUMENT);

        assertEquals(test.getType(), DevCardType.MONUMENT);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE),2);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 3);
    }
}