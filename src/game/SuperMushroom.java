package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * SuperMushroom Class
 */
public class SuperMushroom extends Item {

    /**
     * Price for SuperMushroom item
     */
    private final int PRICE;

    /**
     * Constructor for SuperMushroom
     */
    public SuperMushroom() {

        super("Super Mushroom", '^', false);
        this.addAction(new ConsumeAction(this));
        this.addCapability(Status.MUSH);
        this.PRICE = 400;
    }

    /**
     * Inform item in actor's inventory the passage of time.
     *
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation,actor);
        if (isConsumed()) {
            actor.removeItemFromInventory(this);
        }
        if (actor.hasCapability(Status.HURT)){
            actor.removeCapability(Status.TALL);
            actor.removeCapability(Status.SUPER);
        }
    }

    /**
     * Method to check if item has been consumed using item's status
     * @return True is item has been consumed
     */
    public boolean isConsumed() {
        return hasCapability(Status.CONSUMED);
    }

    /**
     * Method to get item price
     * @return price value
     */
    public int getPrice() {
        return PRICE;
    }

}
