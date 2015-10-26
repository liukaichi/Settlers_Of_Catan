/**
 *
 */
package client.base;

import client.facade.ClientFacade;
import client.state.GameplayState;
import client.state.InitialState;

import java.util.Observable;
import java.util.Observer;

/**
 * @author cstaheli
 */
public abstract class ObserverController extends Controller implements Observer
{
    protected GameplayState state;

    /**
     * @param view the view that this controller uses.
     */
    protected ObserverController(IView view)
    {
        super(view);
        state = new InitialState();
        ClientFacade.getInstance().addObserver(this);
    }

    public void setState(GameplayState state)
    {
        this.state = state;
    }

    public GameplayState getState()
    {
        return state;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override public abstract void update(Observable observable, Object o);

}
