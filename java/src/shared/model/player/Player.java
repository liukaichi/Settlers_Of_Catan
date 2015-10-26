package shared.model.player;

import client.data.PlayerInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.PlayerIndex;
import shared.definitions.StructureType;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.Bank;
import shared.model.bank.PlayerBank;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resources;

/**
 * Represents a player playing the game. There can be up to 4 players in a
 * single game.
 */
public class Player
{
    /**
     * @see Bank
     */
    private PlayerBank bank;

    /**
     * @see PlayerInfo
     */
    private PlayerInfo info;
    private boolean discarded;
    private boolean playedDev;
    private int resourceNumber;

    public boolean isDiscarded()
    {
        return discarded;
    }

    public void setDiscarded(boolean discarded)
    {
        this.discarded = discarded;
    }

    /**
     * A player bank is instantiated with the creation of each new player
     */
    public Player() throws CatanException
    {
        bank = new PlayerBank();
        info = new PlayerInfo();
        discarded = false;
        playedDev = false;
    }

    public Player(PlayerInfo playerInfo)
    {
        info = playerInfo;
    }

    public Player(String json)
    {
        JsonElement jele = new JsonParser().parse(json);
        JsonObject jobj = jele.getAsJsonObject();

        bank = new PlayerBank();
        info = new PlayerInfo();

        this.bank.setKnights(jobj.get("soldiers").getAsInt());
        this.bank.setMonuments(jobj.get("monuments").getAsInt());
        this.bank.setVictoryPoints(jobj.get("victoryPoints").getAsInt());

        this.bank.setPlayerResources(new Resources(jobj.get("resources").getAsJsonObject().toString()));
        this.bank.setPlayerDevCards(
                new DevCards(jobj.get("oldDevCards").getAsJsonObject().toString(), DevCard.AmountType.PLAYABLE));
        this.bank.getDevCards()
                .setDevCards(jobj.get("newDevCards").getAsJsonObject().toString(), DevCard.AmountType.UNPLAYABLE);

        this.info.setName(jobj.get("name").getAsString());
        this.info.setPlayerIndex(jobj.get("playerIndex").getAsInt());
        this.info.setColor(CatanColor.valueOf(jobj.get("color").getAsString().toUpperCase()));
        this.info.setId(jobj.get("playerID").getAsInt());
        this.playedDev = jobj.get("playedDevCard").getAsBoolean();
        this.discarded = jobj.get("discarded").getAsBoolean();
    }

    public PlayerInfo getPlayerInfo()
    {
        return info;
    }

    public PlayerBank getBank()
    {
        return bank;
    }

    public TradeOffer createOffer(Player receiver)
    {
        return new TradeOffer(this, receiver);
    }

    public boolean canBuyRoad()
    {
        return bank.canBuyRoad();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment road
     * count
     *
     * @throws CatanException
     */
    public void buyRoad() throws CatanException
    {
        bank.buyRoad();
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     *
     * @return true if both conditions are met
     */
    public boolean canBuySettlement()
    {
        return bank.canBuySettlement();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment
     * settlement count
     *
     * @throws CatanException
     */
    public void buySettlement() throws CatanException
    {
        bank.buySettlement();
    }

    /**
     * Determines if the PlayerBank has Cities left to purchase AND if the
     * resources required are available
     *
     * @return true if both conditions are met
     */
    public boolean canBuyCity()
    {
        return bank.canBuyCity();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment city
     * count
     *
     * @throws CatanException
     * @throws InsufficientResourcesException
     */
    public void buyCity() throws CatanException
    {
        bank.buyCity();
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     *
     * @return true if both conditions are met
     */
    public boolean canBuyDevCard()
    {
        return bank.canBuyDevCard();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment the
     * appropriate DevCard count
     *
     * @throws InsufficientResourcesException
     */
    public void buyDevCard() throws InsufficientResourcesException
    {
        bank.buyDevCard();
    }

    /**
     * Determines if the PlayerBank has the specified DevCard to play AND if the
     * DevCard is playable during the turn
     *
     * @param type the type of DevCard being checked
     * @return true if both conditions are met
     */
    public boolean canPlayDevCard(DevCardType type)
    {
        if (!playedDev)
        {
            return bank.canPlayDevCard(type);
        } else
        {
            return false;
        }
    }


    /**
     * Plays the action of the specified DevCard
     *
     * @param type -- the type of DevCard to play
     * @throws InsufficientResourcesException
     */
    public void playDevCard(DevCardType type) throws CatanException
    {
        if (!playedDev)
        {
            try
            {
                bank.playDevCard(type);
            } catch (InsufficientResourcesException e)
            {
                e.printStackTrace();
            }
        } else
        {
            throw new CatanException();
        }
    }

    /**
     * Gets the players name from info
     *
     * @return string -- the name of the player
     */
    public String getName()
    {
        return info.getName();
    }

    @Override public String toString()
    {

        JsonObject player = new JsonObject();
        {
            player.add("resources", bank.getResources().toJsonObject());
            player.add("oldDevCards", bank.getDevCards().toJsonObject(DevCard.AmountType.PLAYABLE));
            player.add("newDevCards", bank.getDevCards().toJsonObject(DevCard.AmountType.UNPLAYABLE));
            player.addProperty("roads", bank.amountOf(StructureType.ROAD));
            player.addProperty("cities", bank.amountOf(StructureType.CITY));
            player.addProperty("settlements", bank.amountOf(StructureType.SETTLEMENT));
            player.addProperty("soldiers", bank.getKnights());
            player.addProperty("victoryPoints", bank.getVictoryPoints());
            player.addProperty("monuments", bank.getMonuments());
            player.addProperty("playedDevCard", playedDev);
            player.addProperty("discarded", discarded);
            player.addProperty("playerID", info.getId());
            player.addProperty("playerIndex", info.getNormalizedPlayerIndex());
            player.addProperty("name", getName());
            player.addProperty("color", info.getColor().toString().toLowerCase());
        }

        return player.toString();
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Player player = (Player) o;

        if (discarded != player.discarded)
            return false;
        if (playedDev != player.playedDev)
            return false;
        if (bank != null ? !bank.equals(player.bank) : player.bank != null)
            return false;
        return !(info != null ? !info.equals(player.info) : player.info != null);

    }

    @Override public int hashCode()
    {
        return 0;
    }

    /**
     * @return
     */
    public CatanColor getPlayerColor()
    {
        return info.getColor();
    }

    public int getVictoryPoints()
    {
        return bank.getVictoryPoints();
    }
    public PlayerIndex getPlayerIndex(){
        return getPlayerInfo().getPlayerIndex();
    }

    public int getResourceCount()
    {
        return bank.getResources().totalResources();
    }
}
