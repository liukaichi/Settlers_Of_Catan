package server.manager;

import client.data.PlayerInfo;
import server.ServerModel;
import server.facade.AbstractServerFacade;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.exceptions.CatanException;
import shared.model.player.Player;

/**
 * Created by cstaheli on 12/9/2015.
 */
public class AIUser extends PlayerInfo
{
    private static final long serialVersionUID = 8357080919526279951L;
    private AITurn turn;

    public AIUser(int id, String username, CatanColor color, AIType type)
    {
        super(id, username, color);
        switch (type)
        {
        case LARGEST_ARMY:
            turn = new LargestArmyTurn();
            break;
        case LONGEST_ROAD:
            turn = new LongestRoadTurn();
            break;
        }
    }

    public void playTurn(int gameID)
    {
        turn.execute(gameID);
    }

    private interface AITurn
    {
        void execute(int gameID);
    }

    private class LargestArmyTurn implements AITurn
    {
        @Override public void execute(int gameID)
        {
            AbstractServerFacade facade = AbstractServerFacade.getInstance();
            try
            {
                ServerModel model = GameManager.getInstance().getGame(gameID);
                Player player = model.getPlayer(getPlayerIndex());
                if (player.canBuyDevCard())
                {
                    player.buyDevCard();
                } else if (player.canPlayDevCard(DevCardType.SOLDIER))
                {
                    player.playDevCard(DevCardType.SOLDIER);
                }
                facade.finishTurn(gameID, getPlayerIndex());
            } catch (CatanException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class LongestRoadTurn implements AITurn
    {

        @Override public void execute(int gameID)
        {
            AbstractServerFacade facade = AbstractServerFacade.getInstance();
            try
            {

                facade.finishTurn(gameID, getPlayerIndex());
            } catch (CatanException e)
            {
                e.printStackTrace();
            }
        }
    }
}
