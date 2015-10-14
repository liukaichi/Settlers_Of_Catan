package client.communication;

import java.util.Observable;

import client.base.Controller;

/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController
{

    public ChatController(IChatView view)
    {

        super(view);
    }

    @Override
    public IChatView getView()
    {
        return (IChatView) super.getView();
    }

    @Override
    public void sendMessage(String message)
    {

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
