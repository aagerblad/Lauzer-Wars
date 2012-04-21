import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Dexter
 * 
 */
public class Pillar {
	private Image image = null;

	/**
	 * Initialize the pillar object.
	 * 
	 * @param tileDistance
	 * @throws SlickException
	 */
	public Pillar(float tileDistance) throws SlickException {
		image = new Image("src/resource/pillar.png")
				.getScaledCopy(tileDistance / 100);
	}

	/**
	 * @return the image representing the pillar.
	 */
	public Image getImage() {
		return image;
	}
}
