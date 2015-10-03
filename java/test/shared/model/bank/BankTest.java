package shared.model.bank;

import org.junit.Before;
import org.junit.Test;
import shared.definitions.DevCardType;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resources;

import java.util.Collections;
import java.util.Stack;

import static org.junit.Assert.*;

/**
 * Created by Adrian on 9/30/2015.
 */
public class BankTest {

    Bank test;
    Stack<DevCardType> deck;

    @Before
    public void setUp() {
        test = new Bank(false);
        deck = test.getDevCardDeck();
    }

    @Test
    public void devCardDeckShouldBeInitializedWith25DevCards() {
        deck = test.getDevCardDeck();
        assertEquals(25, deck.size());
    }

    @Test
    public void devCardDeckShouldHaveCorrectNumberofEachCard() {

        int monopoly = Collections.frequency(deck, DevCardType.MONOPOLY);
        int monument = Collections.frequency(deck, DevCardType.MONUMENT);
        int roadBuilding = Collections.frequency(deck, DevCardType.ROAD_BUILD);
        int yearOfPlenty = Collections.frequency(deck, DevCardType.YEAR_OF_PLENTY);
        int soldier = Collections.frequency(deck, DevCardType.SOLDIER);

        assertEquals(2, monopoly);
        assertEquals(2, roadBuilding);
        assertEquals(2, yearOfPlenty);
        assertEquals(5, monument);
        assertEquals(14, soldier);
    }

    @Test
    public void devCardsShouldBeInitializedWithZeroCards() {
        DevCards list = test.getDevCards();

        assertNotNull(list);
        assertEquals(0, list.totalCards());
    }

    @Test
    public void resourcesShouldBeInitializedWithZeroResources() {
        Resources list = test.getResources();

        assertNotNull(list);
        assertEquals(0, list.totalResources());
    }
}