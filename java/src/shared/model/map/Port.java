package shared.model.map;

import shared.definitions.PortType;
import shared.definitions.TradeRatio;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

public class Port
{
    PortType resource;
    HexLocation location;
    EdgeDirection direction;
    TradeRatio ratio;
}
