package client.map;

import client.base.ObserverController;
import client.data.GameInfo;
import client.data.RobPlayerInfo;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ClientModel;
import shared.model.map.CatanMap;
import shared.model.map.Hex;
import shared.model.map.structure.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 * Implementation for the map controller
 */
public class MapController extends ObserverController implements IMapController
{

    private IRobView robView;
    private HexLocation robberLocation;
    private EdgeLocation roadBuildingLoc1, roadBuildingLoc2;
    private boolean isDevCard;

    public MapController(IMapView view, IRobView robView)
    {
        super(view);
        setRobView(robView);
        initializeMap();
    }

    /**
     * Builds a map completely out of water. The updateMapView() will replace
     * tiles that aren't water.
     */
    private void initializeMap()
    {
        for (int x = 0; x <= 3; ++x)
        {

            int maxY = 3 - x;
            for (int y = -3; y <= maxY; ++y)
            {
                HexType hexType = HexType.WATER;
                HexLocation hexLoc = new HexLocation(x, y);
                getView().addHex(hexLoc, hexType);
            }

            if (x != 0)
            {
                int minY = x - 3;
                for (int y = minY; y <= 3; ++y)
                {
                    HexType hexType = HexType.WATER;
                    HexLocation hexLoc = new HexLocation(-x, y);
                    getView().addHex(hexLoc, hexType);
                }
            }
        }

    }

    @Override public IMapView getView()
    {
        return (IMapView) super.getView();
    }

    public IRobView getRobView()
    {
        return robView;
    }

    private void setRobView(IRobView robView)
    {
        this.robView = robView;
    }

    private void updateMapView(ClientModel model)
    {

        CatanMap map = model.getMap();
        HashMap<HexLocation, Hex> hexes = (HashMap<HexLocation, Hex>) map.getHexes();
        for (Hex hex : hexes.values())
        {
            getView().addHex(hex.getLocation(), hex.getHexType());
            if (hex.getNumberTile() != -1)
                getView().addNumber(hex.getLocation(), hex.getNumberTile());

            LOGGER.fine("Adding Hex." + hex);
        }
        getView().placeRobber(map.getRobberLocation());

        HashMap<EdgeLocation, Road> roads = (HashMap<EdgeLocation, Road>) map.getRoads();
        GameInfo game = model.getGameInfo();
        for (Road road : roads.values())
        {
            CatanColor playerColor = game.getPlayerColor(road.getOwner());
            getView().placeRoad(road.getLocation(), playerColor);
            LOGGER.fine("building Road." + road);
        }

        HashMap<VertexLocation, MapStructure> structures = (HashMap<VertexLocation, MapStructure>) map.getStructures();
        for (MapStructure mapStructure : structures.values())
        {
            CatanColor color = game.getPlayerColor(mapStructure.getOwner());
            if (mapStructure instanceof Settlement)
            {
                getView().placeSettlement(mapStructure.getLocation(), color);
                LOGGER.fine("PlaceSettlement. " + mapStructure);
            } else if (mapStructure instanceof City)
            {
                getView().placeCity(mapStructure.getLocation(), color);
                LOGGER.fine("PlaceCity. " + mapStructure);
            }
        }

        ArrayList<Port> ports = (ArrayList<Port>) map.getPorts();
        for (Port port : ports)
        {
            getView().addPort(port.getEdgeLocation(), port.getResource());
            LOGGER.fine("Adding port. " + port);
        }

    }

    @Override public boolean canPlaceRoad(EdgeLocation edgeLocation)
    {
        return state.canPlaceRoad(edgeLocation.getNormalizedLocation());
    }

    @Override public boolean canPlaceSettlement(VertexLocation vertexLocation)
    {
        return state.canPlaceSettlement(vertexLocation.getNormalizedLocation());
    }

    @Override public boolean canPlaceCity(VertexLocation vertexLocation)
    {
        return state.canPlaceCity(vertexLocation.getNormalizedLocation());
    }

    @Override public boolean canPlaceRobber(HexLocation hexLocation)
    {

        return state.canPlaceRobber(hexLocation);
    }

    @Override public void placeRoad(EdgeLocation edgeLocation)
    {
        state.placeRoad(edgeLocation.getNormalizedLocation());
    }

    @Override public void placeSettlement(VertexLocation vertexLocation)
    {
        state.placeSettlement(vertexLocation.getNormalizedLocation());
    }

    @Override public void placeCity(VertexLocation vertexLocation)
    {
        state.placeCity(vertexLocation.getNormalizedLocation());
    }

    @Override public void placeRobber(HexLocation hexLocation)
    {
        state.placeRobber(hexLocation);

    }

    @Override public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {
        state.startMove(pieceType, isFree, allowDisconnected);
    }

    @Override public void cancelMove()
    {

    }

    @Override public void playSoldierCard()
    {
        getView().startDrop(PieceType.ROBBER, null, false);
    }

    public boolean isDevCard()
    {
        return isDevCard;
    }

    public void setIsDevCard(boolean isDevCard)
    {
        this.isDevCard = isDevCard;
    }

    @Override public void playRoadBuildingCard()
    {
        setIsDevCard(true);
        startMove(PieceType.ROAD, true, false);
    }

    @Override public void robPlayer(RobPlayerInfo victim)
    {
        state.robPlayer(victim, robberLocation);
    }

    public EdgeLocation getRoadBuildingLoc1()
    {
        return this.roadBuildingLoc1;
    }

    public void setRoadBuildingLoc1(EdgeLocation edgeLoc)
    {
        this.roadBuildingLoc1 = edgeLoc;
    }

    public EdgeLocation getRoadBuildingLoc2()
    {
        return this.roadBuildingLoc2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;
        state.update(this, model, arg);
        this.updateMapView(model);

    }

    public HexLocation setRobberLocation(HexLocation hexLocation)
    {
        return robberLocation = hexLocation;
    }
}
