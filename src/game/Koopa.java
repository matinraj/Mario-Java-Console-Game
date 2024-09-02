package game;

import edu.monash.fit2099.engine.actions.Action;
import edu.monash.fit2099.engine.actions.ActionList;
import edu.monash.fit2099.engine.actions.DoNothingAction;
import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.items.Item;
import edu.monash.fit2099.engine.positions.Exit;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.Location;
import edu.monash.fit2099.engine.weapons.IntrinsicWeapon;

import java.util.HashMap;
import java.util.Map;

public class Koopa extends Enemy {

    /**
     * state count keeps track of which form koopa will take and when it will officially die
     */
    private int stateCount;

    /**
     * priority, behaviour
     */
    private final Map<Integer, Behaviour> behaviours = new HashMap<>(); // priority, behaviour

    /**
     * Constructor for Koopa.
     */
    public Koopa() {
        super("Koopa", 'K', 100, 30, 50);
        this.behaviours.put(10, new WanderBehaviour());

        this.addCapability(Status.TURTLE);
        this.stateCount = 2;
        this.addItemToInventory(new SuperMushroom()); // adding SuperMushroom so that it can drop later
        this.behaviours.put(20, new AttackBehaviour());
    }

    /**
     * Get state count
     * @return statecount
     */
    public int getStateCount() {
        return stateCount;
    }

    /**
     * Set statecount
     * @param stateCount statecount
     */
    public void setStateCount(int stateCount) {
        this.stateCount = stateCount;
    }

    /**
     * Koopas's turn
     *
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction
     *                   with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action
     */
    @Override
    public Action playTurn(ActionList actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(Status.DORMANT)){
            this.removeCapability(Status.HURT);
            this.behaviours.clear();
        }

        //Check if Koopa needs to be reset or not
        if(this.hasCapability(Status.RESET_ITEM)){
            map.removeActor(this);
            System.out.println(this + " is dead.");
        } else {
            /**
             * depending on its state, it can perform certain actions or no actions at all
             * when stateCount is 1, it is in dormant state, it cannot perform any actions
             * so all actions have been cleared, instead returns DoNothingAction()
             * when it dies, at stateCount 0, it should drop all item,
             * also just SuperMushroom, then remove Actor from map
             * if none of the above conditions were met,
             * it should still perform usual things that it can like following or attacking
             */
            if (this.stateCount == 1 || this.hasCapability(Status.DORMANT)) {
                return new DoNothingAction();
            }
            else if ((this.stateCount == 2) && !(this.isConscious())) {
                // to get Koopa into 2nd state when it's hitPoints is reduced to 0
                this.setStateCount(this.stateCount - 1);
                this.addCapability(Status.DORMANT);
                //either change it during end of attack action on this Koopa, or
                this.setDisplayChar('D');
                this.removeCapability(Status.HURT);
                this.behaviours.clear();
                return new DoNothingAction();
            }
            else if (this.hasCapability(Status.BREAK)) {
                ActionList dropActions = new ActionList();
                // drop all items
                for (Item item : this.getInventory())
                    dropActions.add(item.getDropAction(this));
                for (Action drop : dropActions)
                    drop.execute(this, map);

                map.removeActor(this);
                System.out.println(this + "'s shell has been broken.");
            }
            else {
                // this section ensures that attackers/enemy can follow player after being attacked
                if (this.hasCapability(Status.HURT)) {
                    Location loc = map.locationOf(this);
                    for (Exit ignored : loc.getExits()) {
                        Actor actor = ignored.getDestination().getActor();
                        if (actor != null) {
                            this.behaviours.put(10, new FollowBehaviour(actor));
                        }
                    }
                }
            }

            for (Behaviour Behaviour : behaviours.values()) {
                Action action = Behaviour.getAction(this, map);
                if (action != null)
                    return action;
            }
        }

        return new DoNothingAction();

    }


    /**
     * Action that can be carried out on Koopa
     *
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return actionlist
     */
    @Override
    public ActionList allowableActions(Actor otherActor, String direction, GameMap map) {
        ActionList actions = new ActionList();
        // it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
        if((otherActor.hasCapability(Status.HOSTILE_TO_ENEMY) && !(this.hasCapability(Status.DORMANT)))) {
            actions.add(new AttackAction(this, direction));
        }
        else if(otherActor.hasCapability(Status.HIT_SHELL) && this.hasCapability(Status.DORMANT)){
            actions.add(new AttackAction(this, direction));
        }
        return actions;

    }


    /**
     * Create and returns an intrinsic weapon
     *
     * @return new Intrinsic weapon object
     */
    @Override
    protected IntrinsicWeapon getIntrinsicWeapon() {
        return new IntrinsicWeapon(this.getDamagePoint(), "kick");
    }
}
