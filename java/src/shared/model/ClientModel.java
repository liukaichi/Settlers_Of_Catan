package shared.model;

import java.util.ArrayList;
import java.util.List;

import client.data.GameInfo;
import shared.definitions.PlayerIndex;

public class ClientModel
{
    private GameInfo gameInfo;
    private Bank bank;
    private MessageList chat, log;
    private CatanMap map;
    private List<Player> players;
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
        players = new ArrayList<Player>();
        tradeOffer = null;
        turnTracker = null;
        version = 0;
        winner = PlayerIndex.NONE;
    }
}
