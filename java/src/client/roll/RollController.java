package client.roll;

import java.util.Observable;

import client.base.Controller;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController
{

    private IRollResultView resultView;

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

    @Override
    public void rollDice()
    {

        getResultView().showModal();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        // TODO Auto-generated method stub

    }

}
