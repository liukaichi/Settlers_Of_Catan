package shared.model;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import com.google.gson.*;
import shared.definitions.PlayerIndex;
import shared.definitions.TurnStatus;
import shared.definitions.exceptions.CatanException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.bank.Bank;
import shared.model.bank.PlayerBank;
import shared.model.bank.card.DevCard;
import shared.model.map.CatanMap;
import shared.model.message.Chat;
import shared.model.message.Log;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.util.Observable;

/**
 * The client model for the Catan game
 *
 * @author amandafisher
 */
public class ClientModel extends Observable
{
    private GameInfo gameInfo;
    private Bank bank;
    private Chat chat;
    private Log log;
    private CatanMap map;
    private TradeOffer tradeOffer;
    private TurnTracker turnTracker;
    private int version;
    private PlayerIndex winner;
    private boolean isUpdating;

    public ClientModel()
    {
        bank = null;
        chat = null;
        log = null;
        map = null;
        tradeOffer = null;
        turnTracker = null;
        version = 0;
        winner = PlayerIndex.NONE;
    }

    public void updateModel(ClientModel model)
    {
        /*
        For thread safety, don't update if previous update has not finished.
         */
        if(!isUpdating) {
            isUpdating = true;
            this.gameInfo = model.gameInfo;
            this.bank = model.bank;
            this.chat = model.chat;
            this.log = model.log;
            this.map = model.map;
            this.tradeOffer = model.tradeOffer;
            this.turnTracker = model.turnTracker;
            this.version = model.version;
            this.winner = model.winner;
            this.setChanged();
            if (turnTracker != null){
                this.notifyObservers(turnTracker.getStatus());
            }
            else
            {
                this.notifyObservers();
            }

            isUpdating = false;
        }
    }

    public ClientModel(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject model = (JsonObject) parser.parse(json);
        String resourceJson = model.get("deck").toString();
        bank = new Bank(true);
        bank.initDevCards(resourceJson);
        map = new CatanMap(model.getAsJsonObject("map").toString());

        gameInfo = new GameInfo();
        JsonArray players = model.getAsJsonArray("players");

        for (JsonElement player : players)
        {
            if (!(player instanceof JsonNull))
            {
                Player newPlayer = new Player(player.toString());
                newPlayer.populatePorts(map);
                gameInfo.addPlayer(newPlayer);

            }
        }

        log = new Log(model.getAsJsonObject("log").toString());
        chat = new Chat(model.getAsJsonObject("chat").toString());
        bank.initResources(model.getAsJsonObject("bank").toString());
        turnTracker = new TurnTracker(model.getAsJsonObject("turnTracker").toString());

        winner = PlayerIndex.fromInt(model.getAsJsonPrimitive("winner").getAsInt());
        version = model.getAsJsonPrimitive("version").getAsInt();

        if (model.has("tradeOffer"))
        {
            tradeOffer = new TradeOffer(model.getAsJsonObject("tradeOffer").toString());
        }

    }

    public GameInfo getGameInfo()
    {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo)
    {
        this.gameInfo = gameInfo;
    }

    public Bank getBank()
    {
        return bank;
    }

    public Chat getChat()
    {
        return chat;
    }

    public Log getLog()
    {
        return log;
    }

    public CatanMap getMap()
    {
        return map;
    }

    public TradeOffer getTradeOffer()
    {
        return tradeOffer;
    }

    public TurnTracker getTurnTracker()
    {
        return turnTracker;
    }

    public int getVersion()
    {
        return version;
    }

    public PlayerIndex getWinner()
    {
        return winner;
    }

