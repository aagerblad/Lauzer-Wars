package mainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {

	int StateID = -1;
	Image background = null;
	Image pointer = null;
	int pointerPositionY = 350;
	int pointerPositionX = 23;
	int pointerValue = 0;
	Image backgroundComplete = null;
	Image start2pGameOption = null;
	Image start1pGameOption = null;
	Image exitOption = null;
	Image laser = null;
	private boolean laserShot;
	private boolean gameplayStateToStart;
	private int tick;
	private boolean gameToExit;
	private boolean mainMenuReseted;

	public MainMenuState(int stateID) {
		this.StateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("resources/MainMenuBackground.png");
		pointer = new Image("resources/MainMenuPointer.png").getSubImage(0, 0,
				130, 89);
		backgroundComplete = new Image("resources/MainMenuComplete.png");
		start2pGameOption = backgroundComplete.getSubImage(171, 339, 686 - 171,
				412 - 339);
		start1pGameOption = backgroundComplete.getSubImage(171, 408, 708 - 171,
				476 - 408);
		exitOption = backgroundComplete.getSubImage(171, 480, 318 - 171,
				554 - 480);
		laser = new Image("resources/MainMenuLaser.png").getSubImage(117, 297,
				800 - 117, 456 - 297);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		pointer.draw(pointerPositionX, pointerPositionY);
		start2pGameOption.draw(171, 339);
		start1pGameOption.draw(171, 440);
		exitOption.draw(171, 520);
		if (laserShot) {
			laser.draw(120, pointerPositionY - 50);
		}

	}

	private void resetMainMenu() {
		try {

			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mainMenuReseted = true;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (!mainMenuReseted) {
			resetMainMenu();
		}
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_DOWN)
				|| input.isKeyPressed(Input.KEY_S)) {
			if (pointerValue < 2) {
				pointerValue++;
			}
			pointerPosition();
		}

		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
			if (pointerValue > 0) {
				pointerValue--;
			}
			pointerPosition();
		}

		if (input.isKeyPressed(Input.KEY_ENTER)
				|| input.isKeyPressed(Input.KEY_SPACE)) {
			handlePointer(game);
		}

		if (gameplayStateToStart || gameToExit) {
			tick += delta;

			if (tick >= 800) {
				if (gameplayStateToStart) {
					gameplayStateToStart = false;
					laserShot = false;
					game.enterState(1);
				} else if (gameToExit) {
					container.exit();
					return;
				}
			}
		}

	}

	private void pointerPosition() {
		switch (pointerValue) {
		case 0:
			pointerPositionY = 350;
			break;
		case 1:
			pointerPositionY = 440;
			break;
		case 2:
			pointerPositionY = 530;
			break;
		default:
			break;
		}
	}

	private void handlePointer(StateBasedGame game) {
		mainMenuReseted = false;
		laserShot = true;
		switch (pointerValue) {
		case 0:
			gameplayStateToStart = true;
			break;
		case 1:
			break;
		case 2:
			gameToExit = true;
			break;
		default:
			break;
		}

	}

	@Override
	public int getID() {
		return StateID;
	}

}
