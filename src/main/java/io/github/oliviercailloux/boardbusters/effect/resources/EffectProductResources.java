package io.github.oliviercailloux.boardbusters.effect.resources;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.effect.EffectType;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.resources.Resources;

public class EffectProductResources extends Effect {

    private final Multiset<Resources> producedResources;

    /**
     * This boolean say if this effect is for a card or a wonder. It affects the behaviour of applyCardEffect
     */
    private final boolean isCardEffect;

    /**
     * Constructor of an effect that increase the resources of the player by the quantity
     *
     * @param producedResources a map of the resources to increase (Resources) by the quantity (Integer)
     * @throws IllegalArgumentException if the one of the resources is negative
     */
    public EffectProductResources(Multiset<Resources> producedResources, boolean isCardEffect){
        super(EffectType.PRODUCT_RESOURCES);
        if(producedResources.isEmpty())
            throw new IllegalArgumentException("There must be at least one quantity");
        this.producedResources = producedResources;
        this.isCardEffect = isCardEffect;
    }

    /**
     * Apply the Product Resources effect to the designated player
     *
     * @param player the player who will earn coins
     * @throws IllegalArgumentException if the player is null
     */
    @Override
    public void applyEffect(Player player){
        if(player == null)
            throw new IllegalArgumentException("The player must not be null.");
        if(isCardEffect) player.getCardResources().addAll(producedResources);
        else player.getResources().addAll(producedResources);
    }

    public Multiset<Resources> getResources(){
        return this.producedResources;
    }

    @Override
    public String toString(){
        StringBuilder resourcesString = new StringBuilder("Produces: \n");
        for(Resources resource : producedResources.elementSet()){
            resourcesString.append(resource.name())
                    .append(": ")
                    .append(producedResources.count(resource))
                    .append("\n");
        }

        return resourcesString.toString();
    }
}
