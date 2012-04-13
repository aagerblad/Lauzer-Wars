import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;



public class Main extends BasicGame {	
	
	Image land = null;
	Image plane = null;
	Image sprite = null;
	float positionX = 0;
	float positionY = 0;
	float scale = 1.0f;
	
	public Main() {
		super("Super awesome game");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Main());
		
		app.setDisplayMode(800, 600, false);
		app.start();
		
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		plane.draw(positionX, positionY, scale);
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		land = new Image("src/resource/link.jpg");
		plane = new Image("src/resource/sprite.png");
		
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		 
        if(input.isKeyDown(Input.KEY_A))
        {
            plane.rotate(-0.2f * delta);
        }
 
        if(input.isKeyDown(Input.KEY_D))
        {
            plane.rotate(0.2f * delta);
        }
 
        if(input.isKeyDown(Input.KEY_W))
        {
            float hip = 0.4f * delta;
 
            float rotation = plane.getRotation();
 
            positionX+= hip * Math.sin(Math.toRadians(rotation));
            positionY-= hip * Math.cos(Math.toRadians(rotation));
        }
 
        if(input.isKeyDown(Input.KEY_2))
        {
            scale += (scale >= 5.0f) ? 0 : 0.1f;
            plane.setCenterOfRotation(plane.getWidth()/2.0f*scale, plane.getHeight()/2.0f*scale);
        }
        if(input.isKeyDown(Input.KEY_1))
        {
            scale -= (scale <= 1.0f) ? 0 : 0.1f;
            plane.setCenterOfRotation(plane.getWidth()/2.0f*scale, plane.getHeight()/2.0f*scale);
        }
		
	}
}
