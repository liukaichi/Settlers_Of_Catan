package shared.model.bank;

import java.util.ArrayList;
import java.util.List;

import client.main.Catan;
import com.sun.javafx.geom.AreaOp;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.Structures;
import shared.model.map.structure.Port;

/**
 * This class extends Bank with properties and methods specific to the Player
 */
public class PlayerBank extends Bank
{
    private Structures structures;
    private int knights, victoryPoints, monuments;
    private DevCards devCards;
    private Resources resources;
    /**
     * List of ports owned by player
     */
    private List<PortType> ports;

    public PlayerBank() throws CatanException
    {
        super(false);
        knights = 0;
        victoryPoints = 0;
        monuments = 0;
        ports = new ArrayList<>();
        devCards = super.getDevCards();
        resources = super.getResources();
        structures = new Structures();
    }

    public Structures getStructures() {
        return structures;
    }

    private int amountOf(ResourceType type){
        return resources.getResource(type).getAmount();
    }

    private int amountOf(DevCardType cardType, DevCard.AmountType amountType){
        return devCards.getCard(cardType).getAmount(amountType);
    }

    /**
     * Determines if PlayerBank has the resources to purchase a DevCard
     * 
     * @return true if the condition is met
     */
    public boolean canBuyDevCard() {
        if ((amountOf(ResourceType.SHEEP) > 0)
                && (amountOf(ResourceType.ORE) > 0)
                && (amountOf(ResourceType.WHEAT) > 0)
                && (!super.getDevCardDeck().empty())){
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
        if (canBuyDevCard()){
            devCards.getCard(super.getDevCardDeck().pop()).addCard(DevCard.AmountType.UNPLAYABLE, 1);
        }
        else {
            //cant buy card message
        }

    }

    /**
     * Determines if PlayerBank has the resources to purchase a Road
     * 
     * @return true if the condition is met
     */
    public boolean canBuyRoad()
    {
        if ((amountOf(ResourceType.BRICK) > 0) && (amountOf(ResourceType.WOOD) > 0)){
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
    public void buyRoad() throws InsufficientResourcesException
    {
        if (canBuyRoad()){
            try{
                resources.getResource(ResourceType.BRICK).subResource(1);
                resources.getResource(ResourceType.WOOD).subResource(1);
                structures.getStructure(StructureType.ROAD).addAmount(1);
            }
            catch (CatanException e){
                e.getMessage();
            }
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a Settlement
     * 
     * @return true if the condition is met
     */
    public boolean canBuySettlement()
    {
        if((amountOf(ResourceType.SHEEP) > 0)
                && (amountOf(ResourceType.WOOD) > 0)
                && (amountOf(ResourceType.WHEAT) > 0)
                && (amountOf(ResourceType.BRICK) > 0)){
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
    public void buySettlement() throws InsufficientResourcesException
    {
        if (canBuySettlement()){

            try{
                resources.getResource(ResourceType.SHEEP).subResource(1);
                resources.getResource(ResourceType.WOOD).subResource(1);
                resources.getResource(ResourceType.WHEAT).subResource(1);
                resources.getResource(ResourceType.BRICK).subResource(1);
                structures.getStructure(StructureType.SETTLEMENT).addAmount(1);
            }
            catch (CatanException e){
                e.getMessage();
            }
        }
    }

    /**
     * Determines if PlayerBank has the resources to purchase a City
     * 
     * @return true if the condition is met
     */
    public boolean canBuyCity()
    {
        if ((amountOf(ResourceType.ORE) > 2) && (amountOf(ResourceType.WHEAT) > 1)){
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
    public void buyCity() throws InsufficientResourcesException
    {
        if (canBuyCity()){
            try{
                resources.getResource(ResourceType.ORE).subResource(3);
                resources.getResource(ResourceType.WHEAT).subResource(2);
                structures.getStructure(StructureType.CITY).addAmount(1);
                structures.getStructure(StructureType.SETTLEMENT).subAmount(1);
            }
            catch (CatanException e){
                e.getMessage();
            }
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
        if (amountOf(type, DevCard.AmountType.PLAYABLE) > 0){
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
        if (canPlayDevCard(type)){
            DevCard card = devCards.getCard(type);
            card.subCard(DevCard.AmountType.PLAYABLE, 1);
            card.addCard(DevCard.AmountType.PLAYED, 1);
            card.playAction();
        }
        else{
            //cannot play
        }
    }

    /**
     * Determines if a Player has control over a Port
     * 
     * @param type
     *        the type of Port to check
     * @return true if the condition is met
     */
    public boolean canAccessPort(PortType type)
    {
        if (ports.contains(type)){
            return true;
        }
        return false;
    }

}
