import java.util.Random;

import org.newdawn.slick.SlickException;

public class Tile {

	private Player playerOnTile = null;
	private Mirror mirrorOnTile = null;
	private Pillar pillarOnTile = null;
	private Random random = null;

	public Tile(boolean addMirror, float tileDistance) throws SlickException {
		random = new Random();
		if (addMirror) {
			mirrorOnTile = new Mirror(random.nextInt(2), tileDistance);
		}
	}

	public Player getPlayer() {
		return playerOnTile;
	}

	public Mirror getMirror() {
		return mirrorOnTile;
	}

	public Pillar getPillar() {
		return pillarOnTile;
	}

	public boolean hasPlayer() {
		if (playerOnTile != null) {
			return true;
		}
		return false;
	}

	public boolean hasMirror() {
		if (mirrorOnTile != null) {
			return true;
		}
		return false;
	}

	public boolean hasPillar() {
		if (pillarOnTile != null) {
			return true;
		}
		return false;
	}

	public boolean hasCollision() {
		if (playerOnTile != null || pillarOnTile != null) {
			return true;
		}
		return false;
	}

	public void addPlayer(Player player) {
		playerOnTile = player;
	}

	public void removePlayer() {
		playerOnTile = null;
	}

	public void addPillar(Pillar pillar) {
		pillarOnTile = pillar;
	}

}
