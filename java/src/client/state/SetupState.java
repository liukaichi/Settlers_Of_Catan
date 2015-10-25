/**
 *
 */
package client.state;

import client.base.ObserverController;
import client.facade.ClientFacade;
import shared.definitions.TurnStatus;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

import java.util.logging.Logger;

/**
 * This state is used in the first two rounds of the game, where players place roads and cities only.
 *
 */
public class SetupState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private TurnStatus roundNumber;

    public SetupState(ObserverController controller, TurnStatus roundNumber)
    {
        super(controller);
        this.roundNumber = roundNumber;
    }

    /*
     * Map controller methods
     */
    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceRoad(shared.definitions.
     * PlayerIndex, shared.locations.EdgeLocation)
     */
    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return facade.canPlaceRoad(edgeLoc);
    }


    @Override public void placeRoad(EdgeLocation edgeLoc)
    {
        facade.placeRoad(edgeLoc, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see client.state.ICatanGameMethods#canPlaceSettlement(shared.locations.
     * VertexLocation)
     */
    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {
        return facade.canPlaceSettlement(vertLoc);
    }


    @Override public void placeSettlement(VertexLocation vertLoc)
    {
        facade.placeSettlement(vertLoc, true);
    }


}
