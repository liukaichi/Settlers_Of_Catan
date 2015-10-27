package client.map;

import client.base.ObserverController;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.facade.ClientFacade;
import client.state.InitialState;
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
import java.util.logging.Logger;

/**
 * Implementation for the map controller
 */
public class MapController extends ObserverController implements IMapController
{

    private IRobView robView;
    private PlayerInfo currentPlayer;
    private ClientFacade facade;
    private static final Logger LOGGER = Logger.getLogger(MapController.class.getName());
    private HexLocation robberLocation;

    public MapController(IMapView view, IRobView robView)
    {

        super(view);

        setRobView(robView);

        setupWater();

        state = new InitialState();

        facade = ClientFacade.getInstance();
        /*
        // @formatter:off
        try
        {
            initFromModel(new ClientModel(new String(Files.readAllBytes(
                    Paths.get("C:\\Users\\cstaheli\\git\\the-settlers-of-catan\\sample\\complexJSONModel.json")))));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        // @formatter:on
        */

    }

    /**
     * Builds a map completely out of water. The initFromModel() will replace
     * tiles that aren't water.
     */
    private void setupWater()
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

    private void initFromModel(ClientModel model)
    {

        CatanMap map = model.getMap();
        HashMap<HexLocation, Hex> hexes = (HashMap<HexLocation, Hex>) map.getHexes();
        for (Hex hex : hexes.values())
        {
            getView().addHex(hex.getLocation(), hex.getHexType());
            if(hex.getNumberTile() != -1)
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
            LOGGER.info("Adding port. " + port);
        }

        /*
        // <temp>
        //@formatter:off
        Random rand = new Random();

        for (int x = 0; x <= 3; ++x)
        {

            int maxY = 3 - x;
            for (int y = -3; y <= maxY; ++y)
            {
                int r = rand.nextInt(HexType.values().length);
                HexType hexType = HexType.values()[r];
                HexLocation hexLoc = new HexLocation(x, y);
                getView().addHex(hexLoc, hexType);
                getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest), CatanColor.RED);
                getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest), CatanColor.BLUE);
                getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South), CatanColor.ORANGE);
                getView().placeSettlement(new VertexLocation(hexLoc, VertexDirection.NorthWest), CatanColor.GREEN);
                getView().placeCity(new VertexLocation(hexLoc, VertexDirection.NorthEast), CatanColor.PURPLE);
            }

            if (x != 0)
            {
                int minY = x - 3;
                for (int y = minY; y <= 3; ++y)
                {
                    int r = rand.nextInt(HexType.values().length);
                    HexType hexType = HexType.values()[r];
                    HexLocation hexLoc = new HexLocation(-x, y);
                    getView().addHex(hexLoc, hexType);
                    getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest), CatanColor.RED);
                    getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest), CatanColor.BLUE);
                    getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South), CatanColor.ORANGE);
                    getView().placeSettlement(new VertexLocation(hexLoc, VertexDirection.NorthWest), CatanColor.GREEN);
                    getView().placeCity(new VertexLocation(hexLoc, VertexDirection.NorthEast), CatanColor.PURPLE);
                }
            }
        }

        PortType portType = PortType.BRICK;
        getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
        getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
        getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
        getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
        getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
        getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);

        getView().placeRobber(new HexLocation(0, 0));

        getView().addNumber(new HexLocation(-2, 0), 2);
        getView().addNumber(new HexLocation(-2, 1), 3);
        getView().addNumber(new HexLocation(-2, 2), 4);
        getView().addNumber(new HexLocation(-1, 0), 5);
        getView().addNumber(new HexLocation(-1, 1), 6);
        getView().addNumber(new HexLocation(1, -1), 8);
        getView().addNumber(new HexLocation(1, 0), 9);
        getView().addNumber(new HexLocation(2, -2), 10);
        getView().addNumber(new HexLocation(2, -1), 11);
        getView().addNumber(new HexLocation(2, 0), 12);

        // </temp>     
        // @formatter:on
         *     
         */

    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLocation)
    {
        return state.canPlaceRoad(edgeLocation.getNormalizedLocation());
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertexLocation)
    {

        return state.canPlaceSettlement(vertexLocation.getNormalizedLocation());
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertexLocation)
    {

        return state.canPlaceCity(vertexLocation.getNormalizedLocation());
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLocation)
    {

        return state.canPlaceRobber(hexLocation);
    }

    @Override
    public void placeRoad(EdgeLocation edgeLocation)
    {
        state.placeRoad(edgeLocation.getNormalizedLocation());
        //getView().placeRoad(edgeLocation, ClientFacade.getInstance().getClientPlayer().getColor());
    }

    @Override
    public void placeSettlement(VertexLocation vertexLocation)
    {
        state.placeSettlement(vertexLocation.getNormalizedLocation());
        //getView().placeSettlement(vertexLocation, ClientFacade.getInstance().getClientPlayer().getColor());
    }


    @Override
    public void placeCity(VertexLocation vertexLocation)
    {
        state.placeCity(vertexLocation.getNormalizedLocation());
        //getView().placeCity(vertexLocation, ClientFacade.getInstance().getClientPlayer().getColor());
    }

    @Override
    public void placeRobber(HexLocation hexLocation)
    {
        getRobView().setPlayers(facade.getRobPlayerInfo(hexLocation));
        robberLocation = hexLocation;
        getRobView().showModal();
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {

        getView().startDrop(pieceType, ClientFacade.getInstance().getClientPlayer().getColor(), true);
    }

    @Override
    public void cancelMove()
    {

    }

    @Override
    public void playSoldierCard()
    {
        //state.playSoldierCard();
    }

    @Override
    public void playRoadBuildingCard()
    {

    }

    @Override
    public void robPlayer(RobPlayerInfo victim)
    {
        getRobView().closeModal();
        state.robPlayer(victim, robberLocation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        ClientModel model = (ClientModel) o;
        state.update(this, model, arg);
        this.initFromModel(model);

    }

}
