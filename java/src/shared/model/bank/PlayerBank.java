package shared.model.bank;

import java.util.*;

import shared.definitions.*;
import shared.definitions.exceptions.*;
import shared.model.bank.card.*;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.*;

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

    @Override
    public DevCards getDevCards()
    {
        return playerDevCards;
    }

    @Override
    public Resources getResources()
    {
        return playerResources;
    }

    public void setKnights(int knights)
    {
        this.knights = knights;
    }

    public void setVictoryPoints(int victoryPoints)
    {
        this.victoryPoints = victoryPoints;
    }

    public void setMonuments(int monuments)
    {
        this.monuments = monuments;
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

    public void addKnights(int knights)
    {
        this.knights += knights;
    }

    public int getVictoryPoints()
    {
        return victoryPoints;
    }

    public void addVictoryPoints(int victoryPoints)
    {
        this.victoryPoints += victoryPoints;
    }

    public int getMonuments()
    {
        return monuments;
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

    @Override
    public void giveResource(ResourceType type, int num) throws InsufficientResourcesException
    {
        if ((playerResources.getResource(type).getAmount() - num) < 0)
        {
            throw new InsufficientResourcesException();
        }
        else
        {
            playerResources.getResource(type).subResource(num);
        }
    }

    @Override
    public void takeResource(ResourceType type, int num) throws CatanException
    {
        if ((playerResources.getResource(type).getAmount() + num) > 19)
        {
            throw new CatanException();
        }
        else
        {
            playerResources.getResource(type).addResource(num);
        }
    }

    public void payResource(ResourceType type, int num)
    {
        try
        {
            this.giveResource(type, num);
            super.takeResource(type, num);
        }
        catch (CatanException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a DevCard
     * 
     * @return true if the condition is met
     */
    public boolean canBuyDevCard()
    {
        if ((amountOf(ResourceType.SHEEP) > 0) && (amountOf(ResourceType.ORE) > 0) && (amountOf(ResourceType.WHEAT) > 0)
                && (!super.getDevCardDeck().empty()))
        {
            return true;
        }
        return false;
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
        if ((amountOf(ResourceType.BRICK) > 0) && (amountOf(ResourceType.WOOD) > 0))
        {
            return true;
        }
        return false;
    }

    /**
     * Increments the road count in PlayerBank and decrements appropriate
     * resources
     *
     * @throws InsufficientResourcesException
     */
    public void buyRoad() throws CatanException
    {
        if (canBuyRoad())
        {
            payResource(ResourceType.BRICK, 1);
            payResource(ResourceType.WOOD, 1);
            structures.getStructure(StructureType.ROAD).addAmount(1);
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a Settlement
     * 
     * @return true if the condition is met
     */
    public boolean canBuySettlement()
    {
        if ((amountOf(ResourceType.SHEEP) > 0) && (amountOf(ResourceType.WOOD) > 0)
                && (amountOf(ResourceType.WHEAT) > 0) && (amountOf(ResourceType.BRICK) > 0))
        {
            return true;
        }
        return false;
    }

    /**
     * Increments the settlement count in PlayerBank and decrements appropriate
     * resources
     * 
     * @throws InsufficientResourcesException
     */
    public void buySettlement() throws CatanException
    {
        if (canBuySettlement())
        {
            payResource(ResourceType.SHEEP, 1);
            payResource(ResourceType.WOOD, 1);
            payResource(ResourceType.WHEAT, 1);
            payResource(ResourceType.BRICK, 1);

            structures.getStructure(StructureType.SETTLEMENT).addAmount(1);
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a City
     * 
     * @return true if the condition is met
     */
    public boolean canBuyCity()
    {
        if ((amountOf(ResourceType.ORE) > 2) && (amountOf(ResourceType.WHEAT) > 1)
                && (amountOf(StructureType.SETTLEMENT) > 0))
        {
            return true;
        }
        return false;
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
            structures.getStructure(StructureType.CITY).addAmount(1);
            structures.getStructure(StructureType.SETTLEMENT).subAmount(1);
        }
    }

    /**
     * Determines if the specified DevCard is playable AND is available in
     * PlayerBank
     * 
     * @param type
     *        the type of DevCard to check
     * @return true if both conditions are met
     */
    public boolean canPlayDevCard(DevCardType type)
    {
        if (amountOf(type, DevCard.AmountType.PLAYABLE) > 0)
        {
            return true;
        }

        return false;
    }

    /**
     * Calls playAction() on the DevCard
     * 
     * @param type
     *        the type of DevCard to play
     * @throws InsufficientResourcesException
     */
    public void playDevCard(DevCardType type) throws InsufficientResourcesException
    {
        if (canPlayDevCard(type))
        {
            DevCard card = playerDevCards.getCard(type);
            card.subCard(DevCard.AmountType.PLAYABLE, 1);
            card.addCard(DevCard.AmountType.PLAYED, 1);
            card.playAction(this);
        }
    }

    /**
     * Determines if a player has control over a Port
     * 
     * @param type
     *        the type of Port to check
     * @return true if the condition is met
     */
    public boolean canAccessPort(PortType type)
    {
        if (ports.contains(type))
        {
            return true;
        }
        return false;
    }

    public void addPort(PortType type)
    {
        ports.add(type);
    }

    @Override
    public boolean equals(Object o)
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

    @Override
    public int hashCode()
    {
        return 0;
    }
}
