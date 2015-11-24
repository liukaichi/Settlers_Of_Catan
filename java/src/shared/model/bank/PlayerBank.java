package shared.model.bank;

import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.BankStructure;
import shared.model.bank.structure.Structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class extends Bank with properties and methods specific to the player
 */
public class PlayerBank extends Bank
{
    private Structures structures;
    private int knights, victoryPoints, monuments;
    private Resources playerResources;
    private DevCards playerDevCards;

    /**
     * List of ports owned by player
     */
    private List<PortType> ports;

    public PlayerBank()
    {
        super(false);
        knights = 0;
        victoryPoints = 0;
        monuments = 0;
        ports = new ArrayList<>();
        structures = new Structures();
        playerDevCards = new DevCards();
        playerResources = new Resources(false);
    }

    @Override public DevCards getDevCards()
    {
        return playerDevCards;
    }

    @Override public Resources getResources()
    {
        return playerResources;
    }

    public List<Resource> getAllResources()
    {
        return getResources().getAllResources();
    }


    public void setPlayerResources(Resources playerResources)
    {
        this.playerResources = playerResources;
    }

    public void setPlayerDevCards(DevCards playerDevCards)
    {
        this.playerDevCards = playerDevCards;
    }

    public int getKnights()
    {
        return knights;
    }

    public void setKnights(int knights)
    {
        this.knights = knights;
    }

    public void addKnights(int knights)
    {
        this.knights += knights;
    }

    public int getVictoryPoints()
    {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints)
    {
        this.victoryPoints = victoryPoints;
    }

    public void addVictoryPoints(int victoryPoints)
    {
        this.victoryPoints += victoryPoints;
    }

    public int getMonuments()
    {
        return monuments;
    }

    public void setMonuments(int monuments)
    {
        this.monuments = monuments;
    }

    public void addMonuments(int monuments)
    {
        this.monuments += monuments;
    }

    public Resources gameResources()
    {
        return super.getResources();
    }

    public Structures getStructures()
    {
        return structures;
    }

    public int amountOf(ResourceType type)
    {
        return playerResources.getResource(type).getAmount();
    }

    public int amountOf(DevCardType cardType, DevCard.AmountType amountType)
    {
        return playerDevCards.getCard(cardType).getAmount(amountType);
    }

    public int amountOf(StructureType type)
    {
        return structures.getStructure(type).getAmount(BankStructure.AmountType.BUILT);
    }

    public void payResource(ResourceType type, int num) throws InsufficientResourcesException
    {
        if ((playerResources.getResource(type).getAmount() - num) < 0)
        {
            throw new InsufficientResourcesException("Not enough of resource to give.");
        } else
        {
            //
            super.addResource(type, num);
            playerResources.getResource(type).subResource(num);
        }
    }

    public void earnResource(ResourceType type, int num) throws CatanException
    {
        if (type == null)
        {
            return;
        }
        if ((playerResources.getResource(type).getAmount() + num) > 19)
        {
            throw new CatanException();
        } else
        {
            super.subResource(type, num);
            playerResources.getResource(type).addResource(num);
        }
    }

    public boolean hasEnoughResources(Resources cost)
    {
        List<Resource> resourcesList = playerResources.getAllResources();
        List<Resource> costList = cost.getAllResources();
        for (int i = 0; i < resourcesList.size(); ++i)
        {
            if (resourcesList.get(i).getAmount() < costList.get(i).getAmount())
            {
                return false;
            }
        }
        return true;
    }

