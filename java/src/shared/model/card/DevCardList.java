package shared.model.card;

import shared.definitions.DevCardType;

public abstract class DevCardList
{
    private int amount;
    private DevCardType type;

    public int getAmount()
    {
        return amount;
    }

    public abstract void playAction();
}