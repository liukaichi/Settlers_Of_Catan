/**
 * 
 */
package client.state;

import java.util.logging.Logger;

import shared.definitions.Dice;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;

/**
 * @author cstaheli
 *
 */
public class RollingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Override
    public Dice rollDice()
    {
        return super.rollDice();
    }
}
