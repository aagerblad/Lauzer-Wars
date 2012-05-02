package mainPackage;

public class TimeHandler {

	private long player1LaserTime = 0;
	private long player2LaserTime = 0;
	private long player1hitTime = 0;
	private long player2hitTime = 0;
	private boolean player1LaserActivated;
	private boolean player2LaserActivated;
	private int player1ButtonEastPressed = 0;
	private int player1ButtonWestPressed = 0;
	private int player1ButtonNorthPressed = 0;
	private int player1ButtonSouthPressed = 0;

	private int player2ButtonEastPressed = 0;
	private int player2ButtonWestPressed = 0;
	private int player2ButtonNorthPressed = 0;
	private int player2ButtonSouthPressed = 0;

	private static final int PARALYZE_TIME = 300;
	private static final int INVULNERABLE_TIME = 500;
	private static final int LASER_LIFE = 100; // Time in s * 10^-2
	private static final int PRESS_TIME = 10;

	private static final int ROTATION_NORTH = 0;
	private static final int ROTATION_WEST = 270;
	private static final int ROTATION_SOUTH = 180;
	private static final int ROTATION_EAST = 90;

	public TimeHandler() {

	}

	/**
	 * Updates variables counting how long a laser has been out.
	 */
	public void tick() {
		if (player1LaserActivated) {
			player1LaserTime++;
		}
		if (player2LaserActivated) {
			player2LaserTime++;
		}
	}

	/**
	 * Updates variables counting the time after a player has been hit.
	 * 
	 * @param idOfPlayer
	 */
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

	/**
	 * Updates variables counting how long different buttons have been pressed.
	 * 
	 * @param idOfPlayer
	 * @param rotation
	 */
	public void pressTick(int idOfPlayer, float rotation) {
		if (idOfPlayer == 1) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				player1ButtonEastPressed++;
				break;
			case ROTATION_WEST:
				player1ButtonWestPressed++;
				break;
			case ROTATION_NORTH:
				player1ButtonNorthPressed++;
				break;
			case ROTATION_SOUTH:
				player1ButtonSouthPressed++;

			default:
				break;
			}
		} else if (idOfPlayer == 2) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				player2ButtonEastPressed++;
				break;
			case ROTATION_WEST:
				player2ButtonWestPressed++;
				break;
			case ROTATION_NORTH:
				player2ButtonNorthPressed++;
				break;
			case ROTATION_SOUTH:
				player2ButtonSouthPressed++;

			default:
				break;
			}
		}
	}

	/**
	 * Sets the variable connected to the specified rotation to zero.
	 * 
	 * @param idOfPlayer
	 * @param rotation
	 */
	public void pressReset(int idOfPlayer, int rotation) {
		if (idOfPlayer == 1) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				player1ButtonEastPressed = 0;
				break;
			case ROTATION_WEST:
				player1ButtonWestPressed = 0;
				break;
			case ROTATION_NORTH:
				player1ButtonNorthPressed = 0;
				break;
			case ROTATION_SOUTH:
				player1ButtonSouthPressed = 0;

			default:
				break;
			}
		} else if (idOfPlayer == 2) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				player2ButtonEastPressed = 0;
				break;
			case ROTATION_WEST:
				player2ButtonWestPressed = 0;
				break;
			case ROTATION_NORTH:
				player2ButtonNorthPressed = 0;
				break;
			case ROTATION_SOUTH:
				player2ButtonSouthPressed = 0;

			default:
				break;
			}
		}
	}

	/**
	 * Returns true if the button has been pressed long enough to trigger an
	 * action.
	 * 
	 * @param idOfPlayer
	 * @param rotation
	 * @return
	 */
	public boolean pressingDone(int idOfPlayer, float rotation) {
		if (idOfPlayer == 1) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				if (player1ButtonEastPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}
			case ROTATION_WEST:
				if (player1ButtonWestPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}
			case ROTATION_NORTH:
				if (player1ButtonNorthPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}
			case ROTATION_SOUTH:
				if (player1ButtonSouthPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}

			default:
				return false;
			}
		} else if (idOfPlayer == 2) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				if (player2ButtonEastPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}
			case ROTATION_WEST:
				if (player2ButtonWestPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}
			case ROTATION_NORTH:
				if (player2ButtonNorthPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}
			case ROTATION_SOUTH:
				if (player2ButtonSouthPressed >= PRESS_TIME) {
					return true;
				} else {
					return false;
				}

			default:
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Set the variable connected to the specified button to PRESS_TIME,
	 * regardless of how long the button has actually been pressed.
	 * 
	 * @param idOfPlayer
	 * @param rotation
	 */
	public void setPressed(int idOfPlayer, float rotation) {
		if (idOfPlayer == 1) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				if (player1ButtonEastPressed == 0) {
					player1ButtonEastPressed = PRESS_TIME;
				}
				break;
			case ROTATION_WEST:
				if (player1ButtonWestPressed == 0) {
					player1ButtonWestPressed = PRESS_TIME;
				}
				break;
			case ROTATION_NORTH:
				if (player1ButtonNorthPressed == 0) {
					player1ButtonNorthPressed = PRESS_TIME;
				}
				break;
			case ROTATION_SOUTH:
				if (player1ButtonSouthPressed == 0) {
					player1ButtonSouthPressed = PRESS_TIME;
				}
			default:
				break;
			}
		} else if (idOfPlayer == 2) {
			switch ((int) rotation) {
			case ROTATION_EAST:
				if (player2ButtonEastPressed == 0) {
					player2ButtonEastPressed = PRESS_TIME;
				}
				break;
			case ROTATION_WEST:
				if (player2ButtonWestPressed == 0) {
					player2ButtonWestPressed = PRESS_TIME;
				}
				break;
			case ROTATION_NORTH:
				if (player2ButtonNorthPressed == 0) {
					player2ButtonNorthPressed = PRESS_TIME;
				}
				break;
			case ROTATION_SOUTH:
				if (player2ButtonSouthPressed == 0) {
					player2ButtonSouthPressed = PRESS_TIME;
				}
			default:
				break;
			}
		}
	}

	/**
	 * Return true if it is time for the player to stop being paralyzed.
	 * 
	 * @param idOfPlayer
	 * @return
	 */
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

	/**
	 * Return true if it is time for the player to stop being invulnerable.
	 * 
	 * @param idOfPlayer
	 * @return
	 */
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
