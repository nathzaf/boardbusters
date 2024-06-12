package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.resources.Resources;

import java.util.List;

/**
 * These structures increase player's military might.
 */
public class CardMilitaryStructures extends Card {

    private final List<String> chainedCardsNames;

    public CardMilitaryStructures(String name, int minPlayers, Multiset<Resources> cost, int age, EffectMilitaryMight effect, List<String> chainedCardsNames){
        super(name, minPlayers, cost, age, effect, CardType.MILITARY_STRUCTURE);
        this.chainedCardsNames = chainedCardsNames;
    }

    @Override
    public EffectMilitaryMight getEffect(){
        return (EffectMilitaryMight) super.getEffect();
    }

    @Override
    public boolean isChainedTo(Card nextCardToBuild){
        return this.chainedCardsNames.contains(nextCardToBuild.getName());
    }
}
