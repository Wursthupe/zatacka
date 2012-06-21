/**
 * 
 */
package fhbi.maw.csas.games.zatacka;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * @author Christian Stührmann
 * @author Alexander Sundermann
 */
@SuppressWarnings("serial")
public class Field extends JPanel {

	// hold the points from 2 players
	private ArrayList<Point2D> points_p1, points_p2;

	/**
	 * Initializes an empty field (JPanel)
	 * @see JPanel
	 */
	public Field() {
		super();
		reset();
	}

	/**
	 * Set points to draw for player 1
	 * @param points_p1 points to draw for player 1
	 */
	public void setPoints_p1(ArrayList<Point2D> points_p1) {
		this.points_p1 = points_p1;
	}

	/**
	 * Set points to draw for player 2
	 * @param points_p1 points to draw for player 2
	 */
	public void setPoints_p2(ArrayList<Point2D> points_p2) {
		this.points_p2 = points_p2;
	}

	/**
	 * Checks if a point belongs to player 1 or player 2
	 * @param point the point to check
	 * @return true, if point belongs to player 1/2, else false.
	 */
	public boolean hasPoint(Point2D point) {
		for (Point2D p : points_p1)
			if (point.getX() == p.getX() && point.getY() == p.getY())
				return true;

		for (Point2D p : points_p2)
			if (point.getX() == p.getX() && point.getY() == p.getY())
				return true;

		return false;
	}

	/**
	 * Is called by repaint().
	 * Draws the snakes for player 1 and player 2.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(3));

		double currentX, currentY;
		double lastX = 0, lastY = 0;
		for (Point2D p : points_p1) {
			currentX = p.getX();
			currentY = p.getY();

			if (lastX != 0 && currentX != 0) {
				g2.setColor(Color.RED);
				g2.draw(new Line2D.Double(lastX, lastY, currentX, currentY));
			}

			lastX = currentX;
			lastY = currentY;
		}

		lastX = 0; lastY = 0;
		for (Point2D p : points_p2) {
			currentX = p.getX();
			currentY = p.getY();

			if (lastX != 0 && currentX != 0) {
				g2.setColor(Color.BLUE);
				g2.draw(new Line2D.Double(lastX, lastY, currentX, currentY));
			}

			lastX = currentX;
			lastY = currentY;
		}
	}

	/**
	 * Sets the points for player 1 and player 2 to an empty list.
	 */
	public void reset() {
		points_p1 = new ArrayList<Point2D>();
		points_p2 = new ArrayList<Point2D>();
	}

}
