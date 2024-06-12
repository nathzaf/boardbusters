package io.github.oliviercailloux.boardbusters.effect.commercial;

import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.resources.Resources;

import java.util.Set;

/**
 * @author pneveu
 * This class represent the effect of commercial cards that reduces the cost resources
 * Indeed, some commercial cards enable to buy ressources to left or right neighbour according to each card
 * at a minimum cost (1 coin)
 */
public class EffectCommercialBenefits extends Effect {

    private final boolean onLeftNeighbour;

    private final boolean onRightNeighbour;

    private final Set<Resources> onWhichResources;

    /**
     * Constructor of an effect that adds coins to the player
     *
     * @throws IllegalArgumentException if the amount of coins is not positive
     */
    public EffectCommercialBenefits(boolean onLeftNeighbour, boolean onRightNeighbour, Set<Resources> onWhichResources){
        super(EffectType.COMMERCIAL_BENEFITS);
        if(onWhichResources == null) throw new NullPointerException();
        if(onWhichResources.isEmpty()) throw new IllegalArgumentException("Resources set can't be empty");
        if(!onLeftNeighbour && !onRightNeighbour)
            throw new IllegalArgumentException("At least one of both neighbours must be true");
        this.onLeftNeighbour = onLeftNeighbour;
        this.onRightNeighbour = onRightNeighbour;
        this.onWhichResources = onWhichResources;
    }

    /**
     * Apply the "Commercial Benefits" effect to the designated players
     *
     * @param player the player who will gain commercial benefits with its neighbours
     * @throws NullPointerException if the player is null
     */
    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new NullPointerException("The player must not be null.");
        player.addCommercialBenefits(onLeftNeighbour, onRightNeighbour, onWhichResources);
    }

    //Later to be ameliorated with name of neighbour by getting their name
    @Override
    public String toString(){
        if(onLeftNeighbour && onRightNeighbour)
            return "Commercial benefits with both neighbour";
        else if(onRightNeighbour)
            return "Commercial benefits with right neighbour";
        else if(onLeftNeighbour)
            return "Commercial benefits with left neighbour";
        else
            return "error";
    }
}
