package io.github.oliviercailloux.boardbusters.effect.guild;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

public class EffectGuildScientists extends Effect {

    public EffectGuildScientists(){
        super(EffectType.GUILD_SCIENTISTS);
    }

    /**
     * Ask user what scientific symbol he wants to build
     */
    @Override
    public void applyEffect(Player player){

    }

    @Override
    public String toString(){
        return null;
    }
}