    public void setWinner(PlayerIndex winner)
    {
        this.winner = winner;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public boolean canBuyDevCard()
    {
        return bank.getDevCards().totalCards() > 0;
    }

    /**
     * Method that indicates whether a player has the ability to place a
     * settlement in a certain location on the map
     * 
     * @param player this will be the player placing the settlement
     * @param location this will be the location of the settlement; must ensure that
     *        this space on the map is empty
     * @param allowDisconnected whether or not the settlement can be disconnected. Only true during setup. Not currently used.
     * @return boolean  returns true if the location is vacant and at least
     *         two spaces away from another settlement otherwise returns false
     */
    public boolean canPlaceSettlement(PlayerIndex player, VertexLocation location, boolean allowDisconnected)
    {
        return map.canPlaceSettlement(player, location, allowDisconnected);
    }

    /**
     * Method that indicates whether a player has the ability to place a city in
     * a certain location on the map
     * 
     * @param player this will be the player placing the city
     * @param location this will be the location of the city; must ensure that this
     *        space already has a settlement located their owned by this player
     * @return boolean returns true if there is a settlement at the specified
     *         location and it is owned by the player otherwise returns false
     */
    public boolean canPlaceCity(PlayerIndex player, VertexLocation location)
    {
        return map.canPlaceCity(player, location);
    }

    /**
     * Method that indicates whether a player has the ability to place a city on
     * a certain edge on the map
     * 
     * @param player this will be the player placing the road
     * @param location this will be the edge location where the road will be placed;
     *        must ensure this space is empty on the map
     * @param allowDisconnected whether or not the road can be disconnected. Only true during setup.
     * @return boolean  returns true if the player owns a settlement or city
     *         at the neighboring vertex locations and there is no current road
     *         there otherwise returns false
     */
    public boolean canPlaceRoad(PlayerIndex player, EdgeLocation location, boolean allowDisconnected)
    {
        return map.canPlaceRoad(player, location, allowDisconnected);
    }

    /**
     * Method that indicates whether a player has the ability to move a robber
     * on a certain Hex
     * 
     * @param player this will be the player placing the robber
     * @param location this will be the hex location where the robber will be placed;
     *        cannot place on water or where the robber already is
     * @return boolean  returns true if it is not moving to its current
     *         location and it is not a sea piece otherwise returns false
     */
    public boolean canMoveRobber(PlayerIndex player, HexLocation location)
    {
        return map.canMoveRobber(player, location);
    }

    public boolean canBuyRoad(Player player)
    {
        return player.canBuyRoad();
    }

    /**
     * Updates the PlayerBank to decrement resources used and increment road
     * count
     * 
     * @throws CatanException
     */
    public void buyRoad(Player player) throws CatanException
    {
        player.buyRoad();
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     * 
     * @return true if both conditions are met
     */
    public boolean canBuySettlement(Player player)
    {
        return player.canBuySettlement();
    }

    /**
     * Determines if the PlayerBank has Cities left to purchase AND if the
     * resources required are available
     * 
     * @return true if both conditions are met
     */
    public boolean canBuyCity(Player player)
    {
        return player.canBuyCity();
    }

    /**
     * Updates the currentTurn counter
     */
    public void updateCurrentTurn(PlayerInfo playerCurrentTurn)
    {
        turnTracker.updateCurrentTurn(playerCurrentTurn);
    }

    /**
     * Updates the longestRoad counter. A player has the longest road if he or
     * she has at least 5 roads
     */
    public void updateLongestRoad(PlayerBank playerLongestRoad)
    {
        turnTracker.updateLongestRoad(playerLongestRoad);
    }

    /**
     * Updates the largest army counter A player has the largest army if he or
     * she has at least 3 knights
     */
    public void updateLargestArmy(PlayerBank playerLargestArmy)
    {
        turnTracker.updateLargestArmy(playerLargestArmy);
    }

    /**
     * Updates the status string based on the current phase of the player's turn
     */
    public void updateStatus(TurnStatus playerTurnStatus)
    {
        turnTracker.updateStatus(playerTurnStatus);
    }

    /**
     * returns a serialized json representation of the object.
     * 
     * @return a string of json
     */
    @Override
    public String toString()
    {
        JsonParser parser = new JsonParser();
        JsonObject model = new JsonObject();
        model.add("deck", parser.parse(bank.getDevCards().toString(DevCard.AmountType.PLAYABLE)));
        model.add("map", parser.parse(map.toString()));
        JsonArray players = new JsonArray();
        for (Player player : gameInfo.getPlayers())
        {
            players.add(parser.parse(player.toString()));
        }
        model.add("players", players);
        model.add("log", parser.parse(log.toString()));
        model.add("chat", parser.parse(chat.toString()));
        model.add("bank", parser.parse(bank.getResources().toString()));
        model.add("turnTracker", parser.parse(turnTracker.toString()));
        model.addProperty("winner", winner.getIndex());
        model.addProperty("version", version);

        if (tradeOffer != null)
        {
            model.add("tradeOffer", parser.parse(tradeOffer.toString()));
        }

        return model.toString();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ClientModel that = (ClientModel) o;

        if (version != that.version)
            return false;
        if (gameInfo != null ? !gameInfo.equals(that.gameInfo) : that.gameInfo != null)
            return false;
        if (bank != null ? !bank.equals(that.bank) : that.bank != null)
            return false;
        if (chat != null ? !chat.equals(that.chat) : that.chat != null)
            return false;
        if (log != null ? !log.equals(that.log) : that.log != null)
            return false;
        if (map != null ? !map.equals(that.map) : that.map != null)
            return false;
        if (tradeOffer != null ? !tradeOffer.equals(that.tradeOffer) : that.tradeOffer != null)
            return false;
        if (turnTracker != null ? !turnTracker.equals(that.turnTracker) : that.turnTracker != null)
            return false;
        return winner == that.winner;

    }

    /**
     * if client player is being offered a trade this returns true
     * @return true if the model contains a trade offer, false otherwise.
     */
    public boolean hasTradeOffer()
    {
        TradeOffer offer = getTradeOffer();
        PlayerInfo clientPlayer = ClientFacade.getInstance().getClientPlayer();
        int currentPlayer = clientPlayer.getNormalizedPlayerIndex();
        return (offer != null && (offer.getReceiver() == currentPlayer || offer.getSender() == currentPlayer));
    }
}
