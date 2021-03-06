package shared.model.bank.card;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.definitions.exceptions.CatanException;
import shared.definitions.exceptions.InsufficientResourcesException;
import shared.model.bank.PlayerBank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a list of Development Cards
 */
public class DevCards implements Serializable
{

    private static final long serialVersionUID = -3980645116635388446L;
    private DevCard monopoly, roadBuilding, yearOfPlenty, soldier, monument;

    public DevCards()
    {
        initialize();
    }

    public DevCards(boolean isBank)
    {
        this();
        if (isBank)
        {
            monopoly.setAmountPlayable(2);
            roadBuilding.setAmountPlayable(2);
            yearOfPlenty.setAmountPlayable(2);
            soldier.setAmountPlayable(14);
            monument.setAmountPlayable(5);
        }
    }

    public DevCards(String json, DevCard.AmountType type)
    {
        this();
        setDevCards(json, type);
    }

    public void setDevCards(String json, DevCard.AmountType type)
    {

        JsonElement jele = new JsonParser().parse(json);
        JsonObject jobj = jele.getAsJsonObject();

        switch (type)
        {
        case UNPLAYABLE:
            this.yearOfPlenty.setAmountUnplayable(jobj.get("yearOfPlenty").getAsInt());
            this.monopoly.setAmountUnplayable(jobj.get("monopoly").getAsInt());
            this.roadBuilding.setAmountUnplayable(jobj.get("roadBuilding").getAsInt());
            this.soldier.setAmountUnplayable(jobj.get("soldier").getAsInt());
            this.monument.setAmountUnplayable(jobj.get("monument").getAsInt());
            break;
        case PLAYABLE:
            this.yearOfPlenty.setAmountPlayable(jobj.get("yearOfPlenty").getAsInt());
            this.monopoly.setAmountPlayable(jobj.get("monopoly").getAsInt());
            this.roadBuilding.setAmountPlayable(jobj.get("roadBuilding").getAsInt());
            this.soldier.setAmountPlayable(jobj.get("soldier").getAsInt());
            this.monument.setAmountPlayable(jobj.get("monument").getAsInt());
            break;
        case PLAYED:
            this.yearOfPlenty.setAmountPlayed(jobj.get("yearOfPlenty").getAsInt());
            this.monopoly.setAmountPlayed(jobj.get("monopoly").getAsInt());
            this.roadBuilding.setAmountPlayed(jobj.get("roadBuilding").getAsInt());
            this.soldier.setAmountPlayed(jobj.get("soldier").getAsInt());
            this.monument.setAmountPlayed(jobj.get("monument").getAsInt());
            break;
        }
    }

    private void initialize()
    {
        monopoly = new DevCard(DevCardType.MONOPOLY)
        {
            private static final long serialVersionUID = -7776231042072106220L;

            @Override public void playAction(PlayerBank context, Object data)
            {
                //name 1 resource
                //ALL players give all of that resource
            }
        };

        roadBuilding = new DevCard(DevCardType.ROAD_BUILD)
        {
            private static final long serialVersionUID = -5430856387781252399L;

            @Override public void playAction(PlayerBank context, Object data)
            {
                try
                {
                    context.buyRoad(true);
                    context.buyRoad(true);
                } catch (InsufficientResourcesException e)
                {
                    e.printStackTrace();
                } catch (CatanException e)
                {
                    e.printStackTrace();
                }
                // player can build 2 roads
            }
        };

        yearOfPlenty = new DevCard(DevCardType.YEAR_OF_PLENTY)
        {
            private static final long serialVersionUID = 1727925863791607745L;

            @Override public void playAction(PlayerBank context, Object data)
            {
                //take 2 resources from supply
            }

            private void action(PlayerBank context, ResourceType type1, ResourceType type2)
            {
                context.getResources().getResource(type1).addResource(1);
                context.getResources().getResource(type2).addResource(1);
            }
        };

        soldier = new DevCard(DevCardType.SOLDIER)
        {
            private static final long serialVersionUID = 7503957538481665980L;

            @Override public void playAction(PlayerBank context, Object data)
            {
                //move robber
                context.addKnights(1);
            }
        };

        monument = new DevCard(DevCardType.MONUMENT)
        {
            private static final long serialVersionUID = 8376801985366926291L;

            @Override public void playAction(PlayerBank context, Object data)
            {
                context.addMonuments(1);
            }
        };
    }

    public DevCard getCard(DevCardType type)
    {
        switch (type)
        {
        case MONOPOLY:
            return monopoly;
        case ROAD_BUILD:
            return roadBuilding;
        case YEAR_OF_PLENTY:
            return yearOfPlenty;
        case SOLDIER:
            return soldier;
        case MONUMENT:
            return monument;
        }

        return null;
    }

    public int totalCards()
    {
        return monopoly.total() + monument.total() + yearOfPlenty.total() + soldier.total() + roadBuilding.total();
    }

    public String toString(DevCard.AmountType type)
    {
        JsonObject devCards = toJsonObject(type);

        return devCards.toString();
    }

    public JsonObject toJsonObject(DevCard.AmountType type)
    {
        JsonObject devCards = new JsonObject();
        {
            devCards.addProperty("monopoly", monopoly.getAmount(type));
            devCards.addProperty("monument", monument.getAmount(type));
            devCards.addProperty("roadBuilding", roadBuilding.getAmount(type));
            devCards.addProperty("soldier", soldier.getAmount(type));
            devCards.addProperty("yearOfPlenty", yearOfPlenty.getAmount(type));
        }
        return devCards;
    }

    public List<DevCard> getDevCardList()
    {
        return Arrays.asList(monopoly, roadBuilding, yearOfPlenty, soldier, monument);
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DevCards devCards = (DevCards) o;

        if (monopoly != null ? !monopoly.equals(devCards.monopoly) : devCards.monopoly != null)
            return false;
        if (roadBuilding != null ? !roadBuilding.equals(devCards.roadBuilding) : devCards.roadBuilding != null)
            return false;
        if (yearOfPlenty != null ? !yearOfPlenty.equals(devCards.yearOfPlenty) : devCards.yearOfPlenty != null)
            return false;
        if (soldier != null ? !soldier.equals(devCards.soldier) : devCards.soldier != null)
            return false;
        return !(monument != null ? !monument.equals(devCards.monument) : devCards.monument != null);

    }

    @Override public int hashCode()
    {
        return 0;
    }
}
