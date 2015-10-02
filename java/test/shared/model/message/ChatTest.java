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
	PlayerIndex index = player.getIndex();
	Chat chat; 
	MessageLine messageLine;
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
		String message = "Let's play Catan!";
		messageLine = new MessageLine(index, message);; 
		chat.addMessageLine(index, message); 
		chat.addMessageLine(messageLine); 
		List<MessageLine> messageLines = chat.getMessages();
		String returnValue = "\"chat\":{";
        for (MessageLine messageLine : messageLines) 
        {
			returnValue += messageLine.toString();
		}
        returnValue += "},";
        assert(returnValue.equals(chat.toString())); 
	}

	@Test
	public void test() 
	{
		fail("Not yet implemented");
	}

}
