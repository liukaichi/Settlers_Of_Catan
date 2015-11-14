package shared.locations;

import java.util.HashMap;
import java.util.Map;

/**
 * Enums for Edge directions and their respective opposites
 */
public enum EdgeDirection
{

    NorthWest, North, NorthEast, SouthEast, South, SouthWest;

    private static final Map<String, EdgeDirection> abreviationToType = new HashMap<String, EdgeDirection>();
    private static final Map<EdgeDirection, String> typeToAbreviation = new HashMap<EdgeDirection, String>();

    static
    {
        abreviationToType.put("NW", NorthWest);
        abreviationToType.put("N", North);
        abreviationToType.put("NE", NorthEast);
        abreviationToType.put("SE", SouthEast);
        abreviationToType.put("S", South);
        abreviationToType.put("SW", SouthWest);

        typeToAbreviation.put(NorthWest, "NW");
        typeToAbreviation.put(North, "N");
        typeToAbreviation.put(NorthEast, "NE");
        typeToAbreviation.put(SouthEast, "SE");
        typeToAbreviation.put(South, "S");
        typeToAbreviation.put(SouthWest, "SW");
    }

    static
    {
        NorthWest.opposite = SouthEast;
        North.opposite = South;
        NorthEast.opposite = SouthWest;
        SouthEast.opposite = NorthWest;
        South.opposite = North;
        SouthWest.opposite = NorthEast;
    }

    private EdgeDirection opposite;

    public static String toAbreviation(EdgeDirection dir)
    {
        String abreviation = typeToAbreviation.get(dir);
        if (abreviation == null)
            return null;
        return abreviation;
    }

    public static EdgeDirection fromAbreviation(String abreviation)
    {
        EdgeDirection type = abreviationToType.get(abreviation);
        if (type == null)
            return null;
        return type;
    }

    public EdgeDirection getOppositeDirection()
    {
        return opposite;
    }
}

