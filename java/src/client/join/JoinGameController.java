package client.join;

import client.base.IAction;
import client.base.ObserverController;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import client.misc.IMessageView;
import shared.communication.CreateGameRequest;
import shared.communication.ListGamesResponse;
import shared.definitions.CatanColor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * Implementation for the join game controller
 */
public class JoinGameController extends ObserverController implements IJoinGameController
{
    private static final Logger LOGGER = Logger.getLogger(JoinGameController.class.getName());
    private static JoinGameController instance;
    private INewGameView newGameView;
    private ISelectColorView selectColorView;
    private IMessageView messageView;
    private IAction joinAction;
    private ClientFacade facade;
    private GameInfo currentGame;
    private boolean joiningGame;
    private ActionListener action = new ActionListener()
    {
        @Override public void actionPerformed(ActionEvent e)
        {
            LOGGER.info("JOIN GAME CONTROLLER TIMER UPDATING");
            updateView();
        }
    };
    final private Timer timer = new Timer(2000, action);

    /**
     * JoinGameController constructor
     *
     * @param view            Join game view
     * @param newGameView     New game view
     * @param selectColorView Select color view
     * @param messageView     Message view (used to display error messages that occur while the
     *                        user is joining a game)
     */
    public JoinGameController(IJoinGameView view, INewGameView newGameView, ISelectColorView selectColorView,
            IMessageView messageView)
    {

        super(view);
        instance = this;
        setNewGameView(newGameView);
        setSelectColorView(selectColorView);
        setMessageView(messageView);
        facade = ClientFacade.getInstance();
    }

    public static JoinGameController getInstance()
    {
        return instance;
    }

    public IJoinGameView getJoinGameView()
    {

        return (IJoinGameView) super.getView();
    }

    /**
     * Returns the action to be executed when the user joins a game
     *
     * @return The action to be executed when the user joins a game
     */
    public IAction getJoinAction()
    {

        return joinAction;
    }

    /**
     * Sets the action to be executed when the user joins a game
     *
     * @param value The action to be executed when the user joins a game
     */
    public void setJoinAction(IAction value)
    {

        joinAction = value;
    }

    public INewGameView getNewGameView()
    {

        return newGameView;
    }

    public void setNewGameView(INewGameView newGameView)
    {
        this.newGameView = newGameView;
    }

    public ISelectColorView getSelectColorView()
    {

        return selectColorView;
    }

    public void setSelectColorView(ISelectColorView selectColorView)
    {

        this.selectColorView = selectColorView;
    }

    public IMessageView getMessageView()
    {

        return messageView;
    }

    public void setMessageView(IMessageView messageView)
    {

        this.messageView = messageView;
    }

    /**
     * get list of games from server, save them into your pre-game model
     * JoinGameView().setGames(list of games, your player info) updateView
     */
    @Override public void start()
    {
        updateView();
        LOGGER.info("JOIN GAME CONTROLLER TIMER STARTING");
        timer.start();
    }

    private void updateView()
    {
        ListGamesResponse response = facade.listGames();
        List<GameInfo> games = response.getGames();
        getJoinGameView().setGames(response.getGames().toArray(new GameInfo[games.size()]),
                ClientFacade.getInstance().getClientPlayer());
        if (currentGame != null)
        {
            for (GameInfo game : games)
            {
                if (game.getId() == currentGame.getId())
                {
                    currentGame = game;
                    updateColorSelector(currentGame);
                    break;
                }
            }
        }
        if (!getSelectColorView().isModalShowing() && !getNewGameView().isModalShowing())
        {
            if (getJoinGameView().isModalShowing())
            {
                getJoinGameView().closeModal();
            }
            getJoinGameView().showModal();
        }
    }

    @Override public void startCreateNewGame()
    {
        if (getNewGameView().isModalShowing())
            getNewGameView().closeModal();
        getNewGameView().showModal();
    }

    @Override public void cancelCreateNewGame()
    {
        if (getNewGameView().isModalShowing())
            getNewGameView().closeModal();
    }

    /**
     * <ul>
     * <li>Create a new Game board based on the options in the View(Random or
     * not)
     * <li>Send create game request to server
     * <li>update Game List closeModal
     * </ul>
     */
    @Override public void createNewGame()
    {
        boolean randomTiles = getNewGameView().getRandomlyPlaceHexes();
        boolean randomNumbers = getNewGameView().getRandomlyPlaceNumbers();
        boolean randomPorts = getNewGameView().getUseRandomPorts();
        String name = getNewGameView().getTitle();

        facade.createNewGame(new CreateGameRequest(randomTiles, randomNumbers, randomPorts, name));
        if (getNewGameView().isModalShowing())
            getNewGameView().closeModal();
        this.start();
    }

    /**
     * <ul>
     * <li>Iterate through player in GameInfo and disable each color that has
     * already been used in ColorSelectView
     * <li>check if you are already in
     * <li>if so, call JoinGame with the color you had already picked
     * </ul>
     */
    @Override public void startJoinGame(GameInfo game)
    {
        this.currentGame = game;
        joiningGame = true;
        updateColorSelector(game);
    }

    private void updateColorSelector(GameInfo game)
    {
        resetColors();
        disablePlayerColors(game);
        if (joiningGame)
        {
            if (getSelectColorView().isModalShowing())
                getSelectColorView().closeModal();
            getSelectColorView().showModal();
        }
    }

    private void resetColors()
    {
        for (CatanColor color : CatanColor.values())
        {
            getSelectColorView().setColorEnabled(color, true);
        }
    }

    private void disablePlayerColors(GameInfo game)
    {
        List<PlayerInfo> players = game.getPlayers();
        for (PlayerInfo player : players)
        {
            getSelectColorView().setColorEnabled(player.getColor(), false);
            if (facade.getClientPlayer().equals(player))
            {
                getSelectColorView().setColorEnabled(player.getColor(), true);
            }
        }
    }

    @Override public void cancelJoinGame()
    {
        joiningGame = false;
        if (getJoinGameView().isModalShowing())
            getJoinGameView().closeModal();
    }

    /**
     * call join game on server
     */
    @Override public void joinGame(CatanColor color)
    {

        updateView();
        boolean colorAlreadyExists = false;
        for (PlayerInfo info : currentGame.getPlayers())
        {
            if (info.getColor().equals(color) && info.getId() != facade.getClientPlayer().getId())
            {
                colorAlreadyExists = true;
                break;
            }
        }
        if (!colorAlreadyExists)
        {
            facade.joinGame(currentGame.getId(), color);
            // If join succeeded
            getSelectColorView().closeModal();
            getJoinGameView().closeModal();
            LOGGER.info("JOIN GAME CONTROLLER TIMER STOPPING");
            timer.stop();
            joinAction.execute();
        }

    }

    @Override public void update(Observable observable, Object o)
    {

    }

    /**
     * Resets the current game.
     */
    public void resetCurrentGame()
    {
        currentGame = null;
    }
}
