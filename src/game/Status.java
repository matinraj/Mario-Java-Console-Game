package game;

/**
 * Use this enum class to give `buff` or `debuff`.
 * It is also useful to give a `state` to abilities or actions that can be attached-detached.
 */
public enum Status {
    HOSTILE_TO_ENEMY, // use this status to be considered hostile towards enemy (e.g., to be attacked by enemy)
    TALL, // use this status to tell that current instance has "grown".
    DORMANT, // use this status only for Koopa
    BREAK, // when wrench breaks shell
    TURTLE, // to indicate enemy with shell

    SUPER, // Indicate Super Mushroom
    INVINCIBLE, //Indicate Power Star
    CONSUMED,  //Indicate that item has been consumed
    HURT, //Indicate if actor has been hurt
    POWER,
    MUSH,
    HIT_SHELL, // Indicate that Player has Wrench in inventory

    RESETTABLE, // Indicate that Player has not executed the Reset Action
    RESET_ITEM, // Indicate that Reset Action has been called and Item should be reset

    FERTILE, // use this status for Fertile grounds
}
