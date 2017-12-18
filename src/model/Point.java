package model;

import java.util.Objects;

/**
 * A modified version of the common "Point" class in Java - a point with X and Y 
 * coordinates - which is designed to be a mutable class rather than "java.awt.Point",
 * which is mutable.
 * 
 * @author Logan Stafford
 * @version 2.0
 */
public final class Point {

    /** The X coordinate. */
    
    private final int myX;

    /** The Y coordinate. */
    
    private final int myY;

    /**
     * Constructs a Point using the provided coordinates.
     * 
     * @param theX the X coordinate.
     * @param theY the Y coordinate.
     */
    public Point(final int theX, final int theY) {
        myX = theX;
        myY = theY;
    }

    /**
     * Returns the X coordinate.
     * 
     * @return the X coordinate of the Point.
     */
    public int getX() {
        return myX;
    }

    /**
     * Returns the Y coordinate.
     * 
     * @return the Y coordinate of the Point.
     */
    public int getY() {
        return myY;
    }

    /**
     * Creates a new point transformed by x and y.
     * 
     * @param theX the X factor to transform by.
     * @param theY the Y factor to transform by.
     * @return the new transformed Point.
     */
    public Point transform(final int theX, final int theY) {
        return new Point(myX + theX, myY + theY);
    }
    
    /**
     * Creates a new point transformed by another Point.
     * 
     * @param thePoint the Point to transform with.
     * @return the new transformed Point.
     */
    public Point transform(final Point thePoint) {
        return transform(thePoint.getX(), thePoint.getY());
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(myX, myY);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", myX, myY);
    }
}