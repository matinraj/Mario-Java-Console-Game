package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * One-time action for resetting the game.
 */

public class ResetAction extends Action {

    /**
     * The ResetManager instance.
     */
    protected ResetManager resetManager;

    /**
     * Constructor to create an Action that will enable the Actor to reset the game once throughout the entire game.
     *
     * @param actor the Actor that uses this action
     */
    public ResetAction(Actor actor){
        resetManager = ResetManager.getInstance();
    }

    /**
     * Allow the Actor to reset the game.
     *
     * @see Action#execute(Actor, GameMap)
     * @see Status#RESETTABLE
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return an indicator that the game has been reset
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Get ResetManager instance and call run
        resetManager.run();
        actor.removeCapability(Status.RESETTABLE);
        return "Game has been reset!";
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String
     */
    @Override
    public String menuDescription(Actor actor) {
        return "Reset the game";
    }

    /**
     * Overrides the super.hotkey() to not have the hotkey randomly assigned to this Action.
     *
     * @return the hotkey 'r'
     */
    @Override
    public String hotkey(){
        return "r";
    }

}
