package shared.communication;

import client.data.GameInfo;
/**
 * Class that creates the game response
 * @author dtaylor
 *
 */
public class CreateGameResponse
{
    private GameInfo gameInfo;

    public CreateGameResponse(int id, String title)
    {
        gameInfo = new GameInfo(id, title);
    }
}
