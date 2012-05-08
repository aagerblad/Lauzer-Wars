package mainPackage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Dexter
 * @author Andreas
 * 
 */
public class Player {

	private Image image = null;
	private float scale = 1.0f;
	private float posX = 0;
	private float posY = 0;
	private int MAXLIFE = 1;// TODO
	private int life = MAXLIFE;
	private static final int ROTATION_NORTH = 0;
	private static final int ROTATION_WEST = 270;
	private static final int ROTATION_SOUTH = 180;
	private static final int ROTATION_EAST = 90;
	private static final int NORTH = 0;
	private static final int WEST = 1;
	private static final int SOUTH = 2;
	private static final int EAST = 3;
	private boolean[] isWalking = null;
	private boolean[] isKeyPressed = null;
	private float distance = 0;
	private static final float TILE_DISTANCE = 1;
	private static final float SPEED = 0.075f;
	private String name = null;
	private int id = 0;
	private boolean hasShot;
	private boolean paralyzed;
	private boolean invulnerable;

	// Only floats which equals TILE_DISTANCE/n where n is a integer

	/**
	 * Creates a player-object
	 * 
	 * @param image
	 *            Sprite for character
	 * @param posX
	 *            starting coordinate, x-wise
	 * @param posY
	 *            starting coordinate, y-wise
	 */
	public Player(String name, int id, Image image, float posX, float posY) {
		this.posX = posX;
		this.posY = posY;
		this.image = image;
		this.name = name;
		this.id = id;
		isWalking = new boolean[4];
		isKeyPressed = new boolean[5];
	}

	public void resurrect() {
		life = MAXLIFE;
	}

	public Image getImage() {
		return image;
	}

	public float getPosX() {
		return posX;
	}

	public float getPosY() {
		return posY;
	}

	public float getScale() {
		return scale;
	}

	public float getRotation() {
		return image.getRotation();
	}

	public boolean getKeyPressed(int keyNumber) {
		return isKeyPressed[keyNumber];
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setRotation(float r) {
		if (!paralyzed) {
			image.setRotation(r);
		}
	}

	public void move(int direction) {
		if (!aldreadyWalking() && !paralyzed) {
			switch (direction) {
			case EAST:
				image.setRotation(ROTATION_EAST);
				break;
			case WEST:
				image.setRotation(ROTATION_WEST);
				break;
			case NORTH:
				image.setRotation(ROTATION_NORTH);
				break;
			case SOUTH:
				image.setRotation(ROTATION_SOUTH);
				break;

			default:
				break;
			}
			isWalking[direction] = true;
		}
	}

	public void setKeyPressed(int direction, boolean b) {
		isKeyPressed[direction] = b;
	}

	public boolean isWalking(int direction) {
		return isWalking[direction];
	}

	public void walkAnimate(int direction) {
		if (distance >= TILE_DISTANCE) {
			isWalking[direction] = false;
			distance = 0;
			isKeyPressed[direction] = false;
		} else {
			float distanceLeft = TILE_DISTANCE - distance;
			switch (direction) {
			case NORTH:
				if (walkOverLap()) {
					posY -= distanceLeft;
					break;
				} else {
					posY -= SPEED;
					break;
				}
			case WEST:
				if (walkOverLap()) {
					posX -= distanceLeft;
					break;
				} else {
					posX -= SPEED;
					break;
				}
			case SOUTH:
				if (walkOverLap()) {
					posY += distanceLeft;
					break;
				} else {
					posY += SPEED;
					break;
				}
			case EAST:
				if (walkOverLap()) {
					posX += distanceLeft;
					break;
				} else {
					posX += SPEED;
					break;
				}
			default:
				System.err.println("Invalid direction");
				break;
			}
			distance += SPEED;
		}

	}

	private boolean walkOverLap() {
		return distance + SPEED > TILE_DISTANCE;
	}

	public boolean aldreadyWalking() {
		for (int i = 0; i < isWalking.length; i++) {
			if (isWalking[i] == true) {
				return true;
			}
		}
		return false;
	}

	public void hit(int idOfPlayer, int rotation, float tileDistance)
			throws SlickException {
		paralyzed = true;
		invulnerable = true;
		life--;
		System.out.println(name + ": " + life);
		float lastRotation = image.getRotation();
		switch (idOfPlayer) {
		case 1:
			image = new Image("resources/Character1stopped.png")
					.getScaledCopy(tileDistance / 100);
			image.rotate(lastRotation);
			image.setAlpha(0.4f);
			break;
		case 2:
			image = new Image("resources/Character2stopped.png")
					.getScaledCopy(tileDistance / 100);
			image.rotate(lastRotation);
			image.setAlpha(0.4f);
			break;

		default:
			break;
		}
	}

	public void setParalyzed(boolean b, float tileDistance)
			throws SlickException {
		paralyzed = b;
		float lastRotation = image.getRotation();
		if (b == false) {
			switch (id) {
			case 1:
				image = new Image("resources/Character1.png")
						.getScaledCopy(tileDistance / 100);
				image.rotate(lastRotation);
				image.setAlpha(0.4f);
				break;
			case 2:
				image = new Image("resources/Character2.png")
						.getScaledCopy(tileDistance / 100);
				image.rotate(lastRotation);
				image.setAlpha(0.4f);
				break;
			default:
				break;
			}
		}
	}

	public void setInvulnerable(boolean b) {
		invulnerable = b;
		if (b == false) {
			image.setAlpha(1);
		}
	}

	public boolean hasShot() {
		return hasShot;
	}

	public boolean isParalyzed() {
		return paralyzed;
	}

	public void setShot(boolean b) {
		hasShot = b;
	}

	public boolean isInvulnerable() {
		return invulnerable;
	}

	public boolean isDead() {
		if (life <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void setPosition(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

}
