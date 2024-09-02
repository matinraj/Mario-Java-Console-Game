package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.GameMap;

import java.util.ArrayList;
import java.util.Random;

/**
 * TradeAction class where actor exchanges money from their wallet for
 * items which will be added to their inventory.
 */
public class TradingAction extends Action {

    /**
     * Actor that intends to trade
     */
    private Actor target;
    /**
     * Item that is being traded
     */
    private Item item;
    /**
     * Price of the item
     */
    private int itemPrice;


    /**
     * Constructor for TradeAction Class
     * @param target the actor trading
     * @param item  the item being traded
     * @param itemPrice  price of item
     */
    public TradingAction(Actor target, Item item, int itemPrice){
        this.target = target;
        this.item = item;
        this.itemPrice = itemPrice;
    }

    /**
     * Method where item's price value will be deducted from actor's wallet
     * if there is sufficient balance. Item will be addded to inventory.
     *
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return menuDescription of the Action done.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        Player player = (Player) actor;
        if (player.getBalance() >= itemPrice){
            player.getWallet().removeFromWallet(itemPrice);
            player.addItemToInventory(item);

        }
        else{
            return "You don't have enough coin!";
        }
        return menuDescription(actor);
    }

    /**
     * A string describing the action suitable for displaying in the UI menu.
     *
     * @param actor The actor performing the action.
     * @return a String, e.g. "Player buys Power Star from Toad ($600)"
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor + " buys " + item + " from " + target + " ($"+itemPrice+")";
    }
}
