package client.poller;

import client.facade.ClientFacade;
import server.proxy.IProxy;
import shared.model.ClientModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will periodically poll the server to see if the model has been
 * changed.<br>
 * <br>
 * this will ensure that every player's Model is updated at all times. It will
 * use java.util.Timer, or java.util.TimerTask to periodically poll every 3-5
 * seconds. <br>
 * <br>
 * This Poller supports dependency injection with the IProxy interface.
 */
public class Poller
{

    private IProxy proxy;
    private Poll poll;

    Poller(IProxy proxy)
    {
        this.proxy = proxy;
        poll = new Poll(this.proxy);
        Timer timer;
        timer = new Timer();
        timer.schedule(poll, 0, 2000);
    }

    public void setPollProxy(IProxy newProxy)
    {
        poll.setProxy(newProxy);
    }



    /*---------POLL INNER CLASS----------*/

    /**
     * This class extends TimerTask. This will allow for Poller to be able to run Poll() on regular intervals
     */
    class Poll extends TimerTask
    {
        private int currentVersion = 0;
        private IProxy proxy;

        Poll(IProxy proxy)
        {
            this.proxy = proxy;
        }

        public void setProxy(IProxy proxy)
        {
            this.proxy = proxy;
        }

        /**
         * Goes through the Proxy to check for updates in the server's model.
         * Executes updateModel() if model has changed.
         */
        public void poll()
        {
            ClientModel serverModel = proxy.getGameState(currentVersion);
            if (serverModel.getVersion() != currentVersion)
            {
                updateModel(serverModel);
            }
        }

        /**
         * Replaces the old model with the given new one.
         *
         * @param newModel the new model received from the server.
         */

        public void updateModel(ClientModel newModel)
        {

            ClientFacade.getInstance().setModel(newModel);
            currentVersion = newModel.getVersion();

        }

        @Override public void run()
        {
            poll();
        }
    }

}
