package io.github.oliviercailloux.boardbusters.effect.treasureandvictorypoints.treasure;

import io.github.oliviercailloux.boardbusters.effect.military.EffectMilitaryMight;
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

public class EffectEarnCoinsTest {
    @Test
    void earnCoinsToPlayer(){
        // Create the wonder
        List<WonderStage> rhodesWonderStages = Arrays.asList(
                new WonderStage(Resources.of(ORE, ORE), new EffectMilitaryMight(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectVictoryPoints(2)),
                new WonderStage(Resources.of(ORE, ORE, ORE), new EffectEarnCoins(6))
        );
        Wonder rhodes = new Wonder("The Colossus of Rhodes", Resources.of(ORE), rhodesWonderStages);

        // Create the effect and the player
        EffectEarnCoins effectEarnCoins = new EffectEarnCoins(2);
        Player player = new Player("Player 1", rhodes);

        // Apply the effect to the player
        Assertions.assertEquals(3, player.getTreasure());
        effectEarnCoins.applyEffect(player);
        Assertions.assertEquals(5, player.getTreasure());
    }

    @Test
    void earnCoinsNonPositiveCoins(){
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new EffectEarnCoins(0));
    }

    @Test
    void applyToNullPlayer(){
        EffectEarnCoins effectEarnCoins = new EffectEarnCoins(1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> effectEarnCoins.applyEffect(null));
    }
}
