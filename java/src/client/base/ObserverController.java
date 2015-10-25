/**
 * 
 */
package client.base;

import client.facade.ClientFacade;
import client.state.GameplayState;

import java.util.*;

/**
 * @author cstaheli
 *
 */
public abstract class ObserverController extends Controller implements Observer
{
    protected GameplayState state;
    /**
     * @param view
     */
    protected ObserverController(IView view)
    {
        super(view);
        ClientFacade.getInstance().addObserver(this);
    }

    public void setState(GameplayState state)
    {
        this.state = state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public abstract void update(Observable observable, Object o);

}
