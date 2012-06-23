/**
 * 
 */
package fhbi.maw.csas.games.zatacka;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * @author Christian Stührmann
 * @author Alexander Sundermann
 */
@SuppressWarnings("serial")
public class TwoPlayerZatacka extends JFrame {

	// Fenstergröße, wird statisch und unveränderlich gehalten!
	private Dimension windowSize;

	// das Spielfeld
	private Field field;

	// Spielernamen
	private String p1_name, p2_name;

	// Statuszeile
	private JLabel statusLabel;

	// Komponente für den Fensteraufbau
	private Container contentPane;

	// Spieler
	private Player p1, p2;

	// Spielzeit in Sekunden
	private int playTime;

	// Anzeige der Spielzeit
	private JLabel labelPlayTime;

	// Wurde ein neues Spiel gestartet?
	private boolean gameRunning;
	
	// wurde ein laufendes Spiel angehalten?
	private boolean gamePaused;

	// Geschwindigkeit des Spiels
	// alle x ms bewegen sich die Schlangen
	private int speed_ms;

	// bewegt die Schlangen im Zeitabstand von speed_ms
	private Timer t_move;

	// aktualisiert die Spielzeit jede Sekunde
	private Timer t_playtime;

	/**
	 * @throws HeadlessException
	 */
	public TwoPlayerZatacka() throws HeadlessException {
		this("Simple TwoPlayerZatacka", "Player 1", "Player 2");
	}

	/**
	 * Initializes a non-resizeable 500x500px game window.
	 * If this window is closed, the program will exit.
	 * <br>
	 * Call showGame() to show the window centered on the screen.
	 * @param title Titelzeile des Fensters
	 * @param p1_name name of player 1
	 * @param p2_name name of player 2
	 * @throws HeadlessException
	 */
	public TwoPlayerZatacka(String title, String p1_name, String p2_name)
			throws HeadlessException {
		super(title);

		// Programm beenden, falls Fenster geschlossen wird
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Fenstergröße setzen
		windowSize = new Dimension(500, 500);
		this.setMinimumSize(windowSize);
		this.setMaximumSize(windowSize);

		setFocusable(true);

		// Fenstergröße darf nicht verändert werden
		this.setResizable(false);

		// Erstelle Fensterkomponenten
		initComponents();

		gameRunning = false;

		speed_ms = 5;

		this.p1_name = p1_name;
		this.p2_name = p2_name;
		
		this.addMouseListener(new MouseHandler());
		this.addKeyListener(new KeyHandler());
	}

	/*
	 * Erstellt die Fensterkomponenten und ordnet sie im BorderLayout an.
	 */
	private void initComponents() {
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(5, 5));

		createMenubar();
		createStatusbars();
		createField();

