/**
 * Point class object
 * @author Alan Kai (yogijogi)
 * @version 1.0
 */
public class Point {
    /**
     * Rectangle name
     */
    public String name = "";
    /**
     * Rectangle x-coordinate
     */
    public int x;
    /**
     * Rectangle y-coordinate
     */
    public int y;
    /**
     * Rectangle width
     */
    public int w;
    /**
     * Rectangle height
     */
    public int h;
    /**
     * Rectangle Constructor
     * @param n name of rectangle
     * @param xCoord X coordinate
     * @param yCoord y Coordinate
     * @param width width
     * @param height Height
     */
    public Point(String n, int xCoord, int yCoord, int width, int height)
    {
        name = n;
        x = xCoord;
        y = yCoord;
        w = width;
        h = height;
    }
    /**
     * Rectangle Constructor
     * @param xCoord X coordinate
     * @param yCoord y Coordinate
     * @param width width
     * @param height Height
     */
    public Point(int xCoord, int yCoord, int width, int height) {
        x = xCoord;
        y = yCoord;
        w = width;
        h = height;
    }
    /**
     * Point constructor
     * @param n name of Point
     * @param xCoord x coordinate
     * @param yCoord y coordinate
     */
    public Point(String n, int xCoord, int yCoord) {
        name = n;
        x = xCoord;
        y = yCoord;
    }
    /**
     * Point Constructor
     * @param xCoord x coordinate
     * @param yCoord y coordinate
     */
    public Point(int xCoord, int yCoord) {
        x = xCoord;
        y = yCoord;
    }
    /**
     * Get X value method
     * @return value of x
     */
    public int getX() {
        return x;
    }
    /**
     * Get y value method
     * @return value of y
     */
    public int getY() {
        return y;
    }
    /**
     * Get width value method
     * @return value of width
     */
    public int getW() {
        return w;
    }
    /**
     * Get height value method
     * @return value of h
     */
    public int getH() {
        return h;
    }
    /**
     * Get name of rectangle method
     * @return String name of rectangle
     */
    public String getName() {
        return name;
    }
    /**
     * ToString method for the Point (excludes the name of point)
     * @return String of the point
     */
    public String toString() {
        String rect = (x + ", " + y);
        return rect;
    }
    /**
     * ToString method for the rectangle (excludes the name of rectangle)
     * @return String of the rectangle
     */
    public String toRString() {
        String rect = (x + ", " + y + ", " + w + ", " + h);
        return rect;
    }
    /**
     * Equals method
     * @param o The rectangle Object
     * @return True if equals; false if not
     */
    public boolean equals(Object o) {
        Point r = (Point) o;
        return this.x == r.x && this.y == r.y;
    }
}
