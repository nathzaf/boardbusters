package io.github.oliviercailloux.boardbusters.player;

public class BuyNeed {

    private final Player player;

    private final int coins;

    public BuyNeed(Player player, int coins){
        this.player = player;
        this.coins = coins;
    }

    public Player getPlayer(){
        return player;
    }

    public int getCoins(){
        return coins;
    }
}
