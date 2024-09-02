package game;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.weapons.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 *
	 * @param target the Actor to attack
	 * @param direction direction to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * Execute method to carry out attack action
	 * on target
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return String of action description
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();
		int damage = weapon.damage();

		// Misses Target
		if (!(rand.nextInt(100) <= weapon.chanceToHit()) && !actor.hasCapability(Status.INVINCIBLE)) {
			return actor + " misses " + target + ".";
		}

		// Hits target
		if (target.hasCapability(Status.INVINCIBLE) || (target.hasCapability(Status.DORMANT) &&
				!(actor.hasCapability(Status.HIT_SHELL)))) {
			target.hurt(0);
			damage = 0;
		}
		else if (actor.hasCapability(Status.INVINCIBLE)){
			target.hurt(Integer.MAX_VALUE);
			target.addCapability(Status.HURT);
			if (target.hasCapability(Status.DORMANT)){
				target.addCapability(Status.BREAK);
			}
		}
		else if(actor.hasCapability(Status.HIT_SHELL) && target.hasCapability(Status.DORMANT)){
			target.addCapability(Status.BREAK);
		}
		else{
			target.hurt(damage);
			target.addCapability(Status.HURT);
		}

		// String for Wrench attack
		String result = "";
		if (actor.hasCapability(Status.HIT_SHELL)){
			result += actor + " hits " + target + " with Wrench.";
		}
		else{
			result += actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		}


		// Check if target is Alive
		if (!(target.isConscious()) && !(target.hasCapability(Status.TURTLE))) {
			ActionList dropActions = new ActionList();
			// drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);
			// remove actor
			map.removeActor(target);
			result += System.lineSeparator() + target + " is killed.";
		}

		// Check if target is broken
		if (target.hasCapability(Status.BREAK)) {
			ActionList dropActions = new ActionList();
			// drop all items
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction(actor));
			for (Action drop : dropActions)
				drop.execute(target, map);
			// remove actor
			map.removeActor(target);
			result += System.lineSeparator() + target + "'s shell is broken.";
		}

		return result;
	}

	/**
	 * Returns a description of this movement suitable to display in the menu.
	 *
	 * @param actor The actor performing the action.
	 * @return a String, e.g. "Player interacts with Toad"
	 */

	@Override
	public String menuDescription(Actor actor) {
		String string = "";
		if (actor.hasCapability(Status.HIT_SHELL) && target.hasCapability(Status.DORMANT)){
			return actor + " attacks " + target + " at " + direction + " with Wrench.";
		}
		else{
			return actor + " attacks " + target + " at " + direction;
		}
	}
}
