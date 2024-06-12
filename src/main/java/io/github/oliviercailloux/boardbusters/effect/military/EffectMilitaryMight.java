package io.github.oliviercailloux.boardbusters.effect.military;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;

public class EffectMilitaryMight extends Effect {

    private final int militaryMightIncrease;

    /**
     * Constructor of an effect that adds military might to the player
     *
     * @param militaryMightIncrease the military might to add to the player
     * @throws IllegalArgumentException if the military might is not positive
     */
    public EffectMilitaryMight(int militaryMightIncrease){
        super(EffectType.MILITARY_MIGHT);
        if(militaryMightIncrease <= 0)
            throw new IllegalArgumentException("The military might must be positive.");
        this.militaryMightIncrease = militaryMightIncrease;
    }

    /**
     * Apply the "Military Might" effect to the designated player
     *
     * @param player the player who will earn coins
     * @throws IllegalArgumentException if the player is null
     */
    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        player.setMilitaryMight(player.getMilitaryMight() + militaryMightIncrease);
    }

    @Override
    public String toString(){
        return "Military might increased by " + militaryMightIncrease + ".";
    }

    public int getMilitaryMightIncrease(){
        return militaryMightIncrease;
    }
}
