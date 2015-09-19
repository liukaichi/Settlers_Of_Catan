package shared.model.bank;

import shared.model.card.DevCardHand;
import shared.model.resource.ResourceList;

public class Bank
{
    private ResourceList resources;
    private DevCardHand devCards;

    public ResourceList getResourceList(ResourceList list)
    {
        return resources;
    };

    public DevCardHand getDevCardList(DevCardHand list)
    {
        return devCards;
    };
}
