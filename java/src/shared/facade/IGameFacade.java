package shared.facade;

import shared.communication.ListAIResponse;
import shared.model.ClientModel;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IGameFacade
{
    ClientModel getGameState();
    void addAI();
    ListAIResponse listAI();
}
