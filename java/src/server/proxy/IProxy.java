package server.proxy;

import java.util.List;

import client.data.*;
import shared.model.*;


public interface IProxy {

	String method();
	
	//User Methods
	void userLogin(UserParam userParameter); //Or just Username/Password. We should discuss this, probably.
	
	void userRegister(UserParam userParameter);
	
	//Game Methods
	List<GameInfo> listGames();
	
	//Util Method
	
	//Move Methods
	
	
}
