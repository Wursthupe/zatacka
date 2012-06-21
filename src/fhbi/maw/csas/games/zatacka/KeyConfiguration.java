package fhbi.maw.csas.games.zatacka;

/**
 * @author Christian Stührmann
 * @author Alexander Sundermann
 */
public class KeyConfiguration {
	private char leftKey, upKey, rightKey, downKey;

	/**
	 * Initializes a KeyConfiguration with the given parameters to
	 * move left, up, right and down.
	 * This configuration could be assigned to a player. 
	 * 
	 * @param leftKey Key to move left
	 * @param upKey Key to move up
	 * @param rightKey Key to move right
	 * @param downKey Key to move down
	 */
	public KeyConfiguration(char leftKey, char rightKey, char upKey, char downKey) {
		this.leftKey = leftKey;
		this.upKey = upKey;
		this.rightKey = rightKey;
		this.downKey = downKey;
	}

	/**
	 * Get the key to move left
	 * @return the leftKey
	 */
	public char getLeftKey() {
		return leftKey;
	}

	/**
	 * Set the key to move left
	 * @param leftKey the leftKey to set
	 */
	public void setLeftKey(char leftKey) {
		this.leftKey = leftKey;
	}

	/**
	 * Get the key to move up
	 * @return the upKey
	 */
	public char getUpKey() {
		return upKey;
	}

	/**
	 * Set the key to move up
	 * @param upKey the upKey to set
	 */
	public void setUpKey(char upKey) {
		this.upKey = upKey;
	}

	/**
	 * Get the key to move right
	 * @return the rightKey
	 */
	public char getRightKey() {
		return rightKey;
	}

	/**
	 * Set the key to move right
	 * @param rightKey the rightKey to set
	 */
	public void setRightKey(char rightKey) {
		this.rightKey = rightKey;
	}

	/**
	 * Get the key to move down
	 * @return the downKey
	 */
	public char getDownKey() {
		return downKey;
	}

	/**
	 * Set the key to move down
	 * @param downKey the downKey to set
	 */
	public void setDownKey(char downKey) {
		this.downKey = downKey;
	}
}
