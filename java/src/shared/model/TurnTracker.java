package shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.data.PlayerInfo;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.model.bank.PlayerBank;
import shared.model.message.MessageLine;
import shared.model.player.Player;

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
		String returnString = "\"turnTracker\":{";
		returnString += "\"status\":\""+ status.toString() + "\",";
		returnString += "\"currentTurn\": "+ currentTurn.toString() + "\",";
		returnString += "\"longestRoad\": "+ getLongestRoad().toString() + "\",";
		returnString += "\"longestArmy\": "+ getLargestArmy().toString() + "\"},";
		return returnString; 
	}
}
