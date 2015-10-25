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
 * @author cstaheli
 *
 */
public class SetupState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private TurnStatus turnStatus;
    public SetupState(ObserverController controller, TurnStatus firstRound)
    {
        super(controller);
        turnStatus = firstRound;
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
        return ClientFacade.getInstance().canPlaceRoad(edgeLoc);
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
        return ClientFacade.getInstance().canPlaceSettlement(vertLoc);
    }

}
