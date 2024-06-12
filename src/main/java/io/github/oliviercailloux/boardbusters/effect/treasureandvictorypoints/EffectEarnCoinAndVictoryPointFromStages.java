package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * Effect that make the player earning coins (and victory points at the end of the game)
 * according to the number of stages he has built
 */
public class EffectEarnCoinAndVictoryPointFromStages extends Effect {

    private final int victoryPointsForEach;

    private final int coinsForEach;

    public EffectEarnCoinAndVictoryPointFromStages(int victoryPointsForEach, int coinsForEach){
        super(EffectType.EARN_COIN_AND_VICTORY_POINT_FROM_STAGES);
        if(victoryPointsForEach < 0 || coinsForEach < 0)
            throw new IllegalArgumentException("Amounts to earn must be positive");
        this.victoryPointsForEach = victoryPointsForEach;
        this.coinsForEach = coinsForEach;
    }

    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        int coinsGains = coinsForEach * player.getWonder().getCurrentStage();
        player.increaseTreasure(coinsGains);
    }

    public void applyEffectForVictoryPoints(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        int victoryPointsGains = victoryPointsForEach * player.getWonder().getCurrentStage();
        player.increaseVictoryPoints(victoryPointsGains);
    }

    @Override
    public String toString(){
        return "Earn " + coinsForEach + " coins for each stage you have built.\nEarn as well "
                + victoryPointsForEach + " victory points\nfor each at the end of the game";
    }
}
