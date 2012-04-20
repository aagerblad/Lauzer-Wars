import java.util.Random;

import org.newdawn.slick.SlickException;

public class Tile {

	private Player playerOnTile = null;
	private Mirror mirrorOnTile = null;
	private Pillar pillarOnTile = null;
	private Random random = null;

	public Tile(float tileDistance) throws SlickException {
		random = new Random();
	}

	/**
	 * @return the player on the tile, or null if no player exists.
	 */
	public Player getPlayer() {
		return playerOnTile;
	}

	/**
	 * @return the mirror on the tile, or null if no mirror exists.
	 */
	public Mirror getMirror() {
		return mirrorOnTile;
	}

	/**
	 * @return the pillar on the tile, or null if no pillar exists.
	 */
	public Pillar getPillar() {
		return pillarOnTile;
	}

	/**
	 * @return true if there is a player on the tile.
	 */
	public boolean hasPlayer() {
		if (playerOnTile != null) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if there is a mirror on the tile.
	 */
	public boolean hasMirror() {
		if (mirrorOnTile != null) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if there is a pillar on the tile.
	 */
	public boolean hasPillar() {
		if (pillarOnTile != null) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if there is some type of collision.
	 */
	public boolean hasCollision() {
		if (playerOnTile != null || pillarOnTile != null) {
			return true;
		}
		return false;
	}

	/**
	 * Adds a reference to a player to the tile.
	 * 
	 * @param player
	 *            The player to add to the tile.
	 */
	public void addPlayer(Player player) {
		playerOnTile = player;
	}

	/**
	 * Removes the reference to the player on the tile.
	 */
	public void removePlayer() {
		playerOnTile = null;
	}

	/**
	 * Adds a reference to a pillar to the tile.
	 * 
	 * @param pillar
	 *            The pillar to add to the tile.
	 */
	public void addPillar(Pillar pillar) {
		pillarOnTile = pillar;
	}

	/**
	 * Adds a reference to a pillar on the tile.
	 * 
	 * @param mirror
	 *            The mirror to add to the tile
	 * @throws SlickException
	 */
	public boolean addMirror(float tileDistance) throws SlickException {
		int randomMirror = random.nextInt(2);
		System.out.println(randomMirror);
		if (randomMirror == 1) {
			Mirror mirrorToAdd = new Mirror(random.nextInt(2), tileDistance);
			mirrorOnTile = mirrorToAdd;
		}
		return true; // TODO
	}

}