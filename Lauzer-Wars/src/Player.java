import org.newdawn.slick.Image;

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
	private static final float SPEED = 0.05f;
	private String name = null;
	private int id = 0;
	private boolean hasShot;

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
	
	public String getName(){
		return name;
	}
	
	public int getId() {
		return id;
	}

	private void setRotation(float r) {
		image.setRotation(r);
	}

	public void moveNorth(boolean collision) {
		if (!collision) {
			move(NORTH, ROTATION_NORTH);
		} else {
			setRotation(ROTATION_NORTH);
		}
	}

	public void moveWest(boolean collision) {
		if (!collision) {
			move(WEST, ROTATION_WEST);
		} else {
			setRotation(ROTATION_WEST);
		}
	}

	public void moveSouth(boolean collision) {
		if (!collision) { // TODO
			move(SOUTH, ROTATION_SOUTH);
		} else {
			setRotation(ROTATION_SOUTH);
		}
	}

	public void moveEast(boolean collision) {
		if (!collision) { // TODO
			move(EAST, ROTATION_EAST);
		} else {
			setRotation(ROTATION_EAST);
		}
	}

	private void move(int direction, int wantedRotation) {
		boolean directionPressed = isKeyPressed[direction];
		if (!directionPressed) {
			isKeyPressed[direction] = true;
			if (image.getRotation() == wantedRotation && !aldreadyWalking()) {
				isWalking[direction] = true;
			} else {
				image.setRotation(wantedRotation);
			}
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
			System.out.println(Math.round(posX) + ", " + Math.round(posY));
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

	public void die() {
		System.out.println(name + " got lauzered to death");
		// TODO
		return;
	}

	public boolean hasShot() {
		return hasShot;
	}

	public void setShot(boolean b) {
		hasShot = b;
	}

}
