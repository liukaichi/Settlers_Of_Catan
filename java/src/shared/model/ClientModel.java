package shared.model;

import client.data.GameInfo;
import client.data.PlayerInfo;
import client.facade.ClientFacade;
import com.google.gson.*;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.bank.Bank;
import shared.model.bank.card.DevCard;
import shared.model.map.CatanMap;
import shared.model.message.Chat;
import shared.model.message.Log;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

/**
 * The client model for the Catan game.
 *
 * @author amandafisher
 */
public class ClientModel extends Observable
{
    protected TradeOffer tradeOffer;
    private GameInfo gameInfo;
    private List<Player> players;
    private Bank bank;
    private Chat chat;
    private Log log;
    private CatanMap map;
    private TurnTracker turnTracker;
    private int version;
    private PlayerIndex winner;
    private boolean isUpdating;

    /**
     * Initializes everything properly.
     */
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
        players = new ArrayList<>();
    }

    /**
     * Initializes a ClientModel from Json.
     *
     * @param json the Json to initialize from.
     */
    public ClientModel(String json)
    {
        this();
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
                this.players.add(newPlayer);
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

    public void setGameInfo(GameInfo gameInfo)
    {
        this.gameInfo = gameInfo;
    }

    /**
     * Per the Observer pattern, this method replaces the pieces of the model and then notifies the Observers to the
     * model that a change has been made.
     *
     * @param model the model to replace
     * @see Observable
     * @see java.util.Observer
     */
    public void updateModel(ClientModel model)
    {
        /*
        For thread safety, don't update if previous update has not finished.
         */
        if (!isUpdating)
        {
            isUpdating = true;
            this.players = model.players;
            this.bank = model.bank;
            this.chat = model.chat;
            this.log = model.log;
            this.map = model.map;
            this.tradeOffer = model.tradeOffer;
            this.turnTracker = model.turnTracker;
            this.version = model.version;
            this.winner = model.winner;
            this.setChanged();

            if (turnTracker != null)
            {
                this.notifyObservers(turnTracker.getStatus());
            } else
            {
                this.notifyObservers();
            }

            isUpdating = false;
        }
    }

    public List<PlayerInfo> getPlayerInfos()
    {
        List<PlayerInfo> playerInfos = new ArrayList<>();
        for (Player player : players)
        {
            playerInfos.add(player.getPlayerInfo());
        }
        return Collections.unmodifiableList(playerInfos);
    }

    public List<Player> getPlayers()
    {
        return Collections.unmodifiableList(players);
    }

    /**
     * Gets the color of the player with the given index.
     *
     * @param index the index of the player.
     * @return the color of the player with the given index.
     */
    public CatanColor getPlayerColor(PlayerIndex index)
    {
        return players.get(index.getIndex()).getPlayerColor();
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

    public void setVersion(int version)
    {
        this.version = version;
    }

    public PlayerIndex getWinner()
    {
        return winner;
    }

    public void setWinner(PlayerIndex winner)
    {
        this.winner = winner;
    }

    /**
     * Determines if there are any dev cards to buy.
     *
     * @return true if there are any dev cards to buy, false otherwise.
     */
    public boolean canBuyDevCard()
    {
        return bank.getDevCards().totalCards() > 0;
    }

    /**
     * Method that indicates whether a player has the ability to place a
     * settlement in a certain location on the map
     *
     * @param player            this will be the player placing the settlement
     * @param location          this will be the location of the settlement; must ensure that
     *                          this space on the map is empty
     * @param allowDisconnected whether or not the settlement can be disconnected. Only true during setup. Not currently used.
     * @return boolean  returns true if the location is vacant and at least
     * two spaces away from another settlement otherwise returns false
     */
    public boolean canPlaceSettlement(PlayerIndex player, VertexLocation location, boolean allowDisconnected)
    {
        return map.canPlaceSettlement(player, location, allowDisconnected);
    }

    /**
     * Method that indicates whether a player has the ability to place a city in
     * a certain location on the map
     *
     * @param player   this will be the player placing the city
     * @param location this will be the location of the city; must ensure that this
     *                 space already has a settlement located their owned by this player
     * @return boolean returns true if there is a settlement at the specified
     * location and it is owned by the player otherwise returns false
     */
    public boolean canPlaceCity(PlayerIndex player, VertexLocation location)
    {
        return map.canPlaceCity(player, location);
    }

    /**
     * Method that indicates whether a player has the ability to place a city on
     * a certain edge on the map
     *
     * @param player            this will be the player placing the road
     * @param location          this will be the edge location where the road will be placed;
     *                          must ensure this space is empty on the map
     * @param allowDisconnected whether or not the road can be disconnected. Only true during setup.
     * @return boolean  returns true if the player owns a settlement or city
     * at the neighboring vertex locations and there is no current road
     * there otherwise returns false
     */
    public boolean canPlaceRoad(PlayerIndex player, EdgeLocation location, boolean allowDisconnected)
    {
        return map.canPlaceRoad(player, location, allowDisconnected);
    }

    /**
     * Builds a city for the given player at the given location.
     *
     * @param player   the player who is building the road.
     * @param location the location where the road is being built.
     */
    public void buildRoad(PlayerIndex player, EdgeLocation location)
    {
        map.buildRoad(player, location);
    }

    /**
     * Method that indicates whether a player has the ability to move a robber
     * on a certain Hex
     *
     * @param player   this will be the player placing the robber
     * @param location this will be the hex location where the robber will be placed;
     *                 cannot place on water or where the robber already is
     * @return boolean  returns true if it is not moving to its current
     * location and it is not a sea piece otherwise returns false
     */
    public boolean canMoveRobber(PlayerIndex player, HexLocation location)
    {
        return map.canMoveRobber(player, location);
    }

    /**
     * Determines if the given player can buy a road.
     *
     * @param player the player to check.
     * @return true if the given player can buy a road, false otherwise.
     */
    public boolean canBuyRoad(Player player)
    {
        return player.canBuyRoad();
    }

    /**
     * Determines if the PlayerBank has Settlements left to purchase AND if the
     * resources required are available
     *
     * @param player the player to check.
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
     * @param player the player to check.
     * @return true if both conditions are met
     */
    public boolean canBuyCity(Player player)
    {
        return player.canBuyCity();
    }


    /**
     * returns a serialized json representation of the object.
     *
     * @return a string of json
     */
    @Override public String toString()
    {
        JsonParser parser = new JsonParser();
        JsonObject model = new JsonObject();
        model.add("deck", parser.parse(bank.getDevCards().toString(DevCard.AmountType.PLAYABLE)));
        model.add("map", parser.parse(map.toString()));
        JsonArray players = new JsonArray();
        for (Player player : this.players)
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

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(model);
        return json.toString();
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ClientModel that = (ClientModel) o;

        if (version != that.version)
            return false;
        //        if (gameInfo != null ? !gameInfo.equals(that.gameInfo) : that.gameInfo != null)
        //            return false;
        if (players != null ? !players.equals(that.players) : that.players != null)
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
     *
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
