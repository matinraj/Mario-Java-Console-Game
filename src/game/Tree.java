package game;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Ground;
import edu.monash.fit2099.engine.positions.Location;

import java.util.Random;

/**
 * Abstract Tree class
 */
public abstract class Tree extends Ground implements Resettable {

    /**
     * Variable for random value
     */
    protected Random rand = new Random();

    /**
     * Age of tree class
     */
    protected int age;

    /**
     * Constructor for Tree Class.
     * @param displayChar Character displayed on map
     */
    public Tree(char displayChar) {
        super(displayChar);
        age = 0;
        registerInstance();
    }

    /**
     * Passage of time for Tree objects
     * @param location The location of the Ground
     */
    public void tick(Location location){
        super.tick(location);
        age++;
        spawn(location);
        grow(location);
        // If actor has power star, high grounds (trees) are destroyed to dirt drops a coin
        if (location.containsAnActor() &&location.getActor().hasCapability(Status.INVINCIBLE)){
            location.setGround(new Dirt());
            location.addItem(new Coin(5));
        }

        //Actual Implementation to Reset Item
        if(this.hasCapability(Status.RESET_ITEM)){
            int successRate = 50;  //50% chance to convert back to Dirt
            if(rand.nextInt(100) <= successRate)
                location.setGround(new Dirt());
            this.removeCapability(Status.RESET_ITEM);
        }
    }

    /**
     * Spawn items and enemies
     *
     * @param location The location of the Ground object
     */
    public abstract void spawn(Location location);

    /**
     * Grow tree - Create new Tree object to replace it
     *
     * @param location The location of the Ground object
     */
    public abstract void grow(Location location);

    /**
     * To make Tree resettable
     */
    @Override
    public void resetInstance(){
        this.addCapability(Status.RESET_ITEM);
    }

    /**
     * Check if actor can enter Tree ground
     *
     * @param actor the Actor to check
     * @return True if actor is Invincible else false
     */
    @Override
    public boolean canActorEnter(Actor actor){
        if (actor.hasCapability(Status.INVINCIBLE)){
            return true;
        }
        return false;
    }

    /**
     * List of actions that can be done on the Tree objects
     *
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return list of actions that can be done on the object
     */
    @Override
    public ActionList allowableActions(Actor actor, Location location, String direction){
        ActionList actList = new ActionList();

        Boolean isInvincible = actor.hasCapability(Status.INVINCIBLE);

        if (actor.hasCapability(Status.HOSTILE_TO_ENEMY) && !(location.map().locationOf(actor).equals(location)) && !isInvincible){
            actList.add(new JumpAction(this, direction));
        }

        return actList;
    }
}
