package shared.definitions;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Holds the values of two dice, and has dice functionality
 * 
 * @author dtaylor
 *
 */
public class Dice
{
    private int die1;
    private int die2;

    /**
     * Rolling two dice and returning the results
     */
    public int rollDice()
    { 
        die1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        die2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        return die1 + die2; 
    }
}
