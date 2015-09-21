package shared.model.card;

import shared.definitions.DevCardType;

/**
 * Object that represents a player's hand of development cards in the Catan game
 * @author amandafisher
 *
 */
public class DevCardHand
{
    private MonopolyCardList monopoly;
    private RoadBuildingCardList roadBuilding;
    private YearOfPlentyCardList yearOfPlenty;
    private KnightCardList knight;
    private VictoryPointCardList monument;
    
    /**
     * Gets a list of cards matching the specified card type
     * @param cardType -- the card type of the list to be returned
     * @returns DevCardList of the specified DevCardType
     */
    public DevCardList getDevCardList(DevCardType cardType)
    {
    	
    }
}
