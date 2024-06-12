package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

public class EffectVictoryPoints extends Effect {

    private final int victoryPointScored;

    /**
     * Constructor of an effect that increase or decrease the victory points of the player
     *
     * @param victoryPointScored the victory points to increase or decrease,
     *                           can be positive or negative but not zero
     * @throws IllegalArgumentException if the victory points is zero
     */
    public EffectVictoryPoints(int victoryPointScored){
        super(EffectType.VICTORY_POINTS);
        if(victoryPointScored == 0)
            throw new IllegalArgumentException("The victory point can't be zero.");
        this.victoryPointScored = victoryPointScored;
    }

    /**
     * Apply the Victory Points effect to the designated player
     *
     * @param player the player who will earn coins
     * @throws IllegalArgumentException if the player is null
     */
    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        player.setVictoryPoints(Math.max(player.getVictoryPoints() + victoryPointScored, 0));
    }

    /**
     * @return the effect depending on whether the victory points are positive or negative,
     * by taking the absolute value.
     */
    @Override
    public String toString(){
        return "Victory points " + ((victoryPointScored > 0) ? "increased by " : "decreased by ") + Math.abs(victoryPointScored) + ".";
    }

    public int getVictoryPointScored(){
        return victoryPointScored;
    }

}
