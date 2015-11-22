package shared.model.bank;

import org.junit.Before;
import org.junit.Test;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.card.DevCard;
import shared.model.bank.resource.Resource;
import shared.model.bank.structure.BankStructure;
import shared.model.player.Player;

import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by Adrian on 9/30/2015.
 */
public class PlayerBankTest
{

    private PlayerBank test;
    private Bank bank;

    @Before public void setUp()
    {
        test = new PlayerBank();
        bank = new Bank(true);

        // Make sure bank can receive resources
        try
        {
            bank.giveResource(ResourceType.BRICK, 4);
            bank.giveResource(ResourceType.WOOD, 4);
            bank.giveResource(ResourceType.WHEAT, 4);
            bank.giveResource(ResourceType.SHEEP, 4);
            bank.giveResource(ResourceType.ORE, 4);
        } catch (InsufficientResourcesException e)
        {
            e.printStackTrace();
        }

    }

    // Helper methods to get playerResources
    public Resource sheep()
    {
        return test.getResources().getResource(ResourceType.SHEEP);
    }

    public Resource ore()
    {
        return test.getResources().getResource(ResourceType.ORE);
    }

    public Resource wood()
    {
        return test.getResources().getResource(ResourceType.WOOD);
    }

    public Resource wheat()
    {
        return test.getResources().getResource(ResourceType.WHEAT);
    }

    public Resource brick()
    {
        return test.getResources().getResource(ResourceType.BRICK);
    }

    // Helper methods to get global bank resources
    public Resource gameSheep()
    {
        return bank.getResources().getResource(ResourceType.SHEEP);
    }

    public Resource gameBrick()
    {
        return bank.getResources().getResource(ResourceType.BRICK);
    }

    public Resource gameOre()
    {
        return bank.getResources().getResource(ResourceType.ORE);
    }

    public Resource gameWheat()
    {
        return bank.getResources().getResource(ResourceType.WHEAT);
    }

    public Resource gameWood()
    {
        return bank.getResources().getResource(ResourceType.WOOD);
    }

    @Test public void testCanBuyDevCard()
    {
        assertFalse(test.canBuyDevCard());

        sheep().addResource(1);
        wheat().addResource(1);
        ore().addResource(1);
        assertTrue(test.canBuyDevCard());
    }

    @Test public void testBuyDevCard()
    {
        try
        {
            test.buyDevCard();

            sheep().addResource(1);
            wheat().addResource(1);
            ore().addResource(1);
            test.buyDevCard();

            assertEquals(1, test.getDevCards().totalCards());
            assertEquals(24, test.getDevCardDeck().size());

            assertEquals(0, sheep().getAmount());
            assertEquals(0, wheat().getAmount());
            assertEquals(0, ore().getAmount());

            assertEquals(16, gameWheat().getAmount());
            assertEquals(16, gameSheep().getAmount());
            assertEquals(16, gameOre().getAmount());

        } catch (InsufficientResourcesException e)
        {
            e.printStackTrace();
        }
    }

    @Test public void testCanBuyRoad()
    {

        assertFalse(test.canBuyRoad());

        brick().addResource(3);
        wood().addResource(2);
        assertTrue(test.canBuyRoad());
    }

