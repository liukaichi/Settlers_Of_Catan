package shared.definitions;

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
