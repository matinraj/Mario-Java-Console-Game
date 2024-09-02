package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.GameMap;

/**
 * Abstract Enemy class
 */
public abstract class Enemy extends Actor implements Resettable {

    /**
     * damage point of enemy
     */
    private int damagePoint;

    /**
     * Probability of hit
     */
    private int hitChance;

    /**
     * Constructor
     * @param name name of enemey
     * @param displayChar char to display on console
     * @param hitPoints hit points
     * @param damagePoint points to inflict damage
     * @param hitChance probability of hit
     */
    public Enemy(String name, char displayChar, int hitPoints, int damagePoint, int hitChance){
        super(name, displayChar, hitPoints);
        this.damagePoint = damagePoint;
        this.hitChance = hitChance;
        registerInstance();
    }

    /**
     * Enemy's turn
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction
     *                   with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return
     */
    public abstract Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display);

    /**
     * Get damage point of enemy
     * @return damage points
     */
    public int getDamagePoint() {
        return damagePoint;
    }

    /**
     * Set damage points for enemy
     * @param damagePoint new damage points
     */
    public void setDamagePoint(int damagePoint) {
        this.damagePoint = damagePoint;
    }

    /**
     * get chance of hit
     *
     * @return probability of hit
     */
    public int getHitChance() {
        return hitChance;
    }

    /**
     * set chance of hit
     *
     * @param hitChance probability of hit
     */
    public void setHitChance(int hitChance) {
        this.hitChance = hitChance;
    }

    /**
     * Add capability to this item so that it can be reset when ResetAction is called by Player.
     * @see Resettable#resetInstance()
     * @see Status#RESET_ITEM
     */
    @Override
    public void resetInstance(){
        //Removes ALL enemies
        this.addCapability(Status.RESET_ITEM);
    }


}
