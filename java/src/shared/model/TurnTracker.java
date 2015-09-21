package shared.model;

import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;

/**
 * This class manages player's turns during the Catan game
 * @author amandafisher
 *
 */
public class TurnTracker
{
    private PlayerIndex currentTurn, longestRoad, largestArmy;
    private TurnStatus status;
    
    /**
    * Updates the currentTurn counter
    */
    public void updateCurrentTurn(){
        
    }
    
    /**
    * Updates the longestRoad counter.
    * A player has the longest road if he or she has at least 5 roads
    */    
    public void updateLongestRoad(){
        
    }
    
    /**
    * Updates the largest army counter
    * A player has the largest army if he or she has at least 3 knights
    */    
    public void updateLargestArmy(){
        
    }
    
    /**
    * Updates the status string based on the current phase of the player's turn
    */   
    public void updateStatus(){
        
    }

	public PlayerIndex getCurrentTurn() {
		return currentTurn;
	}

	public PlayerIndex getLongestRoad() {
		return longestRoad;
	}

	public PlayerIndex getLargestArmy() {
		return largestArmy;
	}

	public TurnStatus getStatus() {
		return status;
	}
    
    
    
}
