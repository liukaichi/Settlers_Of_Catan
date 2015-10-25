package client.state;

import client.base.ObserverController;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.logging.Logger;

/**
 * This state is the main state that allows most gameplay actions.
 */
public class PlayingState extends GameplayState
{
    private static final Logger LOGGER = Logger.getLogger(PlayingState.class.getName());

    public PlayingState(ObserverController controller)
    {
        super(controller);
    }

    @Override public void endTurn()
    {
        LOGGER.info("Ending Turn");
        facade.endTurn();
    }

    @Override public boolean canBuyDevCard()
    {
        return facade.canBuyDevCard();
    }

    @Override public boolean canPlaceCity(VertexLocation vertLoc)
    {
        return facade.canPlaceCity(vertLoc);
    }

    @Override public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return facade.canPlaceRoad(edgeLoc);
    }

    @Override public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return facade.canPlaceSettlement(vertLoc);
    }

    @Override public void placeCity(VertexLocation vertLoc)
    {
        facade.placeCity(vertLoc);
    }

    @Override public void placeRoad(EdgeLocation edgeLoc)
    {
        facade.placeRoad(edgeLoc, false);
    }

    @Override public void placeSettlement(VertexLocation vertLoc)
    {
        facade.placeSettlement(vertLoc, false);
    }

}
