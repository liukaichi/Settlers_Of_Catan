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
    	int currentTurnInt = tracker.getAsJsonObject("currentTurn").getAsInt();
    	currentTurn = PlayerIndex.fromInt(currentTurnInt);
    	
    	String statusString = tracker.getAsJsonObject("status").getAsString();
    	status = TurnStatus.valueOf(statusString.toUpperCase()); 
    	
    	int longestRoadInt = tracker.getAsJsonObject("longestRoad").getAsInt();
    	longestRoad = PlayerIndex.fromInt(longestRoadInt);
    	
    	int largestArmyInt = tracker.getAsJsonObject("largestArmy").getAsInt();
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
//    	if (playerLongestRoad.amountOf(type))
    }
    
    /**
    * Updates the largest army counter
    * A player has the largest army if he or she has at least 3 knights
    */    
    public void updateLargestArmy(PlayerIndex playerLargestArmy)
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
		String returnString = "\"turnTracker\":{";
		returnString += "\"status\":\""+ status + "\",";
		returnString += "\"currentTurn\": "+ currentTurn + "\",";
		returnString += "\"longestRoad\": "+ getLongestRoad().toString() + "\",";
		returnString += "\"longestArmy\": "+ getLargestArmy().toString() + "\"},";
		return returnString; 
	}
}
