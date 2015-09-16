package shared.model.map;

import shared.definitions.PlayerIndex;
import shared.locations.VertexLocation;

public abstract class VertexObject
{
    private PlayerIndex owner;
    // private EdgeLocation location; // what is this? Why in the VertexObject?
    private VertexLocation location; // I think they meant this one.
    private int victoryPointValue;
}
