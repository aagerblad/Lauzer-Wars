package mainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState {

	int stateID = -1;
	int pointerValue = 0;
	float pointerPosValueY = 280;
	Image background = null;
	Image pointer = null;

	public GameOverState(int stateID) throws SlickException {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		switch (stateID) {
		case 2:
			background = new Image("resources/GameOverP2.png");
			break;
		case 3:
			background = new Image("resources/GameOverP1.png");
			break;
		default:
			break;
		}
		pointer = new Image("resources/MainMenuPointer.png");

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		pointer.draw(80, pointerPosValueY);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_DOWN)
				|| input.isKeyPressed(Input.KEY_S)) {
			if (pointerValue == 1) {
				pointerValue = 0;
				pointerPosValueY = 380;
			}
		}

		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
			if (pointerValue == 0) {
				pointerValue = 1;
				pointerPosValueY = 280;
			}
		}

		if (input.isKeyPressed(Input.KEY_SPACE)
				|| input.isKeyPressed(Input.KEY_ENTER)) {
			if (pointerValue == 1) {
				Music music = new Music("resources/music.ogg");
				music.loop();
				game.enterState(1); // Enter Game loop again
			} else if (pointerValue == 0) {
				game.enterState(0); // Enter Main menu
			}
		}

	}

	@Override
	public int getID() {
		return stateID;
	}

}
