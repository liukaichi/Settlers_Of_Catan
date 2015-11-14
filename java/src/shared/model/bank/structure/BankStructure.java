package shared.model.bank.structure;

import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;

/**
 * This class represents a MapStructure in the Catan game
 */
public class BankStructure
{
    private final int amountMax;
    private StructureType type;
    private int amountBuilt;
    private int amountRemaining;

    /**
     * Initializes a BankStructure of the given type.
     *
     * @param type the type of BankStructure.
     */
    public BankStructure(StructureType type)
    {
        this.type = type;
        amountBuilt = 0;
        switch (type)
        {
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

    public void setAmountRemaining(int amountRemaining)
    {
        this.amountRemaining = amountRemaining;
        this.amountBuilt = this.amountMax - this.amountRemaining;
    }

    public int getAmountBuilt()
    {
        return amountBuilt;
    }

    public void setAmountBuilt(int amountBuilt)
    {
        this.amountBuilt = amountBuilt;
        this.amountRemaining = this.amountMax - this.amountBuilt;
    }

    public int getAmountMax()
    {
        return amountMax;
    }

    public StructureType getType()
    {
        return type;
    }

    /**
     * Gets the amount built of a certain type.
     *
     * @param type the type.
     * @return the amount built of the type.
     */
    public int getAmount(AmountType type)
    {
        switch (type)
        {
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
     * Increments the specified amount from this BankStructure
     *
     * @param addAmount amount to increment by
     * @throws CatanException if the amount currently built plus the amount to build exceeds the maximum allowed.
     */
    public void addAmountBuilt(int addAmount) throws CatanException
    {
        if ((amountBuilt + addAmount) > amountMax)
        {
            throw new CatanException();
        }
        amountBuilt += addAmount;
        amountRemaining -= addAmount;
    }

    /**
     * Decrement the specified amount from this BankStructure
     *
     * @param subAmount amount to decrement by
     * @throws CatanException if there are currently none built.
     */
    public void subAmountBuilt(int subAmount) throws CatanException
    {
        if (amountBuilt == 0)
        {
            throw new CatanException();
        }
        amountBuilt -= subAmount;
        amountRemaining += subAmount;
    }

    /**
     * Used to define the type of amount for structures.
     */
    public enum AmountType
    {
        /**
         * Amount Built
         */
        BUILT,
        /**
         * Maximum amount
         */
        MAX,
        /**
         * Amount remaining.
         */
        REMAINING
    }

}
