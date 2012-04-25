package mainPackage;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Mirror {

	private static final int NORTH_EAST_ORIENTATION = 0;
	private static final int NORTH_WEST_ORIENTATION = 1;
	private Image image = null;
	private int orientation = 0;

	/**
	 * Initialize the mirror object.
	 * 
	 * @param orientation
	 * @param tileDistance
	 * @throws SlickException
	 */
	public Mirror(int orientation, float tileDistance) throws SlickException {
		switch (orientation) {
		case NORTH_EAST_ORIENTATION:
			this.orientation = orientation;
			image = new Image("src/resource/Mirror NE.png")
					.getScaledCopy(tileDistance / 100); // TODO
			break;

		case NORTH_WEST_ORIENTATION:
			this.orientation = orientation;
			image = new Image("src/resource/Mirror NE.png")
					.getScaledCopy(tileDistance / 100); // TODO
			image.rotate(90);
			break;

		default:
			System.err.println("Illegal mirror orientation");
			break;
		}
	}

	/**
	 * @return Orientation of mirror, 0 if NE, 1 if NW
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * @return The image representing the mirror.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * Change the orientation of the mirror to NE if NW, and to NW if NE.
	 */
	public void changeOrientation() {
		if (orientation == NORTH_EAST_ORIENTATION) {
			orientation = NORTH_WEST_ORIENTATION;
		} else {// orientation == NORTH_WEST_ORIENTATION
			orientation = NORTH_EAST_ORIENTATION;
		}
		image.rotate(90);
	}

}
