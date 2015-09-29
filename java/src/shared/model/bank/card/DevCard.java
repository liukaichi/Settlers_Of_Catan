package shared.model.bank.card;

import shared.definitions.DevCardType;

/**
 * This class represents a Development Card
 */
public abstract class DevCard {
    private DevCardType type;
    private int amountPlayable, amountUnplayable;

    public DevCardType getType() {
        return type;
    }

    public int getAmountPlayable() {
        return amountPlayable;
    }

    public int getAmountUnplayable() {
        return amountUnplayable;
    }

    /**
     * Plays the action on a specified DevCard
     */
    public abstract void playAction();

    /**
     * Increments the playable count for this DevCard
     * @param addAmount -- amount to increment
     */
    public void addPlayable(int addAmount){}

    /**
     * Decrements the playable count for this DevCard
     * @param subAmount -- amount to decrement
     */
    public void subPlayable(int subAmount){}

    /**
     * Increments the unplayable count for this DevCard
     * @param addAmount -- amount to increment
     */
    public void addUnplayable(int addAmount){}

    /**
     * Decrements the unplayable count for this DevCard
     * @param subAmount -- amount to decrement
     */
    public void subUnplayable(int subAmount){}
}
