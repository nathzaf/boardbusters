package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectProductResources;
import io.github.oliviercailloux.boardbusters.resources.Resources;

/**
 * These structures produce raw resources (stone, clay, wood, ore).
 */
public class CardRawMaterials extends Card {

    public CardRawMaterials(String name, int minPlayers, Multiset<Resources> cost, int age, EffectProductResources effect){
        super(name, minPlayers, cost, age, effect, CardType.RAW_MATERIALS);
    }

    @Override
    public EffectProductResources getEffect(){
        return (EffectProductResources) super.getEffect();
    }
}
