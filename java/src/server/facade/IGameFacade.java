package server.facade;

import shared.communication.ListAIResponse;
import shared.definitions.AIType;
import shared.definitions.exceptions.GameQueryException;
import shared.model.ClientModel;

/**
 * The interface for the actions available when the player has joined a game. Includes getting the game state, as well
 * as adding AI's to the game.
 */
public interface IGameFacade
{
    /**
     * Retrieves the current GameplayState
     *
     * @param version the version to check the game against.
     * @return the current GameplayState. If the version in the server's game is older than the version supplied,
     * null is returned to indicate that nothing has changed.
     */
    ClientModel getGameState(int version);

    /**
     * Add an AI player to the game specified by the id.
     *
     * @param gameID the id of the game to add the AI to.
     * @param aiType the type of AI to the game.
     */
    String addAI(int gameID, AIType aiType) throws GameQueryException;

    /**
     * List the types of AI available.
     *
     * @return ListAIResponse holds the list of AI's that are available.
     */
    ListAIResponse listAI();
}
