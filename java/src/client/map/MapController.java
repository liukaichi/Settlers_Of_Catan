package client.map;

import java.util.*;

import client.base.Controller;
import client.data.*;
import client.state.GameplayState;
import shared.definitions.*;
import shared.locations.*;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController
{

    private IRobView robView;
    private GameplayState state;
    private PlayerInfo currentPlayer;

    public MapController(IMapView view, IRobView robView)
    {

        super(view);

        setRobView(robView);

        initFromModel();
    }

    @Override
    public IMapView getView()
    {

        return (IMapView) super.getView();
    }

    private IRobView getRobView()
    {
        return robView;
    }

    private void setRobView(IRobView robView)
    {
        this.robView = robView;
    }

    protected void initFromModel()
    {

        // <temp>

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
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc)
    {
        return state.canPlaceRoad(PlayerIndex.NONE, edgeLoc);
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc)
    {

        return true;
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc)
    {

        return true;
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc)
    {

        return true;
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc)
    {

        getView().placeRoad(edgeLoc, CatanColor.ORANGE);
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc)
    {

        getView().placeSettlement(vertLoc, CatanColor.ORANGE);
    }

    @Override
    public void placeCity(VertexLocation vertLoc)
    {

        getView().placeCity(vertLoc, CatanColor.ORANGE);
    }

    @Override
    public void placeRobber(HexLocation hexLoc)
    {

        getView().placeRobber(hexLoc);

        getRobView().showModal();
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
    {

        getView().startDrop(pieceType, CatanColor.ORANGE, true);
    }

    @Override
    public void cancelMove()
    {

    }

    @Override
    public void playSoldierCard()
    {

    }

    @Override
    public void playRoadBuildingCard()
    {

    }

    @Override
    public void robPlayer(RobPlayerInfo victim)
    {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg)
    {
        // TODO Auto-generated method stub

    }

}
