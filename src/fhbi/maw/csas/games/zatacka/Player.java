package fhbi.maw.csas.games.zatacka;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Christian Stührmann
 * @author Alexander Sundermann
 */
public class Player {

	// name of the player
	private String name;

	// for easier moving the snake
	private double x, y;

	// Use by Graphics2D
	private Point2D point_snakehead;

	// Use by Zatacka-Rules
	private Direction direction;

	// a key configuration to navigate the snake
	private KeyConfiguration keyConf;

	// points the snake ran along
	private ArrayList<Point2D> points;

	/**
	 * Initializes a player with given parameters.
	 * @param name Name of the player
	 * @param point2d Starting point
	 * @param direction Direction at start the snake is moving
	 * @param keyConf A KeyConfiguration for moving the snake to left, right, up and down
	 */
	public Player(String name, Point2D point2d, Direction direction, KeyConfiguration keyConf) {
		this.name = name;
		
		this.point_snakehead = point2d;
		this.x = point2d.getX();
		this.y = point2d.getY();
		
		this.setDirection(direction);
		this.keyConf = keyConf;
		
		points = new ArrayList<Point2D>();
	}

	/**
	 * Initializes a player with given parameters.
	 * @param name Name of the player
	 * @param x the X-Coordinate for starting point
	 * @param y the Y-Coordinate for starting point
	 * @param direction Direction at start the snake is moving
	 * @param keyConf A KeyConfiguration for moving the snake to left, right, up and down
	 */
	public Player(String name, int x, int y, Direction direction, KeyConfiguration keyConf) {
		this(name, new Point2D.Double(x, y), direction, keyConf);
	}

	/**
	 * Returns the player name
	 * @return name of the player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Return the actual position of the snake head
	 * @return snake head
	 */
	public Point2D getSnakeHead() {
		return this.point_snakehead;
	}

//	/**
//	 * 
//	 * @param snakehead
//	 */
//	public void setSnakeHead(Point2D snakehead) {
//		this.point_snakehead = snakehead;
//	}

//	public int getY() {
//		return y;
//	}
//
//	public void setY(int y) {
//		this.y = y;
//	}
//
//	public int getX() {
//		return x;
//	}
//
//	public void setX(int x) {
//		this.x = x;
//	}

	/**
	 * Returns the direction the snake is moving
	 * @return move direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Sets the direction the snake is moving next
	 * @param direction new direction
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Lets the snake move one pixel based on the direction.
	 * <br>
	 * Sets the snake head on the new position 
	 */
	public void move() {
		if (direction == Direction.NORTH) {
			this.y--;
		} else if (direction == Direction.EAST) {
			this.x++;
		} else if (direction == Direction.SOUTH) {
			this.y++;
		} else if (direction == Direction.WEST) {
			this.x--;
		}

		this.point_snakehead = new Point2D.Double(x, y);
	}
	
	/**
	 * Adds a point the snake moved along
	 * @param p point to add
	 */
	public void addPoint(Point2D p) {
		points.add(p);
	}

	/**
	 * Returns a list of points the snake was moving
	 * @return list of points the snake was moving
	 */
	public ArrayList<Point2D> getPoints() {
		return points;
	}

	public char getRightKey() {
		return keyConf.getRightKey();
	}

	public char getLeftKey() {
		return keyConf.getLeftKey();
	}

	public char getUpKey() {
		return keyConf.getUpKey();
	}

	public char getDownKey() {
		return keyConf.getDownKey();
	}

}
