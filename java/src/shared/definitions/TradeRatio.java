package shared.definitions;

/**
 * Defines the trade ratios that can be used in ports/maritime trades.
 * 
 * @author Cache Staheli
 *
 */
public enum TradeRatio
{
    FOUR(4), THREE(3), TWO(2);

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
