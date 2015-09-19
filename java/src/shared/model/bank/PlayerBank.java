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

    public void canBuyPiece(PieceType type) throws InsufficientFundsException, IllegalArgumentException
    {

    }

    public void canBuyDevCard() throws InsufficientFundsException
    {

    }

    public void canPlayDevCard(DevCardType type) throws InsufficientFundsException
    {

    }

}
