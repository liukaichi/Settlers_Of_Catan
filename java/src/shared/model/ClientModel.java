package shared.model;

import client.data.GameInfo;
import client.data.PlayerInfo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.PlacementException;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.bank.Bank;
import shared.model.bank.card.DevCard;
import shared.model.map.CatanMap;
import shared.model.map.Hex;
import shared.model.map.structure.City;
import shared.model.map.structure.Road;
import shared.model.map.structure.Settlement;
import shared.model.map.structure.Structure;
import shared.model.message.Chat;
import shared.model.message.Log;
import shared.model.message.MessageLine;
import shared.model.player.Player;
import shared.model.player.TradeOffer;

/**
 * The client model for the Catan game
 * @author amandafisher
 *
 */
public class ClientModel
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

    public ClientModel(String json)
    {
        JsonParser parser = new JsonParser();
        JsonObject model = (JsonObject) parser.parse(json);
        bank.initDevCards(model.getAsJsonObject("deck").toString());
        map = new CatanMap(model.getAsJsonObject("map").toString());

        gameInfo = new GameInfo();
        JsonArray players = model.getAsJsonArray("players");
        // TODO Should be: for(Player player : gameinfo.getPlayers();
        for (Player player : gameInfo.getPlayers()){
            gameInfo.addPlayer(new Player(player.toString()));
        }

        // TODO these few need to have constructors using JSON.
        log = new Log(model.getAsJsonObject("log").toString());
        chat = new Chat(model.getAsJsonObject("chat").toString());
        bank.initResources(model.getAsJsonObject("bank").toString());
        turnTracker = new TurnTracker(model.getAsJsonObject("turnTracker").toString());

        winner = PlayerIndex.fromInt(model.getAsJsonPrimitive("winner").getAsInt());
        version = model.getAsJsonPrimitive("version").getAsInt();

        model.add("log", parser.parse(log.toString()));
        model.add("chat", parser.parse(chat.toString()));
        model.add("bank", parser.parse(bank.getResources().toString()));
        model.add("turnTracker", parser.parse(turnTracker.toString()));
        model.addProperty("winner", winner.getIndex());
        model.addProperty("version",version);

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
    public void setVersion(int version){
        this.version = version;
    }

    /**
     * Method that indicates whether a player has the ability to place a
     * settlement in a certain location on the map
     * 
     * @param player
     *        -- this will be the player placing the settlement
     * @param location
     *        -- this will be the location of the settlement; must ensure that
     *        this space on the map is empty
     * @return boolean -- returns true if the location is vacant and at least
     *         two spaces away from another settlement otherwise returns false
     */
    public boolean canPlaceSettlement(PlayerIndex player, VertexLocation location)
    {
        return map.canPlaceSettlement(player, location);
    }

    /**
     * Method that indicates whether a player has the ability to place a city in
     * a certain location on the map
     * 
     * @param player
     *        -- this will be the player placing the city
     * @param location
     *        -- this will be the location of the city; must ensure that this
     *        space already has a settlement located their owned by this player
     * @return boolean -- returns true if there is a settlement at the specified
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
     * @param player
     *        -- this will be the player placing the road
     * @param location
     *        -- this will be the edge location where the road will be placed;
     *        must ensure this space is empty on the map
     * @return boolean -- returns true if the player owns a settlement or city
     *         at the neighboring vertex locations and there is no current road
     *         there otherwise returns false
     */
    public boolean canPlaceRoad(PlayerIndex player, EdgeLocation location)
    {
        return map.canPlaceRoad(player, location);
    }

    /**
     * Method that indicates whether a player has the ability to move a robber
     * on a certain Hex
     * 
     * @param player
     *        -- this will be the player placing the robber
     * @param location
     *        -- this will be the hex location where the robber will be placed;
     *        cannot place on water or where the robber already is
     * @return boolean -- returns true if it is not moving to its current
     *         location and it is not a sea piece otherwise returns false
     */
    public boolean canMoveRobber(PlayerIndex player, HexLocation location)
    {
        return map.canMoveRobber(player, location);
    }

    /**
     * Method that places a road on the map
     * 
     * @param player
     *        -- this will be the player placing the road
     * @param location
     *        -- this will be the hex location where the road will be placed
     * @throws PlacementException
     */
    public void placeRoad(PlayerIndex player, EdgeLocation location) throws PlacementException
    {
        try
        {
            map.placeRoad(player, location);
        }
        catch (Exception e)
        {
            throw new PlacementException();
        }

    }

    /**
     * Method that places a settlement on the map
     * 
     * @param player
     *        -- this will be the player placing the settlement
     * @param location
     *        -- this will be the vertex location where the settlement will be
     *        placed
     * @throws PlacementException
     */
    public void placeSettlement(PlayerIndex player, VertexLocation location) throws PlacementException
    {
        try
        {
            map.placeSettlement(player, location);
        }
        catch (Exception e)
        {
            throw new PlacementException();
        }
    }

    /**
     * Method that places a city on the map
     * 
     * @param player
     *        -- this will be the player placing the city
     * @param location
     *        -- this will be the vertex location where the city will be placed
     * @throws PlacementException
     */
    public void placeCity(PlayerIndex player, VertexLocation location) throws PlacementException
    {
        try
        {
            map.placeCity(player, location);
        }
        catch (Exception e)
        {
            throw new PlacementException();
        }
    }

    /**
     * Method that moves the robber on the map
     * 
     * @param player
     *        -- this will be the player moving the robber
     * @param location
     *        -- this will be the hex location where the robber will be placed
     * @throws PlacementException
     */
    public void moveRobber(PlayerIndex player, HexLocation location) throws PlacementException
    {
        try
        {
            map.moveRobber(player, location);
        }
        catch (Exception e)
        {
            throw new PlacementException();
        }
    }
    @Override
    public String toString(){
        JsonParser parser = new JsonParser();
        JsonObject model = new JsonObject();
        model.add("deck", parser.parse(bank.getDevCards().toString(DevCard.AmountType.PLAYABLE)));
        model.add("map", parser.parse(map.toString()));
        JsonArray players = new JsonArray();

        // TODO Should be: for(Player player : gameinfo.getPlayers();
        for (Player player : gameInfo.getPlayers()){
            players.add(parser.parse(player.toString()));
        }
        model.add("players", players);

        JsonArray logLines = new JsonArray();
        // TODO The line toString needs to be changed according to how chats are structured.
        for (MessageLine line : getLog().getMessages()){
            logLines.add(parser.parse(line.toString()));
        }

        JsonArray chatLines = new JsonArray();
        // TODO The chat toString needs to be changed according to how chats are structured.
        for (MessageLine line : getLog().getMessages()){
            chatLines.add(parser.parse(line.toString()));
        }

        model.add("log", parser.parse(log.toString()));
        model.add("chat", parser.parse(chat.toString()));
        model.add("bank", parser.parse(bank.getResources().toString()));
        model.add("turnTracker", parser.parse(turnTracker.toString()));
        model.addProperty("winner", winner.getIndex());
        model.addProperty("version",version);

        return model.toString();
    }
}
