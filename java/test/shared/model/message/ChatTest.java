/**
 * 
 */
package shared.model.message;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import shared.definitions.PlayerIndex;
import shared.model.player.Player;

import java.util.List;

/**
 * @author amandafisher
 *
 */
public class ChatTest 
{
	Player player; 
	String sourceName = player.getPlayerInfo().getName();
	Chat chat; 
	MessageLine messageLine;
	String message;
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
		message = "Let's play Catan!";
		messageLine = new MessageLine(sourceName, message);;
		
	}

	@Test
	public void test() 
	{
		chat.addMessageLine(sourceName, message);
		List<MessageLine> messageLines = chat.getMessages();
		
		  //assert(returnValue.equals(chat.toString()));
	}

}
