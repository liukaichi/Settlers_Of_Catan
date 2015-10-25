/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.roll.RollController;
import shared.definitions.Dice;

import java.util.logging.Logger;

/**
 * This state allows all of the rolling options.
 */
public class RollingState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public RollingState(ObserverController controller)
    {
        super(controller);
        showModal();
    }

    @Override public int rollDice(Dice dice)
    {
        int diceRollResult = -1;
        if (controller instanceof RollController)
        {
            RollController rollController = (RollController) controller;
            diceRollResult = dice.rollDice();
            rollController.getResultView().setRollValue(diceRollResult);
            facade.rollDice(diceRollResult);
            rollController.getResultView().showModal();
        }
        else{
            LOGGER.severe("The controller who called rollDice() was not RollController");
        }

        return diceRollResult;
    }

    @Override public void showModal()
    {
        ((RollController) controller).getRollView().showModal();
    }
}
