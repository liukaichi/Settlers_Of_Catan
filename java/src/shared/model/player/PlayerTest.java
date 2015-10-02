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
            Player player2 = new Player();
            player.createOffer(player2);
        } catch (CatanException e){

        }

    }

    @Test
    public void testGetPlayerInfo() throws Exception {

    }

    @Test
    public void testGetName() throws Exception {

    }
}