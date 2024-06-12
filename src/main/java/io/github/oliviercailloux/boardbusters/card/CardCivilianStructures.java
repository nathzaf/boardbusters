package io.github.oliviercailloux.boardbusters.card;

import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints.EffectVictoryPoints;
import io.github.oliviercailloux.boardbusters.resources.Resources;

import java.util.List;

/**
 * These structures score victory points.
 */
public class CardCivilianStructures extends Card {

    private final List<String> chainedCardsNames;

    public CardCivilianStructures(String name, int minPlayers, Multiset<Resources> cost, int age, EffectVictoryPoints effect, List<String> chainedCardsNames){
        super(name, minPlayers, cost, age, effect, CardType.CIVILIAN_STRUCTURES);
        this.chainedCardsNames = chainedCardsNames;
    }

    @Override
    public EffectVictoryPoints getEffect(){
        return (EffectVictoryPoints) super.getEffect();
    }

    @Override
    public boolean isChainedTo(Card nextCardToBuild){
        return this.chainedCardsNames.contains(nextCardToBuild.getName());
    }
}
