package game;


/**
 * A wallet system to keep track of amount of coins player has.
 */

public class Wallet {

    /**
     * Balance in wallet
     */
    private int money;

    /**
     * Constructor.
     */
    public Wallet(){
        money = 0;
    }

    /**
     * Add to balance of wallet according to value of Coin picked up.
     *
     * @param coin Coin picked up from ground
     */
    public void addToWallet(Coin coin){
        money += coin.getValue();
    }

    /**
     * Deduct from balance of wallet.
     *
     * @param value Amount to be deducted
     */
    public void removeFromWallet(int value){
        money -= value;
    }

    /**
     * A brief description of the wallet to show the balance.
     *
     * @return A String displaying the balance
     */
    public String toString(){
        return "Wallet Balance: " + money;
    }

    public int getMoney() {
        return money;
    }
}
