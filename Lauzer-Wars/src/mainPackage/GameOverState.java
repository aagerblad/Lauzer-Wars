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
	float pointerPosValueY = 0;
	float pointerPosValueX = 0;
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
			background = new Image("resources/GameOverPlayer2Options.png");
			pointer = new Image("resources/Character1.png");
			break;
		case 3:
			background = new Image("resources/GameOverPlayer1Options.png");
			pointer = new Image("resources/Character2.png");
			break;
		default:
			break;
		}
		pointer.setRotation(90);
		handlePointer(0);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		pointer.draw(pointerPosValueX, pointerPosValueY);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_DOWN)
				|| input.isKeyPressed(Input.KEY_S)) {
			handlePointer(1);
		}

		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
			handlePointer(0);
		}

		if (input.isKeyPressed(Input.KEY_SPACE)
				|| input.isKeyPressed(Input.KEY_ENTER)) {


			handleValue(game);
		}

	}

	private void handleValue(StateBasedGame game) throws SlickException {
		switch (pointerValue) {
		case 0:
			Music music = new Music("resources/music.ogg");
			music.loop();
			game.enterState(1); // Enter Game loop again
			break;
		case 1:
			game.enterState(0); // Enter Main menu			
			break;
		default:
			break;
		}

	}

	private void handlePointer(int i) {
		pointerValue = i;
		switch (pointerValue) {
		case 0:
			pointerPosValueX = 150;
			pointerPosValueY = 350;
			break;
		case 1:
			pointerPosValueX = 150;
			pointerPosValueY = 440;
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
