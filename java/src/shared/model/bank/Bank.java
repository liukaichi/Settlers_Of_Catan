package shared.model.bank;

import shared.model.card.DevCardHand;
import shared.model.resource.ResourceList;

public class Bank
{
    private ResourceList resources;
    private DevCardHand devCards;

    /**
     * Returns a ResourceList object
     * @returns the ResourceList in this Bank
     */
    public ResourceList getResourceList()
    {
        return resources;
    };

    /**
     * Returns a DevCardHand object
     * @returns the DevCardHand in this Bank
     */
    public DevCardHand getDevCardList()
    {
        return devCards;
    };
}
