package shared.definitions;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines the trade ratios that can be used in ports/maritime trades.
 *
 * @author Cache Staheli
 */
public enum TradeRatio
{
    FOUR(4), THREE(3), TWO(2);
    private static final Map<Integer, TradeRatio> intToTypeMap = new HashMap<>();

    static
    {
        for (TradeRatio type : TradeRatio.values())
        {
            intToTypeMap.put(type.getRatio(), type);
        }
    }

    private final int ratio;

    TradeRatio(int ratio)
    {
        this.ratio = ratio;
    }

    public static TradeRatio fromInt(int i)
    {
        TradeRatio type = intToTypeMap.get(i);
        if (type == null)
            return null;
        return type;
    }

    public int getRatio()
    {
        return ratio;
    }
}
