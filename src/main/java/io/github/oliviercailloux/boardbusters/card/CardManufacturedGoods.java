package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectProductResources;
import io.github.oliviercailloux.boardbusters.resources.Resources;

/**
 * These structures produce manufactured resources (glass, textile, papyrus).
 */
public class CardManufacturedGoods extends Card {

    public CardManufacturedGoods(String name, int minPlayers, Multiset<Resources> cost, int age, EffectProductResources effect){
        super(name, minPlayers, cost, age, effect, CardType.MANUFACTURED_GOODS);
    }

    @Override
    public EffectProductResources getEffect(){
        return (EffectProductResources) super.getEffect();
    }
}
