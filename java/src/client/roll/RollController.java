package client.roll;

import client.base.Controller;
import client.facade.ClientFacade;
import shared.definitions.Dice;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController
{

    private IRollResultView resultView;
    private Dice dice;

    /**
     * RollController constructor
     *
     * @param view       Roll view
     * @param resultView Roll result view
     */
    public RollController(IRollView view, IRollResultView resultView)
    {

        super(view);

        setResultView(resultView);
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
}
