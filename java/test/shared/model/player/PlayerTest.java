package shared.model.player;

import client.main.Catan;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;
import shared.model.bank.Bank;
import shared.model.bank.card.DevCard;
import shared.model.bank.resource.Resource;

import static org.junit.Assert.*;

/**
 * Created by Adrian on 10/1/2015.
 */
public class PlayerTest {

    Player player;
    Bank gameBank;

    // Helper methods
    public Resource sheep(){
        return player.getBank().getResources().getResource(ResourceType.SHEEP);
    }
    public Resource wheat(){
        return player.getBank().getResources().getResource(ResourceType.WHEAT);
    }
    public Resource wood(){
        return player.getBank().getResources().getResource(ResourceType.WOOD);
    }
    public Resource brick(){
        return player.getBank().getResources().getResource(ResourceType.BRICK);
    }
    public Resource ore(){
        return player.getBank().getResources().getResource(ResourceType.ORE);
    }

    @Before
    public void setUp() throws Exception {
        player = new Player();
        gameBank = new Bank(true);

        player.getBank().takeResource(ResourceType.SHEEP, 7);
        player.getBank().takeResource(ResourceType.WOOD, 7);
        player.getBank().takeResource(ResourceType.WHEAT, 7);
        player.getBank().takeResource(ResourceType.BRICK, 7);
        player.getBank().takeResource(ResourceType.ORE, 7);
    }

    @Test
    public void createOffer() {
        try{
            player.getPlayerInfo().setPlayerIndex(0);
            Player player2 = new Player();
            player2.getPlayerInfo().setPlayerIndex(1);
            player.createOffer(player2);
        } catch (CatanException e){

        }

    }

    @Test
    public void testSerialization(){
        try {
            Player player = new Player();
            player.getPlayerInfo().setName("Wazowski");
            String json = player.toString();
            String expected = "{\"cities\":0,\"color\":\"white\",\"discarded\":false,\"monuments\":0,\"name\":\"Wazowski\"," +
                    "\"newDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0}," +
                    "\"oldDevCards\":{\"monopoly\":0,\"monument\":0,\"roadBuilding\":0,\"soldier\":0,\"yearOfPlenty\":0}," +
                    "\"playerIndex\":-1,\"playedDevCard\":false,\"playerID\":-1," +
                    "\"resources\":{\"brick\":0,\"ore\":0,\"sheep\":0,\"wheat\":0,\"wood\":0}," +
                    "\"roads\":0,\"settlements\":0,\"soldiers\":0,\"victoryPoints\":0}";
            assertEquals(expected, json);

            Player player2 = new Player();
            String json2 = player2.toString();
            assertNotEquals(json, json2);

            player2 = new Player(json);
            json2 = player2.toString();
            assertEquals(json, json2);

        } catch (CatanException e) {
            e.printStackTrace();
        }
    }

}