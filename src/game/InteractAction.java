package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * An Action which enables Player to interact with NPC on the map.
 */
public class InteractAction extends Action {

    /**
     * The Actor that is to be interacted with
     */
    protected Actor target;

    /**
     * Random number generator
     */
    protected Random rand = new Random();

    /**
     * An instance of the Monologue class which stores an array of possible monologues.
     */
    protected Monologue monologues;

    /**
     * Constructor.
     *
     * @param target the Actor to interact with
     */
    public InteractAction(Actor target){
        this.target = target;
        monologues = new Monologue();
    }

    /**
     * Allows the actor to display a random message according to the conditions given.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @see Status#INVINCIBLE
     * @see Status#HIT_SHELL
     * @return A random String from monologues
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        //Copy Monologues to a temporary ArrayList to modify according to the given conditions
        ArrayList<String> tempArray = monologues.getMonologues();
        String monologue = "Toad: ";

        //Check if actor has Power Star Capability
        if(actor.hasCapability(Status.INVINCIBLE)){
            tempArray.remove(3);
            monologue += tempArray.get(rand.nextInt(tempArray.size()));

        } else if(actor.hasCapability(Status.HIT_SHELL)){
            //Check if actor has Wrench
            tempArray.remove(2);
            monologue += tempArray.get(rand.nextInt(tempArray.size()));
        }

        //No condition
        else{
            monologue += tempArray.get(rand.nextInt(tempArray.size()));
        }

        return monologue;
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player interacts with Toad"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " interacts with " + target;
    }
}
