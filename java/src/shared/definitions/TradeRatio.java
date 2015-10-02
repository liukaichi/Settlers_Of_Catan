package shared.definitions;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines the trade ratios that can be used in ports/maritime trades.
 * 
 * @author Cache Staheli
 *
 */
public enum TradeRatio
{
    FOUR(4), THREE(3), TWO(2);
    private static final Map<Integer, TradeRatio> intToTypeMap = new HashMap<Integer, TradeRatio>();
    
    static {
        for (TradeRatio type : TradeRatio.values()) {
            intToTypeMap.put(type.getRatio(), type);
        }
    }

    public static TradeRatio fromInt(int i) {
    	TradeRatio type = intToTypeMap.get(Integer.valueOf(i));
        if (type == null) 
            return null;
        return type;
    }

    private final int ratio;

    private TradeRatio(int ratio)
    {
        this.ratio = ratio;
    }

    public int getRatio()
    {
        return ratio;
    }
}
