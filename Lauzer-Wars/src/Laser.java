import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Laser {
	
	private Image image = null;
	private static final int ROTATION_NORTH = 0;
	private static final int ROTATION_WEST = 270;
	private static final int ROTATION_SOUTH = 180;
	private static final int ROTATION_EAST = 90;
	
	
	public Laser(int direction, float tileDistance) throws SlickException {
		image = new Image("src/resource/Laser.png").getScaledCopy(tileDistance/100);
		image.rotate(90); //TODO issue with sprite
		image.rotate(direction);
	}
	
	public Image getImage() {
		return image;
	}
	

}
