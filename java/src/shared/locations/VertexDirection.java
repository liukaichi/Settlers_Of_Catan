package shared.locations;

import java.util.HashMap;
import java.util.Map;

/**
 * Enums for Vertex directions and their respective opposites
 */
public enum VertexDirection
{
    West, NorthWest, NorthEast, East, SouthEast, SouthWest;

    private static final Map<String, VertexDirection> abreviationToType = new HashMap<String, VertexDirection>();
    private static final Map<VertexDirection, String> typeToAbreviation = new HashMap<VertexDirection, String>();

    static
    {
        abreviationToType.put("NW", NorthWest);
        abreviationToType.put("W", West);
        abreviationToType.put("NE", NorthEast);
        abreviationToType.put("SE", SouthEast);
        abreviationToType.put("E", East);
        abreviationToType.put("SW", SouthWest);

        typeToAbreviation.put(NorthWest, "NW");
        typeToAbreviation.put(West, "W");
        typeToAbreviation.put(NorthEast, "NE");
        typeToAbreviation.put(SouthEast, "SE");
        typeToAbreviation.put(East, "E");
        typeToAbreviation.put(SouthWest, "SW");
    }

    static
    {
        West.opposite = East;
        NorthWest.opposite = SouthEast;
        NorthEast.opposite = SouthWest;
        East.opposite = West;
        SouthEast.opposite = NorthWest;
        SouthWest.opposite = NorthEast;
    }

    private VertexDirection opposite;

    public static VertexDirection fromAbreviation(String abreviation)
    {
        VertexDirection type = abreviationToType.get(abreviation);
        if (type == null)
            return null;
        return type;
    }

    /**
     * @return
     */
    public static String toAbreviation(VertexDirection dir)
    {
        String abreviation = typeToAbreviation.get(dir);
        if (abreviation == null)
            return null;
        return abreviation;
    }

    public VertexDirection getOppositeDirection()
    {
        return opposite;
    }
}