    public void subtractResources(Resources cost)
    {
        List<Resource> resourcesList = playerResources.getAllResources();
        List<Resource> costList = cost.getAllResources();
        for (int i = 0; i < resourcesList.size(); ++i)
        {
            resourcesList.get(i).subResource(costList.get(i).getAmount());
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a DevCard
     *
     * @return true if the condition is met
     */
    public boolean canBuyDevCard()
    {
        Resources cost = new Resources(0, 0, 1, 1, 1);
        return hasEnoughResources(cost) && !super.getDevCardDeck().empty();
    }

    /**
     * Increments the appropriate DevCard count in PlayerBank and decrements
     * appropriate resources
     *
     * @throws InsufficientResourcesException
     */
    public void buyDevCard() throws InsufficientResourcesException
    {
        if (canBuyDevCard())
        {
            payResource(ResourceType.SHEEP, 1);
            payResource(ResourceType.ORE, 1);
            payResource(ResourceType.WHEAT, 1);

            playerDevCards.getCard(super.getDevCardDeck().pop()).addCard(DevCard.AmountType.UNPLAYABLE, 1);
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a Road
     *
     * @return true if the condition is met
     */
    public boolean canBuyRoad()
    {
        Resources cost = new Resources(1, 1, 0, 0, 0);
        return hasEnoughResources(cost) && structures.getAmountRemaining(StructureType.ROAD) > 0;
    }

    /**
     * Increments the road count in PlayerBank and decrements appropriate
     * resources
     *
     * @throws InsufficientResourcesException
     */
    public void buyRoad(boolean isFree) throws CatanException
    {
        if (isFree)
        {
            structures.getStructure(StructureType.ROAD).addAmountBuilt(1);
        }
        else if (canBuyRoad())
        {
            payResource(ResourceType.BRICK, 1);
            payResource(ResourceType.WOOD, 1);
            structures.getStructure(StructureType.ROAD).addAmountBuilt(1);
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a Settlement
     *
     * @return true if the condition is met
     */
    public boolean canBuySettlement()
    {
        Resources cost = new Resources(1, 1, 1, 1, 0);
        return hasEnoughResources(cost) && structures.getAmountRemaining(StructureType.SETTLEMENT) > 0;
    }

    /**
     * Increments the settlement count in PlayerBank and decrements appropriate
     * resources
     *
     * @throws InsufficientResourcesException
     * @param isFree
     */
    public void buySettlement(boolean isFree) throws CatanException
    {
        if(isFree)
        {
            structures.getStructure(StructureType.SETTLEMENT).addAmountBuilt(1);
        }
        else if (canBuySettlement())
        {
            payResource(ResourceType.SHEEP, 1);
            payResource(ResourceType.WOOD, 1);
            payResource(ResourceType.WHEAT, 1);
            payResource(ResourceType.BRICK, 1);
            structures.getStructure(StructureType.SETTLEMENT).addAmountBuilt(1);
        }
        else
        {
            throw new InsufficientResourcesException("not enough resources");
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a City
     *
     * @return true if the condition is met
     */
    public boolean canBuyCity()
    {
        Resources cost = new Resources(0, 0, 0, 2, 3);
        return hasEnoughResources(cost) && (structures.getAmountRemaining(StructureType.CITY) > 0);
    }

    /**
     * Increments the city count in PlayerBank and decrements appropriate
     * resources
     *
     * @throws InsufficientResourcesException
     */
    public void buyCity() throws CatanException
    {
        if (canBuyCity())
        {
            payResource(ResourceType.ORE, 3);
            payResource(ResourceType.WHEAT, 2);
            structures.getStructure(StructureType.CITY).addAmountBuilt(1);
            structures.getStructure(StructureType.SETTLEMENT).subAmountBuilt(1);
        }
        else
        {
            throw new InsufficientResourcesException("Not enough resources");
        }
    }

    /**
     * Determines if the specified DevCard is playable AND is available in PlayerBank
     *
     * @param type the type of DevCard to check
     * @return true if both conditions are met
     */
    public boolean canPlayDevCard(DevCardType type)
    {
        return amountOf(type, DevCard.AmountType.PLAYABLE) > 0;

    }

    /**
     * Calls playAction() on the DevCard
     *
     * @param type the type of DevCard to play
     * @param data
     * @throws InsufficientResourcesException
     */
    public void playDevCard(DevCardType type, Object... data) throws InsufficientResourcesException
    {
        if (canPlayDevCard(type))
        {
            DevCard card = playerDevCards.getCard(type);
            card.subCard(DevCard.AmountType.PLAYABLE, 1);
            card.addCard(DevCard.AmountType.PLAYED, 1);
            //            card.playAction(this, data);
        }
    }

    /**
     * Determines if a player has control over a Port
     *
     * @param type the type of Port to check
     * @return true if the condition is met
     */
    public boolean canAccessPort(PortType type)
    {
        return ports.contains(type);
    }

    public void addPort(PortType type)
    {
        ports.add(type);
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        PlayerBank that = (PlayerBank) o;

        if (knights != that.knights)
            return false;
        if (victoryPoints != that.victoryPoints)
            return false;
        if (monuments != that.monuments)
            return false;
        if (structures != null ? !structures.equals(that.structures) : that.structures != null)
            return false;
        if (playerResources != null ? !playerResources.equals(that.playerResources) : that.playerResources != null)
            return false;
        if (playerDevCards != null ? !playerDevCards.equals(that.playerDevCards) : that.playerDevCards != null)
            return false;
        return !(ports != null ? !ports.equals(that.ports) : that.ports != null);

    }

    @Override public int hashCode()
    {
        return 0;
    }

    public ResourceType rob() throws InsufficientResourcesException {
        List<ResourceType> resources = new ArrayList<>();
        for (Resource resource: getAllResources())
        {
            if (resource.getAmount() > 0)
            {
                resources.add(resource.getType());
            }
        }
        if (resources.size() > 0)
        {
            Collections.shuffle(resources);
            ResourceType robbedType = resources.get(0);
            subResource(robbedType, 1);
            return robbedType;
        }
        else
        {
            throw new InsufficientResourcesException("No resources to rob");
        }


    }

}
