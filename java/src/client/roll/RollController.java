package client.roll;

import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.definitions.Dice;

import java.util.Observable;

/**
 * Implementation for the roll controller
 */
public class RollController extends ObserverController implements IRollController
{

    private IRollResultView resultView;
    private Dice dice;

    /**
     * RollController constructor
     *
     * @param view
     *        Roll view
     * @param resultView
     *        Roll result view
     */
    public RollController(IRollView view, IRollResultView resultView)
    {

        super(view);

        setResultView(resultView);

        dice = new Dice();
    }

    public IRollResultView getResultView()
    {
        return resultView;
    }

    public void setResultView(IRollResultView resultView)
    {
        this.resultView = resultView;
    }

    public IRollView getRollView()
    {
        return (IRollView) getView();
    }

    /**
     * Gets called by the view when the roll dice button is pressed
     * To Do (not necessarily in this class):
     * close the rollView
     * Generate Dice results
     * Send Roll Results to server
     * set Result view value to roll value
     * getResultView.showModel()
     */
    @Override public void rollDice()
    {
    	getRollView().closeModal();
    	int diceRollResult = dice.rollDice();
    	ClientFacade.getInstance().rollDice(diceRollResult);
    	getResultView().setRollValue(diceRollResult);
        getResultView().showModal();
    }

    @Override public void update(Observable observable, Object o)
    {

    }
}
