/**
 * 
 */
package client.base;

import java.util.*;

/**
 * @author cstaheli
 *
 */
public abstract class ObserverController extends Controller implements Observer
{

    /**
     * @param view
     */
    protected ObserverController(IView view)
    {
        super(view);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable arg0, Object arg1)
    {
        // TODO Auto-generated method stub

    }

}
