/**
 *
 */
package shared.model;

import org.junit.AfterClass;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author amandafisher
 */
public class TurnTrackerTest
{

    String json;
    TurnTracker turnTracker;

    /**
     * @throws java.lang.Exception
     */
    @AfterClass public static void tearDownAfterClass() throws Exception
    {
    }

    @Test public void test()
    {
        try
        {
            turnTracker = new TurnTracker(
                    new String(Files.readAllBytes(Paths.get("sample/complexTurnTrackerModel.json"))));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        PlayerIndex currentTurn = turnTracker.getCurrentTurn();
        TurnStatus status = turnTracker.getStatus();
        PlayerIndex longestRoad = turnTracker.getLongestRoad();
        PlayerIndex largestArmy = turnTracker.getLargestArmy();

        assertEquals(currentTurn, PlayerIndex.PLAYER_0);
        assertEquals(status, TurnStatus.Playing);
        assertEquals(longestRoad, PlayerIndex.NONE);
        assertEquals(largestArmy, PlayerIndex.NONE);
    }

}
