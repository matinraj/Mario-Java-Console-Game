package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;

public class AttackBehaviour implements Behaviour{


    /**
     * this method checks for if target is not null and is HOSTILE_TO_ENEMY, then it will instantiate a new
     * AttackAction unique to both Koopa and Goomba.
     * By doing so, it requires the Exit class to get a location and its corresponding target's location
     * in order to perform the attack.
     * attack stats are all initialised under the attacker's class
     * as Intrinsic weapon since Attack action requires a weapon.
     *
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return Attack action
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        Location loc = map.locationOf(actor);

        for (Exit ignored: loc.getExits()){
            Actor target = ignored.getDestination().getActor();
            if (target != null && target.hasCapability(Status.HOSTILE_TO_ENEMY)){
                actor.addCapability(Status.HURT);
                return new AttackAction(target, ignored.getName());
            }
        }


        return null;
    }
}
