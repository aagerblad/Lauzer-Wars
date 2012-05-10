package mainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CreditsState extends BasicGameState {

	private int stateID = -1;
	private Image background = null;

	public CreditsState(int stateID) throws SlickException {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("resources/credits.png");

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);

	}

	@Override
	public void update(GameContainer container, final StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();

		if (input.isKeyPressed(Input.KEY_SPACE)) {
			game.enterState(0); //Enter main menu
		}

	}

	@Override
	public int getID() {
		return stateID;
	}
}

