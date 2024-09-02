package game;

import java.util.ArrayList;

/**
 * A Monologue Class to store all the possible monologues by Toad.
 */
public class Monologue {

    /**
     * An array of possible sentences.
     */
    private ArrayList<String> monologues;

    /**
     * Constructor.
     */
    public Monologue() {
        monologues = new ArrayList<>();
        monologues.add("The princess is depending on you! You are our only hope.");
        monologues.add("Being imprisoned in these walls can drive a fungus crazy :(");
        monologues.add("You might need a wrench to smash Koopa's hard shells.");
        monologues.add("You better get back to finding the Power Stars.");
    }

    /**
     * To get the array of monologues.
     *
     * @return an array list of monologues
     */
    public ArrayList<String> getMonologues(){
        return monologues;
    }

}

