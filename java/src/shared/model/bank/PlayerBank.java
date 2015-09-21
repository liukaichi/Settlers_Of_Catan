package shared.model.bank;

import shared.definitions.*;
import shared.definitions.exceptions.InsufficientFundsException;

public class PlayerBank extends Bank
{
    private int cities, settlements, roads;
    private int soldiers, monuments;
    // should this be it's own class?
    private int victoryPoints;

    public PlayerBank()
    {
        super();
    }

    /**
     * Determines if the player can purchase a PieceType
     * @param type -- the type of structure being purchased
     * @returns true if game conditions are met
     * @throws InsufficientFundsException if there are insufficient structures left to be built
     * @throws IllegalArgumentException if @param type is not a PieceType
     */
    public boolean canBuyPiece(PieceType type) throws InsufficientFundsException, IllegalArgumentException
    {

    }
    
    /**
     * Determines if the player can purchase a DevCard
     * @returns true if game conditions are met
     * @throws InsufficientFundsException if there are insufficient resources to buy a DevCard
     */
    public boolean canBuyDevCard() throws InsufficientFundsException
    {

    }
    
    /**
     * Determines if the player is allowed to play a DevCard
     * @param type -- the DevCard being played
     * @returns true if the player is allowed to play a DevCard
     * @throws InsufficientFundsException if the player does not have the specified DevCard
     */
    public boolean canPlayDevCard(DevCardType type) throws InsufficientFundsException
    {

    }

}
