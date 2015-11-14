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
     * @return the current GameplayState. If the version in the server's game is older than the version supplied,
     * null is returned to indicate that nothing has changed.
     * @param version the version to check the game against.
     */
    ClientModel getGameState(int version);

    ClientModel getGameState();

    /**
     * Add an AI player to the game specified by the id.
     * @param aiType the type of AI to the game.
     * @param gameID the id of the game to add the AI to.
     */
    void addAI(AIType aiType, int gameID);

    /**
     * List the types of AI available.
     * @return ListAIResponse holds the list of AI's that are available.
     */
    ListAIResponse listAI();
}
