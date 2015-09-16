package shared.definitions;

public enum PlayerIndex
{
    NONE(-1), PLAYER_0(0), PLAYER_1(1), PLAYER_2(2), PLAYER_3(3);

    private final int index;

    private PlayerIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }
}
