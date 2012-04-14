import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;



public class Main extends BasicGame {	

	Image land = null;
	Image player1 = null;
	Image sprite = null;
	float player1positionX = 0;
	float player1positionY = 0;
	float scale = 1.0f;
	float speed = 100;
	boolean key_Apressed = false;
	boolean key_Wpressed = false;
	boolean key_Spressed = false;
	boolean key_Dpressed = false;




	public Main() {
		super("Super awesome game");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = null;
		app = new AppGameContainer(new Main());

		app.setDisplayMode(800, 600, false);
		app.start();


	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		player1.draw(player1positionX, player1positionY, scale);

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {

		land = new Image("src/resource/link.jpg");
		player1 = new Image("src/resource/sprite.png");

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if(input.isKeyDown(Input.KEY_A))
		{
			//            plane.rotate(-0.2f * delta);
			if (player1positionX > 0 && !key_Apressed) {
				key_Apressed = true;
				if (player1.getRotation()==90) {
					player1positionX-= speed;
				} else {
					player1.setRotation(90);
				}
			}
		} else {
			key_Apressed = false;
		}

		if(input.isKeyDown(Input.KEY_D))
		{
			//            plane.rotate(0.2f * delta);
			if (player1positionX < 800 - player1.getWidth() && !key_Dpressed) {
				key_Dpressed = true;
				if (player1.getRotation()==270) {
					player1positionX+= speed;
				} else {
					player1.setRotation(270);
				}
			} 
		} else {
			key_Dpressed = false;
		}

		if(input.isKeyDown(Input.KEY_W))
		{
		
			if (player1positionY > 0 && !key_Wpressed) {
				key_Wpressed = true;
				if (player1.getRotation()==0) {				
					
					player1positionY-= speed;
				} else {
					
					player1.setRotation(0);
					
				}

			}
		} else {
			key_Wpressed = false;
		}

		if (input.isKeyDown(Input.KEY_S)) {
			if (player1positionY < (600 - player1.getHeight()) && !key_Spressed) {
				key_Spressed = true;
				if (player1.getRotation()==180) {
					player1positionY+= speed;
					
				} else {
					player1.setRotation(180);
				}
			}
		} else {
			key_Spressed = false;
		}

		if(input.isKeyDown(Input.KEY_2))
		{
			scale += (scale >= 5.0f) ? 0 : 0.1f;
			player1.setCenterOfRotation(player1.getWidth()/2.0f*scale, player1.getHeight()/2.0f*scale);
		}
		if(input.isKeyDown(Input.KEY_1))
		{
			scale -= (scale <= 1.0f) ? 0 : 0.1f;
			player1.setCenterOfRotation(player1.getWidth()/2.0f*scale, player1.getHeight()/2.0f*scale);
		}

	}
}
