package server.proxy;

import java.util.List;
import java.util.logging.Level;

import client.data.*;
import shared.model.*;


public interface IProxy {

	String method();
	
	//User Methods
	/** Description
	 * 
	 * @param userParameter
	 */
	void userLogin(UserParam userParameter); //Or just Username/Password. We should discuss this, probably.
	/** Description
	 * 
	 * @param userParameter
	 */
	void userRegister(UserParam userParameter);
	
	//Game Methods
	/** Description
	 * 
	 * @return
	 */
	List<GameInfo> listGames();
	
	/** Adds an AI player to the current game.
	 * <p>
	 * <b>On success:</b>
	 * <ul>
	 * <li>1. The server returns an HTTP 200 success response with “Success” in the body.
	 * <li>2. A new AI player of the specified type has been added to the current game. The server selected a name and color for the player.
	 * </ul>
	 * <b>On failure:</b>
	 * <ul>
	 * <li>1. The server returns an HTTP 400 error response, and the body contains an error message.
	 * </ul>
	 * @param aiType Values returned by listAI, currently only LARGEST_ARMY is supported
	 */
	void addAI(AIType aiType);
	
	//Util Method
	/** Sets the server’s logging level.
	 * <p>
	 * <b>On success:</b>
	 * <ul>
	 * <li>1. The server returns an HTTP 200 success response with “Success” in the body.
	 * <li>2. The Server is using the specified logging level
	 * </ul>
	 * <b>On failure:</b>
	 * <ul>
	 * <li>1. The server returns an HTTP 400 error response, and the body contains an error message
	 * </ul>
	 * @param level Valid values include: SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
	 */
	void changeLogLevel(Level level);
	
	//Move Methods
	
	
}
