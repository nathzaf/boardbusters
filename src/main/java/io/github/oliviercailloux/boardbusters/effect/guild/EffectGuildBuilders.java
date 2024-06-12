package io.github.oliviercailloux.boardbusters.effect.guild;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * Effect that make the player earning victory points
 * according to the number of onWhichTypeOfCard his neighbour have
 * Note that it is only according to its neighbour and not himself
 */
public class EffectGuildBuilders extends Effect {

    public EffectGuildBuilders(){
        super(EffectType.GUILD_BUILDERS);
    }

    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        int victoryPointsGains = player.getWonder().getCurrentStage() + player.getLeftNeighbour().getWonder().getCurrentStage() + player.getRightNeighbour().getWonder().getCurrentStage();
        player.increaseVictoryPoints(victoryPointsGains);
    }

    @Override
    public String toString(){
        return "Gaining 1 victory point for each of \nyour neighbours number of build stages";
    }
}
