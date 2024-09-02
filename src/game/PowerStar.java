package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * PowerStar Class
 */
public class PowerStar extends Item implements Resettable {

    /**
     * Fade counter to keep track of power star's life
     */
    private int fade;
    /**
     * Price of power star
     */
    private final int PRICE;

    /**
     * Constant variable for life duration of power star
     */
    private static final int LIFE = 10;

    /**
     * Constructor for PowerStar
     */
    public PowerStar() {

        super("Power Star", '*', false);
        this.addAction(new ConsumeAction(this));
        this.addCapability(Status.POWER);
        this.fade = LIFE;
        this.PRICE = 600;
        registerInstance();

    }

    public void resetFade(){
        this.fade = LIFE;
    }


        /**
         * Tick for when power star is on the ground
         * @param location Location on map
         */
    public void tick(Location location){
        //Actual Implementation to reset PowerStar's lifeline on the ground
        if(this.hasCapability(Status.RESET_ITEM)) {
            this.resetFade();

        } else {
            if (!isConsumed()) {
                if (fade == 0) {
                    location.removeItem(this);
                }
                fade--;
            }
        }
    }

    /**
     * Tick when actor has consumed Item (inside inventory)
     * @param currentLocation The location of the actor carrying this Item.
     * @param actor The actor carrying this Item.
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        super.tick(currentLocation,actor);

        if (isConsumed()) {
            actor.removeItemFromInventory(this);
        }
            if (fade == 0){
                actor.removeItemFromInventory(this);
            }
        fade--;
    }

    /**
     * Method to get item price
     * @return price value
     */
    public int getPrice() {
        return PRICE;
    }

    /**
     * Method to check if item has been consumed using item's status
     * @return True is item has been consumed
     */
    public boolean isConsumed() {
        return hasCapability(Status.CONSUMED);
    }

    /**
     * Get remaining life of power star
     * @return remaining life count of power star
     */
    public int getFade() {
        return fade;
    }

    @Override
    public void resetInstance() {
        this.addCapability(Status.RESET_ITEM);
    }

}
