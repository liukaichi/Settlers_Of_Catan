package shared.model.bank;

import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resources;
/**
 * The base class bank, used to hold resources and dev cards
 * @author dtaylor
 *
 */
public class Bank
{
    private Resources resources;
    private DevCards devCards;


    /**
     * Returns a Resources object
     * 
     * @return the Resources in this Bank
     */
    public Resources getResources()
    {
        return resources;
    }

    /**
     * Returns a DevCardHand object
     * 
     * @return the DevCardHand in this Bank
     */
    public DevCards getDevCards()
    {
        return devCards;
    }
}
