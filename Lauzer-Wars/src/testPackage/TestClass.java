package testPackage;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class TestClass extends BasicGame {



	static final int SIZE_X = 800;
	static final int SIZE_Y = 600;
	Image a = null;
	Image s = null;
	Image d = null;
	Image f = null;
	Image g = null;
	Image h = null;
	Image j = null;
	Image k = null;
	Image l = null;
	Image left = null;
	Image right = null;
	Image up = null;
	Image down = null;
	boolean aIsPressed = false;
	private boolean sIsPressed;
	private boolean dIsPressed;
	private boolean fIsPressed;
	private boolean gIsPressed;

	public TestClass() {
		super("Super awesome game");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = null;
		app = new AppGameContainer(new TestClass());

		app.setDisplayMode(SIZE_X, SIZE_Y, false);
		app.start();

	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		if (aIsPressed) {
			a.draw(0, 0);
		}
		if (sIsPressed) {
			s.draw(100, 0);
		}
		if (dIsPressed) {
			d.draw(200, 0);
		}
		if (fIsPressed) {
			f.draw(300, 0);
			
		}
		if (gIsPressed) {
			g.draw(400, 0);
		}

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		a = new Image("src/resource/sprite.png");
		s = new Image("src/resource/sprite.png");
		d = new Image("src/resource/sprite.png");
		f = new Image("src/resource/sprite.png");
		g = new Image("src/resource/sprite.png");
		h = new Image("src/resource/sprite.png");
		j = new Image("src/resource/sprite.png");
		k = new Image("src/resource/sprite.png");
		l = new Image("src/resource/sprite.png");
		left = new Image("src/resource/sprite.png");
		right = new Image("src/resource/sprite.png");
		up = new Image("src/resource/sprite.png");
		down = new Image("src/resource/sprite.png");


	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_A)) {
			aIsPressed = true;
		} else {
			aIsPressed = false;
		}
		
		if (input.isKeyDown(Input.KEY_S)) {
			sIsPressed = true;
		} else {
			sIsPressed = false;
		}
		
		if (input.isKeyDown(Input.KEY_D)) {
			dIsPressed = true;
		} else {
			dIsPressed = false;
		}
		
		if (input.isKeyDown(Input.KEY_F)) {
			fIsPressed = true;
		} else {
			fIsPressed = false;
		}
		
		if (input.isKeyDown(Input.KEY_G)) {
			gIsPressed = true;
		} else {
			gIsPressed = false;
		}
		
		
		

	}



}
