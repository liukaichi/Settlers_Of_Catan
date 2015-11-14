package shared.model.bank;

import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.ResourceType;
import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resources;

import java.util.Collections;
import java.util.Set;
import java.util.Stack;

/**
 * The base class bank, used to hold resources and dev cards
 *
 * @author dtaylor
 */
public class Bank
{
    private static Resources resources;
    private static Stack<DevCardType> devCardDeck;
    private DevCards devCards;

    /**
     * Initializes the Bank with resources. If the bank is specified to be the game bank, it is given 19 of each resource.
     *
     * @param isGameBank if the Bank is the game bank.
     */
    public Bank(boolean isGameBank)
    {
        initialize(isGameBank);
    }

    private void initializeDevCardDeck()
    {
        for (int i = 0; i < 2; i++)
        {
            devCardDeck.push(DevCardType.MONOPOLY);
        }
        for (int i = 0; i < 2; i++)
        {
            devCardDeck.push(DevCardType.ROAD_BUILD);
        }
        for (int i = 0; i < 2; i++)
        {
            devCardDeck.push(DevCardType.YEAR_OF_PLENTY);
        }
        for (int i = 0; i < 5; i++)
        {
            devCardDeck.push(DevCardType.MONUMENT);
        }
        for (int i = 0; i < 14; i++)
        {
            devCardDeck.push(DevCardType.SOLDIER);
        }
        Collections.shuffle(devCardDeck);
    }

    private void initialize(boolean isGameBank)
    {
        resources = new Resources(isGameBank);
        if (devCardDeck == null)
        {
            devCardDeck = new Stack<>();
            initializeDevCardDeck();
        }
    }

    public Stack<DevCardType> getDevCardDeck()
    {
        return devCardDeck;
    }

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

    /**
     * Initializes the deck of dev cards from Json
     *
     * @param json the Json to initialize the deck from.
     */
    public void initDevCards(String json)
    {
        devCards = new DevCards(json, DevCard.AmountType.PLAYABLE);
    }

    /**
     * Initializes the resources from Json.
     *
     * @param json the Json to initialize the resources from.
     */
    public void initResources(String json)
    {
        resources = new Resources(json);
    }

    /**
     * Gives an amount of resources of the given type.
     *
     * @param type the type to give.
     * @param num  the amount to give.
     * @throws InsufficientResourcesException if there are insufficient resources to execute the transactions.
     */
    public void giveResource(ResourceType type, int num) throws InsufficientResourcesException
    {
        if ((resources.getResource(type).getAmount() - num) < 0)
        {
            throw new InsufficientResourcesException();
        } else
        {
            resources.getResource(type).subResource(num);
        }
    }

    /**
     * Takes an amount of resources of the given type.
     *
     * @param type the type to take.
     * @param num  the amount to take.
     * @throws CatanException if something goes wrong.
     */
    public void takeResource(ResourceType type, int num) throws CatanException
    {
        if ((resources.getResource(type).getAmount() + num) > 19)
        {
            throw new CatanException();
        } else
        {
            resources.getResource(type).addResource(num);
        }
    }

    @Override public int hashCode()
    {
        return 0;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Bank bank = (Bank) o;

        return !(devCards != null ? !devCards.equals(bank.devCards) : bank.devCards != null);

    }

    public void awardPlayers(ResourceType resourceType, StructureType type, Set<PlayerIndex> players)
    {
        int num = 1;
        if (type.equals(StructureType.CITY))
        {
            num = 2;
        }
        for (PlayerIndex playerIndex : players)
        {
            try
            {
                giveResource(resourceType, num);
            } catch (InsufficientResourcesException e)
            {
                e.printStackTrace();
            }
        }
    }
}
