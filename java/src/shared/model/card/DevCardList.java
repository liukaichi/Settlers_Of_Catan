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

    /**
     * Ability for player to play any of the 6 development cards
     */
    public abstract void playAction();
}