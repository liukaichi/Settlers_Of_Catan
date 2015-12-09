package shared.locations;

import java.io.Serializable;

/**
 * Represents the location of a hex on a hex map
 */
public class HexLocation implements Comparable<HexLocation>, Serializable
{

    private static final long serialVersionUID = -654751048435350322L;
    private int x;
    private int y;

    public HexLocation(int x, int y)
    {
        setX(x);
        setY(y);
    }

    public int getX()
    {
        return x;
    }

    private void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    private void setY(int y)
    {
        this.y = y;
    }

    @Override public String toString()
    {
        return "HexLocation [x=" + x + ", y=" + y + "]";
    }

    @Override public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    /**
     * Equals function that check for matching x and y coordinates
     *
     * @param obj Object to be compared to
     * @return true if equal, otherwise returns false.
     */
    @Override public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HexLocation other = (HexLocation) obj;
        if (x != other.x)
            return false;
        return y == other.y;
    }

    /**
     * Returns a HexLocation in the given direction of the hex.
     *
     * @param dir Which direction you want the location
     * @return A Hex Location in the given direction. If there is no hex, returns null.
     */
    public HexLocation getNeighborLoc(EdgeDirection dir)
    {
        switch (dir)
        {
        case NorthWest:
            return new HexLocation(x - 1, y);
        case North:
            return new HexLocation(x, y - 1);
        case NorthEast:
            return new HexLocation(x + 1, y - 1);
        case SouthWest:
            return new HexLocation(x - 1, y + 1);
        case South:
            return new HexLocation(x, y + 1);
        case SouthEast:
            return new HexLocation(x + 1, y);
        default:
            assert false;
            return null;
        }
    }

    @Override
    public int compareTo(HexLocation o) {
        if(this.getY() < o.getY())
        {
            return -1;
        }
        else if(this.getY() == o.getY())
        {
            if(this.getX() < o.getX())
            {
                return -1;
            }
            else if(this.getX() == o.getX())
            {
                return 0;
            }
        }
        return 1;
    }
}

