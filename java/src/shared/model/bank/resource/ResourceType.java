package shared.model.bank.resource;

/**
 * Object representing a resource in the Catan game
 * @author amandafisher
 *
 */
public class ResourceType
{
    private shared.definitions.ResourceType type;
    private int amount;

    public ResourceType(shared.definitions.ResourceType sheep){
        type = sheep;
        amount = 0;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public shared.definitions.ResourceType getType()
    {
        return type;
    }

    /**
     * Increases the count of this resource by the specified amount
     * @param num -- number of this resource to increase
     */
    public void addResource(int num)
    {
        amount += num;
    }

    /**
     * Decreases the count of this resource by the specified amount
     * @param num -- number of this resrouce to decrease
     */
    public void subResource(int num)
    {
        amount -= num;
    }
}