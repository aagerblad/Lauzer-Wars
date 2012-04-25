import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Laser {

	private Image image = null;
	private static final int ROTATION_NORTH = 0;
	private static final int ROTATION_WEST = 270;
	private static final int ROTATION_SOUTH = 180;
	private static final int ROTATION_EAST = 90;
	private int id = 0;

	/**
	 * Iniitiate a laser object.
	 * 
	 * @param direction
	 *            the direction the laser is traveling in.
	 * @param tileDistance
	 * @throws SlickException
	 */
	public Laser(int lastDirection, int direction, float tileDistance, int id)
			throws SlickException {
		this.id = id;
		if (lastDirection == direction) {
			if (id == 1) {
				image = new Image("src/resource/Laser.png")
						.getScaledCopy(tileDistance / 100);
			} else if (id == 2) {
				image = new Image("src/resource/Laser2.png")
						.getScaledCopy(tileDistance / 100);
			}
			image.rotate(90); // TODO issue with sprite
			image.rotate(direction);
		} else {
			if (id == 1) {
				image = new Image("src/resource/rotatedlaser.png")
						.getScaledCopy(tileDistance / 100);
			} else if (id == 2) {
				image = new Image("src/resource/rotatedlaser2.png")
						.getScaledCopy(tileDistance / 100);
			}
			image.rotate(270);
			switch (lastDirection) {
			case ROTATION_NORTH:
				if (direction == ROTATION_WEST) {
					// Do nothing.
				} else if (direction == ROTATION_EAST) {
					image = image.getFlippedCopy(true, true);
				}
				break;
			case ROTATION_WEST:
				if (direction == ROTATION_NORTH) {
					image = image.getFlippedCopy(true, false);
				} else if (direction == ROTATION_SOUTH) {
					image = image.getFlippedCopy(true, true);
				}
			case ROTATION_SOUTH:
				if (direction == ROTATION_WEST) {
					image = image.getFlippedCopy(false, false);
				} else if (direction == ROTATION_EAST) {
					image = image.getFlippedCopy(true, false);
				}
			case ROTATION_EAST:
				if (direction == ROTATION_NORTH) {
					image = image.getFlippedCopy(false, false); // TODO
				} else if (direction == ROTATION_SOUTH) {
					// Do nothing
				}

			default:
				break;
			}
		}
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the image representing the laser.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Changes the image of the laser into one that represents the reflection of
	 * the laser in a mirror.
	 * 
	 * @param rotation
	 * @param tileDistance
	 * @throws SlickException
	 */
	public void setRotated(int lastRotation, int rotation, float tileDistance)
			throws SlickException {
		image = new Image("src/resource/rotatedlaser.png")
				.getScaledCopy(tileDistance / 100);
	}

	/**
	 * Draw the laser at a new alpha value, simulating a fadeout.
	 * 
	 * @param newAlpha
	 */
	public void fade(float newAlpha) {
		image.setAlpha(newAlpha);
	}
}
