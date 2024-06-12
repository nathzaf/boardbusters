package io.github.oliviercailloux.boardbusters.effect.military;

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

public class EffectMilitaryMightTest {
    @Test
    void affectMilitaryMightToPlayer(){
        // Create the wonder
        List<WonderStage> rhodesWonderStages = Arrays.asList(
                new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectEarnCoins(6))
        );
        Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesWonderStages);

        // Create the effect and the player
        EffectMilitaryMight effectMilitaryMight = new EffectMilitaryMight(2);
        Player player = new Player("Player 1", rhodes);

        // Apply the effect to the player
        Assertions.assertEquals(0, player.getMilitaryMight());
        effectMilitaryMight.applyEffect(player);
        Assertions.assertEquals(2, player.getMilitaryMight());
    }

    @Test
    void militaryMightNonPositiveConstructor(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EffectMilitaryMight(0));
    }

    @Test
    void applyToNullPlayer(){
        EffectMilitaryMight effectMilitaryMight = new EffectMilitaryMight(1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> effectMilitaryMight.applyEffect(null));
    }
}
