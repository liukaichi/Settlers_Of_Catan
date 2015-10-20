package client.main;

import java.util.logging.Logger;

import javax.swing.*;

import client.base.IAction;
import client.catan.CatanPanel;
import client.facade.ClientFacade;
import client.join.*;
import client.login.*;
import client.misc.MessageView;

/**
 * Main entry point for the Catan program
 */
@SuppressWarnings("serial")
public class Catan extends JFrame
{

    private CatanPanel catanPanel;

    public Catan()
    {

        client.base.OverlayView.setWindow(this);

        this.setTitle("Settlers of Catan");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        catanPanel = new CatanPanel();
        this.setContentPane(catanPanel);

        display();
    }

    private void display()
    {
        pack();
        setVisible(true);
    }

    //
    // Main
    //

    public static void main(final String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Catan();
                if (args.length == 2)
                {
                    String host = args[0];
                    String port = args[1];
                    ClientFacade.getInstance().setProxy(host, port);
                    Logger LOGGER = Logger.getLogger(Catan.class.getName());
                    LOGGER.info("host: " + host + ", port: " + port);
                }

                PlayerWaitingView playerWaitingView = new PlayerWaitingView();
                final PlayerWaitingController playerWaitingController = new PlayerWaitingController(playerWaitingView);
                playerWaitingView.setController(playerWaitingController);

                JoinGameView joinView = new JoinGameView();
                NewGameView newGameView = new NewGameView();
                SelectColorView selectColorView = new SelectColorView();
                MessageView joinMessageView = new MessageView();
                final JoinGameController joinController = new JoinGameController(joinView, newGameView, selectColorView,
                        joinMessageView);
                joinController.setJoinAction(new IAction()
                {
                    @Override
                    public void execute()
                    {
                        playerWaitingController.start();
                    }
                });
                joinView.setController(joinController);
                newGameView.setController(joinController);
                selectColorView.setController(joinController);
                joinMessageView.setController(joinController);

                LoginView loginView = new LoginView();
                MessageView loginMessageView = new MessageView();
                LoginController loginController = new LoginController(loginView, loginMessageView);
                loginController.setLoginAction(new IAction()
                {
                    @Override
                    public void execute()
                    {
                        joinController.start();
                    }
                });
                loginView.setController(loginController);
                loginView.setController(loginController);

                loginController.start();
            }
        });
    }
}
