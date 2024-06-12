package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

public class EffectEarnCoins extends Effect {

    private final int coins;

    /**
     * Constructor of an effect that adds coins to the player
     *
     * @param coins the coins to add to the player's treasure
     * @throws IllegalArgumentException if the amount of coins is not positive
     */
    public EffectEarnCoins(int coins){
        super(EffectType.EARN_COINS);
        if(coins <= 0){
            throw new IllegalArgumentException("Coins must be positive.");
        }
        this.coins = coins;
    }

    /**
     * Apply the "Earn Coins" effect to the designated player
     *
     * @param player the player who will earn coins
     * @throws IllegalArgumentException if the player is null
     */
    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        player.setTreasure(player.getTreasure() + coins);
    }

    @Override
    public String toString(){
        return "Coins increased by " + coins + ".";
    }
}
