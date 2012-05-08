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
	int pointerPositionY = 200;
	int pointerPositionX = 23;
	int pointerValue = 0;
	Image backgroundComplete = null;
	Image start2pGameOption = null;
	Image start1pGameOption = null;
	Image exitOption = null;
	Image laser = null;
	private boolean gameplayStateToStart;
	private int tick;
	private boolean gameToExit;
	private boolean mainMenuReseted;
	private boolean gameToCredits;
	private boolean gameToHowToPlay;

	public MainMenuState(int stateID) {
		this.StateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("resources/MainMenu.png");
		pointer = new Image("resources/Character1.png");
		pointer.rotate(90);
//		backgroundComplete = new Image("resources/MainMenuComplete.png");

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		pointer.draw(pointerPositionX, pointerPositionY);
		

		Input input = container.getInput();
		g.drawString("Mouse x: " + input .getAbsoluteMouseX(), 10, 25);
		g.drawString("Mouse y: " + input.getAbsoluteMouseY(), 10, 40);

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
			if (pointerValue < 3) {
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

		if (gameplayStateToStart || gameToExit || gameToCredits || gameToHowToPlay) {
			tick += delta;
			if (tick >= 800) {
				if (gameplayStateToStart) {
					gameplayStateToStart = false;
					game.enterState(1);
				} else if (gameToExit) {
					container.exit();
					return;
				} else if (gameToCredits) {
					//TODO add credits state
				} else if (gameToHowToPlay) {
					//TODO add how to play state
				}
			}
		}

	}

	private void pointerPosition() {
		switch (pointerValue) {
		case 0:
			pointerPositionY = 200;
			pointerPositionX = 23;
			pointer.setRotation(90);
			break;
		case 1:
			pointerPositionY = 300;
			pointerPositionX = 450;
			pointer.setRotation(270);
			break;
		case 2:
			pointerPositionY = 390;
			pointerPositionX = 23;
			pointer.setRotation(90);
			break;
		case 3:
			pointerPositionY = 470;
			pointerPositionX = 350;
			pointer.setRotation(270);
			break;
		default:
			break;
		}
	}

	private void handlePointer(StateBasedGame game) throws SlickException {
		mainMenuReseted = false;
		switch (pointerValue) {
		case 0:
			background = new Image("resources/MainMenuOption0.png");
			gameplayStateToStart = true;
			break;
		case 1:
			background = new Image("resources/MainMenuOption1.png");
			break;
		case 2:
			background = new Image("resources/MainMenuOption2.png");
			break;
		case 3:
			background = new Image("resources/MainMenuOption3.png");
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
