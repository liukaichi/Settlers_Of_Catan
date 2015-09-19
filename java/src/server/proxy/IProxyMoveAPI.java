package server.proxy;

public interface IProxyMoveAPI extends IProxyPlayingCommands, IProxyDevCardCommands
{
    // Anytime Commands
    void sendChat();

    // Rolling Commands
    void rollNumber();

    // Miscellaneous Commands
    void acceptTrade();

    void discardCards();
}
