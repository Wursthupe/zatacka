package fhbi.maw.csas.games.zatacka;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author Christian Stührmann
 * @author Alexander Sundermann
 */
public class Player {

	private String name;

	private int x;
	private int y;

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
	 * @param name
	 * @param point2d
	 * @param direction
	 * @param keyConf
	 */
	public Player(String name, Point2D point2d, Direction direction, KeyConfiguration keyConf) {
		this.name = name;
		this.point_snakehead = point2d;
		this.setX((int) this.point_snakehead.getX());
		this.setY((int) this.point_snakehead.getY());
		this.setDirection(direction);
		points = new ArrayList<Point2D>();
		this.keyConf = keyConf;
	}

	/**
	 * Initializes a player with given parameters.
	 * @param name
	 * @param x
	 * @param y
	 * @param direction
	 * @param keyConf
	 */
	public Player(String name, int x, int y, Direction direction, KeyConfiguration keyConf) {
		this.name = name;
		this.setX(x);
		this.setY(y);
		this.point_snakehead = new Point2D.Double(x, y);
		this.setDirection(direction);
		points = new ArrayList<Point2D>();
		this.keyConf = keyConf;
	}

	public String getName() {
		return this.name;
	}

	public Point2D getSnakeHead() {
		return this.point_snakehead;
	}

	public void setSnakeHead(Point2D snakehead) {
		this.point_snakehead = snakehead;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void move() {
		if (direction == Direction.NORTH) {
			this.y -= 1;
		} else if (direction == Direction.EAST) {
			this.x += 1;
		} else if (direction == Direction.SOUTH) {
			this.y += 1;
		} else if (direction == Direction.WEST) {
			this.x -= 1;
		}

		Point2D p = new Point2D.Double(x, y);
		setSnakeHead(p);
	}
	
	public void addPoint(Point2D p) {
		points.add(p);
	}

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
