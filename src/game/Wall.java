package game;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Wall class
 */
public class Wall extends Ground {

	/**
	 * Constructor
	 */
	public Wall() {
		super('#');
	}

	/**
	 * If actor and enter the ground
	 *
	 * @param actor the Actor to check
	 * @return boolean value
	 */
	@Override
	public boolean canActorEnter(Actor actor){
		if (actor.hasCapability(Status.INVINCIBLE)){
			return true;
		}
		return false;

	}

	/**
	 * Indicate passage of time for ground
	 * @param location The location of the Ground
	 */
	public void tick(Location location) {
		// If actor has power star, high grounds (trees) are destroyed to dirt drops a coin
		if (location.containsAnActor() &&location.getActor().hasCapability(Status.INVINCIBLE)){
			location.setGround(new Dirt());
			location.addItem(new Coin(5));
		}
	}
	
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}

	/**
	 * Actions allowed on ground object
	 *
	 @see Actor#allowableActions(Actor, String, GameMap)
	 */
	@Override
	public ActionList allowableActions(Actor actor, Location location, String direction){
		ActionList actList = new ActionList();
		Boolean isInvincible = actor.hasCapability(Status.INVINCIBLE);

		if (actor.hasCapability(Status.HOSTILE_TO_ENEMY) && !(location.map().locationOf(actor).equals(location)) &&!isInvincible){
			actList.add(new JumpAction(this, direction));
		}

		return actList;
	}
}
