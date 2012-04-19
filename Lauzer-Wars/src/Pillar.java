import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Dexter
 * 
 */
public class Pillar {
	private Image image = null;

	public Pillar(float tileDistance) throws SlickException {
		image = new Image("src/resource/pillar.png")
				.getScaledCopy(tileDistance / 100);
	}

	public Image getImage() {
		return image;
	}
}
