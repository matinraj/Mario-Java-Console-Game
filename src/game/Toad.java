package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * A NPC on the game map that is alive, which player can have interactions with.
 */
public class Toad extends Actor {

    /**
     * Constructor.
     */
    public Toad() {
        super("Toad", '0', 0);
    }

    /**
     * Toad does not do anything as it is not a player.
     * 
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the Action to do nothing
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        return new DoNothingAction();
    }

    /**
     * Returns a collection of Actions in which that the otherActor can interact with this entity.
     *
     * @see Actor#allowableActions(Actor, String, GameMap) 
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return A collection of Actions.
     */

    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();

        // Player can interact with Toad
        actions.add(new InteractAction(this));
        actions.add(new TradingAction(this, new SuperMushroom(), new SuperMushroom().getPrice()));
        actions.add(new TradingAction(this, new PowerStar(), new PowerStar().getPrice()));
        actions.add(new TradingAction(this, new Wrench(), new Wrench().getPrice()));

        return actions;
    }
}

