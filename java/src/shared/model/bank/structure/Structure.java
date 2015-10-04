package shared.model.bank.structure;

import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;

/**
 * This class represents a Structure in the Catan game
 */
public class Structure {
    private StructureType type;
    private int amountBuilt;
    private final int amountMax;

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Structure structure = (Structure) o;

        if (amountBuilt != structure.amountBuilt)
            return false;
        if (amountMax != structure.amountMax)
            return false;
        return type == structure.type;

    }

    @Override public int hashCode()
    {
        return 0;
    }

    public enum AmountType{
        BUILT, MAX
    }

    public Structure(StructureType type){
        this.type = type;
        amountBuilt = 0;
        switch (type){
            case CITY:
                amountMax = 4;
                break;
            case SETTLEMENT:
                amountMax = 5;
                break;
            case ROAD:
                amountMax = 15;
                break;
            default:
                amountMax = -1;
        }
    }

    public int getAmountBuilt() {
        return amountBuilt;
    }

    public void setAmountBuilt(int amountBuilt) {
        this.amountBuilt = amountBuilt;
    }

    public int getAmountMax() {
        return amountMax;
    }

    public StructureType getType() {
        return type;
    }

    public int getAmount(AmountType type){
        switch (type){
            case BUILT:
                return amountBuilt;
            case MAX:
                return amountMax;
            default:
                return -1;
        }
    }

    /**
     * Increments the specified amount from this Structure
     * @param addAmount -- amount to increment by
     */
    public void addAmount(int addAmount) throws CatanException {
        if ((amountBuilt + addAmount) > amountMax){
            throw new CatanException();
        }
        amountBuilt += addAmount;
    }

    /**
     * Decrement the specified amount from this Structure
     * @param subAmount -- amount to decrement by
     */
    public void subAmount(int subAmount) throws CatanException {
        if (amountBuilt == 0){
            throw new CatanException();
        }
        amountBuilt -= subAmount;
    }

}
