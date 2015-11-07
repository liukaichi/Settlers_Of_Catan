/**
 *
 */
package client.gui.base;

import client.facade.ClientFacade;
import client.state.GameplayState;
import client.state.InitialState;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

/**
 * @author cstaheli
 */
public abstract class ObserverController extends Controller implements Observer
{
    protected GameplayState state;
    protected ClientFacade facade;
    protected static Logger LOGGER;

    /**
     * @param view the view that this controller uses.
     */
    protected ObserverController(IView view)
    {
        super(view);
        state = new InitialState();
        LOGGER = Logger.getLogger(this.getClass().getName());
        facade = ClientFacade.getInstance();
        facade.addObserver(this);
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
