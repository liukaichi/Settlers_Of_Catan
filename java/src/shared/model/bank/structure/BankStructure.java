package shared.model.bank.structure;

import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;

/**
 * This class represents a MapStructure in the Catan game
 */
public class BankStructure
{
    private StructureType type;
    private int amountBuilt;
    private int amountRemaining;
    private final int amountMax;

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        BankStructure bankStructure = (BankStructure) o;

        if (amountBuilt != bankStructure.amountBuilt)
            return false;
        if (amountMax != bankStructure.amountMax)
            return false;
        if (amountRemaining != bankStructure.amountRemaining)
            return false;

        return type == bankStructure.type;

    }

    @Override public int hashCode()
    {
        return 0;
    }

    public int getAmountRemaining()
    {
        return amountRemaining;
    }

    public enum AmountType{
        BUILT, MAX, REMAINING
    }

    public BankStructure(StructureType type){
        this.type = type;
        amountBuilt = 0;
        switch (type){
            case CITY:
                amountMax = 4;
                amountRemaining = 4;
                break;
            case SETTLEMENT:
                amountMax = 5;
                amountRemaining = 5;
                break;
            case ROAD:
                amountMax = 15;
                amountRemaining = 15;
                break;
            default:
                amountMax = -1;
                amountRemaining = -1;
        }
    }

    public int getAmountBuilt() {
        return amountBuilt;
    }

    public void setAmountBuilt(int amountBuilt) {
        this.amountBuilt = amountBuilt;
        this.amountRemaining = this.amountMax - this.amountBuilt;
    }

    public int getAmountMax() {
        return amountMax;
    }

    public StructureType getType() {
        return type;
    }

    public void setAmountRemaining(int amountRemaining) {
        this.amountRemaining = amountRemaining;
        this.amountBuilt = this.amountMax - this.amountRemaining;
    }

    public int getAmount(AmountType type){
        switch (type){
            case BUILT:

                return amountBuilt;
            case MAX:
                return amountMax;
            case REMAINING:
                return amountRemaining;
            default:
                return -1;
        }
    }

    /**
     * Increments the specified amount from this MapStructure
     * @param addAmount -- amount to increment by
     */
    public void addAmountBuilt(int addAmount) throws CatanException {
        if ((amountBuilt + addAmount) > amountMax){
            throw new CatanException();
        }
        amountBuilt += addAmount;
        amountRemaining -= addAmount;
    }

    /**
     * Decrement the specified amount from this MapStructure
     * @param subAmount -- amount to decrement by
     */
    public void subAmountBuilt(int subAmount) throws CatanException {
        if (amountBuilt == 0){
            throw new CatanException();
        }
        amountBuilt -= subAmount;
        amountRemaining += subAmount;
    }

}
