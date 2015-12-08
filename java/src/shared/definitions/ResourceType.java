package shared.definitions;

import java.io.Serializable;

/**
 * Defines the types of resources.
 *
 * @author Cache Staheli
 */
public enum ResourceType implements Serializable
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

    public ResourceType next()
    {
        switch (this)
            {
            case WOOD:
                return ResourceType.BRICK;
            case BRICK:
                return ResourceType.SHEEP;
            case SHEEP:
                return ResourceType.WHEAT;
            case WHEAT:
                return ResourceType.ORE;
            case ORE:
                return ResourceType.WOOD;
            default:
                return null;
        }
    }

    public static ResourceType toResourceType(HexType hexType)
    {
        switch (hexType)
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
}
