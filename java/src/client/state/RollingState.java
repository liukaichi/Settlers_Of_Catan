/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.roll.RollController;
import client.turntracker.TurnTrackerController;
import shared.definitions.Dice;
import shared.definitions.TurnStatus;

import java.util.logging.Logger;

/**
 * This state allows all of the rolling options.
 */
public class RollingState extends GameplayState
{

    public RollingState(ObserverController controller)
    {
        super(controller);
        currentTurnStatus = TurnStatus.Rolling;
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

    @Override public void updateView()
    {
        if (controller instanceof RollController)
        {
            ((RollController) controller).getRollView().showModal();
        }
        else if (controller instanceof TurnTrackerController)
        {
            ((TurnTrackerController) controller).updatePlayers(facade.getModel());
        }
    }

    @Override public void setTurnTrackerInfo(ObserverController newController)
    {
        TurnTrackerController turnTrackerController = ((TurnTrackerController) newController);
        turnTrackerController.getView().updateGameState("Rolling the Dice, homeslice!", false);
    }
}
