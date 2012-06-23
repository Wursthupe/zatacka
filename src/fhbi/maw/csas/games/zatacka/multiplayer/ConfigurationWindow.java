/**
 * 
 */
package fhbi.maw.csas.games.zatacka.multiplayer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Wursthupe
 *
 */
class ConfigurationWindow extends JFrame {
	
	// die aufrufende Spielkomponente
	private MultiPlayerZatacka parent;
	
	// Komponente für den Fensteraufbau
	private Container contentPane;
	
	// Textfeld für die Spielgeschwindigkeit
	private JTextField txt_speed;

	ConfigurationWindow(MultiPlayerZatacka parent) {
		super("Settings");
		
		this.parent = parent;
		
		contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(10, 10));
		
		initComponents();
	}
	
	void showSettings() {
		// Bildschirmgröße auslesen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Zentriert anzeigen
		int w = this.getSize().width;
		int h = this.getSize().height;
		int x = (screenSize.width - w) / 2;
		int y = (screenSize.height - h) / 2;
		this.setLocation(x, y);

		// Fenster sichtbar machen
		this.setVisible(true);
	}
	
	private void initComponents() {
		initMiscPanel();
		
		this.pack();
	}
	
	private void initMiscPanel() {
		JPanel miscPanel = new JPanel(new GridLayout(1, 2));
		
		JLabel lb_speed = new JLabel("Speed (ms):");
		txt_speed = new JTextField("" + parent.getSpeed_ms());
		miscPanel.add(lb_speed);
		miscPanel.add(txt_speed);
		
		contentPane.add(miscPanel, BorderLayout.SOUTH);
	}
	
}
