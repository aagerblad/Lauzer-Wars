import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.SlickException;

public class Tile {

	private Player playerOnTile = null;
	private Mirror mirrorOnTile = null;
	private Pillar pillarOnTile = null;
	private ArrayList<Laser> laserOnTile = null;
	private Random random = null;

	/**
	 * Initialize the tile object.
	 * 
	 * @param tileDistance
	 * @throws SlickException
	 */
	public Tile(float tileDistance) throws SlickException {
		random = new Random();
		laserOnTile = new ArrayList<Laser>();
	}

	/**
	 * @return The list of lasers on the tile.
	 */
	public ArrayList<Laser> getLaser() {
		return laserOnTile;
	}

	/**
	 * Adds a laser object to the end of the tile's list of lasers.
	 * 
	 * @param direction
	 * @param tileDistance
	 * @return
	 * @throws SlickException
	 */
	public boolean addLaser(int direction, float tileDistance)
			throws SlickException {
		laserOnTile.add(new Laser(direction, tileDistance));
		return true;
	}

	/**
	 * @return true if the tile has any laser objects, false otherwise.
	 */
	public boolean hasLaser() {
		return laserOnTile.size() != 0;
	}

	/**
	 * Remove all the laser objects on the tile.
	 */
	public void clearLaser() {
		laserOnTile.clear();
	}

	/**
	 * Change the image of the laser last added to the list of lasers into one
	 * representing a reflected laser.
	 * 
	 * @param rotation
	 * @param tileDistance
	 * @throws SlickException
	 */
	public void rotateLastLaser(int rotation, float tileDistance)
			throws SlickException {
		Laser rotatedLaser = laserOnTile.get(laserOnTile.size() - 1);
		rotatedLaser.setRotated(rotation, tileDistance);
		laserOnTile.set(laserOnTile.size() - 1, rotatedLaser);
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
		int randomMirror = random.nextInt(6);
		if (randomMirror == 1) {
			Mirror mirrorToAdd = new Mirror(random.nextInt(2), tileDistance);
			mirrorOnTile = mirrorToAdd;
		}
		return true; // TODO
	}

}
