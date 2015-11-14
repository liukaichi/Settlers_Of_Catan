package shared.model.bank.resource;

import shared.definitions.ResourceType;

/**
 * Object representing a resource in the Catan game
 *
 * @author amandafisher
 */
public class Resource
{
    private ResourceType type;
    private int amount;

    public Resource(ResourceType inType)
    {
        type = inType;
        amount = 0;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public ResourceType getType()
    {
        return type;
    }

    /**
     * Increases the count of this resource by the specified amount
     *
     * @param num -- number of this resource to increase
     */
    public void addResource(int num)
    {
        if ((amount + num) <= 19)
            amount += num;
    }

    /**
     * Decreases the count of this resource by the specified amount
     *
     * @param num -- number of this resrouce to decrease
     */
    public void subResource(int num)
    {
        amount -= num;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Resource resource = (Resource) o;

        if (amount != resource.amount)
            return false;
        return type == resource.type;

    }

    @Override public int hashCode()
    {
        return 0;
    }

    @Override public String toString()
    {
        return String.valueOf(type) + ": " + amount;
    }
}