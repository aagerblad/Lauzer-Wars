package mainPackage;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class LauzerWarsGame extends StateBasedGame {
	

	static final int SIZE_X = 800;
	static final int SIZE_Y = SIZE_X * 6 / 8;
	public static final int MAINMENUSTATE          = 0;
    public static final int GAMEPLAYSTATE          = 1;

	public LauzerWarsGame(String name) {
		super(name);
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameplayState(GAMEPLAYSTATE, SIZE_X));
		this.enterState(MAINMENUSTATE);
	}

	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = null;
		app = new AppGameContainer(new LauzerWarsGame("Lauzer Wars"));

		app.setDisplayMode(SIZE_X, SIZE_Y, false);
		app.setIcon("src/resource/Character1.png"); // TODO
													// http://slick.javaunlimited.net/viewtopic.php?p=19642
		app.start();
	}
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		
	}

}
