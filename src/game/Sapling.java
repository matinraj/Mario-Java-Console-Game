package game;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Location;

/**
 * Sapling Class
 */
public class Sapling extends Tree {

    /**
     * Constructor for Sapling Class
     */
    public Sapling() {
        super('t');
    }


    /**
     * Override to drop coin
     *
     * @param location The location of the Ground object
     */
    @Override
    public void spawn(Location location) {
        //10% chance to drop Coin $20 every round
        int successRate = 10;
        //Check if there is Coin already or not
        if(location.getItems().isEmpty() && rand.nextInt(100)<=successRate){
            location.addItem(new Coin(20));
        }

    }

    /**
     * Override to grow as Mature tree
     *
     * @param location The location of the Ground object
     */
    @Override
    public void grow(Location location) {
        if(age==10){
            //create new Ground - Sapling
            location.setGround(new Mature());
        }
    }

}
