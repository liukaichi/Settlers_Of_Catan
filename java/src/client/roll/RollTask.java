package client.roll;

import java.util.TimerTask;

/**
 * Created by liukaichi on 10/24/2015.
 */
public class RollTask extends TimerTask
{
    RollView view;

    RollTask(RollView view)
    {
        this.view = view;
    }

    public void roll()
    {
        view.closeModal();
        view.getController().rollDice();
        cancel();
    }

    @Override public void run()
    {
        roll();
    }
}
