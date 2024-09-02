package game;

import edu.monash.fit2099.engine.positions.Location;

/**
 * Sprout Class
 */
public class Sprout extends Tree{

    /**
     * Constructor for sprout class.
     */
    public Sprout() {
        super('+');
    }

    /**
     * Override to spawn goomba
     *
     * @param location The location of the Ground object
     */
    @Override
    public void spawn(Location location) {
        //10% chance to spawn Goomba
        int successRate = 10;
        if(rand.nextInt(100) <= successRate && !location.containsAnActor()){
            //Create Goomba object at this position
            location.addActor(new Goomba());
        }
    }

    /**
     * Override to grow as sapling
     *
     * @param location The location of the Ground object
     */
    @Override
    public void grow(Location location){
        //Check if age==10
        if(age==10){
            //create new Ground - Sapling
            location.setGround(new Sapling());
        }
    }



}
