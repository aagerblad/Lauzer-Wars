import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame {

	static final int SIZE_X = 800;
	static final int SIZE_Y = 600;
	Image land = null;
	Player player1 = null;
	Image sprite = null;
	float player1positionX = 0;
	float player1positionY = 0;
	float scale = 1.0f;
	float speed = 100;
	float distance = 0;
	boolean key_Apressed = false;
	boolean key_Wpressed = false;
	boolean key_Spressed = false;
	boolean key_Dpressed = false;
	boolean walkingA = false;
	boolean walkingD = false;
	boolean walkingW = false;
	boolean walkingS = false;
	private static final int NORTH = 0;
	private static final int WEST= 1;
	private static final int SOUTH = 2;
	private static final int EAST = 3;

	public Game() {
		super("Super awesome game");
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = null;
		app = new AppGameContainer(new Game());

		app.setDisplayMode(SIZE_X, SIZE_Y, false);
		app.start();

	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		player1.getImage().draw(player1.getPosX(), player1.getPosY(), scale);

	}

	@Override
	public void init(GameContainer arg0) throws SlickException {

		land = new Image("src/resource/link.jpg");
		player1 = new Player(new Image("src/resource/sprite.png"));

	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_A)) {
			player1.moveWest();
		} else {
			player1.setKeyPressed(WEST, false);
		}

		if (player1.isWalking(WEST)) {
			player1.walkAnimate(WEST);
		}

		if (input.isKeyDown(Input.KEY_D)) {
			player1.moveEast();
		} else {
			player1.setKeyPressed(EAST, false);
		}
		
		if (player1.isWalking(EAST)) {
			player1.walkAnimate(EAST);
		}

		if (input.isKeyDown(Input.KEY_W)) {
			player1.moveNorth();
		} else {
			player1.setKeyPressed(NORTH, false);
		}
		
		if (player1.isWalking(NORTH)) {
			player1.walkAnimate(NORTH);
		}

		if (input.isKeyDown(Input.KEY_S)) {
			player1.moveSouth();
		} else {
			player1.setKeyPressed(SOUTH, false);
		}
		
		if (player1.isWalking(SOUTH)) {
			player1.walkAnimate(SOUTH);
		}

	}
}
