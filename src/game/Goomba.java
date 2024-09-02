package game;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

import java.util.HashMap;
import java.util.Map;
/**
 * A little fungus guy.
 */
public class Goomba extends Enemy {
	protected Random rand = new Random();
	private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

	/**
	 * Constructor.
	 */
	public Goomba() {
		super("Goomba", 'g', 20, 10, 50);
		this.behaviours.put(10, new WanderBehaviour());
		this.behaviours.put(20, new AttackBehaviour());
	}

	/**
	 * Can be attack by actors who are hostile to enemy.
	 *
	 * @param otherActor the Actor that might perform an action.
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return list of actions
	 * @see Status#HOSTILE_TO_ENEMY
	 */
	@Override
	public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
		ActionList actions = new ActionList();
		// it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
		if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
			actions.add(new AttackAction(this, direction));

		}
		return actions;
	}

	/**
	 * Figure out what to do next.
	 * @see Actor#playTurn(ActionList, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
		//Check if Goomba needs to be reset or not
		if(this.hasCapability(Status.RESET_ITEM) || !(this.isConscious())) {
			map.removeActor(this);
			System.out.println(this + " is dead.");

		} else {
			boolean check = rand.nextInt(100) <= 10;
			//suicide condition
			if (check) {
				map.removeActor(this);
				System.out.println(this + " is dead.");
			}
			else {
				// this section ensures that attackers/enemy can follow player after being attacked
				if (this.hasCapability(Status.HURT)) {
					Location loc = map.locationOf(this);
					for (Exit ignored : loc.getExits()) {
						Actor actor = ignored.getDestination().getActor();
						if (actor != null) {
							this.behaviours.put(10, new FollowBehaviour(actor));
						}
					}
				}

				for (Behaviour Behaviour : behaviours.values()) {
					Action action = Behaviour.getAction(this, map);
					if (action != null) {
						return action;
					}
				}
			}
		}
		return new DoNothingAction();
	}


	/**
	 * Create and returns an intrinsic weapon
	 *
	 * @return new Intrinsic weapon object
	 */
	@Override
	protected IntrinsicWeapon getIntrinsicWeapon() {
		return new IntrinsicWeapon(this.getDamagePoint(), "punch");
	}


}
