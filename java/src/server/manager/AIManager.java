package server.manager;

import client.data.GameInfo;
import client.data.PlayerInfo;
import server.ServerModel;
import server.facade.AbstractServerFacade;
import shared.definitions.AIType;
import shared.definitions.CatanColor;
import shared.definitions.PlayerIndex;
import shared.definitions.exceptions.CatanException;
import shared.locations.HexLocation;
import shared.model.map.structure.MapStructure;
import shared.model.map.structure.Settlement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Manager for all things "AI."
 */
public class AIManager implements Serializable
{
    private ArrayList<String> aiNames;
    private int id = 0;
    private Map<Integer, AIUser> aiUsers;

    public AIManager()
    {
        aiUsers = new HashMap<>();
        aiNames = new ArrayList<>();
        aiNames.add("Miguel");
        aiNames.add("Hannah");
        aiNames.add("Quinn");
        aiNames.add("Ken");
        aiNames.add("Squall");
    }


    public PlayerInfo createAIPlayer(GameInfo gameInfo, AIType type) throws CatanException
    {
        ArrayList<CatanColor> usedColors = new ArrayList<>();
        ArrayList<String> usedNames = new ArrayList<>();
        for (PlayerInfo info : gameInfo.getPlayers())
        {
            usedColors.add(info.getColor());
            usedNames.add(info.getName());
        }
        AIUser newAI = new AIUser(--id, randomName(usedNames), randomColor(usedColors), type);
        aiUsers.put(id, newAI);
        return newAI;
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

    public AIUser getAIUser(int userID)
    {
        if (aiUsers.containsKey(userID))
        {
            return aiUsers.get(userID);
        }
        else return null;
    }

    public PlayerIndex getRandomOtherPlayer(PlayerIndex playerIndex)
    {
        int newIndex = -1;
        do
        {
            newIndex =  (int)(Math.random() * 4 + .5) % 4;
        } while(newIndex == playerIndex.getIndex());

        return PlayerIndex.fromInt(newIndex);
    }

    public HexLocation getHexToRob(PlayerIndex anotherPlayer, ServerModel model)
    {
        boolean foundHex = false;
        HexLocation hexToRob = null;
        Random generator = new Random();
        do
        {
            Object[] values = model.getMap().getStructures().values().toArray();
            MapStructure mapStructure = (MapStructure) values[generator.nextInt(values.length)];
            if (mapStructure instanceof Settlement)
            {
                if (mapStructure.getOwner() == anotherPlayer && model.canMoveRobber(
                        anotherPlayer,mapStructure.getLocation().getNormalizedLocation().getHexLoc()))
                {
                    foundHex = true;
                    hexToRob = mapStructure.getLocation().getHexLoc();
                }
            }
        } while (foundHex == false);
        return hexToRob;
    }

}
