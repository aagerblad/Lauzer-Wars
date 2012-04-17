import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Mirror {
	
	private static final int NORTH_EAST_ORIENTATION = 1;
	private static final int NORTH_WEST_ORIENTATION = -1;
	private Image image = null;
	private int orientation = 0; 
	
	public Mirror(int orientation) throws SlickException {
		switch (orientation) {
		case NORTH_EAST_ORIENTATION:
			this.orientation = orientation;
			image = new Image("src/resource/Mirror NE.png");
			break;
			
		case NORTH_WEST_ORIENTATION:
			this.orientation = orientation;
			image = new Image("src/resource/Mirroe NE.png");
			image.rotate(90);
			break;

		default:
			System.err.println("Illegal mirror orientation");
			break;
		}
	}
	
	public int getOrientation() {
		return orientation;
	}
	

}
