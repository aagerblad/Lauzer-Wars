
public class Tile {
	
	private Player playerOnTile = null;
	private Mirror mirrorOnTile = null;
	private boolean hasPlayer = false;
	private boolean hasMirror = false;
	
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
