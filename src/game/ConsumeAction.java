package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * ConsumeAction Class.
 * Action to allow items to be consumed.
 */
public class ConsumeAction extends Action {

    /**
     * Item that is being consumed
     */
    private Item item;


    /**
     * ConsumeAction class Constructor.
     *
     * @param item the item to consume
     */
    public ConsumeAction(Item item) {
        this.item = item;
    }

    /**
     * Consume item (remove from map and give actor powers)
     *
     * @see Action#execute(Actor, GameMap)
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a suitable description to display in the UI
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.locationOf(actor).removeItem(item);
        if (item.hasCapability(Status.MUSH) && !checkOtherCapabilities(actor)){
            actor.addCapability(Status.TALL);
            item.addCapability(Status.CONSUMED);
            actor.addCapability(Status.SUPER);
            actor.increaseMaxHp(50);

            if (actor.hasCapability(Status.HURT)){ // If actor has sustained damage, remove power
                actor.removeCapability(Status.HURT);
            }
        }

        if (item.hasCapability(Status.POWER) && !checkOtherCapabilities(actor)){
            item.addCapability(Status.CONSUMED); //To indicate that the item has been consumed by actor
            actor.addCapability(Status.TALL);   // To make player display character UpperCase
            actor.addCapability(Status.INVINCIBLE); //To give player 'Invincible' Capabilities

            actor.heal(200);
            if (actor.hasCapability(Status.HURT)){
                actor.removeCapability(Status.HURT);
            }
        }

        return menuDescription(actor);
    }

    public boolean checkOtherCapabilities(Actor actor){
        boolean hasCapability = (actor.hasCapability(Status.INVINCIBLE) || actor.hasCapability(Status.SUPER));
        return hasCapability;
    }

    /**
     * Method to
     * @return String indicating remaining number of turns for powerStar
     */
    public String turnsRemaining(){
        String str = "";
            if (item.hasCapability(Status.POWER)){
                PowerStar pStar = (PowerStar) item;
                str += " - ";
                str += Integer.toString(pStar.getFade() );
                str += " turns remaining";

            }
        return str;
    }

    /**
     * Describe the action in a format suitable for displaying in the menu.
     *
     * @see Action#menuDescription(Actor)
     * @param actor The actor performing the action.
     * @return a string, e.g. "Player consumes super mushroom"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " consumes " + item + turnsRemaining();
    }
}

