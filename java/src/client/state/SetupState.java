/**
 * 
 */
package client.state;

import java.util.logging.Logger;

import client.facade.ClientFacade;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * @author cstaheli
 *
 */
public class SetupState extends GameplayState
{
    /* Logger */
    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    
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
