package shared.model.card;

import shared.definitions.DevCardType;

public abstract class DevCardList
{
    private int amount;
    private DevCardType type;
    
    /**
     * Gets the amount of cards in this DevCardList
     * @returns the amount of cards in this DevCardList 
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * Ability for player to play any of the 5 development cards
     */
    public abstract void playAction();
}