/**
 * 
 */
package shared.model.message;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.PlayerIndex;
import shared.model.player.Player;

/**
 * @author amandafisher
 *
 */
public class ChatTest 
{
	Player player; 
	PlayerIndex index = player.getPlayerInfo().getPlayerIndex();
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
		messageLine = new MessageLine(index, message);; 
		
	}

	@Test
	public void test() 
	{
		chat.addMessageLine(index, message); 
		chat.addMessageLine(messageLine); 
		List<MessageLine> messageLines = chat.getMessages();
		
        //assert(returnValue.equals(chat.toString())); 
	}

}
