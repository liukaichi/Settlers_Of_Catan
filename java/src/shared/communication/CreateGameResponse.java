package shared.communication;

import client.data.GameInfo;

public class CreateGameResponse
{
    private GameInfo gameInfo;

    public CreateGameResponse(int id, String title)
    {
        gameInfo = new GameInfo(id, title);
    }
}
