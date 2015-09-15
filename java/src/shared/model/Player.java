package shared.model;

import client.data.PlayerInfo;

public class Player
{
    private Bank bank;
    private PlayerInfo playerInfo;
    // probably should create a Name class?
    private String name;

    public Player()
    {
        bank = new PlayerBank();
    }

    public String getName()
    {
        return playerInfo.getName();
    }
}
