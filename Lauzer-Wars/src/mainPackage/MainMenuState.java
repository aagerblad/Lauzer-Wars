package mainPackage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
	private boolean mainMenuReseted = false;
	private Music titleMusic = null;
	private Sound laserSound = null;
	private boolean gameToCredits;
	private boolean gameToHowToPlay;

	public MainMenuState(int stateID) {
		this.StateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		titleMusic = new Music("resources/titlemusic.ogg");
		titleMusic.loop();
		background = new Image("resources/MainMenu.png");
		pointer = new Image("resources/Character1.png");
		pointer.rotate(90);
		laserSound = new Sound("resources/pew.ogg");
		// backgroundComplete = new Image("resources/MainMenuComplete.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0);
		pointer.draw(pointerPositionX, pointerPositionY);
	}

	private void resetMainMenu() throws SlickException {
		
		System.out.println("hej");
		background = new Image("resources/MainMenu.png");
		mainMenuReseted = true;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if (!mainMenuReseted) {
			System.out.println("wat?");
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
			laserSound.play();
			handlePointer(game);
		}

		if (gameplayStateToStart || gameToExit || gameToCredits
				|| gameToHowToPlay) {
			tick += delta;

			if (tick >= 800) {
				tick = 0;
				mainMenuReseted = false;
				if (gameplayStateToStart) {
					gameplayStateToStart = false;
					Music music = new Music("resources/music.ogg");
					music.loop();
					game.enterState(1);
				} else if (gameToExit) {
					container.exit();
					return;
				} else if (gameToCredits) {
					gameToCredits = false;
					game.enterState(4);
				} else if (gameToHowToPlay) {
					// TODO add how to play state
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
			gameToCredits = true;
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
