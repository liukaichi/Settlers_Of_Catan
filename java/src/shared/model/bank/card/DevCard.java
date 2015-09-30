package shared.model.bank.card;

import client.main.Catan;
import shared.definitions.DevCardType;
import shared.definitions.exceptions.CatanException;

/**
 * This class represents a Development Card
 */
public abstract class DevCard {
    private DevCardType type;
    private int amountPlayable, amountUnplayable, amountPlayed;
    public enum AmountType {
        PLAYABLE, UNPLAYABLE, PLAYED
    }

    public DevCardType getType() {
        return type;
    }

    public int getAmount(AmountType type) throws CatanException{
        switch (type){
            case PLAYABLE:
                return amountPlayable;
            case UNPLAYABLE:
                return amountUnplayable;
            case PLAYED:
                return amountPlayed;
        }

        throw new CatanException();
    }

    public DevCard(DevCardType inType){
        type = inType;
        amountUnplayable = 0;
        amountPlayable = 0;
        amountPlayed = 0;
    }
    /**
     * Plays the action on a specified DevCard
     */
    public abstract void playAction();

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
