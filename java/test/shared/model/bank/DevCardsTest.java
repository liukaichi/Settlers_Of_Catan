package shared.model.bank;

import org.junit.Test;
import shared.definitions.DevCardType;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;

import static org.junit.Assert.assertEquals;

/**
 * Created by Adrian on 9/28/2015.
 */
public class DevCardsTest
{

    DevCards list = new DevCards();

    public DevCardsTest()
    {
        list.getCard(DevCardType.MONOPOLY).addCard(DevCard.AmountType.PLAYABLE, 3);
        list.getCard(DevCardType.SOLDIER).addCard(DevCard.AmountType.UNPLAYABLE, 2);
        list.getCard(DevCardType.ROAD_BUILD).addCard(DevCard.AmountType.UNPLAYABLE, 4);
        list.getCard(DevCardType.ROAD_BUILD).addCard(DevCard.AmountType.PLAYED, 2);
        list.getCard(DevCardType.YEAR_OF_PLENTY).addCard(DevCard.AmountType.PLAYABLE, 5);
        list.getCard(DevCardType.MONUMENT).addCard(DevCard.AmountType.UNPLAYABLE, 3);
        list.getCard(DevCardType.MONUMENT).addCard(DevCard.AmountType.PLAYABLE, 2);
    }

    @Test public void testGetMonopoly()
    {
        DevCard test = list.getCard(DevCardType.MONOPOLY);

        assertEquals(test.getType(), DevCardType.MONOPOLY);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 3);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 0);
    }

    @Test public void testGetRoadBuilding()
    {
        DevCard test = list.getCard(DevCardType.ROAD_BUILD);

        assertEquals(test.getType(), DevCardType.ROAD_BUILD);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 0);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYED), 2);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 4);
    }

    @Test public void testGetYearOfPlenty()
    {
        DevCard test = list.getCard(DevCardType.YEAR_OF_PLENTY);

        assertEquals(test.getType(), DevCardType.YEAR_OF_PLENTY);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 5);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 0);
    }

    @Test public void testGetKnight()
    {
        DevCard test = list.getCard(DevCardType.SOLDIER);

        assertEquals(test.getType(), DevCardType.SOLDIER);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 0);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 2);
    }

    @Test public void testGetMonument()
    {
        DevCard test = list.getCard(DevCardType.MONUMENT);

        assertEquals(test.getType(), DevCardType.MONUMENT);
        assertEquals(test.getAmount(DevCard.AmountType.PLAYABLE), 2);
        assertEquals(test.getAmount(DevCard.AmountType.UNPLAYABLE), 3);
    }

    //    @Test
    //    public void testSerialization(){
    //        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(DevCards.class, new DevCards.OldDevCardsAdapter());
    //        Gson gson = gsonBuilder.create();
    //        assertEquals("{\"monopoly\":3,\"monument\":2,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":5}", gson.toJson(list));
    //
    //        gsonBuilder = new GsonBuilder().registerTypeAdapter(DevCards.class, new DevCards.NewDevCardsAdapter());
    //        gson = gsonBuilder.create();
    //        assertEquals("{\"monopoly\":0,\"monument\":3,\"roadBuilding\":4,\"soldier\":2,\"yearOfPlenty\":0}", gson.toJson(list));
    //    }

    @Test public void testSerialization()
    {
        String json = list.toString(DevCard.AmountType.PLAYABLE);
        assertEquals("{\"monopoly\":3,\"monument\":2,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":5}", json);

        json = list.toString(DevCard.AmountType.UNPLAYABLE);
        assertEquals("{\"monopoly\":0,\"monument\":3,\"roadBuilding\":4,\"soldier\":2,\"yearOfPlenty\":0}", json);

        DevCards first = new DevCards();
        assertEquals(0, first.totalCards());

        first = new DevCards(json, DevCard.AmountType.UNPLAYABLE);
        assertEquals(9, first.totalCards());
    }

}