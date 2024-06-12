package io.github.oliviercailloux.boardbusters.effect.guild;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * This class represents the effect of guild decorators.
 * It grants victory points to the player based on the number of build stages of their neighbors' wonders.
 */
public class EffectGuildDecorators extends Effect {

    public EffectGuildDecorators(){
        super(EffectType.GUILD_DECORATORS);
    }

    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        if(player.getWonder().getNumberOfStages() == player.getWonder().getCurrentStage()){
            player.increaseVictoryPoints(7);

        }
    }

    @Override
    public String toString(){
        return "Gaining 1 victory point for each of \nyour neighbours number of build stages";
    }
}
