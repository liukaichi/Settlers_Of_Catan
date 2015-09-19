package shared.model.map.structure;

import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;

/*
 * I called this EdgeObject instead of EdgeValue, because it's same naming
 * convention as VertexObject
 */
public abstract class Road
{
    private PlayerIndex owner;
    private EdgeLocation location;
}
