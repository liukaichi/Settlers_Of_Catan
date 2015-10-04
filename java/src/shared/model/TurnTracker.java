package shared.model;

import client.data.PlayerInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.model.bank.PlayerBank;

/**
 * This class manages player's turns during the Catan game
 * @author amandafisher
 *
 */
public class TurnTracker
{
    private PlayerIndex currentTurn, longestRoad, largestArmy;
    private TurnStatus status;
    
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
    */    
    public void updateLongestRoad(PlayerBank playerLongestRoad)
    {

    }
    
    /**
    * Updates the largest army counter
    * A player has the largest army if he or she has at least 3 knights
    */    
    public void updateLargestArmy(PlayerBank playerLargestArmy)
    {

    }
    
    /**
    * Updates the status string based on the current phase of the player's turn
    */   
    public void updateStatus(TurnStatus playerTurnStatus)
    {
        
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
    
	@Override
	public String toString()
	{
		JsonParser parser = new JsonParser();
		// map
		JsonObject turnTracker = new JsonObject();
		turnTracker.addProperty("status", status.toString());
		turnTracker.addProperty("currentTurn", currentTurn.toString());
		turnTracker.addProperty("longestRoad", longestRoad.toString());
		turnTracker.addProperty("longestArmy", getLargestArmy().toString());

		return turnTracker.toString(); 
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currentTurn == null) ? 0 : currentTurn.hashCode());
		result = prime * result
				+ ((largestArmy == null) ? 0 : largestArmy.hashCode());
		result = prime * result
				+ ((longestRoad == null) ? 0 : longestRoad.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) 
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
		if (status!= other.status)
			return false;
		return true;
	}
	
	
}
