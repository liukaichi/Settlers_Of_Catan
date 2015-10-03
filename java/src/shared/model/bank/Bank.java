package shared.model.bank;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resources;

import java.util.Collections;
import java.util.Stack;

/**
 * The base class bank, used to hold resources and dev cards
 * @author dtaylor
 *
 */
public class Bank
{
    private static Resources resources;
    private DevCards devCards;
    private static Stack<DevCardType> devCardDeck;

    public Bank(boolean isGameBank){
        initialize(isGameBank);
    }

    private void initializeDevCardDeck(){
        for (int i = 0; i < 2; i++) {
            devCardDeck.push(DevCardType.MONOPOLY);
        }
        for (int i = 0; i < 2; i++) {
            devCardDeck.push(DevCardType.ROAD_BUILD);
        }
        for (int i = 0; i < 2; i++) {
            devCardDeck.push(DevCardType.YEAR_OF_PLENTY);
        }
        for (int i = 0; i < 5; i++) {
            devCardDeck.push(DevCardType.MONUMENT);
        }
        for (int i = 0; i < 14; i++) {
            devCardDeck.push(DevCardType.SOLDIER);
        }
        Collections.shuffle(devCardDeck);
    }

    private void initialize(boolean isGameBank){
        resources = new Resources(isGameBank);
        devCards = new DevCards(isGameBank);
        if (devCardDeck == null){
            devCardDeck = new Stack<>();
            initializeDevCardDeck();
        }
    }

    public Stack<DevCardType> getDevCardDeck(){ return devCardDeck; }

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
    public void initDevCards(String json){
        devCards = new DevCards(json, DevCard.AmountType.PLAYABLE);
    }

    public void initResources(String json){
        resources = new Resources(json);
    }

    public void giveResource(ResourceType type, int num) throws InsufficientResourcesException {
        if ((resources.getResource(type).getAmount() - num) < 0){
            throw new InsufficientResourcesException();
        }
        else{
            resources.getResource(type).subResource(num);
        }
    }

    public void takeResource(ResourceType type, int num) throws CatanException {
        if ((resources.getResource(type).getAmount() + num) > 19){
            throw new CatanException();
        }
        else{
            resources.getResource(type).addResource(num);
        }
    }

}
