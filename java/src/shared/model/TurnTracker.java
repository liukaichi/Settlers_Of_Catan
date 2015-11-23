package shared.model;

import client.data.PlayerInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.PlayerIndex;
import shared.definitions.StructureType;
import shared.definitions.TurnStatus;
import shared.model.player.Player;

import java.util.List;

/**
 * This class manages player's turns during the Catan game
 *
 * @author amandafisher
 */
public class TurnTracker
{
    private PlayerIndex currentTurn, longestRoad, largestArmy;
    private TurnStatus status;
    private int LAST_PLAYER = 3;
    private int FIRST_PLAYER = 0;

    public TurnTracker(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject tracker = (JsonObject) parser.parse(json);
        int currentTurnInt = tracker.getAsJsonPrimitive("currentTurn").getAsInt();
        currentTurn = PlayerIndex.fromInt(currentTurnInt);

        String statusString = tracker.getAsJsonPrimitive("status").getAsString();
        status = TurnStatus.valueOf(statusString);

        int longestRoadInt = tracker.getAsJsonPrimitive("longestRoad").getAsInt();
        longestRoad = PlayerIndex.fromInt(longestRoadInt);

        int largestArmyInt = tracker.getAsJsonPrimitive("largestArmy").getAsInt();
        largestArmy = PlayerIndex.fromInt(largestArmyInt);
    }

    public TurnTracker()
    {
        status = TurnStatus.FirstRound;
        currentTurn = PlayerIndex.PLAYER_0;
        longestRoad = PlayerIndex.NONE;
        largestArmy = PlayerIndex.NONE;
    }

    /**
     * Updates the currentTurn counter
     */
    public void updateCurrentTurn(PlayerInfo playerCurrentTurn)
    {
        currentTurn = playerCurrentTurn.getPlayerIndex();
    }

    /**
     * Updates the longestRoad counter.
     * A player has the longest road if he or she has at least 5 roads
     *
     * @param players the list of players to look through.
     */
    public void updateLongestRoad(List<Player> players)
    {
        int longestRoadCount = 5;
        Player longestRoadPlayer = null;
        if (!longestRoad.equals(PlayerIndex.NONE))
        {
            longestRoadPlayer = players.get(longestRoad.getIndex());
            longestRoadCount = longestRoadPlayer.getStructureCount(StructureType.ROAD);
        }
        for (Player player : players)
        {
            if (player.getStructureCount(StructureType.ROAD) > longestRoadCount)
            {
                longestRoadPlayer = player;
            }
        }
        if (longestRoadPlayer != null)
        {
            longestRoad = longestRoadPlayer.getPlayerIndex();
        }
    }

    /**
     * Updates the largest army counter
     * A player has the largest army if he or she has at least 3 knights
     *
     * @param players the list of players to look through.
     */
    public void updateLargestArmy(List<Player> players)
    {
        int largestArmyCount = 4;
        Player largestArmyPlayer = null;
        if (!largestArmy.equals(PlayerIndex.NONE))
        {
            largestArmyPlayer = players.get(largestArmy.getIndex());
            largestArmyCount = largestArmyPlayer.getKnights();
        }
        for (Player player : players)
        {
            if (player.getKnights() > largestArmyCount)
            {
                largestArmyPlayer = player;
            }
        }
        if (largestArmyPlayer != null)
        {
            largestArmy = largestArmyPlayer.getPlayerIndex();
        }
    }

    /**
     * Updates the status string based on the current phase of the player's turn
     */
    public void updateStatus(TurnStatus playerTurnStatus)
    {
        status = playerTurnStatus;
    }

    public PlayerIndex getCurrentTurn()
    {
        return currentTurn;
    }

    public PlayerIndex getLongestRoad()
    {
        return longestRoad;
    }

    public PlayerIndex getLargestArmy()
    {
        return largestArmy;
    }

    public TurnStatus getStatus()
    {
        return status;
    }

    @Override public String toString()
    {
        JsonObject turnTracker = new JsonObject();
        turnTracker.addProperty("status", status.toString());
        turnTracker.addProperty("currentTurn", currentTurn.getIndex());
        turnTracker.addProperty("longestRoad", longestRoad.getIndex());
        turnTracker.addProperty("largestArmy", largestArmy.getIndex());

        return turnTracker.toString();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currentTurn == null) ? 0 : currentTurn.hashCode());
        result = prime * result + ((largestArmy == null) ? 0 : largestArmy.hashCode());
        result = prime * result + ((longestRoad == null) ? 0 : longestRoad.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        TurnTracker other = (TurnTracker) obj;
        if (currentTurn.getIndex() != other.currentTurn.getIndex())
            return false;
        if (largestArmy.getIndex() != other.largestArmy.getIndex())
            return false;
        if (longestRoad.getIndex() != other.longestRoad.getIndex())
            return false;
        return status == other.status;
    }

    public void finishTurn(PlayerIndex playerIndex)
    {
        if (this.status == TurnStatus.FirstRound)
        {
            if (currentTurn.equals(playerIndex))
            {
                if (currentTurn == PlayerIndex.PLAYER_3)
                {
                    status = TurnStatus.SecondRound;
                } else
                {
                    currentTurn = currentTurn.getNext();
                }
            }
        } else if (this.status == TurnStatus.SecondRound)
        {
            if (currentTurn.equals(playerIndex))
            {
                if (currentTurn == PlayerIndex.PLAYER_0)
                {
                    status = TurnStatus.Rolling;
                } else
                {
                    currentTurn = currentTurn.getLast();
                }
            }
        } else if (currentTurn.equals(playerIndex))
        {
            currentTurn = currentTurn.getNext();
            this.status = TurnStatus.Rolling;
        }
    }
}
