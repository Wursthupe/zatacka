/**
 * 
 */
package fhbi.maw.csas.games.zatacka;

import fhbi.maw.csas.games.zatacka.multiplayer.MultiPlayerZatacka;

/**
 * @author Christian Stührmann
 * @author Alexander Sundermann
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		startTwoPlayer();
		startMPZatacka();
	}

	private static void startMPZatacka() {
		MultiPlayerZatacka mz = new MultiPlayerZatacka();
		mz.showGame();
	}

	private static void startTwoPlayer() {
		TwoPlayerZatacka tpz = new TwoPlayerZatacka();
		tpz.showGame();
	}

}