    @Test public void testBuyRoad()
    {
        try
        {
            test.buyRoad(false);

            brick().addResource(3);
            wood().addResource(2);
            test.buyRoad(false);

            assertEquals(1,
                    test.getStructures().getStructure(StructureType.ROAD).getAmount(BankStructure.AmountType.BUILT));

            assertEquals(2, brick().getAmount());
            assertEquals(1, wood().getAmount());

            assertEquals(16, gameBrick().getAmount());
            assertEquals(16, gameWood().getAmount());

        } catch (InsufficientResourcesException e)
        {
            e.printStackTrace();
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
    }

    @Test public void testCanBuySettlement()
    {
        assertFalse(test.canBuySettlement());
        brick().addResource(1);
        wood().addResource(1);
        wheat().addResource(2);
        sheep().addResource(1);

        assertTrue(test.canBuySettlement());
    }

    @Test public void testBuySettlement()
    {
        try
        {
            test.buySettlement(false);

            brick().addResource(1);
            wood().addResource(1);
            wheat().addResource(2);
            sheep().addResource(1);

            test.buySettlement(false);

            assertEquals(1, test.getStructures().getStructure(StructureType.SETTLEMENT)
                    .getAmount(BankStructure.AmountType.BUILT));

            assertEquals(16, gameSheep().getAmount());
            assertEquals(16, gameWood().getAmount());
            assertEquals(16, gameWheat().getAmount());
            assertEquals(16, gameBrick().getAmount());

            assertEquals(0, brick().getAmount());
            assertEquals(0, wood().getAmount());
            assertEquals(1, wheat().getAmount());
            assertEquals(0, sheep().getAmount());

        } catch (InsufficientResourcesException e)
        {
            e.printStackTrace();
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
    }

    @Test public void testCanBuyCity()
    {
        assertFalse(test.canBuyCity());

        wheat().addResource(3);
        ore().addResource(3);
        assertFalse(test.canBuyCity());

        try
        {
            test.getStructures().getStructure(StructureType.SETTLEMENT).addAmountBuilt(1);
            assertTrue(test.canBuyCity());
        } catch (CatanException e)
        {
            e.printStackTrace();
        }

    }

    @Test public void testBuyCity()
    {
        try
        {
            test.buyCity();

            wheat().addResource(3);
            ore().addResource(3);
            test.getStructures().getStructure(StructureType.SETTLEMENT).addAmountBuilt(1);
            test.buyCity();

            assertEquals(1,
                    test.getStructures().getStructure(StructureType.CITY).getAmount(BankStructure.AmountType.BUILT));
            assertEquals(0, test.getStructures().getStructure(StructureType.SETTLEMENT)
                    .getAmount(BankStructure.AmountType.BUILT));

            assertEquals(1, wheat().getAmount());
            assertEquals(0, ore().getAmount());

            assertEquals(17, gameWheat().getAmount());
            assertEquals(18, gameOre().getAmount());

        } catch (InsufficientResourcesException e)
        {
            e.printStackTrace();
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
    }

    @Test public void testCanPlayDevCard()
    {

        assertFalse(test.canPlayDevCard(DevCardType.MONOPOLY));

        test.getDevCards().getCard(DevCardType.MONOPOLY).addCard(DevCard.AmountType.PLAYABLE, 1);
        assertTrue((test.canPlayDevCard(DevCardType.MONOPOLY)));

        assertFalse(test.canPlayDevCard(DevCardType.YEAR_OF_PLENTY));

        test.getDevCards().getCard(DevCardType.YEAR_OF_PLENTY).addCard(DevCard.AmountType.UNPLAYABLE, 1);
        assertFalse((test.canPlayDevCard(DevCardType.YEAR_OF_PLENTY)));
    }

    @Test public void testPlayDevCard()
    {
        // need to implement devcard actions
        Player player;
        try
        {
            player = new Player();

            test.playDevCard(DevCardType.MONUMENT, null);

            assertEquals(0, test.getMonuments());
            test.getDevCards().getCard(DevCardType.MONUMENT).addCard(DevCard.AmountType.PLAYABLE, 2);
            test.playDevCard(DevCardType.MONUMENT, null);

            assertEquals(1, test.getDevCards().getCard(DevCardType.MONUMENT).getAmount(DevCard.AmountType.PLAYABLE));
            assertEquals(1, test.getMonuments());
        } catch (CatanException e)
        {
            e.printStackTrace();
        }
    }

    @Test public void testCanAccessPort()
    {
        assertFalse(test.canAccessPort(PortType.SHEEP));

        test.addPort(PortType.SHEEP);
        assertTrue(test.canAccessPort(PortType.SHEEP));
        assertFalse(test.canAccessPort(PortType.BRICK));
    }

    @Test public void devCardDecksShouldBeEqualAcrossInstances()
    {

        PlayerBank test2 = new PlayerBank();
        Stack<DevCardType> deck2 = test2.getDevCardDeck();

        PlayerBank test3 = new PlayerBank();
        Stack<DevCardType> deck3 = test3.getDevCardDeck();

        PlayerBank test4 = new PlayerBank();
        Stack<DevCardType> deck4 = test4.getDevCardDeck();

        assertSame(deck2, deck3);
        assertSame(deck3, deck4);

        deck3.pop();
        assertSame(deck3, deck4);

        deck4.pop();
        assertSame(deck3, deck4);
    }

    @Test public void resourcesShouldBeEqualAcrossInstances()
    {
        PlayerBank test2 = new PlayerBank();
        PlayerBank test3 = new PlayerBank();

        try
        {
            // Since we constructed new PlayerBanks, Bank gets reinitialized with no resources
            bank.takeResource(ResourceType.SHEEP, 15);
            test2.takeResource(ResourceType.SHEEP, 3);
            test3.takeResource(ResourceType.SHEEP, 3);
        } catch (CatanException e)
        {
            e.printStackTrace();
        }

        test2.payResource(ResourceType.SHEEP, 2);
        assertEquals(17, gameSheep().getAmount());

        test3.payResource(ResourceType.SHEEP, 1);
        assertEquals(18, gameSheep().getAmount());
    }

}