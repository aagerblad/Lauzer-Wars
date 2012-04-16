import org.newdawn.slick.Image;


public class Player {
	
	private Image image = null;
	private float posX = 0;
	private float posY = 0;
	private static final int ROTATION_NORTH = 0;
	private static final int ROTATION_WEST= 90;
	private static final int ROTATION_SOUTH = 180;
	private static final int ROTATION_EAST= 270;
	private static final int NORTH = 0;
	private static final int WEST= 1;
	private static final int SOUTH = 2;
	private static final int EAST = 3;
	private boolean[] isWalking = null;
	private boolean[] isKeyPressed = null; 
	private float distance = 0;
	private static final float SPEED = 100;
	
	public Player (Image image) {
		this.image = image;
		isWalking = new boolean[4];
		isKeyPressed = new boolean[4];
		
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
	
	public float getRotation() {
		return image.getRotation();
	}
	
	private void setRotation(float r) {
		image.setRotation(r);
	}
	
	public void moveNorth() {
		if (posY > 0) {
			move(NORTH, ROTATION_NORTH);
		}
	}

	public void moveWest() {
		if (posX > 0) {
			move(WEST,ROTATION_WEST);			
		}
	}
	
	public void moveSouth() {
		if (posY < 500) { //TODO
			move(SOUTH,ROTATION_SOUTH);			
		}
	}
	
	public void moveEast() {
		if (posX < 700) { //TODO
			move(EAST, ROTATION_EAST);
		}
	}
	
	private void move(int direction, int wantedRotation) {
		boolean directionPressed = isKeyPressed[direction];
		if (!directionPressed) {
			isKeyPressed[direction]= true;
			if (image.getRotation() == wantedRotation) {
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
		if (distance == SPEED) {
			isWalking[direction] = false;
			distance = 0;
			isKeyPressed[direction] = false;
		} else {
			switch (direction) {
			case NORTH:
				posY--;
				break;
			case WEST:
				posX--;
				break;
			case SOUTH:
				posY++;
				break;
			case EAST:
				posX++;
				break;
			default:
				System.err.println("N�got gick feeeeel!!!!"); //Should this be here? //TODO
				break;
			}
			distance += 1;
		}
		
	}
	

}
