package shared.definitions;

/**
 * Defines the types of resources.
 *
 * @author Cache Staheli
 */
public enum ResourceType
{
    WOOD, BRICK, SHEEP, WHEAT, ORE;

    public static ResourceType toResourceType(PortType type)
    {
        switch (type)
        {
        case WOOD:
            return ResourceType.WOOD;
        case BRICK:
            return ResourceType.BRICK;
        case SHEEP:
            return ResourceType.SHEEP;
        case WHEAT:
            return ResourceType.WHEAT;
        case ORE:
            return ResourceType.ORE;
        default:
            return null;
        }
    }
    public static PortType toPortType(ResourceType type)
    {
        switch (type)
        {
        case WOOD:
            return PortType.WOOD;
        case BRICK:
            return PortType.BRICK;
        case SHEEP:
            return PortType.SHEEP;
        case WHEAT:
            return PortType.WHEAT;
        case ORE:
            return PortType.ORE;
        default:
            return null;
        }
    }
}
