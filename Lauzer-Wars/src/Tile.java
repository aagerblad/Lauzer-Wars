import java.util.Random;

import org.newdawn.slick.SlickException;


public class Tile {
	
	private Player playerOnTile = null;
	private Mirror mirrorOnTile = null;
	private boolean hasPlayer = false;
	private boolean hasMirror = false;
	private Random random = null;
	
	public Tile(boolean addMirror) throws SlickException {
		random = new Random();
		if (addMirror) {
			mirrorOnTile = new Mirror(random.nextInt(2));
			hasMirror = true;
		}
	}
	
	public Player getPlayer() {
		return playerOnTile;
	}
	
	public Mirror getMirror() {
		return mirrorOnTile;
	}
	
	public boolean hasPlayer() {
		return hasPlayer;
	}
	
	public boolean hasMirror() {
		return hasMirror;
	}

}
