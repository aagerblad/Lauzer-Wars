package mainPackage;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class LauzerWarsGame extends StateBasedGame implements Runnable {

	static final int SIZE_X = 816;
	static final int SIZE_Y = SIZE_X * 6 / 8;
	public static final int MAINMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;
	public static final int GAMEOVERSTATEP1 = 2;
	public static final int GAMEOVERSTATEP2 = 3;
	public static final int CREDITSSTATE = 4;
	public static final int HOWTOPLAYSTATE = 5;
	

	public LauzerWarsGame() throws SlickException {
		this("Lauzer wars");
	}

	public LauzerWarsGame(String name) throws SlickException {
		super(name);
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE, SIZE_X));
		this.addState(new GameOverState(GAMEOVERSTATEP1));
		this.addState(new GameOverState(GAMEOVERSTATEP2));
		this.addState(new CreditsState(CREDITSSTATE));
		this.addState(new HowToPlayState(HOWTOPLAYSTATE));
		this.enterState(MAINMENUSTATE);
	}

	public static void main(String[] args) throws SlickException {
		LauzerWarsGame main = new LauzerWarsGame("Lauzer Wars");
		main.run();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		AppGameContainer app = null;
		try {
			app = new AppGameContainer(this);

			app.setDisplayMode(SIZE_X, SIZE_Y, false);
			app.setIcon("resources/Character1.png"); // TODO
			// http://slick.javaunlimited.net/viewtopic.php?p=19642
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
