package server.facade;

import shared.model.ClientModel;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IMovesFacade
{
    ClientModel sendChat();
    ClientModel rollNumber();
    ClientModel robPlayer();
    ClientModel finishTurn();
    ClientModel buyDevCard();
    ClientModel yearOfPlenty();
    ClientModel roadBuilding();
    ClientModel soldier();
    ClientModel monopoly();
    ClientModel monument();
    ClientModel buildRoad();
    ClientModel buildSettlement();
    ClientModel buildCity();
    ClientModel offerTrade();
    ClientModel acceptTrade();
    ClientModel maritimeTrade();
    ClientModel discardCards();
}
