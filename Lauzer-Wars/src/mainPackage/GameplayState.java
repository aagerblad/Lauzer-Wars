package mainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

	private int timePile = 0;
	private static final int msPerFrame = 10;
	private Player player1 = null;
	private Player player2 = null;
	private Sound laserSound = null;
	private static final int NORTH = 0;
	private static final int WEST = 1;
	private static final int SOUTH = 2;
	private static final int EAST = 3;
	private static final int CHANGE_MIRROR = 4;
	private static final int ROTATION_NORTH = 0;
	private static final int ROTATION_WEST = 270;
	private static final int ROTATION_SOUTH = 180;
	private static final int ROTATION_EAST = 90;
	private static final int NUMBER_OF_X_TILES = 24 - 1;
	private static final int NUMBER_OF_Y_TILES = 6 * NUMBER_OF_X_TILES / 8;
	private static float tileDistance = 0;
	private static float offset = 0;
	private Tile[][] map = null;
	private TimeHandler timeHandler = null;
	private int stateID;
	private boolean gameHasBeenReset;

	public GameplayState(int stateID, int sizeX) throws SlickException {
		this.stateID = stateID;
		tileDistance = (float) sizeX / (NUMBER_OF_X_TILES + 1);
		offset = tileDistance / 2;
		laserSound = new Sound("resources/pew.ogg");
	}

	public int getID() {
		return stateID;
	}

	/**
	 * Renders the game world, ie the player avatars, and the map.
	 */
	@Override
	public void render(GameContainer arg0, StateBasedGame sgb, Graphics arg1)
			throws SlickException {
		// background.draw(0, 0);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].getImage().draw(tileDistance * i + offset,
						tileDistance * j + offset);
			}
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j].hasMirror()) {
					map[i][j]
							.getMirror()
							.getImage()
							.draw(tileDistance * i + offset,
									tileDistance * j + offset);

				}
				if (map[i][j].hasPillar()) {
					map[i][j]
							.getPillar()
							.getImage()
							.draw(tileDistance * i + offset,
									tileDistance * j + offset);
				}
				if (map[i][j].hasLaser()) {
					for (Laser laser : map[i][j].getLaser()) {
						laser.getImage().draw(tileDistance * i + offset,
								tileDistance * j + offset);
					}

				}
			}
		}

		// TODO offset if rotated OR have different sprites for each rotation
		player1.getImage().draw(player1.getPosX() * tileDistance + offset,
				player1.getPosY() * tileDistance + offset);
		player2.getImage().draw(player2.getPosX() * tileDistance + offset,
				player2.getPosY() * tileDistance + offset);

		Input input = arg0.getInput();
		arg1.drawString("Mouse x: " + input.getAbsoluteMouseX(), 10, 25);
		arg1.drawString("Mouse y: " + input.getAbsoluteMouseY(), 10, 40);

	}

	/**
	 * Initializes the players and the map.
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame sbg)
			throws SlickException {
		timeHandler = new TimeHandler();
		player1 = new Player("Andreas", 1,
				new Image("resources/Character1.png")
						.getScaledCopy(tileDistance / 100 // TODO
						), 1, 1);
		player2 = new Player("Dexter", 2,
				new Image("resources/Character2.png")
						.getScaledCopy(tileDistance / 100 // TODO
						), NUMBER_OF_X_TILES - 2, NUMBER_OF_Y_TILES - 2);
		map = new Tile[NUMBER_OF_X_TILES][NUMBER_OF_Y_TILES];
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(tileDistance);
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
				map[i][j].clearMirror();
				if ((map[i - 1][j].hasPillar() && map[i + 1][j].hasPillar())
						|| (map[i][j - 1].hasPillar())
						&& map[i][j + 1].hasPillar()) {
					// Do nothing
				} else {
					map[i][j].addMirror(tileDistance);
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
					map[i][j].addPillar(new Pillar(tileDistance));
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
			map[i][j].addPillar(new Pillar(tileDistance));
		}
		for (int j = 0; j < NUMBER_OF_Y_TILES; j++) {
			int i = 0;
			map[i][j].addPillar(new Pillar(tileDistance));
		}
		for (int i = 0; i < NUMBER_OF_X_TILES; i++) {
			int j = NUMBER_OF_Y_TILES - 1;
			map[i][j].addPillar(new Pillar(tileDistance));
		}
		for (int j = 0; j < NUMBER_OF_Y_TILES; j++) {
			int i = NUMBER_OF_X_TILES - 1;
			map[i][j].addPillar(new Pillar(tileDistance));
		}
	}

	private void resetGame() throws SlickException {
		addMirrors();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[j].length; j++) {
				map[i][j].clearLaser(1);
				map[i][j].clearLaser(2);
			}
		}
		player1.resurrect();
		player2.resurrect();
		player1.setParalyzed(false, tileDistance);
		player2.setParalyzed(false, tileDistance);
		player1.setInvulnerable(false);
		player2.setInvulnerable(false);
		player1.setPosition(1, 1);
		player2.setPosition(NUMBER_OF_X_TILES - 2, NUMBER_OF_Y_TILES - 2);
		gameHasBeenReset = true;
	}

	/**
	 * Updates the game world.
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if (!gameHasBeenReset) {
			resetGame();

		}

		Input input = gc.getInput();

		// Makes sure the game stays at the set framerate.
		timePile += delta;
		while (timePile >= msPerFrame) {
			timePile -= msPerFrame;
			timeHandler.laserTick();
			if (player1.isDead()) {
				Music gameOverMusic = new Music("resources/titlemusic.ogg");
				gameOverMusic.play();
				System.out.println(player1.getName() + " died.");
				// TODO Add wait
				gameHasBeenReset = false;
				sbg.enterState(2);

			}
			if (player2.isDead()) {
				Music gameOverMusic = new Music("resources/titlemusic.ogg");
				gameOverMusic.play();
				System.out.println(player2.getName() + " died.");
				// TODO Add wait
				gameHasBeenReset = false;
				sbg.enterState(3);
			}
			handlePlayerPositions();
			checkLaserLife();
			handleInput(gc, input);
			checkRotations(); // TODO
		}
	}

	/**
	 * 
	 */
	private void checkRotations() {
		timeHandler.setPressed(1, player1.getRotation());
		timeHandler.setPressed(2, player2.getRotation());
	}

	/*
	 * TODO make this shit work!
	 */
	private void checkLaserLife() {
		if (timeHandler.timeToFade(1)) {
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[x].length; y++) {
					map[x][y].laserFade(1, timeHandler.fadeAmount(1));
				}
			}
		}
		if (timeHandler.timeToFade(2)) {
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[x].length; y++) {
					map[x][y].laserFade(2, timeHandler.fadeAmount(2));
				}
			}

		}

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
			laserSound.play();
			timeHandler.playerJustShotWithHisOrHerLaser(player);
			laserAlgorithm(Math.round(player.getRotation()),
					Math.round(player.getPosX()), Math.round(player.getPosY()),
					player.getId());
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
				Player hitPlayer = map[posX][posY].getPlayer();
				if (!hitPlayer.isInvulnerable()) {
					hitPlayer.hit(hitPlayer.getId(), rotation, tileDistance);
				}
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
		map[posX][posY].addLaser(lastRotation, rotation, tileDistance, id);
		laserAlgorithm(rotation, posX, posY, id);
	}

	/**
	 * Updates the tile matrix with the players' current positions.
	 * 
	 * @throws SlickException
	 */
	private void handlePlayerPositions() throws SlickException {
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

		if (timeHandler.paralyzeDone(1)) {
			player1.setParalyzed(false, tileDistance);
		}

		if (timeHandler.invulnerableDone(1)) {
			player1.setInvulnerable(false);
		}

		if (timeHandler.paralyzeDone(2)) {
			player2.setParalyzed(false, tileDistance);
		}

		if (timeHandler.invulnerableDone(2)) {
			player2.setInvulnerable(false);
		}

		// TODO remove this
		if (player1.isInvulnerable()) {
			timeHandler.hitTick(1);
		}

		if (player2.isInvulnerable()) {
			timeHandler.hitTick(2);
		}
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
	private void handleInput(GameContainer gc, Input input)
			throws SlickException {
		// Player 1:
		// The following methods handle the first player's input.

		int player1X = Math.round(player1.getPosX());
		int player1Y = Math.round(player1.getPosY());

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
			return;
		}

		// Handles the case where the player wants to move west.
		if (input.isKeyDown(Input.KEY_A)) {
			timeHandler.pressTick(1, ROTATION_WEST);
			Tile wantedTile = map[player1X - 1][player1Y];
			if ((timeHandler.pressingDone(1, ROTATION_WEST))
					&& !wantedTile.hasCollision()) {
				player1.move(WEST);
			} else {
				player1.setRotation(ROTATION_WEST);
			}
		} else {
			timeHandler.pressReset(1, ROTATION_WEST);
		}

		if (player1.isWalking(WEST)) {
			player1.walkAnimate(WEST);
		}

		// Handles the case where the player wants to move east.
		if (input.isKeyDown(Input.KEY_D)) {
			timeHandler.pressTick(1, ROTATION_EAST);
			Tile wantedTile = map[player1X + 1][player1Y];
			if (timeHandler.pressingDone(1, ROTATION_EAST)
					&& !wantedTile.hasCollision()) {
				player1.move(EAST);
			} else {
				player1.setRotation(ROTATION_EAST);
			}
		} else {
			timeHandler.pressReset(1, ROTATION_EAST);
		}

		if (player1.isWalking(EAST)) {
			player1.walkAnimate(EAST);
		}

		// Handles the case where the player wants to move north.
		if (input.isKeyDown(Input.KEY_W)) {
			timeHandler.pressTick(1, ROTATION_NORTH);
			Tile wantedTile = map[player1X][player1Y - 1];
			if (timeHandler.pressingDone(1, ROTATION_NORTH)
					&& !wantedTile.hasCollision()) {
				player1.move(NORTH);
			} else {
				player1.setRotation(ROTATION_NORTH);
			}
		} else {
			timeHandler.pressReset(1, ROTATION_NORTH);
		}

		if (player1.isWalking(NORTH)) {
			player1.walkAnimate(NORTH);
		}

		// Handles the case where the player wants to move south.
		if (input.isKeyDown(Input.KEY_S)) {
			timeHandler.pressTick(1, ROTATION_SOUTH);
			Tile wantedTile = map[player1X][player1Y + 1];
			if (timeHandler.pressingDone(1, ROTATION_SOUTH)
					&& !wantedTile.hasCollision()) {
				player1.move(SOUTH);
			} else {
				player1.setRotation(ROTATION_SOUTH);
			}
		} else {
			timeHandler.pressReset(1, ROTATION_SOUTH);
		}

		if (player1.isWalking(SOUTH)) {
			player1.walkAnimate(SOUTH);
		}

		// Handles the case where the player wants to change the orientation of
		// a tile
		if (input.isKeyDown(Input.KEY_LSHIFT)) {
			Tile tileToCheck = map[player1X][player1Y];
			if (tileToCheck.hasMirror()) {
				if (!player1.getKeyPressed(CHANGE_MIRROR)) { // TODO
					Mirror mirrorToChange = tileToCheck.getMirror();
					mirrorToChange.changeOrientation();
				}
			}
			player1.setKeyPressed(CHANGE_MIRROR, true);
		} else {
			player1.setKeyPressed(CHANGE_MIRROR, false);
		}

		// Handles the case where the player wants to shoot.
		if (input.isKeyDown(Input.KEY_LCONTROL)) {
			if (!player1.aldreadyWalking() && !player1.isParalyzed()) {
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
			timeHandler.pressTick(2, ROTATION_WEST);
			if ((timeHandler.pressingDone(2, ROTATION_WEST))
					&& !wantedTile.hasCollision()) {
				player2.move(WEST);
			} else {
				player2.setRotation(ROTATION_WEST);
			}
		} else {
			timeHandler.pressReset(2, ROTATION_WEST);
		}
		if (player2.isWalking(WEST)) {
			player2.walkAnimate(WEST);
		}

		// Handles the case where the player wants to move east.
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			Tile wantedTile = map[player2X + 1][player2Y];
			timeHandler.pressTick(2, ROTATION_EAST);
			if ((timeHandler.pressingDone(2, ROTATION_EAST))
					&& !wantedTile.hasCollision()) {
				player2.move(EAST);
			} else {
				player2.setRotation(ROTATION_EAST);
			}
		} else {
			timeHandler.pressReset(2, ROTATION_EAST);
		}
		if (player2.isWalking(EAST)) {
			player2.walkAnimate(EAST);
		}

		// Handles the case where the player wants to move north.
		if (input.isKeyDown(Input.KEY_UP)) {
			Tile wantedTile = map[player2X][player2Y - 1];
			timeHandler.pressTick(2, ROTATION_NORTH);
			if (timeHandler.pressingDone(2, ROTATION_NORTH)
					&& !wantedTile.hasCollision()) {
				player2.move(NORTH);
			} else {
				player2.setRotation(ROTATION_NORTH);
			}
		} else {
			timeHandler.pressReset(2, ROTATION_NORTH);
		}

		if (player2.isWalking(NORTH)) {
			player2.walkAnimate(NORTH);
		}

		// Handles the case where the player wants to move south.
		if (input.isKeyDown(Input.KEY_DOWN)) {
			Tile wantedTile = map[player2X][player2Y + 1];
			timeHandler.pressTick(2, ROTATION_SOUTH);
			if (timeHandler.pressingDone(2, ROTATION_SOUTH)
					&& !wantedTile.hasCollision()) {
				player2.move(SOUTH);
			} else {
				player2.setRotation(ROTATION_SOUTH);
			}
		} else {
			timeHandler.pressReset(2, ROTATION_SOUTH);
		}

		if (player2.isWalking(SOUTH)) {
			player2.walkAnimate(SOUTH);
		}
		// Handles the case where the player wants to change the orientation
		// of
		// a tile

		if (input.isKeyDown(Input.KEY_RSHIFT)
				|| input.isKeyDown(Input.KEY_MINUS)) {
			Tile tileToCheck = map[player2X][player2Y];
			if (tileToCheck.hasMirror()) {
				if (!player2.getKeyPressed(CHANGE_MIRROR)) {
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
			if (!player2.aldreadyWalking() && !player2.isParalyzed()) {
				handleLaser(player2);
			}
		}
	}
}
