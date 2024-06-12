package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.resources.EffectScientificSymbol;
import io.github.oliviercailloux.boardbusters.resources.Resources;

import java.util.List;

/**
 * These structures score victory points depending on your progress in the three scientific fields.
 */
public class CardScientificStructures extends Card {

    private final List<String> chainedCardsNames;

    public CardScientificStructures(String name, int minPlayers, Multiset<Resources> cost, int age, EffectScientificSymbol effect, List<String> chainedCardsNames){
        super(name, minPlayers, cost, age, effect, CardType.SCIENTIFIC_STRUCTURES);
        this.chainedCardsNames = chainedCardsNames;
    }

    @Override
    public EffectScientificSymbol getEffect(){
        return (EffectScientificSymbol) super.getEffect();
    }

    @Override
    public boolean isChainedTo(Card nextCardToBuild){
        return this.chainedCardsNames.contains(nextCardToBuild.getName());
    }
}
