package mainPackage;

public class TimeHandler {

	private long player1LaserTime = 0;
	private long player2LaserTime = 0;
	private long player1hitTime = 0;
	private long player2hitTime = 0;
	private boolean player1LaserActivated;
	private boolean player2LaserActivated;

	private static final int PARALYZE_TIME = 300;
	private static final int INVULNERABLE_TIME = 500;
	private static final int LASER_LIFE = 100; // Time in s * 10^-2

	public TimeHandler() {

	}

	public void tick() {
		if (player1LaserActivated) {
			player1LaserTime++;
		}
		if (player2LaserActivated) {
			player2LaserTime++;
		}
	}

	public void hitTick(int idOfPlayer) {
		switch (idOfPlayer) {
		case 1:
			player1hitTime++;
			break;
		case 2:
			player2hitTime++;
		default:
			break;
		}
	}

	public boolean paralyzeDone(int idOfPlayer) {
		switch (idOfPlayer) {
		case 1:
			if (player1hitTime == PARALYZE_TIME) {
				return true;
			} else {
				return false;
			}
		case 2:
			if (player2hitTime == PARALYZE_TIME) {
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}
	}

	public boolean invulnerableDone(int idOfPlayer) {
		switch (idOfPlayer) {
		case 1:
			if (player1hitTime >= INVULNERABLE_TIME) {
				player1hitTime = 0;
				return true;
			} else {
				return false;
			}
		case 2:
			if (player2hitTime >= INVULNERABLE_TIME) {
				player2hitTime = 0;
				return true;
			} else {
				return false;
			}
		default:
			return false;
		}
	}

	private void setLaserActivated(int idOfPlayer, boolean b) {
		switch (idOfPlayer) {
		case 1:
			player1LaserActivated = b;
			break;
		case 2:
			player2LaserActivated = b;
		default:
			break;
		}
	}

	public void playerJustShotWithHisOrHerLaser(Player player) {
		setLaserActivated(player.getId(), true);

	}

	public boolean isLaserDone(int idOfPlayer) {
		switch (idOfPlayer) {
		case 1:
			if (player1LaserTime >= LASER_LIFE) {
				player1LaserTime = 0;
				setLaserActivated(1, false);
				return true;
			} else {
				return false;
			}
		case 2:
			if (player2LaserTime >= LASER_LIFE) {
				player2LaserTime = 0;
				setLaserActivated(2, false);
				return true;

			} else {
				return false;
			}
		default:
			return false;
		}
	}

	public boolean timeToFade(int idOfPlayer) {
		switch (idOfPlayer) {
		case 1:
			if (player1LaserTime >= LASER_LIFE / 3) {
				return true;
			} else {
				return false;
			}
		case 2:
			if (player2LaserTime >= LASER_LIFE / 3) {
				return true;
			} else {
				return false;
			}

		default:
			return false;
		}
	}

	public float fadeAmount(int idOfPlayer) {
		if (idOfPlayer == 1) {
			return (float) (LASER_LIFE - player1LaserTime) / 100;
		} else { // idOfPlayer == 2
			return (float) (LASER_LIFE - player2LaserTime) / 100;

		}

	}

}
