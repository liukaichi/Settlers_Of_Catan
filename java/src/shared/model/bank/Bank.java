package shared.model.bank;

import shared.model.card.DevCardList;
import shared.model.resource.ResourceList;

public abstract class Bank
{
    private ResourceList resources;
    private DevCardList devCards;

    public ResourceList getResourceList(ResourceList list)
    {
        return resources;
    };

    public DevCardList getDevCardList(DevCardList list)
    {
        return devCards;
    };
}
