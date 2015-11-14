package shared.model.bank;

import junit.framework.TestCase;
import shared.definitions.DevCardType;
import shared.model.bank.card.DevCard;

/**
 * Created by Adrian on 9/28/2015.
 */
public class DevCardTest extends TestCase
{

    private DevCard setCardWithoutAmount()
    {
        return new DevCard(DevCardType.MONUMENT)
        {
            @Override public void playAction(PlayerBank context)
            {
                //vp ++
            }
        };
    }

    private DevCard setCardWithAmount(int playable, int unplayable, int played)
    {
        DevCard test = new DevCard(DevCardType.ROAD_BUILD)
        {
            @Override public void playAction(PlayerBank context)
            {
                //road +2
            }
        };

        test.addCard(DevCard.AmountType.PLAYABLE, playable);
        test.addCard(DevCard.AmountType.UNPLAYABLE, unplayable);
        test.addCard(DevCard.AmountType.PLAYED, played);
        return test;
    }

    public void testPlayAction() throws Exception
    {
        // TODO
    }

    public void testAddCard() throws Exception
    {
        DevCard test = setCardWithoutAmount();

        test.addCard(DevCard.AmountType.PLAYABLE, 2);
        test.addCard(DevCard.AmountType.UNPLAYABLE, 3);
        test.addCard(DevCard.AmountType.PLAYED, 4);

        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 2);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 3);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYED), 4);
    }

    public void testSubCard() throws Exception
    {
        DevCard test = setCardWithAmount(10, 10, 10);

        test.subCard(DevCard.AmountType.PLAYABLE, 10);
        test.subCard(DevCard.AmountType.UNPLAYABLE, 2);
        test.subCard(DevCard.AmountType.PLAYED, 5);

        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 0);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 8);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYED), 5);
    }

    public void testTotal() throws Exception
    {
        DevCard test = setCardWithAmount(10, 10, 10);

        assertEquals(test.total(), 30);

        test.addCard(DevCard.AmountType.PLAYABLE, 3);
        test.subCard(DevCard.AmountType.PLAYED, 5);
        test.subCard(DevCard.AmountType.UNPLAYABLE, 2);

        assertEquals(test.total(), 26);
    }
}