package mainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HowToPlayState extends BasicGameState {

	private int stateID = -1;
	private Image currentImage = null;
	private Image controls = null;
	private Image rules = null;
	private int currentScreen = 0;

	public HowToPlayState(int stateID) throws SlickException {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		controls = new Image("resources/HowToPlayControls.png");
		rules = new Image("resources/HowToPlayRules.png");
		currentImage = controls;

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		currentImage.draw(0, 0);

	}

	@Override
	public void update(GameContainer container, final StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			handleScreen(1);
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			handleScreen(0);
		}

		if (input.isKeyPressed(Input.KEY_SPACE)) {
			game.enterState(0); //Enter main menu
		}

	}

	private void handleScreen(int i) {
		currentScreen = i;
		switch (currentScreen) {
		case 0:
			currentImage = controls;
			break;
		case 1:
			currentImage = rules;
			break;
		default:
			break;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}
}

