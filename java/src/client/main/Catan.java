package client.main;

import client.base.IAction;
import client.catan.CatanPanel;
import client.facade.ClientFacade;
import client.join.*;
import client.login.LoginController;
import client.login.LoginView;
import client.misc.MessageView;

import javax.swing.*;
import java.util.logging.Logger;

/**
 * Main entry point for the Catan program
 */
@SuppressWarnings("serial")
public class Catan extends JFrame
{

    private static CatanPanel catanPanel;
    private static String host;
    private static String port;
    private static Catan catan;
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
                catan = new Catan();
                if (args.length == 2)
                {
                    host = args[0];
                    port = args[1];
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
    public static Catan getInstance()
{
    return catan;
}

    public void reset(){
        this.setVisible(false);
        this.dispose();

        catan = new Catan();
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

    }
}
