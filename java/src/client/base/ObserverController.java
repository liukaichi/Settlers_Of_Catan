/**
 * 
 */
package client.base;

import client.facade.ClientFacade;
import client.state.GameplayState;
import client.state.InitialState;
import com.sun.deploy.util.SessionState;
import com.sun.org.apache.xml.internal.security.Init;

import java.util.*;

/**
 * @author cstaheli
 *
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

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public abstract void update(Observable observable, Object o);

}
