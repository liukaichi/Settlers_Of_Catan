/**
 * 
 */
package shared.model.message;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.model.message.MessageLine; 
import shared.model.player.Player;

/**
 * @author amandafisher
 *
 */
public class MessageLineTest 
{
	MessageLine messageLine; 
	Player player; 
	PlayerIndex index;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		player = new Player(); 
		index = player.getIndex(); 
		String message = "Let's play Catan";
		messageLine = new MessageLine(index, message);
	}

	@Test
	public void test() 
	{
		String messageLineString = messageLine.toString(); 
		assert(messageLineString.equals("\"lines\":[{" + "\"message\":"  + "Let's play Catan" + "\"," + "\"source\":"  + index.toString() + "\"}]")); 
	}

}
