package shared.definitions;

/**
 * Defines the indexs that can be used for players. <br>
 * <br>
 * NONE is used to either specify that some object is coming from the server, or
 * that no player is associated with it. There are only four player indexes
 * (0-3).
 */
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
