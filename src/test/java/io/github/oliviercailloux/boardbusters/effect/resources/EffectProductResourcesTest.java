package io.github.oliviercailloux.boardbusters.effect.resources;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoins;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints.EffectVictoryPoints;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import io.github.oliviercailloux.boardbusters.wonder.WonderStage;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.github.oliviercailloux.boardbusters.resources.Resources.ORE;
import static io.github.oliviercailloux.boardbusters.resources.Resources.WOOD;

public class EffectProductResourcesTest {
    @Test
    void affectProductResourcesToPlayer(){
        // Create the wonder
        List<WonderStage> rhodesWonderStages = Arrays.asList(
                new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectEarnCoins(6))
        );
        Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesWonderStages);

        // Create the effect and the player
        EffectProductResources effectProductResources =
                new EffectProductResources(Resources.of(ORE, ORE, ORE, ORE, ORE), false);
        Player player = new Player("Player 1", rhodes);

        // Apply the effect to the player
        Assertions.assertEquals(0, player.getResources().count(WOOD));
        Assertions.assertEquals(0, player.getResources().count(Resources.CLAY));
        Assertions.assertEquals(0, player.getResources().count(ORE));
        effectProductResources.applyEffect(player);
        Assertions.assertEquals(0, player.getResources().count(Resources.WOOD));
        Assertions.assertEquals(0, player.getResources().count(Resources.CLAY));
        Assertions.assertEquals(5, player.getResources().count(ORE));
    }

    @Test
    void applyToNullPlayer(){
        EffectProductResources effectProductResources = new EffectProductResources(Resources.of(ORE, ORE, ORE, ORE, ORE), true);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> effectProductResources.applyEffect(null));
    }

    @Test
    void nonPositiveQuantity(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EffectProductResources(HashMultiset.create(), true));
    }

    @Test
    void noPositiveQuantity(){
        Multiset<Resources> resources = HashMultiset.create();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EffectProductResources(resources, true));
    }
}
