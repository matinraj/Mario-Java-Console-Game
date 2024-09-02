package game;

import java.util.Random;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Action class to enable actors to jump over high grounds
 */
public class JumpAction extends Action{

    /**
     * to know where will actor jump to
     */
    private Ground target;

    /**
     * Path of jump
     */
    private String path;

    /**
     * random value
     */
    private Random rand = new Random();

    /**
     * Constructor
     * @param target location to jump
     * @param path direction
     */
    public JumpAction(Ground target, String path) {
        this.target = target;
        this.path = path;
    }

    /**
     * Execute method for jump logic
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return String indicating result of jump action
     */
    @Override
    public String execute(Actor actor, GameMap map) {

        boolean hasMush = actor.hasCapability(Status.SUPER);

        char symbol = target.getDisplayChar();
        int fall = 0, successRate = 0;
        String promptSuccess = "", promptFail = "";

        if (symbol == '#' || symbol == 't'){
            fall = 20;
            successRate = 80;
            promptSuccess = "Whew!";
            promptFail = "Ouch!";
        }
        else if (symbol == '+'){
            fall = 10;
            successRate = 90;
            promptSuccess = "Nice jump!";
            promptFail = "Ooft...";
        }
        else if (symbol == 'T'){
            fall = 30;
            successRate = 70;
            promptSuccess = "Close one...";
            promptFail = "Argh!";
        }

        if(hasMush){
            map.moveActor(actor, this.getLocationOf(actor, map));
            return promptSuccess += "Easy jump!";
        }
        else{
            if (rand.nextInt(100) <= successRate){
                map.moveActor(actor, this.getLocationOf(actor, map));
                return promptSuccess;
            }
            else{
                actor.hurt(fall);
                return promptFail;
            }
        }
    }

    /**
     * Get destination to move
     *
     * @param actor actor
     * @param map map
     * @return destination
     */
    public Location getLocationOf(Actor actor, GameMap map){
        Location location = map.locationOf(actor);
        for(Exit ignored: location.getExits()){
            if (ignored.getName().equals(this.path)){
                return ignored.getDestination();
            }
        }
        return null;
    }

    /**
     * Returns a description of this movement suitable to display in the menu.
     *
     * @param actor The actor performing the action.
     * @return action description
     */
    @Override
    public String menuDescription(Actor actor) {

        return actor + " is going to jump to " + path;
    }
}
