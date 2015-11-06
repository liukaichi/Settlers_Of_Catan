package server.facade;

import shared.communication.ListAIResponse;
import shared.definitions.AIType;
import shared.model.ClientModel;

/**
 * Created by cstaheli on 11/4/2015.
 */
public interface IGameFacade
{
    /**
     * Retrieves the current GameplayState
     * @return the current GameplayState
     * @param version
     */
    ClientModel getGameState(int version);

    /**
     * Add an AI player to the game
     * @param aiType
     */
    void addAI(AIType aiType);

    /**
     * List the types of AI available
     * @return ListAIResponse
     */
    ListAIResponse listAI();
}
