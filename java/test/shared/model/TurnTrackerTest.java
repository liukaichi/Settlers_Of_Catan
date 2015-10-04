/**
 * 
 */
package shared.model;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;

/**
 * @author amandafisher
 *
 */
public class TurnTrackerTest 
{

	String json; 
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{		
		json = "\"turnTracker\": {\"status\": \"FirstRound\",\"currentTurn\": 0, \"longestRoad\": -1,\"largestArmy\": -1}"; 
	}

	@Test
	public void test() 
	{
		TurnTracker turnTracker = new TurnTracker(json); 
		PlayerIndex currentTurn = turnTracker.getCurrentTurn();
		TurnStatus status = turnTracker.getStatus();
		PlayerIndex longestRoad = turnTracker.getLongestRoad(); 
		PlayerIndex largestArmy = turnTracker.getLargestArmy(); 
		
		assert(currentTurn.equals(0)); 
		assert(status.equals("FirstRound"));
		assert(longestRoad.equals(-1));
		assert(largestArmy.equals(-1)); 			
	}

}