		this.pack();
	}

	/*
	 * Erstellt die Menüzeile
	 */
	private void createMenubar() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu menu;
		JMenuItem item;

		// Menü File/Datei
		menu = new JMenu("File");
		menuBar.add(menu);

		// Startet neues Spiel
		item = new JMenuItem("New TwoPlayerZatacka");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		menu.add(item);

		// Zeigt Statitik in einer netten Dialogbox an
		item = new JMenuItem("Show Statistic");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showStatistic();
			}
		});
		menu.add(item);

		// Beendet das Spiel
		item = new JMenuItem("Exit");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitGame();
			}
		});
		menu.add(item);

		// Menü Help/Hilfe
		menu = new JMenu("Help");
		menuBar.add(menu);

		// Zeigt Steuerung und Spielablauf
		item = new JMenuItem("How to play?");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showRulesAndSettings();
			}
		});
		menu.add(item);

		// Zeigt Infos über Programmierer
		item = new JMenuItem("About");
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAbout();
			}
		});
		menu.add(item);
	}

	// Erstellt Statuszeile und Anzeige der Spielzeit
	private void createStatusbars() {
		statusLabel = new JLabel("Status: none");
		contentPane.add(statusLabel, BorderLayout.SOUTH);

		labelPlayTime = new JLabel("Spiel nicht gestartet");
		contentPane.add(labelPlayTime, BorderLayout.NORTH);
	}

	// Erstellt Spielfeld mit weißen Hintergrund
	private void createField() {
		field = new Field();
		field.setBackground(Color.WHITE);
		contentPane.add(field, BorderLayout.CENTER);
	}

	/*
	 * Aktualisiere den (Fenster-/Spiel-) Status
	 * @param statusText Der Statustext
	 */
	private void setStatus(String statusText) {
		statusLabel.setText("Status: " + statusText);
	}

	/*
	 * Aktualisiert die Spielzeit
	 */
	private void updatePlayTime() {
		int mins, secs;
		mins = playTime / 60;
		secs = playTime % 60;
		labelPlayTime.setText(String.format(
				"Spiel läuft seit %d Min und %d Sek", mins, secs));
	}

	// Beendet das Spiel
	private void exitGame() {
		System.exit(0);
	}

	// Spiel zurücksetzen
	private void reset() {
		if (t_playtime != null)
			t_playtime.stop();
		
		if (t_move != null)
			t_move.stop();
		
		playTime = 0;
		updatePlayTime();
		field.reset();
		gameRunning = false;
		initPlayers();
	}

	// Spieler mit zufälliger Laufrichtung und Startpunkt erzeugen
	private void initPlayers() {
		Random rand = new Random();
		int start_x, start_y;
		KeyConfiguration keyConf;
		
		int third_x = field.getWidth() / 3;
		int third_y = field.getHeight() / 3;
		
		keyConf = new KeyConfiguration('a', 'd', 'w', 's');
		
		start_x = rand.nextInt(field.getWidth());
		start_y = rand.nextInt(field.getHeight());
		while(true) {
			System.out.println("One-third x = " + third_x + " One-third y = " + third_y);
			System.out.println("start_x = " + start_x + " start_y = " + start_y + "\n");
			if (start_x < third_x || start_x > 2 * third_x)
				start_x = rand.nextInt(field.getWidth());
			else if (start_y < third_y || start_y > 2 * third_y)
				start_y = rand.nextInt(field.getHeight());
			else
				break;
		}
		
		p1 = new Player(p1_name, new Point2D.Double(start_x, start_y),
				Direction.WEST, keyConf);

		
		keyConf = new KeyConfiguration('k', 'ö', 'o', 'l');
		
		start_x = rand.nextInt(field.getWidth());
		start_y = rand.nextInt(field.getHeight());
		while(true) {
			if (start_x < third_x || start_x > 2 * third_x)
				start_x = rand.nextInt(field.getWidth());
			else if (start_y < third_y || start_y > 2 * third_y)
				start_y = rand.nextInt(field.getHeight());
			else
				break;
		}
		
		p2 = new Player(p2_name, new Point2D.Double(start_x, start_y),
				Direction.EAST, keyConf);
	}

	// Startet ein neues Spiel
	private void newGame() {
		System.out.println("Starting new game!");

		reset();

		gameRunning = true;
		t_playtime = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (gameRunning) {
					playTime++;
					updatePlayTime();
				}
			}
		});
		t_playtime.start();

		t_move = new Timer(speed_ms, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (gameRunning) {
					moveSnakes();
					field.repaint();
				}
			}
		});
		t_move.start();
		
		setStatus("Spiel gestartet...");
	}
	
	// Hält ein Spiel an
	private void pauseGame() {
		gamePaused = true;
		t_move.stop();
		t_playtime.stop();
	}
	
	// Lässt ein angehaltenes Spiel weiterlaufen
	private void resumeGame() {
		gamePaused = false;
		t_move.start();
		t_playtime.start();
	}

	// bewegt die Schlangen auf die nächste Position
	private void moveSnakes() {
		p1.move();
		p2.move();

		Player p_loose = checkCollison();
		if (p_loose != null) {
			t_move.stop();
			t_playtime.stop();
			setStatus("TwoPlayerZatacka Over! " + p_loose.getName() + " hat verloren!");
			gameRunning = false;
		} else {
			p1.addPoint(p1.getSnakeHead());
			p2.addPoint(p2.getSnakeHead());
		}

		field.setPoints_p1(p1.getPoints());
		field.setPoints_p2(p2.getPoints());
	}

	/*
	 * Checks if a snakes head hit a wall or one of the
	 * snakes.
	 */
	private Player checkCollison() {
		Point2D p1_head, p2_head;
		p1_head = p1.getSnakeHead();
		p2_head = p2.getSnakeHead();
		
		// check if snake 1 hit a wall
		if (p1_head.getX() < 0 || p1_head.getX() > field.getWidth()
			|| p1_head.getY() < 0 || p1_head.getY() > field.getHeight())
			return p1;
		
		// check if snake 2 hit a wall
		if (p2_head.getX() < 0 || p2_head.getX() > field.getWidth()
			|| p2_head.getY() < 0 || p2_head.getY() > field.getHeight())
			return p2;
		
		// check if a Player hit player 1 snake
		for (Point2D p2d : p1.getPoints()) {
			if (p2d.getX() == p1_head.getX() && p2d.getY() == p1_head.getY())
				return p1;
			if (p2d.getX() == p2_head.getX() && p2d.getY() == p2_head.getY())
				return p2;
		}
		
		// check if a Player hit player 2 snake
		for (Point2D p2d : p2.getPoints()) {
			if (p2d.getX() == p1_head.getX() && p2d.getY() == p1_head.getY())
				return p1;
			if (p2d.getX() == p2_head.getX() && p2d.getY() == p2_head.getY())
				return p2;
		}
		
		return null;
	}

	// Zeigt Info über die Programmierer
	private void showAbout() {
		JOptionPane.showMessageDialog(this,
				"(c) 2012\nChristian Stührmann & Alexander Sundermann");
	}

	// Zeigt Spielregeln und Steuerung an
	private void showRulesAndSettings() {
		JOptionPane.showMessageDialog(this, "Finde es selber heraus ;-)");
	}

	// Zeigt Spielstatistik an
	private void showStatistic() {
		JOptionPane.showMessageDialog(this,
			String.format("Wins %s: %d\nWins %s: %d",
					p1.getName(), 0, p2.getName(), 0));
	}

	/**
	 * Zeigt das Spielfenster zentriert auf dem Bildschirm an.
	 */
	public void showGame() {
		// Bildschirmgröße auslesen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Zentriert anzeigen
		int w = windowSize.getSize().width;
		int h = windowSize.getSize().height;
		int x = (screenSize.width - w) / 2;
		int y = (screenSize.height - h) / 2;
		this.setLocation(x, y);

		// Fenster sichtbar machen
		this.setVisible(true);
	}

	private class KeyHandler implements KeyListener {
		@Override
		public void keyPressed(KeyEvent ke) {
			char c = Character.toLowerCase(ke.getKeyChar());
			
			// type CTRL + N to start a new game
			if (ke.getKeyCode() == KeyEvent.VK_N && ke.isControlDown())
				newGame();

			// if players were'nt initialised, stop caching keys,
			// else an error will result
			if (p1 == null || p2 == null)
				return;
			
			// pause a running game by typing CTRL + P
			if (ke.getKeyCode() == KeyEvent.VK_P && ke.isControlDown())
				if (gameRunning)
					if (gamePaused)
						resumeGame();
					else
						pauseGame();

			// Command to move left (west)
			if (p1.getLeftKey() == c && p1.getDirection() != Direction.EAST)
				p1.setDirection(Direction.WEST);
			else if (p2.getLeftKey() == c && p2.getDirection() != Direction.EAST)
				p2.setDirection(Direction.WEST);
			
			// Command to move up (north)
			if (p1.getUpKey() == c && p1.getDirection() != Direction.SOUTH)
				p1.setDirection(Direction.NORTH);
			else if (p2.getUpKey() == c && p2.getDirection() != Direction.SOUTH)
				p2.setDirection(Direction.NORTH);
			
			// Command to move right (east)
			if (p1.getRightKey() == c && p1.getDirection() != Direction.WEST)
				p1.setDirection(Direction.EAST);
			else if (p2.getRightKey() == c && p2.getDirection() != Direction.WEST)
				p2.setDirection(Direction.EAST);
			
			// Command to move down (south)
			if (p1.getDownKey() == c && p1.getDirection() != Direction.NORTH)
				p1.setDirection(Direction.SOUTH);
			else if (p2.getDownKey() == c && p2.getDirection() != Direction.NORTH)
				p2.setDirection(Direction.SOUTH);
		}

		@Override
		public void keyReleased(KeyEvent ke) {}

		@Override
		public void keyTyped(KeyEvent ke) {}
	}
	
	private class MouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			if (gamePaused) {
				resumeGame();
				setStatus("Spiel geht weiter...");
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			if (gameRunning) {
				pauseGame();
				setStatus("Spiel angehalten...");
			}
		}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
	}

}
