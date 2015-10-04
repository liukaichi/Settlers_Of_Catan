package shared.model.bank.card;

import shared.definitions.DevCardType;
import shared.model.bank.PlayerBank;

/**
 * This class represents a Development Card
 */
public abstract class DevCard {
    private DevCardType type;
    private int amountPlayable, amountUnplayable, amountPlayed;

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DevCard devCard = (DevCard) o;

        if (amountPlayable != devCard.amountPlayable)
            return false;
        if (amountUnplayable != devCard.amountUnplayable)
            return false;
        if (amountPlayed != devCard.amountPlayed)
            return false;
        return type == devCard.type;

    }

    @Override public int hashCode()
    {
        return 0;
    }

    public enum AmountType {
        PLAYABLE, UNPLAYABLE, PLAYED
    }

    public DevCardType getType() {
        return type;
    }

    public int getAmount(AmountType type){
        switch (type){
            case PLAYABLE:
                return amountPlayable;
            case UNPLAYABLE:
                return amountUnplayable;
            case PLAYED:
                return amountPlayed;
        }
        return -1;
    }

    public DevCard(DevCardType inType){
        type = inType;
        amountUnplayable = 0;
        amountPlayable = 0;
        amountPlayed = 0;
    }

    public void setType(DevCardType type) {
        this.type = type;
    }

    public void setAmounts(int playable, int unplayable, int played){
        setAmountPlayable(playable);
        setAmountPlayed(played);
        setAmountUnplayable(unplayable);
    }

    public void setAmountPlayable(int amountPlayable) {
        this.amountPlayable = amountPlayable;
    }

    public void setAmountUnplayable(int amountUnplayable) {
        this.amountUnplayable = amountUnplayable;
    }

    public void setAmountPlayed(int amountPlayed) {
        this.amountPlayed = amountPlayed;
    }

    /**
     * Plays the action on a specified DevCard
     */
    public abstract void playAction(PlayerBank context);

    /**
     * Increments the specified amount for this DevCard
     * @param type -- amount to increment
     * @param addAmount -- amount to increment by
     */
    public void addCard(AmountType type, int addAmount){
        switch (type){
            case PLAYABLE:
                amountPlayable += addAmount;
                break;
            case PLAYED:
                amountPlayed += addAmount;
                break;
            case UNPLAYABLE:
                amountUnplayable += addAmount;
                break;
        }
    }

    /**
     * Decrements the specified amount for this DevCard
     * @param type -- amount to decrement
     * @param subAmount -- amount to decrement by
     */
    public void subCard(AmountType type, int subAmount){
        //TODO: Throw exception if already 0
        switch (type){
            case PLAYABLE:
                amountPlayable -= subAmount;
                break;
            case PLAYED:
                amountPlayed -= subAmount;
                break;
            case UNPLAYABLE:
                amountUnplayable -= subAmount;
                break;
        }
    }

    /**
     * Sums the numbers amountPlayable, amountUnplayable and amountPlayed.
     * @return the sum of all three numbers
     */
    public int total(){
        return amountPlayable + amountUnplayable + amountPlayed;
    }
}
