package shared.definitions;

import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Integer, PlayerIndex> intToTypeMap = new HashMap<>();

    static
    {
        for (PlayerIndex type : PlayerIndex.values())
        {
            intToTypeMap.put(type.getIndex(), type);
        }
    }

    private final int index;

    PlayerIndex(int index)
    {
        this.index = index;
    }

    public static PlayerIndex fromInt(int i)
    {
        PlayerIndex type = intToTypeMap.get(i);
        if (type == null)
            return PlayerIndex.NONE;
        return type;
    }

    public int getIndex()
    {
        return index;
    }

    public PlayerIndex getNext()
    {
        return index < 3 ? PlayerIndex.fromInt(index + 1) : PlayerIndex.PLAYER_0;
    }
    public PlayerIndex getLast()
    {
        return index > 0 ? PlayerIndex.fromInt(index - 1) : PlayerIndex.PLAYER_3;
    }
}
