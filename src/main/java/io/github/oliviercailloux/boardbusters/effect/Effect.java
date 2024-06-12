package io.github.oliviercailloux.boardbusters.effect;

import io.github.oliviercailloux.boardbusters.player.Player;

/**
 * This class represents an effect that can be applied to a player.
 * Subclasses of this class define specific types of effects.
 */
public abstract class Effect {

    private final EffectType effectType;

    /**
     * Constructs an Effect object with the specified effect type.
     *
     * @param effectType the type of the effect
     */
    public Effect(EffectType effectType){
        this.effectType = effectType;
    }

    public abstract void applyEffect(Player player);

    @Override
    public abstract String toString();

    public EffectType getEffectType(){
        return effectType;
    }
}
