package shared.model.player;

import org.junit.Test;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;

import static org.junit.Assert.*;

/**
 * Created by Adrian on 10/1/2015.
 */
public class TradeOfferTest {

    @Test
    public void createTradeOffer(){
        try {
            Player player = new Player();
            Player player2 = new Player();

            player.getPlayerInfo().setPlayerIndex(0);
            player2.getPlayerInfo().setPlayerIndex(1);

            TradeOffer offer = player.createOffer(player2);

            assertEquals(0, offer.getSender());
            assertEquals(1, offer.getReceiver());
            assertEquals(0, offer.getReceive().totalResources());
            assertEquals(0, offer.getSend().totalResources());

            offer.addToReceive(ResourceType.SHEEP, 2);
            offer.addToSend(ResourceType.BRICK, 2);

            assertEquals(2, offer.getSend().getResource(ResourceType.BRICK).getAmount());
            assertEquals(2, offer.getReceive().getResource(ResourceType.SHEEP).getAmount());

        } catch (CatanException e) {
            e.printStackTrace();
        }

    }
}