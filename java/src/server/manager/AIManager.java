package server.manager;

import client.data.GameInfo;
import client.data.PlayerInfo;
import shared.definitions.CatanColor;
import shared.definitions.exceptions.CatanException;
import shared.model.player.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dtaylor on 11/14/2015.
 */
class AIManager
{
    ArrayList<String> aiNames;
    private int id = 0;

    public AIManager()
    {
        aiNames = new ArrayList<>();
        aiNames.add("Miguel");
        aiNames.add("Hannah");
        aiNames.add("Quinn");
        aiNames.add("Ken");
        aiNames.add("Squall");
    }

    public Player createAIPlayer(GameInfo gameInfo) throws CatanException
    {
        ArrayList<CatanColor> usedColors = new ArrayList<>();
        ArrayList<String> usedNames = new ArrayList<>();
        for (PlayerInfo info : gameInfo.getPlayerInfos())
        {
            usedColors.add(info.getColor());
            usedNames.add(info.getName());
        }
        PlayerInfo aiPlayerInfo = new PlayerInfo(--id, randomName(usedNames), randomColor(usedColors));
        return new Player(aiPlayerInfo);
    }

    private CatanColor randomColor(ArrayList<CatanColor> usedColors)
    {
        Random rand = new Random();
        CatanColor color = CatanColor.values()[rand.nextInt(9)];
        while (usedColors.contains(color))
        {
            color = CatanColor.values()[rand.nextInt(9)];
        }
        return color;
    }

    private String randomName(ArrayList<String> usedNames)
    {
        Random rand = new Random();
        String name = aiNames.get(rand.nextInt(5));
        while (usedNames.contains(name))
        {
            name = aiNames.get(rand.nextInt(5));
        }
        return name;
    }
}
