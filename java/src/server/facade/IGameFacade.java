package server.facade;

import shared.communication.ListAIResponse;
import shared.definitions.AIType;
import shared.model.ClientModel;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IGameFacade
{
    ClientModel getGameState(int version);
    void addAI(AIType aiType);
    ListAIResponse listAI();
}
