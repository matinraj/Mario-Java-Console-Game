package game;

import edu.monash.fit2099.engine.weapons.WeaponItem;

/**
 * Wrench weapon item class
 */
public class Wrench extends WeaponItem {

    /**
     * Price constant for wrench item
     */
    private final int PRICE;

    /**
     * Constructor for Wrench class
     */
    public Wrench() {
        super("wrench", '/', 50, "hits", 80);
        this.PRICE = 200;
        this.addCapability(Status.HIT_SHELL);
    }

    /**
     * Method to get wrench price
     * @return price value
     */
    public int getPrice() {
        return PRICE;
    }
}