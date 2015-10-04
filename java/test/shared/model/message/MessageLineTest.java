/**
 * 
 */
package shared.model.message;

import org.junit.Before;
import org.junit.Test;
import shared.model.player.Player;

/**
 * @author amandafisher
 *
 */
public class MessageLineTest 
{
	MessageLine messageLine; 
	Player player; 
	String sourceName;
	String message; 

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		player = new Player(); 
		sourceName = player.getPlayerInfo().getName();
		message = "Let's play Catan";
		messageLine = new MessageLine(sourceName, message);
	}

	@Test
	public void test() 
	{
		String messageLineString = messageLine.toString(); 
	}

}
