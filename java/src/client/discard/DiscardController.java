package client.discard;

import java.util.Observable;

import client.base.Controller;
import client.misc.IWaitView;
import shared.definitions.ResourceType;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController
{

    private IWaitView waitView;

    /**
     * DiscardController constructor
     * 
     * @param view
     *        View displayed to let the user select cards to discard
     * @param waitView
     *        View displayed to notify the user that they are waiting for other
     *        players to discard
     */
    public DiscardController(IDiscardView view, IWaitView waitView)
    {

        super(view);

        this.waitView = waitView;
    }

    public IDiscardView getDiscardView()
    {
        return (IDiscardView) super.getView();
    }

    public IWaitView getWaitView()
    {
        return waitView;
    }

    @Override
    public void increaseAmount(ResourceType resource)
    {

    }

    @Override
    public void decreaseAmount(ResourceType resource)
    {

    }

    @Override
    public void discard()
    {

        getDiscardView().closeModal();
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
