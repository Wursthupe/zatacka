/**
 * 
 */
package fhbi.maw.csas.games.zatacka;

/**
 * @author Christian St�hrmann
 * @author Alexander Sundermann
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		startTwoPlayer();
	}

	private static void startTwoPlayer() {
		TwoPlayerZatacka twoPlayerZatacka = new TwoPlayerZatacka();
		twoPlayerZatacka.showGame();
	}

}