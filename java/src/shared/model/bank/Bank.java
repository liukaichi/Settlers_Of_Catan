package shared.model.bank;

import shared.model.card.DevCardHand;
import shared.model.resource.ResourceList;

public class Bank
{
    private ResourceList resources;
    private DevCardHand devCards;

    /**
     * Returns a ResourceList object
     * 
     * @return the ResourceList in this Bank
     */
    public ResourceList getResources()
    {
        return resources;
    };

    /**
     * Returns a DevCardHand object
     * 
     * @return the DevCardHand in this Bank
     */
    public DevCardHand getDevCards()
    {
        return devCards;
    };
}
