package shared.model;

import client.data.GameInfo;
import client.data.PlayerInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.PlayerIndex;
import shared.model.bank.Bank;
import shared.model.bank.card.DevCard;
import shared.model.map.CatanMap;
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

    }
}
