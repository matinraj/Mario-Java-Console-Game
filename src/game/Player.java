package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.displays.Menu;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Resettable  {

	/**
	 * new wallet
	 */
	private Wallet wallet = new Wallet();

	/**
	 * menu display
	 */
	private final Menu menu = new Menu();

	/**
	 * ticker for time-sensitive items
	 */
	private int fade = 0;

	/**
	 * lifespan of power star
	 */
	private final int LIFE = 10;

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 * @see Status#HOSTILE_TO_ENEMY
	 * @see Status#RESETTABLE

	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.addCapability(Status.RESETTABLE);
		registerInstance();

	}

	/**
	 * Player's turn
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 *
	 * @see Status#RESETTABLE
	 * @see Status#RESET_ITEM
	 * @see Status#INVINCIBLE
	 * @see Status#TALL
	 * @see Status#SUPER
	 * @see Status#HURT
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		// Add Reset Action to player's action list
		if (this.hasCapability(Status.RESETTABLE))
			actions.add(new ResetAction(this));

		// Actual implementation to Reset Item
		if(this.hasCapability(Status.RESET_ITEM)) {
			this.heal(this.getMaxHp());

			if (this.hasCapability(Status.INVINCIBLE)) {
				this.removeCapability(Status.TALL);
				this.removeCapability(Status.INVINCIBLE);
			}
			else if (this.hasCapability(Status.SUPER)) {
				this.removeCapability(Status.TALL);
				this.removeCapability(Status.SUPER);
			}

		}
		else {
			if (this.hasCapability(Status.INVINCIBLE)) {
				String remaining = Integer.toString(LIFE - fade);
				display.println(this.name + " is INVINCIBLE!" + " - " + remaining + " turns remaining");
				fade++;
				this.addCapability(Status.TALL);
				if (fade == LIFE) {
					this.removeCapability(Status.INVINCIBLE);
					this.removeCapability(Status.TALL);
					fade = 0;
				}
				//map.locationOf(this).
			}

			if (this.hasCapability(Status.HURT)) {
				this.removeCapability(Status.TALL);
				this.removeCapability(Status.SUPER);
			}
		}


		// Print player's hitpoints on console
		String pointsMeter = printHp();
		//String x = Integer.toString(map.locationOf(this).x());
		//String y = Integer.toString(map.locationOf(this).y());
		//display.println(this.name + pointsMeter + " at (" + x +"," + y + ")");

		// print player's wallet balance on console
		display.println(this.wallet + "");

		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	/**
	 * Get display character of player
	 *
	 * @return display character
	 */
	@Override
	public char getDisplayChar(){
		return this.hasCapability(Status.TALL) ? Character.toUpperCase(super.getDisplayChar()): super.getDisplayChar();
	}

	/**
	 * Get wallet
	 *
	 * @return wallet
	 */
	public Wallet getWallet(){
		return wallet;
	}

	/**
	 * Add capability to this item so that it can be reset when ResetAction is called by Player.
	 * @see Resettable#resetInstance()
	 * @see Status#RESET_ITEM
	 */
	public void resetInstance() {
		this.addCapability(Status.RESET_ITEM);
	}

	/**
	 * Get wallet balance of player
	 * @return wallet value
	 */
	public int getBalance() {
		return wallet.getMoney();
	}
}
