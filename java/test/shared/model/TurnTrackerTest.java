/**
 * 
 */
package shared.model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.model.map.CatanMap;

/**
 * @author amandafisher
 *
 */
public class TurnTrackerTest 
{

	String json; 
	TurnTracker turnTracker; 
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
	}



	@Test
	public void test() 
	{
		try 
		{
			turnTracker = new TurnTracker(new String(Files.readAllBytes(Paths.get("sample/complexTurnTrackerModel.json"))));
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}; 
		PlayerIndex currentTurn = turnTracker.getCurrentTurn();
		TurnStatus status = turnTracker.getStatus();
		PlayerIndex longestRoad = turnTracker.getLongestRoad(); 
		PlayerIndex largestArmy = turnTracker.getLargestArmy(); 
		
		assert(currentTurn.equals(0)); 
		assert(status.equals("Playing"));
		assert(longestRoad.equals(-1));
		assert(largestArmy.equals(-1)); 			
	}

}
