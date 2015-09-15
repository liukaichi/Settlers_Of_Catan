package shared.model;

import shared.definitions.PlayerIndex;

public abstract class Bank extends ResourceList
{
    private PlayerIndex owner;
    private DevCardList newDevCards, oldDevCards;
}
