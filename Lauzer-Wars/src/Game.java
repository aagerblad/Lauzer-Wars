import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	static final int SIZE_X = 800;
	static final int SIZE_Y = 600;
	private int timePile = 0;
	private static final int msPerFrame = 10;
	Player player1 = null;
	Player player2 = null;
	private static final int NORTH = 0;
	private static final int WEST = 1;
	private static final int SOUTH = 2;
	private static final int EAST = 3;
	private static final int NUMBER_OF_X_TILES = 15; // TODO Fix the ratio, does
														// not work consistently
														// in current state
	private static final int NUMBER_OF_Y_TILES = 6 * NUMBER_OF_X_TILES / 8;
	private static final float TILE_DISTANCE = 100 * 8 / NUMBER_OF_X_TILES;
	private Tile[][] map = null;
	private Random random = null;

	public Game() {
		super("Lauzer Wars");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = null;
		app = new AppGameContainer(new Game());

		app.setDisplayMode(SIZE_X, SIZE_Y, false);
		app.start();

	}

	/**
	 * Renders the game world, ie the player avatars, and the map.
	 */
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].hasMirror()) {
					map[i][j].getMirror().getImage()
							.draw(TILE_DISTANCE * i, TILE_DISTANCE * j);

				}
			}
		}

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].hasPillar()) {
					map[i][j].getPillar().getImage()
							.draw(TILE_DISTANCE * i, TILE_DISTANCE * j);
				}
			}
		}

		// TODO offset if rotated OR have different sprites for each rotation
		player1.getImage().draw(player1.getPosX() * TILE_DISTANCE,
				player1.getPosY() * TILE_DISTANCE);
		player2.getImage().draw(player2.getPosX() * TILE_DISTANCE,
				player2.getPosY() * TILE_DISTANCE);

	}

	/**
	 * Initializes the players and the map.
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {

		player1 = new Player("Dexter",
				new Image("src/resource/Character1.png")
						.getScaledCopy(TILE_DISTANCE / 100 // TODO
						), 1, 1, NUMBER_OF_X_TILES, NUMBER_OF_Y_TILES);
		player2 = new Player("Andreas",
				new Image("src/resource/Character2.png")
						.getScaledCopy(TILE_DISTANCE / 100 // TODO
						), NUMBER_OF_X_TILES - 2, NUMBER_OF_Y_TILES - 2,
				NUMBER_OF_X_TILES, NUMBER_OF_Y_TILES);
		map = new Tile[NUMBER_OF_X_TILES][NUMBER_OF_Y_TILES];
		random = new Random();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(TILE_DISTANCE);
			}
		}
		// Add the wall surrounding the map.
		addWall();
		// Add the "checkerboard" of pillars in the map.
		addCheckerboard();
		// Add the random mirrors to the map.
		addMirrors();
	}

	/**
	 * Add random mirrors to the map.
	 * 
	 * @throws SlickException
	 */
	private void addMirrors() throws SlickException {
		for (int i = 1; i < NUMBER_OF_X_TILES - 1; i++) {
			for (int j = 1; j < NUMBER_OF_Y_TILES - 1; j++) {
				if ((map[i - 1][j].hasPillar() && map[i + 1][j].hasPillar())
						|| (map[i][j - 1].hasPillar())
						&& map[i][j + 1].hasPillar()) {
					// Do nothing
				} else {
					map[i][j].addMirror(TILE_DISTANCE);
				}
			}
		}
	}

	/**
	 * Add pillars in a checkerboard pattern across the map.
	 * 
	 * @throws SlickException
	 */
	private void addCheckerboard() throws SlickException {
		for (int i = 2; i < NUMBER_OF_X_TILES - 2; i++) {
			for (int j = 2; j < NUMBER_OF_Y_TILES - 2; j++) {
				if ((i % 2 == 0) && (j % 2 == 0)) {
					map[i][j].addPillar(new Pillar(TILE_DISTANCE));
				}
			}
		}
	}

	/**
	 * Adds pillars surrounding the map.
	 * 
	 * @throws SlickException
	 */
	private void addWall() throws SlickException {
		for (int i = 0; i < map.length; i++) {
			int j = 0;
			map[i][j].addPillar(new Pillar(TILE_DISTANCE));
		}
		for (int j = 0; j < NUMBER_OF_Y_TILES; j++) {
			int i = 0;
			map[i][j].addPillar(new Pillar(TILE_DISTANCE));
		}
		for (int i = 0; i < NUMBER_OF_X_TILES; i++) {
			int j = NUMBER_OF_Y_TILES - 1;
			map[i][j].addPillar(new Pillar(TILE_DISTANCE));
		}
		for (int j = 0; j < NUMBER_OF_Y_TILES; j++) {
			int i = NUMBER_OF_X_TILES - 1;
			map[i][j].addPillar(new Pillar(TILE_DISTANCE));
		}
	}

	/**
	 * Updates the game world.
	 */
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		// Makes sure the game stays at the set framrate.
		timePile += delta;
		while (timePile >= msPerFrame) {
			timePile -= msPerFrame;
			handlePlayerPositions();
			handleInput(input);
		}
	}

	/**
	 * Updates the tile matrix with the players' current positions.
	 */
	private void handlePlayerPositions() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].removePlayer();
			}
		}
		int player1X = Math.round(player1.getPosX());
		int player1Y = Math.round(player1.getPosY());
		Tile tileToAddPlayer1 = map[player1X][player1Y];
		tileToAddPlayer1.addPlayer(player1);

		int player2X = Math.round(player2.getPosX());
		int player2Y = Math.round(player2.getPosY());
		Tile tileToAddPlayer2 = map[player2X][player2Y];
		tileToAddPlayer2.addPlayer(player2);
	}

	/**
	 * Reads the input of both players, and excecutes the methods associated
	 * with each key.
	 * 
	 * @param delta
	 * @param input
	 *            The pressed key.
	 */
	private void handleInput(Input input) {
		// Player 1:
		// The following methods handle the first player's input.

		int player1X = Math.round(player1.getPosX());
		int player1Y = Math.round(player1.getPosY());

		// Handles the case where the player wants to move west.
		if (input.isKeyDown(Input.KEY_A)) {
			Tile wantedTile = map[player1X - 1][player1Y];
			if (!wantedTile.hasCollision()) {
				player1.moveWest(false);
			}
			player1.moveWest(true);
		} else {
			player1.setKeyPressed(WEST, false);
		}

		if (player1.isWalking(WEST)) {
			player1.walkAnimate(WEST);
		}

		// Handles the case where the player wants to move east.
		if (input.isKeyDown(Input.KEY_D)) {
			Tile wantedTile = map[player1X + 1][player1Y];
			if (!wantedTile.hasCollision()) {
				player1.moveEast(false);
			}
			player1.moveEast(true);
		} else {
			player1.setKeyPressed(EAST, false);
		}

		if (player1.isWalking(EAST)) {
			player1.walkAnimate(EAST);
		}

		// Handles the case where the player wants to move north.
		if (input.isKeyDown(Input.KEY_W)) {
			Tile wantedTile = map[player1X][player1Y - 1];
			if (!wantedTile.hasCollision()) {
				player1.moveNorth(false);
			} else {
				player1.moveNorth(true);
			}
		} else {
			player1.setKeyPressed(NORTH, false);
		}

		if (player1.isWalking(NORTH)) {
			player1.walkAnimate(NORTH);
		}

		// Handles the case where the player wants to move south.
		if (input.isKeyDown(Input.KEY_S)) {
			Tile wantedTile = map[player1X][player1Y + 1];
			if (!wantedTile.hasCollision()) {
				player1.moveSouth(false);
			}
			player1.moveSouth(true);
		} else {
			player1.setKeyPressed(SOUTH, false);
		}

		if (player1.isWalking(SOUTH)) {
			player1.walkAnimate(SOUTH);
		}

		// Player 2
		// The following methods handle the first player's input.

		int player2X = Math.round(player2.getPosX());
		int player2Y = Math.round(player2.getPosY());
		// Handles the case where the player wants to move west.
		if (input.isKeyDown(Input.KEY_LEFT)) {
			Tile wantedTile = map[player2X - 1][player2Y];
			if (!wantedTile.hasCollision()) {
				player2.moveWest(false);
			}
			player2.moveWest(true);
		} else {
			player2.setKeyPressed(WEST, false);
		}

		if (player2.isWalking(WEST)) {
			player2.walkAnimate(WEST);
		}

		// Handles the case where the player wants to move east.
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			Tile wantedTile = map[player2X + 1][player2Y];
			if (!wantedTile.hasCollision()) {
				player2.moveEast(false);
			}
			player2.moveEast(true);
		} else {
			player2.setKeyPressed(EAST, false);
		}

		if (player2.isWalking(EAST)) {
			player2.walkAnimate(EAST);
		}

		// Handles the case where the player wants to move north.
		if (input.isKeyDown(Input.KEY_UP)) {
			Tile wantedTile = map[player2X][player2Y - 1];
			if (!wantedTile.hasCollision()) {
				player2.moveNorth(false);
			}
			player2.moveNorth(true);
		} else {
			player2.setKeyPressed(NORTH, false);
		}

		if (player2.isWalking(NORTH)) {
			player2.walkAnimate(NORTH);
		}

		// Handles the case where the player wants to move south.
		if (input.isKeyDown(Input.KEY_DOWN)) {
			Tile wantedTile = map[player2X][player2Y + 1];
			if (!wantedTile.hasCollision()) {
				player2.moveSouth(false);
			}
			player2.moveSouth(true);
		} else {
			player2.setKeyPressed(SOUTH, false);
		}

		if (player2.isWalking(SOUTH)) {
			player2.walkAnimate(SOUTH);
		}
	}
}
