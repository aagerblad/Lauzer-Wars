import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	static final int SIZE_X = 1200;
	static final int SIZE_Y = SIZE_X * 6/8;
	private int timePile = 0;
	private static final int msPerFrame = 10;
	Player player1 = null;
	Player player2 = null;
	private static final int NORTH = 0;
	private static final int WEST = 1;
	private static final int SOUTH = 2;
	private static final int EAST = 3;
	private static final int CHANGE_MIRROR = 4;

	private static final int NUMBER_OF_X_TILES = 64 - 1;
	private static final int NUMBER_OF_Y_TILES = 6 * NUMBER_OF_X_TILES / 8;
	private static final float TILE_DISTANCE = (SIZE_X / 8) * 8 / (NUMBER_OF_X_TILES + 1);
	private static final float OFFSET = TILE_DISTANCE / 2;
	private Tile[][] map = null;
	TimeHandler timeHandler = null;

	public Game() {
		// super("Lauzer Wars - a dirty dirty gamedevelopers production ");
		super("Lauzer Wars");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = null;
		app = new AppGameContainer(new Game());

		app.setDisplayMode(SIZE_X, SIZE_Y, false);
		app.setIcon("src/resource/Character1.png"); //TODO http://slick.javaunlimited.net/viewtopic.php?p=19642
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
					map[i][j]
							.getMirror()
							.getImage()
							.draw(TILE_DISTANCE * i + OFFSET,
									TILE_DISTANCE * j + OFFSET);

				}
				if (map[i][j].hasPillar()) {
					map[i][j]
							.getPillar()
							.getImage()
							.draw(TILE_DISTANCE * i + OFFSET,
									TILE_DISTANCE * j + OFFSET);
				}
				if (map[i][j].hasLaser()) {
					for (Laser laser : map[i][j].getLaser()) {
						laser.getImage().draw(TILE_DISTANCE * i + OFFSET,
								TILE_DISTANCE * j + OFFSET);
					}

				}
			}
		}

		// TODO offset if rotated OR have different sprites for each rotation
		player1.getImage().draw(player1.getPosX() * TILE_DISTANCE + OFFSET,
				player1.getPosY() * TILE_DISTANCE + OFFSET);
		player2.getImage().draw(player2.getPosX() * TILE_DISTANCE + OFFSET,
				player2.getPosY() * TILE_DISTANCE + OFFSET);

	}

	/**
	 * Initializes the players and the map.
	 */
	@Override
	public void init(GameContainer arg0) throws SlickException {
		timeHandler = new TimeHandler(); 
		player1 = new Player("Andreas", 1,
				new Image("src/resource/Character1.png")
		.getScaledCopy(TILE_DISTANCE / 100 // TODO
				), 1, 1);
		player2 = new Player("Dexter", 2,
				new Image("src/resource/Character2.png")
		.getScaledCopy(TILE_DISTANCE / 100 // TODO
				), NUMBER_OF_X_TILES - 2, NUMBER_OF_Y_TILES - 2);
		map = new Tile[NUMBER_OF_X_TILES][NUMBER_OF_Y_TILES];
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
			timeHandler.tick();
			handlePlayerPositions();
			checkLaserLife();
			handleInput(input);
		}
	}

	/*
	 * TODO make this shit work!
	 */
	private void checkLaserLife() {
		if (timeHandler.isLaserDone(1)) {
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[x].length; y++) {				
					player1.setShot(false);
					map[x][y].clearLaser(1);
				}
			}
		} else if (timeHandler.isLaserDone(2)) {
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[x].length; y++) {				
					player2.setShot(false);
					map[x][y].clearLaser(2);
				}
			}
		}
	}


	/**
	 * Initiates the laser Algorithm, based on the player position.
	 * 
	 * @param player
	 * @throws SlickException
	 */
	private void handleLaser(Player player) throws SlickException {
		if (!player.hasShot()) {
			player.setShot(true);
			timeHandler.playerJustShotWithHisOrHerLaser(player);
			laserAlgorithm(Math.round(player.getRotation()),
					Math.round(player.getPosX()), Math.round(player.getPosY()), player.getId());
		}

	}

	/**
	 * Calculates the path of the laser, and adds laser to the relevant tiles.
	 * 
	 * @param rotation
	 * @param posX
	 * @param posY
	 * @throws SlickException
	 */
	private void laserAlgorithm(int rotation, int posX, int posY, int id)
			throws SlickException {
		int lastRotation = rotation;
		switch (rotation) {
		case 0:
			posY -= 1;
			break;
		case 90:
			posX += 1;
			break;
		case 180:
			posY += 1;
			break;
		case 270:
			posX -= 1;
			break;
		default:
			break;
		}
		if (map[posX][posY].hasCollision()) {
			if (map[posX][posY].hasPlayer()) {
				Player playerToKill = map[posX][posY].getPlayer(); //TODO
				playerToKill.die();
			}
			return;

		} else if (map[posX][posY].hasMirror()) {
			int orientation = map[posX][posY].getMirror().getOrientation();
			/**
			 * This if-statement says that if the mirror is in NorthEast
			 * orientation and the direction of the laser is either east or west
			 * OR the mirror is in NorthWest orientation and the direction is
			 * either north or south, the rotation will change by 90 degrees
			 * counter clockwise otherwise the rotation will be 90 degrees
			 * clockwise.
			 */
			if ((orientation == 0 && (rotation == 90 || rotation == 270))
					|| (orientation == 1 && (rotation == 0 || rotation == 180))) { // NE
				rotation += 360;
				rotation -= 90;
				rotation = rotation % 360;

			} else {

				rotation += 90;
				rotation = rotation % 360;

			}
		}
		map[posX][posY].addLaser(lastRotation, rotation, TILE_DISTANCE, id);
		laserAlgorithm(rotation, posX, posY, id);
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
	 * @throws SlickException
	 */
	private void handleInput(Input input) throws SlickException {
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

		// Handles the case where the player wants to change the orientation of
		// a tile
		if (input.isKeyDown(Input.KEY_LSHIFT)) {
			Tile tileToCheck = map[player1X][player1Y];
			if (tileToCheck.hasMirror()) {
				if (!player1.getKeyPressed(CHANGE_MIRROR)
						&& !player1.aldreadyWalking()) { // TODO
					Mirror mirrorToChange = tileToCheck.getMirror();
					mirrorToChange.changeOrientation();
				}
			}
			player1.setKeyPressed(CHANGE_MIRROR, true);
		} else {
			player1.setKeyPressed(CHANGE_MIRROR, false);
		}

		// Handles the case where the player wants to shoot.
		if (input.isKeyDown(Input.KEY_Q)) {
			if (!player1.aldreadyWalking()) {
				handleLaser(player1);
			}
		}


		// Player 2
		// The following methods handle the second player's input.

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
		// Handles the case where the player wants to change the orientation of
		// a tile
		if (input.isKeyDown(Input.KEY_ENTER)) {
			Tile tileToCheck = map[player2X][player2Y];
			if (tileToCheck.hasMirror()) {
				if (!player2.getKeyPressed(CHANGE_MIRROR)
						&& !player2.aldreadyWalking()) { 
					Mirror mirrorToChange = tileToCheck.getMirror();
					mirrorToChange.changeOrientation();
				}
			}
			player2.setKeyPressed(CHANGE_MIRROR, true);
		} else {
			player2.setKeyPressed(CHANGE_MIRROR, false);
		}

		// Handles the case where the player wants to shoot.
		if (input.isKeyDown(Input.KEY_RCONTROL)) {
			if (!player2.aldreadyWalking()) {
				handleLaser(player2);
			}
		}
	}
}
