package client.poller;

import client.facade.ClientFacade;
import server.proxy.IProxy;
import shared.model.ClientModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class will periodically pollTask the server to see if the model has been
 * changed.<br>
 * <br>
 * this will ensure that every player's Model is updated at all times. It will
 * use java.util.Timer, or java.util.TimerTask to periodically pollTask every 3-5
 * seconds. <br>
 * <br>
 * This Poller supports dependency injection with the IProxy interface.
 */
public class Poller
{

    private IProxy proxy;
    private PollTask pollTask;
    private int currentVersion = 0;

    Poller(IProxy proxy)
    {
        this.proxy = proxy;
        pollTask = new PollTask(this.proxy);
        Timer timer;
        timer = new Timer();
        timer.schedule(pollTask, 0, 3000);
    }

    public void setPollProxy(IProxy newProxy)
    {
        pollTask.setProxy(newProxy);
    }

    public int getCurrentVersion(){
        return currentVersion;
    }

    private PollTask getPollTask(){
        return pollTask;
    }


    /*---------POLL INNER CLASS----------*/

    /**
     * This class extends TimerTask. This will allow for Poller to be able to run PollTask() on regular intervals
     */
    public class PollTask extends TimerTask
    {

        private IProxy proxy;

        PollTask(IProxy proxy)
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
         * @pre When the model changes it must update the version number.
         * @post The ClientFacade will hold an updated model.
         * @return returns true if model was updated. False if model stayed the same.
         */
        public boolean poll()
        {
            ClientModel serverModel = proxy.getGameState(currentVersion);
            if (serverModel != null)
            {
                updateModel(serverModel);
                return true;
            }
            return false;
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
