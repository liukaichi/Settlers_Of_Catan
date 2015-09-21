package shared.model.resource;

import shared.definitions.ResourceType;

/**
 * Object representing the many different resources in the Catan game
 * @author amandafisher
 *
 */
public class Resource
{
    private ResourceType type;
    private int amount;

    public int getAmount()
    {
        return amount;
    }

    public ResourceType getType()
    {
        return type;
    }

    /**
     * Increases the count of this resource by @param num amount
     * @param num -- number of this resource to increase
     */
    public void addResource(int num)
    {

    }

    /**
     * Decreases the count of this resource by the @param num amount
     * @param num -- number of this resrouce to decrease
     */
    public void subResource(int num)
    {

    }
}