package shared.model.player;

import client.data.PlayerInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.StructureType;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import javafx.scene.paint.Color;
import jdk.nashorn.internal.parser.JSONParser;
import shared.definitions.*;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.*;
import shared.model.bank.card.DevCard;
import shared.model.bank.card.DevCards;
import shared.model.bank.resource.Resource;
import shared.model.bank.resource.Resources;
import shared.model.bank.structure.Structure;
import shared.model.map.structure.Road;
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

    public boolean isDiscarded() {
        return discarded;
    }

    public void setDiscarded(boolean discarded) {
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

    public Player(String json){
        JsonElement jele = new JsonParser().parse(json);
        JsonObject jobj = jele.getAsJsonObject();

        bank = new PlayerBank();
        info = new PlayerInfo();

        this.bank.getStructures().getStructure(StructureType.CITY).setAmountBuilt(jobj.get("cities").getAsInt());
        this.bank.getStructures().getStructure(StructureType.SETTLEMENT).setAmountBuilt(jobj.get("settlements").getAsInt());
        this.bank.getStructures().getStructure(StructureType.ROAD).setAmountBuilt(jobj.get("roads").getAsInt());

        this.bank.setKnights(jobj.get("soldiers").getAsInt());
        this.bank.setMonuments(jobj.get("monuments").getAsInt());
        this.bank.setVictoryPoints(jobj.get("victoryPoints").getAsInt());

        this.bank.setPlayerResources(new Resources(jobj.get("resources").getAsJsonObject().toString()));
        this.bank.setPlayerDevCards(new DevCards(jobj.get("oldDevCards").getAsJsonObject().toString(), DevCard.AmountType.PLAYABLE));
        this.bank.getDevCards().setDevCards(jobj.get("newDevCards").getAsJsonObject().toString(), DevCard.AmountType.UNPLAYABLE);

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
    public PlayerBank getBank() {return bank;}

    public TradeOffer createOffer(Player receiver){
        return new TradeOffer(this, receiver);
    }
    
    boolean canBuyRoad()
    {
    	return bank.canBuyRoad();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment road count
     */
    public void buyRoad() {
        try {
            bank.buyRoad();
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        } catch (CatanException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     * @return true if both conditions are met
     */
    public boolean canBuySettlement()
    {
    	return bank.canBuySettlement();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment settlement count
     */
    public void buySettlement()
    {
        try {
            bank.buySettlement();
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        } catch (CatanException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the PlayerBank has Cities left to purchase AND if the
     * resources required are available
     * @return true if both conditions are met
     */
    public  boolean canBuyCity()
    {
    	return bank.canBuyCity();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment city count
     * @throws InsufficientResourcesException
     */
    public void buyCity() {
        try {
            bank.buyCity();
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        } catch (CatanException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     * @return true if both conditions are met
     */
    public boolean canBuyDevCard()
    {
        return bank.canBuyDevCard();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment the appropriate DevCard count
     * @throws InsufficientResourcesException
     */
    public void buyDevCard() {
        try {
            bank.buyDevCard();
        } catch (InsufficientResourcesException e) {
            e.printStackTrace();
        }
    }

    /**
     * Determines if the PlayerBank has the specified DevCard to play AND if the DevCard is playable
     * during the turn
     * @param type the type of DevCard being checked
     * @throws InsufficientResourcesException
     * @return true if both conditions are met
     */
    public boolean canPlayDevCard(DevCardType type)
    {
        if(!playedDev){
            return bank.canPlayDevCard(type);
        }
        else{
            return false;
        }
    }

    /**
     * Plays the action of the specified DevCard
     * @param type -- the type of DevCard to play
     * @throws InsufficientResourcesException
     */
    public void playDevCard(DevCardType type) throws CatanException {
        if(!playedDev){
            try {
                bank.playDevCard(type);
            } catch (InsufficientResourcesException e) {
                e.printStackTrace();
            }
        }
        else{
            throw new CatanException();
        }
    }
    
    /**
     * Gets the players name from info
     * @return string -- the name of the player
     */
    public String getName()
    {
    	return info.getName();
    }

    @Override
    public String toString() {

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
}
