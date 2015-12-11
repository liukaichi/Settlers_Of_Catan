package server.manager;

import client.data.PlayerInfo;
import server.ServerModel;
import server.facade.AbstractServerFacade;
import shared.communication.moveCommands.AcceptTradeCommand;
import shared.communication.moveCommands.DiscardCardsCommand;
import shared.communication.moveCommands.FinishTurnCommand;
import shared.communication.moveCommands.RollNumberCommand;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.player.Player;

import java.io.Serializable;

/**
 * Created by cstaheli on 12/9/2015.
 */
public class AIUser extends PlayerInfo implements Serializable
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

    public void discard(int gameID)
    {
        AbstractServerFacade facade = AbstractServerFacade.getInstance();
        AIManager aiManager = GameManager.getAIManager();
        try
        {
            ServerModel model = GameManager.getInstance().getGame(gameID);
            Player player = model.getPlayer(getPlayerIndex());
            if (model.getTurnTracker().getStatus() == TurnStatus.Discarding)
            {
                if (player.getResourcesCount() > 7)
                {
                    int discardingCount = player.getResourceCount() / 2;
                    Resources discardingResources = new Resources();
                    if (discardingCount > 0)
                    {
                        int i = 0;
                        while (discardingCount > 0)
                        {

                            for (Resource resource : player.getResources().getAllResources())
                            {
                                if (resource.getAmount() - i > 0)
                                {
                                    discardingCount--;
                                    discardingResources.increase(resource.getType());
                                }
                                if (discardingCount == 0)
                                {
                                    break;
                                }
                            }
                            i++;
                        }
                        (new DiscardCardsCommand(getPlayerIndex(), discardingResources)).execute(gameID);
                    }
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private interface AITurn extends Serializable
    {
        void execute(int gameID);
    }

    private class LargestArmyTurn implements AITurn
    {
        @Override public void execute(int gameID)
        {
            //SETUP
            AbstractServerFacade facade = AbstractServerFacade.getInstance();
            AIManager aiManager = GameManager.getAIManager();
            try
            {
                ServerModel model = GameManager.getInstance().getGame(gameID);
                Player player = model.getPlayer(getPlayerIndex());

                //SETUP ROUNDS
                if (model.getTurnTracker().getStatus() == TurnStatus.FirstRound
                        || model.getTurnTracker().getStatus() == TurnStatus.SecondRound)
                {
                    EdgeLocation roadLocation = null;
                    do
                    {
                        roadLocation = model.getMap().getRandomEdgeLocation();
                    } while (!model.canPlaceRoad(getPlayerIndex(), roadLocation, true));

                    facade.buildRoad(gameID, getPlayerIndex(), roadLocation, true);

                    for (VertexLocation vertex : model.getMap().getNearbyVertices(roadLocation))
                    {
                        if (model.canPlaceSettlement(getPlayerIndex(), vertex))
                        {
                            facade.buildSettlement(gameID, getPlayerIndex(), vertex, true);
                            break;
                        }
                    }
                    FinishTurnCommand command = new FinishTurnCommand(getPlayerIndex());
                    command.execute(gameID);

                } else if (model.getTradeOffer() != null) //IF there's a trade going on
                {
                    if (model.getTradeOffer().getReceiver() == getPlayerIndex().getIndex())
                    {
                        (new AcceptTradeCommand(getPlayerIndex(), false)).execute(gameID);
                        return;
                    }
                }  else //PLAYING ROUNDS
                {
                    int firstDie = (int) (Math.random() * 6 + .5) % 6 + 1;
                    int secondDie = (int) (Math.random() * 6 + .5) % 6 + 1;
                    (new RollNumberCommand(getPlayerIndex(), firstDie + secondDie)).execute(gameID);

                    /*if (model.getTurnTracker().getStatus() == TurnStatus.Discarding)
                    {
                        int discardingCount = player.getResourcesCount() - 7;
                        Resources discardingResources = new Resources();
                        if (discardingCount > 0)
                        {
                            int i = 0;
                            while (discardingCount > 0)
                            {

                                for (Resource resource : player.getResources().getAllResources())
                                {
                                    if (resource.getAmount() - i > 0)
                                    {
                                        discardingCount--;
                                        discardingResources.increase(resource.getType());
                                    }
                                    if (discardingCount == 0)
                                    {
                                        break;
                                    }
                                }
                                i++;
                            }
                            (new DiscardCardsCommand(getPlayerIndex(), discardingResources)).execute(gameID);
                        }
                    }*/

                    if (model.getTurnTracker().getStatus() == TurnStatus.Robbing)
                    {
                        PlayerIndex anotherPlayer = aiManager.getRandomOtherPlayer(getPlayerIndex());
                        HexLocation placeToRob = aiManager.getHexToRob(anotherPlayer, model);

                        facade.robPlayer(gameID, getPlayerIndex(), anotherPlayer, placeToRob);
                    }

                    if (player.canBuyDevCard())
                    {
                        facade.buyDevCard(gameID, getPlayerIndex());

                    } else if (player.canPlayDevCard(DevCardType.SOLDIER))
                    {
                        PlayerIndex anotherPlayer = aiManager.getRandomOtherPlayer(getPlayerIndex());
                        HexLocation placeToRob = aiManager.getHexToRob(anotherPlayer, model);

                        facade.soldier(gameID, getPlayerIndex(), anotherPlayer, placeToRob);
                    }
                    FinishTurnCommand command = new FinishTurnCommand(getPlayerIndex());
                    command.execute(gameID);
                }

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
