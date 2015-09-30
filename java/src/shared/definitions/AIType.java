package shared.definitions;

/**
 * The allowed AI player types. <br>
 * Currently, LARGEST_ARMY is the only supported type.
 *
 */
public enum AIType
{
    LARGEST_ARMY("LARGEST_ARMY");

    private String type;

    private AIType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "[ \" " + type + "\" ]";
    }
}
