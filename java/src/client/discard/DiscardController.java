package client.discard;

import client.base.ObserverController;
import client.misc.IWaitView;
import shared.definitions.ResourceType;
import shared.model.ClientModel;

import java.util.Observable;

/**
 * Discard controller implementation
 */
public class DiscardController extends ObserverController implements IDiscardController
{

    private IWaitView waitView;

    /**
     * DiscardController constructor
     *
     * @param view
     *        View displayed to let the user select cards to discard
     * @param waitView
     *        View displayed to notify the user that they are waiting for other
     *        players to discard disable discardbutton in the getDiscardView()
     *
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

    /**
     * add recource to a list to be discarded
     * getDiscardView().setStateMessage(message); where message is how many out
     * of required amount have been selected enable discard button if enough
     * cards (but not more) have been selected
     */
    @Override
    public void increaseAmount(ResourceType resource)
    {
        state.increaseAmount(resource);
    }

    /**
     * undoes things you did in increase function
     */
    @Override
    public void decreaseAmount(ResourceType resource)
    {
        state.decreaseAmount(resource);
    }

    /**
     * send request to server with a map of resource types and how many of each
     * are discarded
     */
    @Override
    public void discard()
    {
        state.discardResources();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;
        state.update(this, model, arg);
    }
}
