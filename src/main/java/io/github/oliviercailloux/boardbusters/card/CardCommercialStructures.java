package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.Effect;
import io.github.oliviercailloux.boardbusters.resources.Resources;

import java.util.List;

/**
 * These structures earn coins, produce resources, change commerce rules and sometimes earn victory points.
 */
public class CardCommercialStructures extends Card {

    private final List<String> chainedCardsNames;

    public CardCommercialStructures(String name, int minPlayers, Multiset<Resources> cost, int age, Effect effect, List<String> chainedCardsNames){
        super(name, minPlayers, cost, age, effect, CardType.COMMERCIAL_STRUCTURES);
        this.chainedCardsNames = chainedCardsNames;
    }

    @Override
    public boolean isChainedTo(Card nextCardToBuild){
        return this.chainedCardsNames.contains(nextCardToBuild.getName());
    }
}
