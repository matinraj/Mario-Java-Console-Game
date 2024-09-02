package game;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Coin Class which stores its value.
 */


public class Coin extends Item implements Resettable {

    /**
     * Value of Coin (Can be of any integer e.g. $5, $10...)
     */
    private int value;

    /**
     * Constructor.
     *
     * @param value the currency
     */
    public Coin(int value) {
        super("Coin", '$', true);
        this.value = value;
        registerInstance();
    }

    public int getValue(){
        return value;
    }

    /**
     * Add Coin to wallet and remove from player's inventory
     * @see Item#tick(Location, Actor)
     */
    @Override
    public void tick(Location currentLocation, Actor actor) {
        //Add Coin value to Player's (actor) wallet - Downcast actor to player since only player has wallet system
        Player player = (Player) actor;
        Wallet wallet = player.getWallet();
        wallet.addToWallet(this);

        //Remove Coin from player's inventory
        player.removeItemFromInventory(this);
    }

    /**
     * To remove coin on the ground if ResetAction is called by Player.
     * @see Item#tick(Location)
     * @see Status#RESET_ITEM
     */
    public void tick(Location currentLocation) {
        //Actual Implementation to remove coins from ground
        if(this.hasCapability(Status.RESET_ITEM)){
            currentLocation.removeItem(this);
            this.removeCapability(Status.RESET_ITEM);
        }
    }

    /**
     * Add capability to this item so that it can be reset when ResetAction is called by Player.
     * @see Resettable#resetInstance()
     * @see Status#RESET_ITEM
     */
    @Override
    public void resetInstance() {
        //Remove all Coin objects on the ground - add Capability to Item
        this.addCapability(Status.RESET_ITEM);
    }
}
