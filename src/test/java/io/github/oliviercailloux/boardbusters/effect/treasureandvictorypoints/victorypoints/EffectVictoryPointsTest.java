package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.victorypoints;

import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
import io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure.EffectEarnCoins;
import io.github.oliviercailloux.boardbusters.player.Player;
import io.github.oliviercailloux.boardbusters.wonder.Wonder;
import io.github.oliviercailloux.boardbusters.wonder.WonderStage;
import io.github.oliviercailloux.boardbusters.resources.Resources;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.github.oliviercailloux.boardbusters.resources.Resources.ORE;

public class EffectVictoryPointsTest {
    // Create the wonder
    List<WonderStage> rhodesWonderStages = Arrays.asList(
            new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
            new WonderStage(Resources.of(ORE, ORE), new EffectVictoryPoints(2)),
            new WonderStage(Resources.of(ORE, ORE, ORE, ORE), new EffectEarnCoins(6))
    );
    Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesWonderStages);

    @Test
    void affectPositiveVictoryPointsToPlayer(){
        // Create the effect and the player
        EffectVictoryPoints effectVictoryPoints = new EffectVictoryPoints(3);
        Player player = new Player("Player 1", rhodes);

        // Apply the effect to the player
        Assertions.assertEquals(0, player.getVictoryPoints());
        effectVictoryPoints.applyEffect(player);
        Assertions.assertEquals(3, player.getVictoryPoints());
    }

    @Test
    void affectNegativeVictoryPointsToPlayer(){
        // Create the effect and the player
        EffectVictoryPoints effectVictoryPoints = new EffectVictoryPoints(-5);
        Player player = new Player("Player 1", rhodes);

        // Apply the effect to the player
        Assertions.assertEquals(0, player.getVictoryPoints());
        effectVictoryPoints.applyEffect(player);
        Assertions.assertEquals(0, player.getVictoryPoints());
    }

    @Test
    void affectPositiveAndNegativeVictoryPointsToPlayer(){
        // Create the effect and the player
        EffectVictoryPoints positiveEffect = new EffectVictoryPoints(3);
        EffectVictoryPoints negativeEffect = new EffectVictoryPoints(-6);
        Player player = new Player("Player 1", rhodes);

        // Apply the effect to the player
        Assertions.assertEquals(0, player.getVictoryPoints());
        positiveEffect.applyEffect(player);
        Assertions.assertEquals(3, player.getVictoryPoints());
        negativeEffect.applyEffect(player);
        Assertions.assertEquals(0, player.getVictoryPoints());
    }

    @Test
    void victoryPointsZeroConstructor(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EffectVictoryPoints(0));
    }

    @Test
    void applyToNullPlayer(){
        EffectVictoryPoints effectVictoryPoints = new EffectVictoryPoints(4);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> effectVictoryPoints.applyEffect(null));
    }
}
