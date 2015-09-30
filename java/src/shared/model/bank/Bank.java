package shared.model.bank;

import shared.definitions.DevCardType;
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
    private Resources resources;
    private DevCards devCards;
    private Stack<DevCardType> devCardDeck;

    public Bank(Boolean isGameBank){
        initialize();
        if (isGameBank){
            initializeDevCardDeck();
        }
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

    private void initialize(){
        resources = new Resources();
        devCards = new DevCards();
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
}
