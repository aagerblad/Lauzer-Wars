package mainPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.SlickException;

public class Tile {

	private static final int CHANCE_OF_MIRROR = 4;
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
	public boolean addLaser(int lastDirection, int direction,
			float tileDistance, int id) throws SlickException {
		laserOnTile.add(new Laser(lastDirection, direction, tileDistance, id));
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
	public void clearLaser(int idToRemove) {
		Iterator<Laser> i = laserOnTile.iterator();
		while (i.hasNext()) {
			if (i.next().getId() == idToRemove) {
				i.remove();
			}
		}

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
	
	public void clearMirror() {
		mirrorOnTile = null;
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
		int randomMirror = random.nextInt(CHANCE_OF_MIRROR);
		if (randomMirror == 1) {
			Mirror mirrorToAdd = new Mirror(random.nextInt(2), tileDistance);
			mirrorOnTile = mirrorToAdd;
		}
		return true; // TODO
	}

	public void laserFade(int idToFade, float newAlpha) {
		for (Laser laser : laserOnTile) {
			if (laser.getId() == idToFade) {
				laser.fade(newAlpha);
			}
		}
	}
}
