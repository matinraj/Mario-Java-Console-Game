package game;

import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.Location;

import java.util.ArrayList;

/**
 * Mature tree class
 */
public class Mature extends Tree {

    /**
     * Constructor of mature class.
     */
    public Mature() {
        super('T');
    }

    /**
     * Override to spawn Koopa
     *
     * @param location The location of the Ground object
     */
    @Override
    public void spawn(Location location) {
        //15% chance to spawn Koopa
        int successRate = 15;

        if(rand.nextInt(100)<=successRate && !location.containsAnActor()){
            //Create Koopa object in this location
            location.addActor(new Koopa());
        }
    }

    /**
     * Override to grow new sprouts on fertile squares
     *
     * @param location The location of the Ground object
     */
    @Override
    public void grow(Location location) {
        //can grow new sprout every 5 turns
        if(age%5==0){
            ArrayList<Location> fertileSquare = new ArrayList<>(); //To randomly choose from
            Location square;

            for(Exit exit: location.getExits()){
                square = exit.getDestination();
                //Check if Ground is an instance of Dirt, which means it is a fertile square
                if(square.getGround().hasCapability(Status.FERTILE))
                    fertileSquare.add(square);
            }

            //Randomly choose fertile square
            //Add condition if there is actor on square
            boolean exitLoop = false;
            while(!exitLoop) {
                square = fertileSquare.get(rand.nextInt(fertileSquare.size()));
                if(!square.containsAnActor()){
                    square.setGround(new Sprout());
                    exitLoop = true;
                }
            }
        }

    }

    /**
     * Method for Mature tree to wither and turn to dirt
     *
     * @param location The location of the Ground object
     */
    public void wither(Location location){
        //20% to wither and become Dirt
        int successRate = 20;
        if(rand.nextInt(100)<=successRate){
            location.setGround(new Dirt());
        }
    }

    /**
     * Passage of time for Mature objects
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location){
        super.tick(location);
        wither(location);
    }


}
