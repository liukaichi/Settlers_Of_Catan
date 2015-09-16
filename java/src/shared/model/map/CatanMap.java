package shared.model.map;

import java.util.List;

import shared.locations.HexLocation;
import shared.model.*;

public class CatanMap
{
    private List<Hex> hexes;
    private List<Port> ports;
    private List<Road> roads;
    // VertexObject as opposed to Structure. They have the same functionality.
    private List<VertexObject> settlements, cities;
    private int radius;
    private HexLocation robberLocation;
}
