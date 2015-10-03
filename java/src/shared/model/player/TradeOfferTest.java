package shared.model.player;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import shared.definitions.*;
import shared.definitions.exceptions.CatanException;

/**
 * Created by Adrian on 10/1/2015.
 */
public class TradeOfferTest
{

    @Test
    public void createTradeOffer()
    {
        try
        {
            // FIRST CONSTRUCTOR
            Player player = new Player();
            Player player2 = new Player();

            player.getPlayerInfo().setPlayerIndex(0);
            player2.getPlayerInfo().setPlayerIndex(1);

            TradeOffer offer = player.createOffer(player2);

            assertEquals(0, offer.getSender());
            assertEquals(1, offer.getReceiver());
            assertEquals(0, offer.getOffer().totalResources());

            offer.addToOffer(ResourceType.SHEEP, 2);
            offer.addToOffer(ResourceType.BRICK, -2);

            assertEquals(-2, offer.getOffer().getResource(ResourceType.BRICK).getAmount());
            assertEquals(2, offer.getOffer().getResource(ResourceType.SHEEP).getAmount());

            // SECOND CONSTRUCTOR
            TradeOffer offer2 = new TradeOffer(PlayerIndex.fromInt(0), PlayerIndex.fromInt(1), 1, 1, -1, -1, 1);
            assertEquals(0, offer2.getSender());
            assertEquals(1, offer2.getReceiver());
            assertEquals(1, offer2.getOffer().getResource(ResourceType.BRICK).getAmount());
            assertEquals(1, offer2.getOffer().getResource(ResourceType.ORE).getAmount());
            assertEquals(-1, offer2.getOffer().getResource(ResourceType.SHEEP).getAmount());
            assertEquals(-1, offer2.getOffer().getResource(ResourceType.WHEAT).getAmount());
            assertEquals(1, offer2.getOffer().getResource(ResourceType.WOOD).getAmount());

        }
        catch (CatanException e)
        {
            e.printStackTrace();
        }

    }

    /**
     *
     * Method: toString()
     *
     */
    @Test
    public void testSerialize() throws Exception
    {
        Player player = new Player();
        Player player2 = new Player();

        player.getPlayerInfo().setPlayerIndex(0);
        player2.getPlayerInfo().setPlayerIndex(1);

        TradeOffer offer = player.createOffer(player2);

        /*
         * Gson gson = new GsonBuilder().setPrettyPrinting().create(); String
         * json = gson.toJson(offer.toString());
         */
        System.out.println(offer.toString());

        offer.addToOffer(ResourceType.SHEEP, 2);
        offer.addToOffer(ResourceType.BRICK, -2);

        System.out.println(offer.toString());

        // Second offer to print
        TradeOffer offer2 = new TradeOffer(PlayerIndex.fromInt(0), PlayerIndex.fromInt(1), 1, 1, -1, -1, 1);
        System.out.println(offer2.toString());
    }

    @Test
    public void testDeserialize() throws Exception
    {
        TradeOffer offer = new TradeOffer(PlayerIndex.fromInt(0), PlayerIndex.fromInt(1), 1, 1, -1, -1, 1);
        String json = offer.toString();
        TradeOffer offer2 = new TradeOffer(json);

        assertEquals(offer.getReceiver(), offer2.getReceiver());
        assertEquals(offer.getSender(), offer2.getSender());
        assertEquals(offer.getOffer().getResource(ResourceType.BRICK).getAmount(),
                offer2.getOffer().getResource(ResourceType.BRICK).getAmount());

        assertEquals(offer.getOffer().getResource(ResourceType.ORE).getAmount(),
                offer2.getOffer().getResource(ResourceType.ORE).getAmount());

        assertEquals(offer.getOffer().getResource(ResourceType.SHEEP).getAmount(),
                offer2.getOffer().getResource(ResourceType.SHEEP).getAmount());

        assertEquals(offer.getOffer().getResource(ResourceType.WHEAT).getAmount(),
                offer2.getOffer().getResource(ResourceType.WHEAT).getAmount());

        assertEquals(offer.getOffer().getResource(ResourceType.WOOD).getAmount(),
                offer2.getOffer().getResource(ResourceType.WOOD).getAmount());
    }
}