package shared.model.player;

import client.data.PlayerInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.locations.VertexLocation;
import shared.model.bank.Bank;
import shared.model.bank.PlayerBank;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.map.CatanMap;
import shared.model.map.structure.Port;

import java.util.List;

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

    /**
     * A player bank is instantiated with the creation of each new player
     */
    public Player()
    {
        bank = new PlayerBank();
        info = new PlayerInfo();
        discarded = false;
        playedDev = false;
    }

    public Player(PlayerInfo playerInfo)
    {
        this();
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
        this.bank.getStructures().getStructure(StructureType.ROAD).setAmountRemaining(jobj.get("roads").getAsInt());
        this.bank.getStructures().getStructure(StructureType.CITY).setAmountRemaining(jobj.get("cities").getAsInt());
        this.bank.getStructures().getStructure(StructureType.SETTLEMENT)
                .setAmountRemaining(jobj.get("settlements").getAsInt());

        this.info.setName(jobj.get("name").getAsString());
        this.info.setPlayerIndex(jobj.get("playerIndex").getAsInt());
        this.info.setColor(CatanColor.valueOf(jobj.get("color").getAsString().toUpperCase()));
        this.info.setId(jobj.get("playerID").getAsInt());
        this.playedDev = jobj.get("playedDevCard").getAsBoolean();
        this.discarded = jobj.get("discarded").getAsBoolean();
    }

    public boolean isDiscarded()
    {
        return discarded;
    }

    public void setDiscarded(boolean discarded)
    {
        this.discarded = discarded;
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

    public void sendOffer(TradeOffer offer) throws CatanException
    {
        List<Resource> resources = offer.getOffer().getAllResources();
        for (Resource resource : resources)
        {
            ResourceType type = resource.getType();
            int amount = resource.getAmount();
            if (amount < 0)
            {
                resource.addResource(resource.getAmount());

            } else if (amount > 0)
            {
                if (getResources().getAmount(type) < amount)
                {
                    throw new InsufficientResourcesException("Sender does not have enough of " + type);
                } else
                {
                    resource.subResource(resource.getAmount());
                }
            }
        }
    }

    public void acceptOffer(TradeOffer offer) throws CatanException
    {
        List<Resource> resources = offer.getOffer().getAllResources();
        for (Resource resource : resources)
        {
            ResourceType type = resource.getType();
            int amount = resource.getAmount();
            if (amount < 0)
            {
                if (getResources().getAmount(type) < amount)
                {
                    throw new InsufficientResourcesException("Receiver does not have enough of " + type);
                } else
                {
                    bank.subResource(resource.getType(), resource.getAmount());
                }
            } else if (amount > 0)
            {
                resource.addResource(resource.getAmount());
            }
        }
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
    public void buyRoad(boolean isFree) throws CatanException
    {
        bank.buyRoad(isFree);
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
    public void buySettlement(boolean isFree) throws CatanException
    {
        bank.buySettlement(isFree);
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

    public boolean hasPlayedDev()
    {
        return playedDev;
    }

    public void setPlayedDev(boolean playedDev)
    {
        this.playedDev = playedDev;
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
     * @param data
     * @throws InsufficientResourcesException
     */
    public void playDevCard(DevCardType type, Object... data) throws CatanException
    {
        if (!playedDev)
        {
            try
            {
                bank.playDevCard(type, data);
                if (type != DevCardType.MONUMENT)
                {
                    playedDev = true;
                }
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
            player.addProperty("roads", bank.getStructures().getStructure(StructureType.ROAD).getAmountRemaining());
            player.addProperty("cities", bank.getStructures().getStructure(StructureType.CITY).getAmountRemaining());
            player.addProperty("settlements",
                    bank.getStructures().getStructure(StructureType.SETTLEMENT).getAmountRemaining());
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
     * @return gets the color of the player.
     */
    public CatanColor getPlayerColor()
    {
        return info.getColor();
    }

    public int getVictoryPoints()
    {
        return bank.getVictoryPoints();
    }

    public PlayerIndex getPlayerIndex()
    {
        return getPlayerInfo().getPlayerIndex();
    }

    public int getResourceCount()
    {
        return bank.getResources().totalResources();
    }

    public Resources getResources()
    {
        return getBank().getResources();
    }

    public List<Resource> getAllResources()
    {
        return bank.getResources().getAllResources();
    }


    /**
     * Checks if the player can currently trade using a given resource
     *
     * @param type the type of resource
     * @return True if you can trade, false if you can't.
     */
    public boolean canTradeResource(ResourceType type)
    {

        return getBank().getResources().getAmount(type) >= getTradeRatio(type);

    }

    /**
     * Gets the trade ratio of a resource based on what ports you have
     *
     * @param type resource type to check
     * @return the ratio as an integer (returns 3 if you can trade 3:1)
     */
    public int getTradeRatio(ResourceType type)
    {
        PortType port = ResourceType.toPortType(type);

        if (getBank().canAccessPort(port))
        {
            //if you can access the specific resourceport
            return 2;
        } else if (getBank().canAccessPort(PortType.THREE))
        {
            //if you can access the three port
            return 3;
        } else
        {
            //don't got no ports to trade with
            return 4;
        }
    }

    public int getStructureCount(StructureType type)
    {
        return bank.amountOf(type);
    }

    public void populatePorts(CatanMap map)
    {
        for (Port port : map.getPorts())
        {
            for (VertexLocation vertex : map.getNearbyVertices(port.getEdgeLocation().getNormalizedLocation()))
            {
                if (map.getStructures().containsKey(vertex))
                {
                    if (map.getStructures().get(vertex).getOwner() == getPlayerIndex())
                    {
                        bank.addPort(port.getResource());
                    }
                }
            }
        }
    }

    public boolean hasEnoughResources(Resources cost)
    {
        return bank.hasEnoughResources(cost);
    }

    public int getKnights()
    {
        return bank.getKnights();
    }

    public void maritimeTrade(TradeRatio ratio, ResourceType inputResource, ResourceType outputResource)
            throws InsufficientResourcesException
    {
        int amountToTrade = ratio.getRatio();
        if (getResources().getAmount(inputResource) < amountToTrade)
        {
            throw new InsufficientResourcesException("Not enough resources to trade away for maritime trade!");
        } else
        {
            bank.getResources().increase(outputResource);
            bank.getResources().decrease(inputResource, amountToTrade);
        }
    }

    public void discardCards(Resources discardedCards)
    {
        bank.subtractResources(discardedCards);
    }

    /**
     * Robs the player of a random resource, and returns the type removed.
     * @return the type of resource that the victim lost.
     */
    public ResourceType robPlayer() throws InsufficientResourcesException {
        return bank.rob();
    }

    public void increaseResource(ResourceType robbedType, int amount) {
        bank.addResource(robbedType,amount);
    }
}

