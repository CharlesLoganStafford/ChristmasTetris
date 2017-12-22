package model;

import java.util.Objects;

/**
 * A modified version of the common "Point" class in Java - a point with X and Y 
 * coordinates - which is designed to be a mutable class rather than "java.awt.Point",
 * which is mutable.
 * 
 * DONE
 * 
 * @author Logan Stafford
 * @version 1.2
 */
public final class Point {

    /** The X coordinate. */
    
    private final int myX;

    /** The Y coordinate. */
    
    private final int myY;

    /**
     * Constructs a Point using the provided integer coordinates.
     * 
     * @param theX The desired x-coordinate of the Point.
     * @param theY The desired y-coordinate of the Point.
     */
    public Point(final int theX, final int theY) {
        myX = theX;
        myY = theY;
    }

    /**
     * The getX method. This returns the current
     * x-coordinate of the Point.
     * 
     * @return The x-coordinate of the Point.
     */
    public int getX() {
        return myX;
    }

    /**
     * The getY method. This returns the current
     * y-coordinate of the Point.
     * 
     * @return The y-coordinate of the Point.
     */
    public int getY() {
        return myY;
    }

    /**
     * Creates a new point which is transformed by 
     * the input x and y values.
     * 
     * @param theX The X factor to transform the Point by.
     * @param theY The Y factor to transform the Point by.
     * 
     * @return A new, transformed Point object.
     */
    public Point transform(final int theX, final int theY) {
        return new Point(myX + theX, myY + theY);
    }
    
    /**
     * Creates a new point which is transformed by 
     * the input x and y values from another Point object.
     *  
     * @param thePoint The Point object used to transform with.
     * 
     * @return A new, transformed Point object.
     */
    public Point transform(final Point thePoint) {
        return transform(thePoint.getX(), thePoint.getY());
    }

    /**
     * An overridden equals method for comparing two Point objects.
     * 
     * Two Point objects can only be considered equal if:
     * - They are of the same class (they must both actually be Point objects),
     * - Neither Point is null, and
     * - Both Point objects have the same x/y coordinate values.
     * 
     * Note that under these requirements, a Point can actually be equal to 
     * itself, which is intended.
     * 
     * @param theOther An object to compare to the current Point.
     * 
     * @return A boolean true/false value of equality between this object
     * and the passed-in object.
     */
    @Override
    public boolean equals(final Object theOther) {
        boolean result = false;
        
        if (theOther == this) {
            result = true;
        } else if (theOther != null && theOther.getClass() == getClass()) {
            final Point p = (Point) theOther;
            result = myX == p.myX && myY == p.myY;
        }
        
        return result;
    }
    
    /**
     * An overridden hashCode method for hashing a Point object based on its
     * coordinate values.
     * 
     * @return A hash value for the given Point object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(myX, myY);
    }

    /**
     * The overridden toString method, for enconding a Point object
     * by its coordinate values into a String "(x, y)", where x and y 
     * refer to their named coordinate positions.
     * 
     * @return A String representation of the given Point object.
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", myX, myY);
    }
    
}