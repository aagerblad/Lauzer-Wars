import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Laser {

	private Image image = null;
	private static final int ROTATION_NORTH = 0;
	private static final int ROTATION_WEST = 270;
	private static final int ROTATION_SOUTH = 180;
	private static final int ROTATION_EAST = 90;

	/**
	 * Iniitiate a laser object.
	 * 
	 * @param direction
	 *            the direction the laser is traveling in.
	 * @param tileDistance
	 * @throws SlickException
	 */
	public Laser(int direction, float tileDistance) throws SlickException {
		image = new Image("src/resource/Laser.png")
				.getScaledCopy(tileDistance / 100);
		image.rotate(90); // TODO issue with sprite
		image.rotate(direction);
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
	public void setRotated(int rotation, float tileDistance)
			throws SlickException {
		image = new Image("src/resource/rotatedlaser.png")
				.getScaledCopy(tileDistance / 100);
	}
}
