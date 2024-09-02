package game;

/**
 * An interface for Items/Actors that are Resettable.
 */

public interface Resettable {
    /**
     * Allows any classes that use this interface to reset abilities, attributes, and/or items.
     * HINT: play around with capability, the actual implementation happens in the tick or playTurn method.
     *
     * Implemented in all classes that implements this interface to enable the items/actors to be reset.
     */
    void resetInstance();

    /**
     * a default interface method that register current instance to the Singleton manager.
     * It allows corresponding class uses to be affected by global reset
     *
     * Used in the Constructors of all classes that implements this interface.
     */
    default void registerInstance(){
        ResetManager.getInstance().appendResetInstance(this);
    }
}
